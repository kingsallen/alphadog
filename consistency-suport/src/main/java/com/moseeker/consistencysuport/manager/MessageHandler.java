package com.moseeker.consistencysuport.manager;

import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 消息处理工具
 *
 * Created by jack on 03/04/2018.
 */
public class MessageHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    MessageRepository messageRepository;

    public MessageHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        messageRepository.initDatabase();
    }

    /**
     * 记录消息
     * @param messageId 消息编号
     * @param name 名称
     * @param param 参数
     * @param period
     */
    public void logMessage(String messageId, String name, String param, String className, String method, int period) throws ConsistencyException {

        logger.debug("MessageHandler logMessage  messageId:{}, name:{}, param:{}", messageId, name, param);

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

        logger.info("MessageHandler logMessage  messageId:{}, name:{}, param:{}", messageId, name, param);
    }
}
