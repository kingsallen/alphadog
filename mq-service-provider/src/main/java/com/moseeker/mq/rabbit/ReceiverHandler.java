package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/8/3.
 */
@Component
public class ReceiverHandler {

    private static Logger log = LoggerFactory.getLogger(ReceiverHandler.class);

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private LogDeadLetterDao logDeadLetterDao;

    @Autowired
    private MessageTemplateEntity messageTemplateEntity;

    @Autowired
    private TemplateMsgProducer templateMsgProducer;

    @RabbitListener(queues = "#{addAwardQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void addAwardHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            employeeEntity.addAwardBefore(jsonObject.getIntValue("employeeId"), jsonObject.getIntValue("companyId"),
                    jsonObject.getIntValue("positionId"), jsonObject.getIntValue("templateId"), jsonObject.getIntValue("berecomUserId"), jsonObject.getIntValue("applicationId"));
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
            log.error(e.getMessage(), e);
        }
    }
    @RabbitListener(queues = "#{sendTemplateQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerMessageTemplate(Message message, Channel channel){
        String msgBody = "{}";
        try{
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            int userId=jsonObject.getIntValue("userId");
            int companyId=jsonObject.getIntValue("companyId");
            int type=jsonObject.getIntValue("type");
            int templateId=jsonObject.getIntValue("templateId");
            MessageTemplateNoticeStruct messageTemplate=messageTemplateEntity.handlerTemplate(userId,companyId,templateId,type);
            if(messageTemplate!=null){
                templateMsgProducer.messageTemplateNotice(messageTemplate);
            }

        }catch(Exception e){
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(Integer.valueOf(message.getMessageProperties().getAppId()));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(message.getMessageProperties().getReceivedExchange());
            logDeadLetterDO.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
            logDeadLetterDO.setQueueName(message.getMessageProperties().getConsumerQueue());
            logDeadLetterDao.addData(logDeadLetterDO);
            log.error(e.getMessage(), e);
        }
    }

}
