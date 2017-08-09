package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
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

    @RabbitListener(queues = "#{addAwardQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void addAwardHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            employeeEntity.addAwardBefor(jsonObject.getIntValue("employeeId"), jsonObject.getIntValue("companyId"),
                    jsonObject.getIntValue("positionId"), jsonObject.getIntValue("templateId"), jsonObject.getIntValue("berecomUserId"));
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
}
