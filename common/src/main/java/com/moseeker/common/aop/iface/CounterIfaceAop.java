package com.moseeker.common.aop.iface;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterInfo;



/**
 * @author ltf
 * 接口统计 Aop
 * 2016年10月31日
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class CounterIfaceAop {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 切入点
	 */
	private static final String POINCUT="@within(com.moseeker.common.annotation.iface.CounterIface) || @annotation(com.moseeker.common.annotation.iface.CounterIface)";
	
	ThreadLocal<CounterInfo> counterLocal = new ThreadLocal<CounterInfo>(){
		@Override
		protected CounterInfo initialValue() {
			return new CounterInfo();
		}
	};
	
	/**
	 * enter before
	 * @param call
	 */
	@Before(value=POINCUT)
	public void doBefore(JoinPoint call) {
		counterLocal.get().setArgs(Arrays.toString(call.getArgs()));
		counterLocal.get().setClassName(call.getTarget().getClass().getName());
		counterLocal.get().setMethod(call.getSignature().getName());
		counterLocal.get().setStartTime(new Date().getTime());
	}
	
	/**
	 * throws exception
	 */
	@AfterThrowing(value=POINCUT)
	public void afterThrowing(JoinPoint call){
		counterLocal.get().setStatus("fail");
		counterLocal.get().setEndTime(new Date().getTime());
		counterLocal.get().setTime(counterLocal.get().getEndTime()-counterLocal.get().getStartTime());
		log.info("counterInfo:{}", JSONObject.toJSONString(counterLocal.get()));
	}
	
	/**
	 * return after
	 */
	@AfterReturning(value=POINCUT)
	public void afterReturn(JoinPoint call){
		counterLocal.get().setStatus("success");
		counterLocal.get().setEndTime(new Date().getTime());
		counterLocal.get().setTime(counterLocal.get().getEndTime()-counterLocal.get().getStartTime());
		log.info("counterInfo:{}", JSONObject.toJSONString(counterLocal.get()));
	}

}
