package com.moseeker.common.aop.dao;

import java.util.Arrays;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.dao.SqlListener;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class NotifyEsAop {
	
	ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
		protected String initialValue() {
			return "{}";
		}
	};
	
	@Before(value="@annotation(sl)", argNames = "sl")
	public void doBefore(SqlListener sl) {
		threadLocal.set(Arrays.toString(sl.value()));
	}
	
	@AfterReturning(pointcut="@annotation(com.moseeker.common.annotation.dao.SqlListener)", returning="returnValue")
	public void afterReturn(Object returnValue) {
		if(returnValue != null) {
			JSONObject jsb = JSONObject.parseObject(JSON.toJSONString(returnValue));
			if (jsb.getIntValue("status") == 0) {
				System.err.println(threadLocal.get());
			}
		}
	}
}
