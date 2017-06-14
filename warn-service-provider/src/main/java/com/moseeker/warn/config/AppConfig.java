package com.moseeker.warn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by lucky8987 on 17/5/10.
 */
@Configuration
@ComponentScan("com.moseeker.warn")
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {
}
