package com.moseeker.function.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.function.constants.BindThirdPart;
import com.moseeker.function.service.chaos.ChaosServiceImpl;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ScheduledTask {

    Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;


    @RabbitListener(queues = {BindThirdPart.BIND_GET_QUEUE_NAME, BindThirdPart.BIND_CONFIRM_GET_QUEUE_NAME, BindThirdPart.BIND_CODE_GET_QUEUE_NAME}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void scheduler(Message message, Channel channel) {
        try{
            String data=new String(message.getBody(), "UTF-8");
            logger.info("获取到数据:"+data);
            JSONObject msg= JSON.parseObject(data);
            logger.info("推送数据到redis中");
            redisClient.set(BindThirdPart.APP_ID, BindThirdPart.KEY_IDENTIFIER,msg.getJSONObject("data").getString(BindThirdPart.CHAOS_ACCOUNTID),data);
            logger.info("推送数据成功");
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            e.printStackTrace();
        }
    }
}