package com.moseeker.warn.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

<<<<<<< HEAD
import com.moseeker.warn.service.ManageService;
=======
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.warn.service.impl.WarnServiceImpl;
import com.moseeker.warn.service.manager.SendManager;
>>>>>>> feature/baseorm-service-provider

/**
 * @author ltf
 * 预警模块启动器
 */
public class WarnServer {
<<<<<<< HEAD
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = initSpring();
		Thread sendMsgThread = new Thread(() -> {
			context.getBean(ManageService.class).sendMessage();
		});
		sendMsgThread.start();
=======
//	private static Logger LOGGER = LoggerFactory.getLogger(WarnServer.class);
	public static void main(String[] args){
		try{
			AnnotationConfigApplicationContext context = initSpring();
			Server server=new Server(WarnServer.class,ServerNodeUtils.getPort(args),context.getBean(WarnServiceImpl.class));
			server.start();
			SendManager send=new SendManager();
			send.start();
			synchronized (WarnServer.class) {
				while(true){
					try{
						WarnServer.class.wait();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
>>>>>>> feature/baseorm-service-provider
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
