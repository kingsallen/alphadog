package com.moseeker.mq.rabbit;

import java.time.LocalDateTime;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/6/28.
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void  send(String name) {
        String str = "hello: ".concat(name).concat(",").concat(LocalDateTime.now().withNano(0).toString());
        amqpTemplate.convertAndSend("hello", str);
        System.out.println("send success...");
    }
}
