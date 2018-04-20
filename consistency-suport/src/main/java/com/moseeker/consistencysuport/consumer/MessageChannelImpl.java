package com.moseeker.consistencysuport.consumer;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.consistencysuport.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 消息通道
 *
 * Created by jack on 2018/4/17.
 */
@Component
public class MessageChannelImpl implements MessageChannel {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmqpTemplate amqpTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    private static final String TOPIC_EXCHANGE_NAME = "consistency_message_echo_exchange";
    private static final String QUEUE_NAME = "consistency_message_echo_queue";
    private static final String CONNECTION_NAME = "consistency_message_connection_factory";
    private static final String AMQP_ADMIN = "consistency_message_AMQP_ADMIN";
    private String routingKey = "consistency_message_routing_key";
    private String bingQueue = "bindingBindQueue";

    @Override
    public void sendMessage(Message message) {
        if (amqpTemplate == null) {
            initMessageChannel();
        }
        amqpTemplate.send(TOPIC_EXCHANGE_NAME, routingKey, MessageBuilder.withBody(JSONObject.toJSONString(message).getBytes()).build());
    }

    @Override
    public void initMessageChannel() {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();

        ConnectionFactory connectionFactory = null;
        try {
            connectionFactory = applicationContext.getBean(ConnectionFactory.class);
        } catch (BeansException e) {
            logger.warn("initMessageChannel ConnectionFactory is not exist!");
        }
        if (connectionFactory == null) {

            ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
            String host = propertiesUtils.get("rabbitmq.host", String.class);
            Integer port = propertiesUtils.get("rabbitmq.port", Integer.class);
            String username = propertiesUtils.get("rabbitmq.username", String.class);
            String password = propertiesUtils.get("rabbitmq.password", String.class);

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredStringValidate("域名", host);
            validateUtil.addRequiredValidate("端口", port);
            validateUtil.addRequiredStringValidate("账号", username);
            validateUtil.addRequiredStringValidate("密码", password);

            connectionFactory = new CachingConnectionFactory(host.trim(), port);
            ((CachingConnectionFactory)connectionFactory).setUsername(username.trim());
            ((CachingConnectionFactory)connectionFactory).setPassword(password.trim());
            ((CachingConnectionFactory)connectionFactory).setChannelCacheSize(25);
            ((CachingConnectionFactory)connectionFactory).setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
            beanFactory.registerSingleton(CONNECTION_NAME, connectionFactory);
        }

        try {
            amqpTemplate = applicationContext.getBean(AmqpTemplate.class);
        } catch (BeansException e) {
            logger.warn("initMessageChannel amqpTemplate is not exist!");
        }
        if (amqpTemplate == null) {
            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            backOffPolicy.setInitialInterval(500);
            backOffPolicy.setMultiplier(10.0);
            backOffPolicy.setMaxInterval(10000);
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setBackOffPolicy(backOffPolicy);
            rabbitTemplate.setRetryTemplate(retryTemplate);

            amqpTemplate = rabbitTemplate;
            beanFactory.registerSingleton(AMQP_ADMIN,amqpTemplate);
        }

        TopicExchange topicExchange = null;
        try {
            topicExchange = applicationContext.getBean(TOPIC_EXCHANGE_NAME, TopicExchange.class);
        } catch (BeansException e) {
            logger.warn("initMessageChannel topicExchange is not exist!");
        }
        if (topicExchange == null) {
            topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);
            beanFactory.registerSingleton(TOPIC_EXCHANGE_NAME, topicExchange);
        }

        Queue queue = null;
        try {
            queue = applicationContext.getBean(QUEUE_NAME, Queue.class);
        } catch (BeansException e) {
            logger.warn("initMessageChannel queue is not exist!");
        }
        if (queue == null) {
            queue = new Queue(QUEUE_NAME, true);
            beanFactory.registerSingleton(QUEUE_NAME, queue);
        }

        List<Binding> bindingList = null;
        try {
            bindingList = applicationContext.getBean(bingQueue, List.class);
        } catch (BeansException e) {
            logger.warn("initMessageChannel bindingList is not exist!");
        }
        if (bindingList != null) {
            Binding binding = null;
            for (Binding bindingTemp : bindingList) {
                if (bindingTemp.getExchange().equals(TOPIC_EXCHANGE_NAME)) {
                    binding = bindingTemp;
                    break;
                }
            }
            if (binding == null) {
                binding = BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
            }
            bindingList.add(binding);
        } else {
            bindingList = new ArrayList<>();
            bindingList.add(BindingBuilder.bind(queue).to(topicExchange).with(routingKey));
            beanFactory.registerSingleton(bingQueue, bindingList);
        }
    }
}
