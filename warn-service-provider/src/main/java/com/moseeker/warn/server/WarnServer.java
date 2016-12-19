package com.moseeker.warn.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.warn.service.impl.WarnServiceImpl;
import com.moseeker.warn.service.manager.SendManager;

/**
 * @author ltf
 * 预警模块启动器
 */
public class WarnServer {
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
