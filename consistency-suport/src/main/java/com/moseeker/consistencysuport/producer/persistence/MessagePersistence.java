package com.moseeker.consistencysuport.producer.persistence;

import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.producer.MessageTypePojo;
import com.moseeker.consistencysuport.producer.db.Message;

import java.util.List;

/**
 * Created by jack on 08/04/2018.
 */
public interface MessagePersistence {

    /**
     * 保存消息
     * @param message
     */
    void logMessage(Message message) throws ConsistencyException;

    /**
     * 初始化数据库
     * @throws ConsistencyException
     */
    void initDB() throws ConsistencyException;

    /**
     * 注册消息类型
     * @param messageTypePojoList
     */
    void registerMessageType(List<MessageTypePojo> messageTypePojoList);
}
