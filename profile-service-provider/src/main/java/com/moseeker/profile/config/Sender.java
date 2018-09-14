package com.moseeker.profile.config;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Service
public class Sender {

    Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate amqpTemplate;

    private Random random = new Random();

    public void  send(String message) {
        MessageProperties msp = new MessageProperties();
        msp.setDelay(5000); // 延迟5s发送
        amqpTemplate.send("profile_company_tag_recom_exchange", "profilecompanytagrecom.#", MessageBuilder.withBody(message.getBytes()).andProperties(msp).build());
        System.out.println("send success...");
    }


    /**
     * 将数据放入rabbitmq队列中
     * @param   form  数据VO
     * @param   routingKey 路由key
     * @param   exchange  交换机
     * @author  cjm
     * @date  2018/7/20
     */
    public void sendMqRequest(Object form, String routingKey, String exchange) {
        String formParams = JSONObject.toJSONString(form);
        logger.info("简历搬家请求参数formParams:{}", formParams);
        MessageProperties msp = new MessageProperties();
        // 延迟发送，避免大量请求在同一时间发送
        msp.setDelay(random.nextInt(5000));
        amqpTemplate.send(exchange, routingKey, MessageBuilder.withBody(formParams.getBytes()).andProperties(msp).build());
        logger.info("send success...");
    }
}
