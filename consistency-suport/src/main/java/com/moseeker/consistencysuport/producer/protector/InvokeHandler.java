package com.moseeker.consistencysuport.producer.protector;

import com.moseeker.consistencysuport.producer.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

/**
 *
 * 提供解析调用方法的接口
 *
 * Created by jack on 09/04/2018.
 */
public interface InvokeHandler {

    /**
     * 获取提供的调用方法
     * @param message
     * @return
     */
    void invoke(Message message) throws ConsistencyException;
}
