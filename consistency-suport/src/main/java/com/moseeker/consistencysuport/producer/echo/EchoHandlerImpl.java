package com.moseeker.consistencysuport.producer.echo;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.consistencysuport.Message;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.apache.commons.lang.StringUtils;
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
    private Notification notification;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public EchoHandlerImpl(MessageRepository messageRepository, Notification notification) {
        this.messageRepository = messageRepository;
        this.notification = notification;
    }

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
        try {
            if (StringUtils.isNotBlank(message.getMessageName()) && StringUtils.isNotBlank(message.getBusinessName())) {
                messageRepository.registerBusiness(message.getMessageName().trim(), message.getBusinessName().trim());
            } else {
                notification.noticeForError(ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_BUSINESS_REGISTER_PARAM_ERROR);
            }
        } catch (ConsistencyException e) {
            notification.noticeForException(e);
        } catch (Exception e) {
            notification.noticeForError(e);
        }
    }

    private void finishBusiness(Message message) {
        try {
            if (StringUtils.isNotBlank(message.getMessageId()) && StringUtils.isNotBlank(message.getBusinessName())) {
                messageRepository.finishBusiness(message.getMessageId(), message.getBusinessName());
            } else {
                notification.noticeForError(ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_MESSAGE_FINISH_PARAM_ERROR);
            }
        } catch (ConsistencyException e) {
            notification.noticeForException(e);
        } catch (Exception e) {
            notification.noticeForError(e);
        }
    }

    private void heartBeat(Message message) {
        try {
            if (StringUtils.isNotBlank(message.getMessageName()) && StringUtils.isNotBlank(message.getBusinessName())) {
                messageRepository.heartBeat(message.getMessageName(), message.getBusinessName());
            } else {
                notification.noticeForError(ConsistencyException.CONSISTENCY_PRODUCER_UPDATE_BUSINESS_HEART_BEAT_PARAM_ERROR);
            }
        } catch (ConsistencyException e) {
            notification.noticeForException(e);
        } catch (Exception e) {
            notification.noticeForError(e);
        }
    }
}
