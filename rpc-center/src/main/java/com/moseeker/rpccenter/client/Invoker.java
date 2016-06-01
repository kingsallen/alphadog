package com.moseeker.rpccenter.client;

import java.lang.reflect.Method;

import com.moseeker.rpccenter.exception.RpcException;

/**
 * Created by zzh on 16/3/30.
 */
public interface Invoker {
    /**
     * 调用
     * <p>
     *
     * @param method
     * @param args
     * @return result
     * @throws RpcException
     */
    Object invoke(Method method, Object[] args) throws RpcException;
}
