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
 * @author Zeluo
 * @date 2019/9/24 10:00
 */
@Slf4j
@Component
public class Job2Processor implements ItemProcessor<String[], LogData[]> {

    private HBaseClient hBaseClient;

    @Autowired
    public Job2Processor(HBaseClient hBaseClient) {
        this.hBaseClient = hBaseClient;
    }

    public static List<LogData> list_temp = new ArrayList<>();
    public static List<LogData> list_adjust = new ArrayList<>(15000);

    private static final String requestId = "\\w*-\\w*-\\w*-\\w*-\\w*";
    private static final String requestArg = "arg\\[[0-9]*\\]:\\s.*";
    private static final String responseArg = "response params: \\{.*\\}";//response params 后面是json对象格式
    private static final String cstId = "[0-9]{5,}";
    private static final String date = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
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
    public LogData[] process(String strs[]) throws Exception {
        log.info("[分析日志-开始] 分析日志开始***************************************");
        // 普通
        List<LogData> list = new ArrayList<>(30000);
        for (String str: strs) {
            LogData logData = new LogData();

            Matcher matcher_requestId = pattern_requestId.matcher(str);
            Matcher matcher_responseArg = pattern_responseArg.matcher(str);
            Matcher matcher_requestArg = pattern_requestArg.matcher(str);
            Matcher matcher_date = pattern_date.matcher(str);

            // 如果日志的内容是adjust，这种日志只有一条请求，没有回应
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
                continue;
            }

            // 普通日志
            if (matcher_requestId.find())
                logData.setRequestId(matcher_requestId.group());
            if (matcher_requestArg.find()) {
                //找出time
                if (matcher_date.find())
                    logData.setDate(matcher_date.group());
                //找出request_params
                String target_str_request = matcher_requestArg.group();
                int end = target_str_request.indexOf("access");
                String substring = target_str_request.substring(0, end);
                logData.setRequestParams(substring);
                //找出cst_id
                Matcher matcher_cstId = pattern_cstId.matcher(substring);
                if (matcher_cstId.find())
                    logData.setId(new BigInteger(matcher_cstId.group()));
                list_temp.add(logData);
            }
            if (matcher_responseArg.find()) {
                logData.setResponseParams(matcher_responseArg.group());
                Iterator<LogData> iterator = list_temp.iterator();
                boolean flag = false;
                while (iterator.hasNext()) {
                    LogData next = iterator.next();
                     if (next.getRequestId().equals(logData.getRequestId())) {
                        iterator.remove();
                        next.setResponseParams(matcher_responseArg.group());
                        list.add(next);
                        flag = true;
                        break;
                    }
                }
                //如果没找到res对应的req
                if (!flag)
                    throw new Exception("没有找到对应的req");
            }
//            list.add(logData);
        }
        log.info("[分析日志-结束] 分析日志结束***************************************");
//        return logData;
        return list.toArray(new LogData[list.size()]);
    }

    private String getReqSub(String str) {
        int end = str.indexOf("access");
        return str.substring(0, end);
    }
}
