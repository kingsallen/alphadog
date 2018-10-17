package com.moseeker.apps.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/10/17.
 */
@Component
public class Sender {
    Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    RabbitTemplate amqpTemplate;

}
