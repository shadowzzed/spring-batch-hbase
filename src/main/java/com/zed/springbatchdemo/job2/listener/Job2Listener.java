package com.zed.springbatchdemo.job2.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * @author Zeluo
 * @date 2019/9/24 10:07
 */
public class Job2Listener extends JobExecutionListenerSupport {
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
            System.out.println("COMPLETED");
    }
}
