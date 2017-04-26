package com.moseeker.rpccenter.client;

import java.lang.reflect.Method;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;

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
    Object invoke(Method method, Object[] args) throws CURDException, BIZException, RpcException;
}
