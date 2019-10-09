package com.zed.springbatchdemo.job2;

import com.zed.springbatchdemo.job2.listener.Job2Listener;
import com.zed.springbatchdemo.job2.model.LogData;
import com.zed.springbatchdemo.job2.processor.Job2Processor;
import com.zed.springbatchdemo.job2.reader.DataAnaylzeReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Zeluo
 * @date 2019/9/24 9:28
 */
@Configuration
public class Job2Config {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job processJob2(@Qualifier("orderStep2") Step orderStep) {
        return jobBuilderFactory.get("dataAnalyze")
                .incrementer(new RunIdIncrementer())
                .listener(new Job2Listener())
                .flow(orderStep)
                .end()
                .build();
    }
    @Bean
    public Step orderStep2(@Qualifier("job2Writer2") ItemWriter<LogData[]> writer) {
        return stepBuilderFactory.get("dataAnalyzeStep1")
                .<String[], LogData[]>chunk(1)
                .reader(new DataAnaylzeReader())
                .processor(new Job2Processor())
                .writer(writer)
                .build();
    }
}
