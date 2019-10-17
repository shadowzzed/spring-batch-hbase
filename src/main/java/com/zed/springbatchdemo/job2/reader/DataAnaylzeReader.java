package com.zed.springbatchdemo.job2.reader;

import com.zed.springbatchdemo.ConstantProperties;
import com.zed.springbatchdemo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zeluo
 * @date 2019/9/24 9:28
 */
@Slf4j
@Component
public class DataAnaylzeReader implements ItemReader<String> {

//    private static final String FILEPATH = "D:\\workspace-commons\\log_api.log";

//    private static final String PATH = "D:\\workspace-commons\\logs";

    //获取所有文件的路径
    private static final ArrayList<String> files = FileUtils.getFiles(ConstantProperties.SOURCE_PATH);

    private int count = 0;

    private int count_should = 0;

    private BufferedReader bufferedReader;
    @Override
    public String read() throws Exception {
        bufferedReader = this.getBuff() == null ? bufferedReader : this.getBuff();
        String str;
        if ((str= bufferedReader.readLine()) != null)
            return str;
        else {
            count_should++;
            this.read();
        }
        return null;
    }

    // 按行读取文件
//    private String[] readContentLine(String filePath) throws IOException, InterruptedException {
//        log.info("[读取日志文件-开始] 读取日志文件开始***************************************");
//        List<String> list = new ArrayList<>();
//
//        FileInputStream inputStream = new FileInputStream(filePath);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String str = null;
//        while ((str = bufferedReader.readLine()) != null) {
//            list.add(str);
//        }
//        Thread.sleep(1000L);
//        inputStream.close();
//        bufferedReader.close();
//        log.info("[读取日志文件-结束] 读取日志文件结束***************************************");
//        return list.toArray(new String[list.size()]);
//    }

    // 动态获取BuffredReader 如果count和count_should相等则返回新的BufferedReader否则返回null
    private BufferedReader getBuff() throws FileNotFoundException {
        if (count == count_should) {
            FileInputStream inputStream = new FileInputStream(files.get(count));
            count++;
            return new BufferedReader(new InputStreamReader(inputStream));
        }
        return null;
    }
}
