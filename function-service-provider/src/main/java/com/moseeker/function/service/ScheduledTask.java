package com.moseeker.function.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.function.config.AppConfig;
import com.moseeker.function.constants.BindThridPart;
import com.moseeker.function.service.chaos.ChaosServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@Component
public class ScheduledTask {

    Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @RabbitListener(queues = {BindThridPart.BIND_GET_QUEUE_NAME,BindThridPart.BIND_CONFIRM_GET_QUEUE_NAME,BindThridPart.BIND_CODE_GET_QUEUE_NAME})
    public void scheduler(String data) {
        logger.info("获取到数据:"+data);
        JSONObject msg= JSON.parseObject(data);
        System.out.println(data);
        logger.info("推送数据到redis中");
        redisClient.set(BindThridPart.APP_ID,BindThridPart.KEY_IDENTIFIER,msg.getString("account_id"),data);
        logger.info("推送数据成功");
    }
}
