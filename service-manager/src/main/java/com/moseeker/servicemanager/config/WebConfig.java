package com.moseeker.servicemanager.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.servicemanager.common.UTF8StringHttpMessageConverter;
import com.moseeker.servicemanager.web.interceptor.TimeStatisticsInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.moseeker.servicemanager.web", "com.moseeker.servicemanager.config",
        "com.moseeker.servicemanager.exception", "com.moseeker.servicemanager.service",
        "com.moseeker.commonservice.annotation"})
@PropertySource("classpath:common.properties")
@Import({AppConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(5242880);//5MB
        resolver.setResolveLazily(true);
        return resolver;
    }

    /**
     * 转换 responseBody 格式 Content-Type: application/json;charset=utf-8
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new UTF8StringHttpMessageConverter());
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        FastJsonHttpMessageConverter4 fastJsonpHttpMessageConverter4 = new FastJsonHttpMessageConverter4();
        fastJsonpHttpMessageConverter4.setSupportedMediaTypes(mediaTypes);
        converters.add(fastJsonpHttpMessageConverter4);
        super.configureMessageConverters(converters);
    }

    @Bean
    public TimeStatisticsInterceptor getTimeStatisticsInterceptor() {
        return new TimeStatisticsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTimeStatisticsInterceptor()).addPathPatterns("/**");
    }
}