package com.zed.springbatchdemo.job2.writer;

import com.zed.springbatchdemo.ConstantProperties;
import com.zed.springbatchdemo.hbase.HBaseClient;
import com.zed.springbatchdemo.job2.model.LogData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zeluo
 * @date 2019/9/25 13:51
 */
@Component
@Slf4j
public class Job2Writer2 implements ItemWriter<LogData[]> {

//    private final String TABLE_NAME = "log_data3";
//    private final String COLUMN_FAMILY = "attribute";
//    private final String REQUEST_ID = "request_id";
//    private final String REQUEST_PARAMS = "request_params";
//    private final String RESPONSE_PARAMS = "response_params";

    private HBaseClient hBaseClient;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public Job2Writer2(
            JdbcTemplate jdbcTemplate,
            HBaseClient hBaseClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.hBaseClient = hBaseClient;
    }

    private final String INSERT_SQL = "INSERT INTO log_data (request_id,request_params,response_params) values (?,?,?)";

    private Integer count = 0;
    @Override
    public void write(List<? extends LogData[]> items) throws Exception {
        log.info("到达writer*******************");
        if (items.size() < 1)
            return;
        LogData[] logData = items.get(0);
        log.info("发送大小,{}",logData.length);
        for (LogData data:logData) {
            hBaseClient.insertOrUpdate(
                    data.getId()+"$"+data.getDate()+"$"+data.getRequestId(),
                    ConstantProperties.COLUMN_FAMILY,
                    new String[]{ConstantProperties.REQUEST_ID, ConstantProperties.REQUEST_PARAMS, ConstantProperties.RESPONSE_PARAMS},
                    new String[]{data.getRequestId(),data.getRequestParams(),data.getResponseParams()});
        }

    }
}
