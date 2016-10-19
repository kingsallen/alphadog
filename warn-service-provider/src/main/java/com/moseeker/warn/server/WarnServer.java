package com.moseeker.warn.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.warn.service.EventConfigService;

/**
 * @author lucky8987
 * 
 */
public class WarnServer {
	
	public static void main(String[] args) throws Exception {
		initSpring();
		EventConfigService.eventMap.forEach((eventKey, event) -> {
			System.out.println(eventKey);
			System.out.println(event);
		});
		while(true);
	}
	
	/**
	 * 加载spring容器
	 * @return
	 */
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.warn");
		annConfig.refresh();
		annConfig.start();
		return annConfig;
	}
}
