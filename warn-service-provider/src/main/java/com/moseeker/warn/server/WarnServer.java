package com.moseeker.warn.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.warn.service.EventConfigService;

/**
 * @author ltf
 * 
 */
public class WarnServer {
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = initSpring();
		EventConfigService bean = context.getBean(EventConfigService.class);
		bean.getEvents().forEach((eventKey, event) -> {
			System.out.println(eventKey);
			System.out.println(event);
		});
	}
	
	/**
	 * 加载spring容器
	 * @return AnnotationConfigApplicationContext
	 */
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.warn");
		annConfig.refresh();
		return annConfig;
	}
}
