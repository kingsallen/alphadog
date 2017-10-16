package com.moseeker.useraccounts.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky8987 on 17/5/12.
 */
@Configuration
@EnableRabbit
@ComponentScan({"com.moseeker.useraccounts", "com.moseeker.entity", "com.moseeker.common.aop.iface", "com.moseeker.common.aop.notify"})
@PropertySource("classpath:common.properties")
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(env.getProperty("rabbitmq.host").trim());
        cf.setPort(Integer.valueOf(env.getProperty("rabbitmq.port").trim()));
        cf.setUsername(env.getProperty("rabbitmq.username").trim());
        cf.setPassword(env.getProperty("rabbitmq.password").trim());
        return cf;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
        RetryTemplate retryTemplate = new RetryTemplate();
        // 重试机制
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        return rabbitTemplate;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }


    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory());
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    /**
     * listener 容器 （consumer 需要手动确认消息）
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(cachingConnectionFactory());
        // 设置手动 ACK
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return listenerContainerFactory;
    }

    /**
     * listener 容器 （AcknowledgeMode：auto）
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactoryAutoAck() {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(cachingConnectionFactory());

        // 设置自动 ACK
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainerFactory;
    }


    @Bean
    public Queue bindAccountQueue() {
        Queue queue = new Queue("chaos.bind.response", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange bindAccountExchange() {
        TopicExchange topicExchange = new TopicExchange("chaos.bind.response.exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> bindingBindAccount() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(bindAccountQueue()).to(bindAccountExchange()).with("chaos.bind.response.#"));
        }};
    }

    @Bean
    public Queue presetQueue() {
        Queue queue = new Queue("chaos.preset.response", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange presetExchange() {
        TopicExchange topicExchange = new TopicExchange("chaos.preset.response.exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> bindingPreset() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(presetQueue()).to(presetExchange()).with("chaos.preset.response.#"));
        }};
    }

    @Bean
    public TopicExchange webPresetExchange() {
        TopicExchange topicExchange = new TopicExchange("chaos", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> webBindingPreset() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(presetQueue()).to(presetExchange()).with("preset.response"));
        }};
    }
}
