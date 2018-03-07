package com.moseeker.position;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.moseeker.position", "com.moseeker.entity", "com.moseeker.common.aop.iface"})
@PropertySource("classpath:common.properties")
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
