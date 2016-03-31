package com.moseeker.rpccenter.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.moseeker.rpccenter.client.Invoker;

/**
 * Created by zzh on 16/3/30.
 */
public class DynamicClientHandler implements InvocationHandler{

    /** {@link Invoker} */
    private final Invoker invoker;

    /**
     * @param invoker
     */
    public DynamicClientHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * 动态代理绑定实例
     * <p>
     *
     * @param classLoader
     *            {@link ClassLoader}
     * @param serviceClass
     *            {@link Class}
     * @return {@link Object}
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public <T> T bind(ClassLoader classLoader, Class<?> serviceClass) throws ClassNotFoundException {
        return (T) Proxy.newProxyInstance(classLoader, new Class[] { serviceClass }, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invoker.invoke(method, args);
    }
}
