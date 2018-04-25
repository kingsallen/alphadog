package com.moseeker.application.config;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

/**
 * Created by lucky8987 on 17/5/10.
 */
@Configuration
@ComponentScan({"com.moseeker.application", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@PropertySource("classpath:common.properties")
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {

    @Bean
    public ApplicationContextAware applicationContextProvider() {
        return new ApplicationContextProvider();
    }
}
