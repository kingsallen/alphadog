package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.entity.pojos.ThirdPartyAccountExt;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.moseeker.useraccounts.pojo.BindResult;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 27/09/2017.
 */
@Component
public class ThirdPartyAccountMessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdPartyAccountService thirdPartyAccountService;
    @Autowired
    private LogDeadLetterDao logDeadLetterDao;

    @RabbitListener(queues = "#{bindAccountQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void bindAccountQueue(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            BindResult bindResult = JSON.parseObject(msgBody, BindResult.class);
            thirdPartyAccountService.bingResultHandler(bindResult);
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(Integer.valueOf(message.getMessageProperties().getAppId()));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(message.getMessageProperties().getReceivedExchange());
            logDeadLetterDO.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
            logDeadLetterDO.setQueueName(message.getMessageProperties().getConsumerQueue());
            logDeadLetterDao.addData(logDeadLetterDO);
            logger.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{presetQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void presetHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            ThirdPartyAccountExt accountExt = JSON.parseObject(msgBody, ThirdPartyAccountExt.class);
            thirdPartyAccountService.thirdPartyAccountExtHandler(accountExt);
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(Integer.valueOf(message.getMessageProperties().getAppId()));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(message.getMessageProperties().getReceivedExchange());
            logDeadLetterDO.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
            logDeadLetterDO.setQueueName(message.getMessageProperties().getConsumerQueue());
            logDeadLetterDao.addData(logDeadLetterDO);
            logger.error(e.getMessage(), e);
        }
    }
}
