package com.moseeker.mq.rabbit;

import java.time.LocalDateTime;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/6/28.
 */
@Component
@RabbitListener(queues = "hello")
public class Receiver {

    @RabbitHandler
    public void process(byte[] str) {
        System.out.println("Receiver: " + new String(str) + "; " + LocalDateTime.now().withNano(0).toString());
    }
}
