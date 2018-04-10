package com.moseeker.consistencysuport.echo;

import com.moseeker.consistencysuport.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 *
 * 消息通道
 *
 * Created by jack on 09/04/2018.
 */
public class MessageChannelImpl implements MessageChannel {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private ConnectionFactory connectionFactory;
    private Environment env;

    private static final String QUEUE_NAME = "consistency_message_echo_queue";
    private static final String TOPIC_EXCHANGE_NAME = "consistency_message_echo_exchange";
    private static final String CONNECTION_NAME = "consistency_message_connection_factory";

    public MessageChannelImpl(ApplicationContext applicationContext, Environment env) {
        this.applicationContext = applicationContext;
        this.env = env;
    }

    @Override
    public void initChannel() throws ConsistencyException {
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
        TopicExchange topicExchange = applicationContext.getBean(TOPIC_EXCHANGE_NAME, TopicExchange.class);
        if (topicExchange == null) {
            topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);
            beanFactory.registerSingleton(TOPIC_EXCHANGE_NAME, topicExchange);
        }

        SimpleMessageListenerContainer container = applicationContext.getBean(SimpleMessageListenerContainer.class);
        if (container == null) {
            container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(queue.getName());
            //container.setMessageListener(listenerAdapter);
        }
    }

    @Override
    public Message receiveMessage(String content) throws ConsistencyException {
        return null;
    }
}
