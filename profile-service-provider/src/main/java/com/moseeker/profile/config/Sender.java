package com.moseeker.profile.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Component
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("#{topicExchange.name}")
    private String exchangeName;


}
