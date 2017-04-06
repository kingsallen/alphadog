package com.moseeker.common.aop;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ltf
 * 接口统计测试类
 * 2016年11月7日
 */
public class CounterIfaceAopTest {
	
	private CounterIfaceService service;

	@Before
	public void before(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.common.aop");
		context.refresh();
		service = context.getBean(CounterIfaceService.class);
	}
	
	//@Test
	public void counter(){
		service.display("vincent");
	}
}
