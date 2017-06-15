package com.moseeker.servicemanager.config;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.servicemanager.common.UTF8StringHttpMessageConverter;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.moseeker.servicemanager.web", "com.moseeker.servicemanager.config"})
@Import(AppConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 转换 responseBody 格式
     * Content-Type: application/json;charset=utf-8
     * @param converters
     */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //converters.add(new UTF8StringHttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}
