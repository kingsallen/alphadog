package com.moseeker.talentpool.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by jack on 27/11/2017.
 */
@Configuration
@ComponentScan({"com.moseeker.talentpool", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
