package com.moseeker.demo1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by jack on 2018/4/18.
 */
@Configuration
@ComponentScan(value = {"com.moseeker.demo1", "com.moseeker.common.aop.iface", "com.moseeker.consistencysuport.consumer"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
