package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.useraccounts.service.Neo4jService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 员工数据更新时用于neo4j的数据更新
 * @Date: 2018/9/27
 * @Author: JackYang
 */

@Component
public class EmployeeChangeReceiveTool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private Neo4jService neo4jService;

    @RabbitListener(queues = "#{employeeChangeQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void  employeeActivationChange(Message message){
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("employeeActivationChange  msgBody : {}", msgBody);
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            if(message.getMessageProperties().getReceivedRoutingKey().equals("user_neo4j.employee_company_update")) {
                Integer companyId = jsonObject.getIntValue("companyId");
                List<Integer> userIds = (List<Integer>) jsonObject.getOrDefault("userIds", new ArrayList<>());
                logger.info("employeeActivationChange userIds:{}",userIds);
                neo4jService.updateUserEmployeeCompany(userIds, companyId);
            }else if(message.getMessageProperties().getReceivedRoutingKey().equals("user_neo4j.friend_update")){
                Integer startUserid = jsonObject.getIntValue("start_user_id");
                Integer endUserId = jsonObject.getIntValue("end_user_id");
                Integer shareChainId = jsonObject.getIntValue("share_chain_id");
                neo4jService.addFriendRelation(startUserid,endUserId,shareChainId);
            }
        } catch (CommonException e) {
            logger.info(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
