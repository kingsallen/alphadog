package com.moseeker.warn.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.warn.service.ManageService;
import com.moseeker.warn.thrift.WarnThriftService;

/**
 * @author ltf
 * 预警模块启动器
 */
public class WarnServer {
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = initSpring();
		Server server=new Server(WarnServer.class,ServerNodeUtils.getPort(args),context.getBean(WarnThriftService.class));
		server.start();
		Thread sendMsgThread = new Thread(() -> {
			context.getBean(ManageService.class).sendMessage();
		});
		sendMsgThread.start();
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
