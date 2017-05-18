package com.moseeker.useraccounts.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by lucky8987 on 17/5/12.
 */
@Configuration
@ComponentScan({"com.moseeker.useraccounts", "com.moseeker.common.aop.iface", "com.moseeker.common.aop.notify"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
