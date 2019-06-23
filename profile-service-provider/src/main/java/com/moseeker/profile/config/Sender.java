package com.moseeker.profile.config;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.thread.ScheduledThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Service
public class Sender {

    Logger logger = LoggerFactory.getLogger(Sender.class);
    ScheduledThread thread=ScheduledThread.Instance;

    @Autowired
    private RabbitTemplate amqpTemplate;

    private Random random = new Random();

    public void send(String message,int time){
        thread.startTast(new Runnable(){
            @Override
            public void run() {
                send( message);
            }
        },time);
    }

    public void  send(String message) {
        logger.info("handlerCompanyTagTalent send message:{}",message);
        MessageProperties msp = new MessageProperties();
        amqpTemplate.send("profile_company_tag_recom_new_exchange", "profilecompanytagnewrecom.#", MessageBuilder.withBody(message.getBytes()).andProperties(msp).build());
        logger.info("send success...");
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
