package com.moseeker.baseorm.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.baseorm.service.ThirdpartAccountService;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
/*
 * baseorm-service-provider的启动类
 */
public class BaseOrmServer {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseOrmServer.class);
    
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.baseorm");
		annConfig.refresh();
		return annConfig;
	}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 AnnotationConfigApplicationContext acac = initSpring();
	        try {
	            Server server = new Server(BaseOrmServer.class,ServerNodeUtils.getPort(args),acac.getBean(ThirdpartAccountService.class));
	            server.start();

	            synchronized (BaseOrmServer.class) {
	                while (true) {
	                    try {
	                    	BaseOrmServer.class.wait();
	                    } catch (Exception e) {
	                        LOGGER.error(" service provider BaseOrmServer error", e);
	                    }
	                }
	            }
	        } catch (Exception e) {
	            LOGGER.error("error", e);
	        }
	}

}
