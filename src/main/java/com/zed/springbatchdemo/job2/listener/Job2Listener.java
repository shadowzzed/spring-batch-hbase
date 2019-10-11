package com.zed.springbatchdemo.job2.listener;

import com.zed.springbatchdemo.InsertProperties;
import com.zed.springbatchdemo.hbase.HBaseClient;
import com.zed.springbatchdemo.job2.model.LogData;
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
            log.info("completed,剩余{}",Job2Processor.list_temp.size());
            try {
                //发送剩余的list中的对象
                hBaseClient.insertRest(InsertProperties.TABLE_NAME);
                // 发送adjust中的对象
                if(Job2Processor.list_adjust.size() > 0)
                    for (LogData logData: Job2Processor.list_adjust) {
                        hBaseClient.insertOrUpdate(
                                InsertProperties.TABLE_NAME,
                                logData.getId()+"$"+logData.getDate()+"$"+logData.getRequestId(),
                                InsertProperties.COLUMN_FAMILY,
                                new String[]{InsertProperties.REQUEST_ID,InsertProperties.REQUEST_PARAMS,InsertProperties.RESPONSE_PARAMS},
                                new String[]{logData.getRequestId(),logData.getRequestParams(),logData.getResponseParams()}
                                );
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
