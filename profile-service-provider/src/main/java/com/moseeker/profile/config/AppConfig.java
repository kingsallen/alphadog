package com.moseeker.profile.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by jack on 17/05/2017.
 */
@Configuration
@ComponentScan({"com.moseeker.profile", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
