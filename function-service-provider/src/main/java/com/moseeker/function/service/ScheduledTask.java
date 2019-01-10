package com.moseeker.function.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.BindThirdPart;
import com.moseeker.function.service.chaos.ChaosServiceImpl;
import com.moseeker.function.service.chaos.PositionForSyncResultPojo;
import com.moseeker.function.service.chaos.PositionSyncConsumer;
import com.moseeker.function.service.chaos.PositionSyncFailedNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 第三方相关功能中使用的rabbitMq监听类
 * @author PYB
 */
@Component
public class ScheduledTask {

    Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    PositionSyncConsumer positionSyncConsumer;

    @Autowired
    PositionSyncFailedNotification failNotification;
    /**
     * 监听绑定第三方账号结果队列
     * @param message
     * @param channel
     */
//    @RabbitListener(queues = {BindThirdPart.BIND_GET_QUEUE_NAME, BindThirdPart.BIND_CONFIRM_GET_QUEUE_NAME, BindThirdPart.BIND_CODE_GET_QUEUE_NAME}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void bindThirdPartyAccountListener(Message message, Channel channel) throws Exception {
        try{
            String data=new String(message.getBody(), "UTF-8");
            logger.info("获取到数据:"+data);
            JSONObject msg= JSON.parseObject(data);
            logger.info("推送数据到redis中");
            redisClient.set(BindThirdPart.APP_ID, BindThirdPart.KEY_IDENTIFIER,msg.getJSONObject("data").getString(BindThirdPart.CHAOS_ACCOUNTID),data);
            logger.info("推送数据成功");
        } catch (Exception e) {
            logger.info("获取绑定第三方账号结果队列报错");
            // 错误日志记录到数据库 的 log_dead_letter 表中
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 职位同步结果监听队列
     * @param message   队列消息
     * @param channel
     * @throws UnsupportedEncodingException
     */
//    @RabbitListener(queues = {BindThirdPart.SYNC_POSITION_GET_QUEUE_NAME}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void positionSyncListener(Message message, Channel channel) throws UnsupportedEncodingException, BIZException {
        String data="";
        try{
            data=new String(message.getBody(), "UTF-8");
            logger.info("成功获取同步职位结果数据:"+data);
            PositionForSyncResultPojo pojo=JSON.parseObject(data, PositionForSyncResultPojo.class);
            if(pojo==null || pojo.getData()==null){
                logger.info("position sync result null data : {}",data);
                return ;
            }
            positionSyncConsumer.positionSyncComplete(pojo);
        }catch (Exception e){
            logger.info("获取职位同步结果队列报错");
            e.printStackTrace();
            failNotification.sendHandlerFailureMail(data,e);
        }
    }
}
