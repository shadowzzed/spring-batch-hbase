package com.zed.springbatchdemo.job2.model;

import lombok.Data;
import org.springframework.core.annotation.Order;

import javax.persistence.*;

/**
 * @author Zeluo
 * @date 2019/9/24 9:29
 */
@Data
public class LogData {
    private Integer id;
    private String requestId;
    private String requestParams;
    private String responseParams;
}
