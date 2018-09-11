package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.exception.CommonException;
import com.moseeker.useraccounts.domain.UpVoteEntity;
import com.moseeker.useraccounts.domain.pojo.ViewLeaderBoardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 记录员工查看点赞数时间
 *
 * @Author: jack
 * @Date: 2018/8/20
 */
@Component
public class LogViewLeaderBoardTimeTool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UpVoteEntity upVoteEntity;

//    @RabbitListener(queues = "#{clearUnViewdUpVoteQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void logViewLeaderBoardTime(Message message) {

        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            logger.info("logViewLeaderBoardTime logViewLeaderBoardTime msgBody : {}", msgBody);
            ViewLeaderBoardVO vo = JSON.parseObject(msgBody, ViewLeaderBoardVO.class);
            upVoteEntity.logViewTime(vo.getEmployeeId(), vo.getViewTime());
        } catch (CommonException e) {
            logger.info(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
