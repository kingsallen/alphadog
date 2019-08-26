package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserWorkwxDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.EmployeeEntity;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户关注和取消关注时更新员工身份。
 * 如果用户取关，则取消员工身份；如果是取关导致的员工身份丢失，那么关注需要重新恢复员工身份。
 * @Author: jack
 * @Date: 2018/8/19
 */
@Component
public class FollowStateSynchronizationTool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private UserWorkwxDao workwxDao ;

    @RabbitListener(queues = "#{followWechatQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void recoverEmployee(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("FollowStateSynchronizationTool followWechat msgBody : {}", msgBody);
            FollowParam followParam = JSON.parseObject(msgBody, FollowParam.class);
            employeeEntity.followWechat(followParam.getUserId(), followParam.getWechatId(),
                    followParam.getSubscribeTime());
        } catch (CommonException e) {
            logger.info(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{unFollowWechatQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void cancelEmployee(Message message) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("FollowStateSynchronizationTool followWechat msgBody : {}", msgBody);
            FollowParam followParam = JSON.parseObject(msgBody, FollowParam.class);
            employeeEntity.unfollowWechat(followParam.getUserId(), followParam.getWechatId(),
                    followParam.getSubscribeTime());
            workwxDao.unbindSysUser(followParam.getUserId());
        } catch (CommonException e) {
            logger.info(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
