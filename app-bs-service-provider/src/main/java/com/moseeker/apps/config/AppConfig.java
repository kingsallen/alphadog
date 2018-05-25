package com.moseeker.apps.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by lucky8987 on 17/5/10.
 */
@Configuration
@ComponentScan({"com.moseeker.apps", "com.moseeker.common.aop.iface","com.moseeker.common.aop.notify", "com.moseeker.entity"})
@PropertySource("classpath:common.properties")
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
