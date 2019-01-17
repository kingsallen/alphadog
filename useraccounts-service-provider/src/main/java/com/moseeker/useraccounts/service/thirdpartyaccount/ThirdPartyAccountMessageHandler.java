package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.pojos.ThirdPartyAccountExt;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.moseeker.useraccounts.pojo.BindResult;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

//    @RabbitListener(queues = "#{bindAccountQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void bindAccountQueue(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("ThirdPartyAccountMessageHandler bindAccountQueue msgBody : {}", msgBody);
            BindResult bindResult = JSON.parseObject(msgBody, BindResult.class);
            thirdPartyAccountService.bingResultHandler(bindResult);
        } catch (CommonException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            log(message, e, msgBody);
        }
    }

//    @RabbitListener(queues = "#{presetQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void presetHandler(Message message, Channel channel) {
        logger.info("处理第三方相关信息队列开始");
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("ThirdPartyAccountMessageHandler presetHandler msgBody : {}", msgBody);
            ThirdPartyAccountExt accountExt = JSON.parseObject(msgBody, ThirdPartyAccountExt.class);
            thirdPartyAccountService.thirdPartyAccountExtHandler(accountExt);
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            log(message, e, msgBody);
        }
    }

    private void log(Message message, Exception e, String msgBody) {
        try {
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(NumberUtils.toInt(message.getMessageProperties().getAppId(), 0));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedExchange(), ""));
            logDeadLetterDO.setRoutingKey(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedRoutingKey(), ""));
            logDeadLetterDO.setQueueName(StringUtils.defaultIfBlank(message.getMessageProperties().getConsumerQueue(), ""));
            logDeadLetterDao.addData(logDeadLetterDO);
            logger.error(e.getMessage(), e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
