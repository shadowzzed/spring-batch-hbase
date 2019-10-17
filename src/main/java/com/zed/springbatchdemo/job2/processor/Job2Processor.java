package com.zed.springbatchdemo.job2.processor;

import com.zed.springbatchdemo.hbase.HBaseClient;
import com.zed.springbatchdemo.job2.model.LogData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提取每一行文本信息中的request_id,request_params,response_params,cst_id,date
 * @rowKey cst_id$date$request_id
 * @author Zeluo
 * @date 2019/9/24 10:00
 */
@Slf4j
@Component
public class Job2Processor implements ItemProcessor<String, LogData[]> {

    private HBaseClient hBaseClient;

    @Autowired
    public Job2Processor(HBaseClient hBaseClient) {
        this.hBaseClient = hBaseClient;
    }

    //存储待完善的对象
    public static List<LogData> list_temp = new ArrayList<>(5000);
    //第三种日志解析后的对象存储的地方 因为没有需要匹配的 所以单独存储
    public static List<LogData> list_adjust = new ArrayList<>(5500);
    //存储完善后的对象
    public static List<LogData> list_finish = new ArrayList<>(5500);

    private static final String requestId = "\\w*-\\w*-\\w*-\\w*-\\w*";
    private static final String requestArg = "arg\\[[0-9]*\\]:\\s.*";
    private static final String responseArg = "response params: \\{.*\\}";//response params 后面是json对象格式 注意有空格
    private static final String cstId = "[0-9]{5,}";//5位以上的纯数字
    private static final String date = "[0-9]{4}-[0-9]{2}-[0-9]{2}";// yyyy-MM-dd
    private static final String cstId_adjust = "customerId\":[0-9]{5,}";//adjust类型请求中找出cstId

    private static Pattern pattern_requestId;
    private static Pattern pattern_responseArg;
    private static Pattern pattern_requestArg;
    private static Pattern pattern_cstId;
    private static Pattern pattern_date;
    private static Pattern pattern_cstId_adjust;

    static {
        pattern_requestId = Pattern.compile(requestId);
        pattern_responseArg = Pattern.compile(responseArg);
        pattern_requestArg = Pattern.compile(requestArg);
        pattern_cstId = Pattern.compile(cstId);
        pattern_date = Pattern.compile(date);
        pattern_cstId_adjust = Pattern.compile(cstId_adjust);
    }

    @Override
    public LogData[] process(String str) throws Exception {

        LogData logData = new LogData();
        Matcher matcher_requestId = pattern_requestId.matcher(str);
        Matcher matcher_responseArg = pattern_responseArg.matcher(str);
        Matcher matcher_requestArg = pattern_requestArg.matcher(str);
        Matcher matcher_date = pattern_date.matcher(str);

        // 如果日志的内容是adjust，这种日志只有一条请求，没有回应 并且cst_id需要在request_params中解析
        if (str.contains("adjust")) {
            // 找出date 理论上每条都应该有
            if (matcher_date.find())
                logData.setDate(matcher_date.group());
            // 找出requestId 理论上每条都应该有
            if (matcher_requestId.find())
                logData.setRequestId(matcher_requestId.group());
            if (matcher_requestArg.find()) {
                // 加工request_params
                String requestParams = this.getReqSub(matcher_requestArg.group());
                logData.setRequestParams(requestParams);
                // 在request_params中找到cstId
                // 得出的结果为customerId":123456 所以从第13个开始取
                Matcher matcher_cstId = pattern_cstId_adjust.matcher(requestParams);
                if (matcher_cstId.find()) {
                    String substring = matcher_cstId.group().substring(12);
                    logData.setId(new BigInteger(substring));
                }
            }
            list_adjust.add(logData);
            if (list_adjust.size() >= 5000) {
                return list_adjust.toArray(new LogData[list_adjust.size()]);
            }
        } else {
            //普通日志
            //先找出request_id
            if (matcher_requestId.find())
                logData.setRequestId(matcher_requestId.group());
            //找出request_params date和cstid在这里面进行搜索 因为response仍然需要绑定到request中
            if (matcher_requestArg.find()) {
                //找出date
                if (matcher_date.find())
                    logData.setDate(matcher_date.group());
                //对request_params进行加工，再分析
                String request_params_reg = matcher_requestArg.group();
                String target = request_params_reg.substring(0, request_params_reg.indexOf("access"));
                logData.setRequestParams(target);
                //获取cstId
                Matcher matcher_cstId = pattern_cstId.matcher(target);
                if (matcher_cstId.find())
                    logData.setId(new BigInteger(matcher_cstId.group()));
                list_temp.add(logData);
            }
            //response需要在list_temp表中找到对应的request请求 然后删除list_temp中的对象，新的完整对象存储后发送到writer
            if (matcher_responseArg.find()) {
                logData.setResponseParams(matcher_responseArg.group());
                Iterator<LogData> iterator = list_temp.iterator();
                boolean flag = false;
                //在一个表中迭代找出对应的request
                while (iterator.hasNext()) {
                    LogData next = iterator.next();
                    if (next.getRequestId().equals(logData.getRequestId())) {
                        iterator.remove();
                        next.setResponseParams(matcher_responseArg.group());
                        flag = true;
                        list_finish.add(next);
                        break;
                    }
                }
                if (!flag)
                    throw new Exception("res没有对应的req");
                if (list_finish.size() >= 5000)
                    return list_finish.toArray(new LogData[list_finish.size()]);
            }
        }
        return null;
    }

    private String getReqSub(String str) {
        int end = str.indexOf("access");
        return str.substring(0, end);
    }
}
