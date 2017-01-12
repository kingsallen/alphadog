package com.moseeker.common.aop.dao;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.moseeker.common.annotation.dao.SqlListener;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class NotifyEsAop {
	
	@Before(value="@annotation(sl)", argNames = "sl")
	public void doBefore(SqlListener sl) {
		System.out.println("tableName:"+sl.value());
	}
	
	@AfterReturning(pointcut="@annotation(com.moseeker.common.annotation.dao.SqlListener)", returning="returnValue")
	public void afterReturn(Object returnValue) {
		if(returnValue != null) {
			System.out.println(returnValue);
		}
	}
}
