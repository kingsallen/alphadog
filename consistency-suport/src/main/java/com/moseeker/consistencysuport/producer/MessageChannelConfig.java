package com.moseeker.consistencysuport.producer;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/4/20.
 */
@Configuration
@EnableRabbit
@PropertySource("classpath:common.properties")
public class MessageChannelConfig {

    @Autowired
    private Environment env;

    @Bean("consistency_message_connection_factory")
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(env.getProperty("rabbitmq.host").trim());
        cf.setPort(Integer.valueOf(env.getProperty("rabbitmq.port").trim()));
        cf.setUsername(env.getProperty("rabbitmq.username").trim());
        cf.setPassword(env.getProperty("rabbitmq.password").trim());
        return cf;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
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
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory());
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    @Bean("consistency_message_echo_queue")
    public Queue messageChannelQueue() {
        Queue queue = new Queue("consistency_message_echo_queue", true, false, false);
        return queue;
    }

    @Bean("consistency_message_echo_exchange")
    public TopicExchange messageChannelExchange() {
        TopicExchange topicExchange = new TopicExchange("consistency_message_echo_exchange", true, false);
        return topicExchange;
    }

    @Bean("consistency_message__binding")
    public List<Binding> binding() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(messageChannelQueue()).to(messageChannelExchange()).with("consistency_message_routing_key.#"));
        }};
    }

    @Bean
    public MessageHandler messageHandler() {
        return new MessageHandler();
    }

    @Bean("consistency_message_listener_adapter")
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(messageHandler(), "receiveMessage");
    }

    @Bean("consistency_message_listener_container")
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(cachingConnectionFactory());
        container.setQueueNames(messageChannelQueue().getName());
        container.setMessageListener(listenerAdapter());
        return container;
    }
}
