package com.moseeker.mq.rabbit;

import java.time.LocalDateTime;
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
        String str = "send: " + name.concat(",").concat(LocalDateTime.now().withNano(0).toString());
        MessageProperties msp = new MessageProperties();
        msp.setDelay(10000); // 延迟10s发送
        System.out.println("send success...");
    }

    public void sendAndReceive(String msg) {
        String str = "";
    }
}
