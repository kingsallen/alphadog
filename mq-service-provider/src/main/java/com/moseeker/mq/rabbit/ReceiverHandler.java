package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.entity.EmployeeEntity;
import com.rabbitmq.client.Channel;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private EmployeeEntity employeeEntity;

    @RabbitListener(queues = "#{addAwardQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void addAwardHandler(Message message, Channel channel) {
        try {
            String msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            employeeEntity.addReward(jsonObject.getIntValue("employeeId"), jsonObject.getIntValue("companyId"), jsonObject.getString("reason"), 0,
                    jsonObject.getIntValue("positionId"), jsonObject.getIntValue("templateId"), jsonObject.getIntValue("berecomUserId"));
        } catch (Exception e) {
            // TODO 错误日志记录到数据库
            e.printStackTrace();
        }
    }
}
