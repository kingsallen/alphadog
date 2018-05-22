package com.moseeker.consistencysuport.producer.echo;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 消息通道
 *
 * 初始化消息处理的消息通道
 *
 * 定时关闭心跳超时的业务
 *
 * Created by jack on 09/04/2018.
 */
public class MessageChannelImpl implements MessageChannel {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private ConnectionFactory connectionFactory;
    private EchoHandler echoHandler;

    private static final String TOPIC_EXCHANGE_NAME = "consistency_message_echo_exchange";
    private static final String CONNECTION_NAME = "consistency_message_connection_factory";
    private static final String LISTENER_ADAPTER = "consistency_message_listener_adapter";
    private static final String LISTENER_CONTAINER = "consistency_message_listener_container";
    private static final String QUEUE_NAME = "consistency_message_echo_queue";

    private String routingKey = "consistency_message_routing_key.#";
    private String bingQueue = "consistency_message__binding";

    public MessageChannelImpl(ApplicationContext applicationContext, Environment env, EchoHandler echoHandler) {
        this.applicationContext = applicationContext;
        this.echoHandler = echoHandler;
    }

    @Override
    public synchronized void initChannel() throws ConsistencyException {

        logger.debug("MessageChannelImpl initChannel");

        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        try {
            connectionFactory = applicationContext.getBean(ConnectionFactory.class);
        } catch (BeansException e) {
            logger.warn(" rabbit mq ConnectionFactory not exist!");
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

            if (StringUtils.isNotBlank(validateUtil.validate())) {
                throw ConsistencyException.CONSISTENCY_PRODUCER_MQ_CONFIG_LOST;
            }

            connectionFactory = new CachingConnectionFactory(host.trim(), port);
            ((CachingConnectionFactory)connectionFactory).setUsername(username.trim());
            ((CachingConnectionFactory)connectionFactory).setPassword(password.trim());
            ((CachingConnectionFactory)connectionFactory).setChannelCacheSize(25);
            ((CachingConnectionFactory)connectionFactory).setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
            beanFactory.registerSingleton(CONNECTION_NAME, connectionFactory);
        }

        Queue queue = null;
        try {
            queue = applicationContext.getBean(QUEUE_NAME, Queue.class);
        } catch (BeansException e) {
            logger.warn(" rabbit mq queue not exist!");
        }
        if (queue == null) {
            queue = new Queue(QUEUE_NAME, true);
            beanFactory.registerSingleton(QUEUE_NAME, queue);
        }

        TopicExchange topicExchange = null;
        try {
            topicExchange = applicationContext.getBean(TOPIC_EXCHANGE_NAME, TopicExchange.class);
        } catch (BeansException e) {
            logger.warn("initChannel topicExchange is not exist!");
        }
        if (topicExchange == null) {
            topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);
            beanFactory.registerSingleton(TOPIC_EXCHANGE_NAME, topicExchange);
        }

        List<Binding> bindingList = null;
        try {
            bindingList = applicationContext.getBean(bingQueue, List.class);
        } catch (BeansException e) {
            logger.warn("initChannel bindingList is not exist!");
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
                bindingList.add(binding);
            }
        } else {
            bindingList = new ArrayList<>();
            bindingList.add(BindingBuilder.bind(queue).to(topicExchange).with(routingKey));
            beanFactory.registerSingleton(bingQueue, bindingList);
        }

        MessageListenerAdapter messageListenerAdapter = null;
        try {
            messageListenerAdapter = applicationContext.getBean("consistency_message_listener_adapter", MessageListenerAdapter.class);
        } catch (BeansException e) {
            logger.warn(" rabbit mq messageListenerAdapter not exist!");
        }
        if (messageListenerAdapter == null) {
            messageListenerAdapter = new MessageListenerAdapter(echoHandler, "handlerMessage");
            beanFactory.registerSingleton(LISTENER_ADAPTER, messageListenerAdapter);
        }

        SimpleMessageListenerContainer container = null;
        try {
            container = applicationContext.getBean("consistency_message_listener_container", SimpleMessageListenerContainer.class);
        } catch (BeansException e) {
            logger.warn(" rabbit mq SimpleMessageListenerContainer not exist!");
        }
        if (container == null) {
            container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(queue.getName());
            container.setMessageListener(messageListenerAdapter);
            beanFactory.registerSingleton(LISTENER_CONTAINER, container);
        }
    }
}
