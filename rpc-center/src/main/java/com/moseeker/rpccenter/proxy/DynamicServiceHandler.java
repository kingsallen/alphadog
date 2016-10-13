package com.moseeker.rpccenter.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.moseeker.rpccenter.common.ServerNode;

/**
 * Created by zzh on 16/3/28.
 */
public class DynamicServiceHandler implements InvocationHandler {

    /** 实际处理实例 */
    private Object target;

    /** {@link ServerNode} */
    private ServerNode serverNode;

    /**
     * 动态代理绑定实例
     * <p>
     *
     * @param classLoader
     * @param serviceClass
     * @param target
     * @param serverNode
     * @return 服务处理类代理
     * @throws ClassNotFoundException
     */
    public Object bind(ClassLoader classLoader, Class<?> serviceClass, Object target, ServerNode serverNode) throws ClassNotFoundException {
        this.target = target;
        this.serverNode = serverNode;
        return Proxy.newProxyInstance(classLoader, new Class[] { serviceClass }, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Object result = method.invoke(target, args);
            return result;
        } catch (Exception e) {
        	e.printStackTrace();
            throw e;
        }
    }
}
