package com.moseeker.consistencysuport.producer.persistence;

import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

/**
 * Created by jack on 08/04/2018.
 */
public interface MessagePersistence {

    /**
     * 保存消息
     * @param message
     */
    void logMessage(Message message) throws ConsistencyException;
}
