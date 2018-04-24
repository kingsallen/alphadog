package com.moseeker.profile.config;

import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Service
public class Sender {
    @Autowired
    private RabbitTemplate amqpTemplate;

    public void  send(String message) {
        MessageProperties msp = new MessageProperties();
        msp.setDelay(5000); // 延迟5s发送
        amqpTemplate.send("profile_company_tag_recom_exchange", "profilecompanytagrecom.#", MessageBuilder.withBody(message.getBytes()).andProperties(msp).build());
        System.out.println("send success...");
    }


}
