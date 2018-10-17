package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 入职申请通过后给员工加入职奖金
 * @Date: 2018/9/27
 * @Author: JackYang
 */

@Component
public class ReferralBonusReceiveTool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadPool tp = ThreadPool.Instance;

    @Autowired
    private EmployeeEntity employeeEntity;

    @RabbitListener(queues = "#{addBonusQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void  referralBonusReceive(Message message){
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("ReferralBonusReceiveTool referralBonusReceive msgBody : {}", msgBody);
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            Integer applicationId = jsonObject.getIntValue("applicationId");
            Integer nowStage = jsonObject.getIntValue("nowStage");
            Integer nextStage = jsonObject.getIntValue("nextStage");
            Integer positionId = jsonObject.getIntValue("positionId");
            Integer move = jsonObject.getIntValue("move");
            Integer applierId = jsonObject.getIntValue("applierId");
            employeeEntity.addReferralBonus(applicationId,nowStage,nextStage,move,positionId,applierId);

        } catch (CommonException e) {
            logger.info(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
