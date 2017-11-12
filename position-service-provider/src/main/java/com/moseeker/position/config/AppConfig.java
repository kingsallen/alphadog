package com.moseeker.position.config;

import com.moseeker.baseorm.redis.cache.CacheClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by lucky8987 on 17/5/11.
 */
@Configuration
@EnableScheduling
@ComponentScan({"com.moseeker.position", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {

    @Bean
    public CacheClient cacheClient(){
        return null;
    }

}
