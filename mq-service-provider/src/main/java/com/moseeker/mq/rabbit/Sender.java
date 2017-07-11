package com.moseeker.mq.rabbit;

import java.time.LocalDateTime;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/6/28.
 */
@Component
public class Sender {

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void  send(String name) {
        String str = name.concat(",").concat(LocalDateTime.now().withNano(0).toString());
        MessageProperties msp = new MessageProperties();
        msp.setDelay(10000);
        amqpTemplate.send("topic-exchange", "hello.test", MessageBuilder.withBody(str.getBytes()).andProperties(msp).build());
//        amqpTemplate.convertAndSend("topic-exchange", "hello.test", str, new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setDelay(15000);
//                return message;
//            }
//        });
        System.out.println("send success...");
    }
}
