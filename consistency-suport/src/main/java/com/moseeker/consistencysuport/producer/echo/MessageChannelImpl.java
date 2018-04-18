package com.moseeker.consistencysuport.producer.echo;

import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

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
    private Environment env;
    private EchoHandler echoHandler;

    private static final String QUEUE_NAME = "consistency_message_echo_queue";
    private static final String CONNECTION_NAME = "consistency_message_connection_factory";
    private static final String LISTENER_ADAPTER = "consistency_message_listener_adapter";
    private static final String LISTENER_CONTAINER = "consistency_message_listener_container";

    public MessageChannelImpl(ApplicationContext applicationContext, Environment env, EchoHandler echoHandler) {
        this.applicationContext = applicationContext;
        this.env = env;
        this.echoHandler = echoHandler;
    }

    @Override
    public synchronized void initChannel() throws ConsistencyException {

        logger.debug("MessageChannelImpl initChannel");

        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        connectionFactory = applicationContext.getBean(CONNECTION_NAME, ConnectionFactory.class);
        if (connectionFactory == null) {
            connectionFactory = new CachingConnectionFactory(env.getProperty("rabbitmq.host").trim(), Integer.valueOf(env.getProperty("rabbitmq.port").trim()));
            ((CachingConnectionFactory)connectionFactory).setUsername(env.getProperty("rabbitmq.username").trim());
            ((CachingConnectionFactory)connectionFactory).setPassword(env.getProperty("rabbitmq.password").trim());
            ((CachingConnectionFactory)connectionFactory).setChannelCacheSize(25);
            ((CachingConnectionFactory)connectionFactory).setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
            beanFactory.registerSingleton(CONNECTION_NAME, connectionFactory);
        }

        Queue queue = applicationContext.getBean(QUEUE_NAME, Queue.class);
        if (queue == null) {
            queue = new Queue(QUEUE_NAME, true);
            beanFactory.registerSingleton(QUEUE_NAME, queue);
        }

        MessageListenerAdapter messageListenerAdapter = applicationContext.getBean(LISTENER_ADAPTER, MessageListenerAdapter.class);
        if (messageListenerAdapter == null) {
            messageListenerAdapter = new MessageListenerAdapter(echoHandler, "handlerMessage");
            beanFactory.registerSingleton(LISTENER_ADAPTER, messageListenerAdapter);
        }

        SimpleMessageListenerContainer container = applicationContext.getBean(LISTENER_CONTAINER, SimpleMessageListenerContainer.class);
        if (container == null) {
            container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(queue.getName());
            container.setMessageListener(messageListenerAdapter);
            beanFactory.registerSingleton(LISTENER_CONTAINER, container);
        }
    }
}
