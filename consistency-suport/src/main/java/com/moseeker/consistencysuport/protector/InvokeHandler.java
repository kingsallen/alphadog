package com.moseeker.consistencysuport.protector;

import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

import java.lang.reflect.Method;

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
