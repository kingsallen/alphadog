package com.moseeker.consistencysuport.producer.persistence;

import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.consistencysuport.producer.MessageTypePojo;
import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.common.MessageRepository;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 消息处理工具
 *
 * Created by jack on 03/04/2018.
 */
public class MessagePersistenceImpl implements MessagePersistence {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    MessageRepository messageRepository;

    public MessagePersistenceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * 记录消息
     * @param messageId 消息编号
     * @param name 名称
     * @param param 参数
     * @param period
     */
    public void logMessage(String messageId, String name, String param, String className, String method, int period) throws ConsistencyException {

        logger.debug("MessagePersistenceImpl logMessage  messageId:{}, name:{}, param:{}", messageId, name, param);

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("消息编号", messageId);
        validateUtil.addStringLengthValidate("消息编号", messageId, null, null, 1, 65);
        validateUtil.addRequiredValidate("业务名称", name);
        validateUtil.addStringLengthValidate("业务名称", name, null, null, 1, 21);
        validateUtil.addStringLengthValidate("参数", param, null, null, 0, 3000);
        validateUtil.addRequiredValidate("类名", className);
        validateUtil.addStringLengthValidate("类名", className, null, null, 1, 60);
        validateUtil.addRequiredValidate("方法名", method);
        validateUtil.addStringLengthValidate("方法名", method, null, null, 1, 60);
        validateUtil.addIntTypeValidate("时间间隔", period, null, null, 1, 12*30*24*60*60);
        String result = validateUtil.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ConsistencyException.validateFailed(result);
        }

        Message message = new Message();
        message.setName(name);
        message.setMessageId(messageId);
        message.setParam(param);
        message.setClassName(className);
        message.setMethod(method);
        message.setPeriod(period);

        messageRepository.saveMessage(message);

        logger.info("MessagePersistenceImpl logMessage  messageId:{}, name:{}, param:{}", messageId, name, param);
    }

    @Override
    public void logMessage(Message message) throws ConsistencyException {
        logger.debug("MessagePersistenceImpl logMessage  messageId:{}, name:{}, param:{}", message.getMessageId(), message.getName(), message.getParam());

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("消息编号", message.getMessageId());
        validateUtil.addStringLengthValidate("消息编号", message.getMessageId(), null, null, 1, 65);
        validateUtil.addRequiredValidate("业务名称", message.getName());
        validateUtil.addStringLengthValidate("业务名称", message.getName(), null, null, 1, 21);
        validateUtil.addStringLengthValidate("参数", message.getParam(), null, null, 0, 3000);
        validateUtil.addRequiredValidate("类名", message.getClassName());
        validateUtil.addStringLengthValidate("类名", message.getClassName(), null, null, 1, 60);
        validateUtil.addRequiredValidate("方法名", message.getMethod());
        validateUtil.addStringLengthValidate("方法名", message.getMethod(), null, null, 1, 60);
        validateUtil.addIntTypeValidate("时间间隔", message.getPeriod(), null, null, 1, 12*30*24*60*60);
        String result = validateUtil.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ConsistencyException.validateFailed(result);
        }
        messageRepository.saveMessage(message);

        logger.info("MessagePersistenceImpl logMessage  messageId:{}, name:{}, param:{}", message.getMessageId(), message.getName(), message.getParam());
    }

    @Override
    public void initDB() throws ConsistencyException {
        messageRepository.initDatabase();
    }

    @Override
    public void registerMessageType(List<MessageTypePojo> messageTypePojoList) {
        messageRepository.registerMessageType(messageTypePojoList);
    }
}
