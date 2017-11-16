package com.moseeker.position.config;

import com.moseeker.baseorm.config.RabbitMQConfig;
import com.moseeker.baseorm.redis.cache.CacheClient;
import com.moseeker.position.constants.VeryEastConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky8987 on 17/5/11.
 */
@Configuration
@EnableScheduling
@ComponentScan({"com.moseeker.position", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import({com.moseeker.baseorm.config.AppConfig.class, RabbitMQConfig.class})
@PropertySource("classpath:common.properties")
public class AppConfig {
    //设置rabbitMQ的Exchange
    @Bean
    public TopicExchange exchange() {
        TopicExchange exchange = new TopicExchange(VeryEastConstant.EXCHANGE, true, false);
        return exchange;
    }


    //绑定第一次推送第三方账号队列(即推送需要绑定的账号和密码)
    @Bean
    public Queue paramQueue() {
        Queue queue = new Queue(VeryEastConstant.PARAM_GET_QUEUE, true, false, false);
        return queue;
    }
    @Bean
    public List<Binding> bindingParamQueue() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(paramQueue()).to(exchange()).with(VeryEastConstant.PARAM_GET_ROUTING_KEY));
        }};
    }
}
