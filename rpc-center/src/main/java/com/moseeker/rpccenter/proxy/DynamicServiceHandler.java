package com.moseeker.rpccenter.proxy;

import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zzh on 16/3/28.
 */
public class DynamicServiceHandler implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(DynamicServiceHandler.class);

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
            if (e.getCause() instanceof CURDException) {
                throw  (CURDException)e.getCause();
            }
            if (e.getCause() instanceof BIZException) {
                throw  (BIZException)e.getCause();
            }
            logger.error(e.getMessage(), e);
        	throw e;
        }
    }
}
