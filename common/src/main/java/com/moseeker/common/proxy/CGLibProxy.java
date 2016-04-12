package com.moseeker.common.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibProxy implements MethodInterceptor {

	private Object target;
	
	private Execution execution;
	
	public CGLibProxy (Object obj, Execution e) {
		this.target = obj;
		this.execution = e;
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		if(method.isAnnotationPresent(Log.class)) {
			execution.beforeMethod();
		}
        Object result = proxy.invoke(target, args);
        if(method.isAnnotationPresent(Log.class)) {
        	 execution.afterMethod();
		}
        return result;
	}

	public Object proxy() {
        return Enhancer.create(target.getClass(), new CGLibProxy(
                target, execution));
    }
}
