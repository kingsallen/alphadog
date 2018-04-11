package com.moseeker.consistencysuport.echo;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.consistencysuport.Message;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 消息处理
 *
 * Created by jack on 10/04/2018.
 */
public class EchoHandlerImpl implements EchoHandler {

    private MessageRepository messageRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handlerMessage(String content) throws ConsistencyException {

        Message message = JSONObject.parseObject(content, Message.class);
        switch (message.getMessageType()) {
            case Finish: finishBusiness(message);break;
            case HeartBeat:heartBeat(message);break;
            case Register:registerBusiness(message); break;
            default:
                logger.error("消息类型不正确！  message:{}", content);
        }

    }


    private void registerBusiness(Message message) {
        messageRepository.registerBusiness(message.getMessageId(), message.getBusinessName());
    }

    private void finishBusiness(Message message) {
        messageRepository.finishBusiness(message.getMessageId(), message.getBusinessName());
    }

    private void heartBeat(Message message) {
        messageRepository.heartBeat(message.getMessageId(), message.getBusinessName());
    }
}
