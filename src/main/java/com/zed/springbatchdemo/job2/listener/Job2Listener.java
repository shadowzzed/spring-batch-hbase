package com.zed.springbatchdemo.job2.listener;

import com.zed.springbatchdemo.ConstantProperties;
import com.zed.springbatchdemo.hbase.HBaseClient;
import com.zed.springbatchdemo.job2.processor.Job2Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Zeluo
 * @date 2019/9/24 10:07
 */
@Component
@Slf4j
public class Job2Listener extends JobExecutionListenerSupport {
    private HBaseClient hBaseClient;

    @Autowired
    public Job2Listener(HBaseClient hBaseClient) {
        this.hBaseClient = hBaseClient;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("completed,剩余{}",HBaseClient.list.size());
            try {
//                hBaseClient.insertAdjust(ConstantProperties.TABLE_NAME);
                hBaseClient.insertAdjust(Job2Processor.list_adjust, ConstantProperties.TABLE_NAME);
                hBaseClient.insertAdjust(Job2Processor.list_temp, ConstantProperties.TABLE_NAME);
                hBaseClient.insertRest(ConstantProperties.TABLE_NAME);
                log.info("剩余表情况{}",HBaseClient.list.size());
//                for (LogData logData: Job2Processor.list_temp)
//                    log.info("{}",logData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
