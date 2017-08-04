package com.moseeker.useraccounts.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/8/3.
 */
@Component
public class ReceiverHandler {

    @RabbitListener(queues = "#{addAwardQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void addAwardHandler(Message message, Channel channel) {

    }
}
