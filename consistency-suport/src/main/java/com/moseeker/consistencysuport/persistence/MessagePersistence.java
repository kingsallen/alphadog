package com.moseeker.consistencysuport.persistence;

import com.moseeker.consistencysuport.db.Message;
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
