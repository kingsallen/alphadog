package com.moseeker.warn.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.warn.thrift.WarnThriftService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ltf
 * 预警模块启动器
 */
@Configuration
@ComponentScan(basePackages = {"com.moseeker.warn", "com.moseeker.baseorm"})
public class WarnServer {

    @Autowired
    private static WarnThriftService service;

    @Autowired
    private static AnnotationConfigApplicationContext context;
	
	public static void main(String[] args){
		try{
//			AnnotationConfigApplicationContext context = initSpring();
//			Server server=new Server(WarnServer.class,ServerNodeUtils.getPort(args),context.getBean(WarnThriftService.class));
//			server.start();
			MoServer server=new MoServer(context,"", service);
			server.startServer();
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
	
//	/**
//	 * 加载spring容器
//	 * @return AnnotationConfigApplicationContext
//	 */
//	public static AnnotationConfigApplicationContext initSpring() {
//		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
//		annConfig.scan("com.moseeker.warn");
//		annConfig.scan("com.moseeker.baseorm");
//		annConfig.refresh();
//		return annConfig;
//	}
}
