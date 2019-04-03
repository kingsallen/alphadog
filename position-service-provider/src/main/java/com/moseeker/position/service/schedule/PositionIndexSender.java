package com.moseeker.position.service.schedule;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PositionIndexSender {
    Logger logger = LoggerFactory.getLogger(PositionIndexSender.class);
    @Autowired
    private RabbitTemplate amqpTemplate;

    public void sendMqRequest(Object form, String routingKey, String exchange) {
        String formParams = JSONObject.toJSONString(form);
        logger.info("简历搬家请求参数formParams:{}", formParams);
        MessageProperties msp = new MessageProperties();
        // 延迟发送，避免大量请求在同一时间发送
        amqpTemplate.send(exchange, routingKey, MessageBuilder.withBody(formParams.getBytes()).andProperties(msp).build());
        logger.info("send success...");
    }
}
