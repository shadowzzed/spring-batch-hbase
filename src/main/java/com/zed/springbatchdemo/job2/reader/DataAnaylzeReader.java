package com.zed.springbatchdemo.job2.reader;

import com.zed.springbatchdemo.ConstantProperties;
import com.zed.springbatchdemo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zeluo
 * @date 2019/9/24 9:28
 */
@Slf4j
public class DataAnaylzeReader implements ItemReader<String[]> {

//    private static final String FILEPATH = "D:\\workspace-commons\\log_api.log";

//    private static final String PATH = "D:\\workspace-commons\\logs";

    //获取所有文件的路径
    private static final ArrayList<String> files = FileUtils.getFiles(ConstantProperties.SOURCE_PATH);

    private int count = 0;
    @Override
    public String[] read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        log.info("第{}次运行read",count);
        if (count < files.size()) {
            String[] strings = readContentLine(files.get(count++));
            return strings;
        } else
            return null;
    }

    private String[] readContentLine(String filePath) throws IOException {
        log.info("[读取日志文件-开始] 读取日志文件开始***************************************");
        List<String> list = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            list.add(str);
        }
        inputStream.close();
        bufferedReader.close();
        log.info("[读取日志文件-结束] 读取日志文件结束***************************************");
        return list.toArray(new String[list.size()]);
    }
}
