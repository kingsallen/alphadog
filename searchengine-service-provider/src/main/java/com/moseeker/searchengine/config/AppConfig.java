package com.moseeker.searchengine.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by lucky8987 on 17/5/24.
 */
@Configuration
@ComponentScan({"com.moseeker.searchengine", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
