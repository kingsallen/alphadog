package com.moseeker.consistencysuport.echo;

import com.moseeker.consistencysuport.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

/**
 *
 * 客户端消息回收处理
 *
 * Created by jack on 09/04/2018.
 */
public interface EchoHandler {

    /**
     * 消息处理
     * @param message 消息体
     * @throws ConsistencyException
     */
    void handlerMessage(String message) throws ConsistencyException;
}
