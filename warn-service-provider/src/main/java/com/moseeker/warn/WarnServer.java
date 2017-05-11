package com.moseeker.warn;

import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.warn.config.AppConfig;
import com.moseeker.warn.thrift.WarnThriftService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ltf
 * 预警模块启动器
 */
public class WarnServer {

	public static void main(String[] args){
		try{
			AnnotationConfigApplicationContext context = initSpring();
//			Server server=new Server(WarnServer.class,ServerNodeUtils.getPort(args),context.getBean(WarnThriftService.class));
//			server.start();
			MoServer server=new MoServer(context,"",context.getBean(WarnThriftService.class));
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
	
	/**
	 * 加载spring容器
	 * @return AnnotationConfigApplicationContext
	 */
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.register(AppConfig.class);
		annConfig.refresh();
		return annConfig;
	}
}
