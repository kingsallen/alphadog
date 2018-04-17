package com.moseeker.consistencysuport.consumer;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.consistencysuport.Message;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    private static final String TOPIC_EXCHANGE_NAME = "consistency_message_echo_exchange";
    private static final String QUEUE_NAME = "consistency_message_echo_queue";
    private String routingKey = "consistency_message_routingkey";
    private String bingQueue = "bindingBindQueue";

    @Override
    public void sendMessage(Message message) {
        amqpTemplate.send(TOPIC_EXCHANGE_NAME, routingKey, MessageBuilder.withBody(JSONObject.toJSONString(message).getBytes()).build());
    }

    @Override
    public void initMessageChannel() {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();

        TopicExchange topicExchange = applicationContext.getBean(TOPIC_EXCHANGE_NAME, TopicExchange.class);
        if (topicExchange == null) {
            topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);
            beanFactory.registerSingleton(TOPIC_EXCHANGE_NAME, topicExchange);
        }

        Queue queue = applicationContext.getBean(QUEUE_NAME, Queue.class);
        if (queue == null) {
            queue = new Queue(QUEUE_NAME, true);
            beanFactory.registerSingleton(QUEUE_NAME, queue);
        }

        List<Binding> bindingList = applicationContext.getBean(bingQueue, List.class);
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
