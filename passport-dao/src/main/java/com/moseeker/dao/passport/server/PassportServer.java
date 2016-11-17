package com.moseeker.dao.passport.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/*
 * baseorm-service-provider的启动类
 */
public class PassportServer {
    private static Logger LOGGER = LoggerFactory.getLogger(PassportServer.class);
    
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.dao.passport");
		annConfig.refresh();
		return annConfig;
	}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 AnnotationConfigApplicationContext acac = initSpring();
	        try {
	        	/*MultiRegServer server  = new MultiRegServer(
	        			PassportServer.class,
	        			ServerNodeUtils.getPort(args),
	        			acac.getBean(JobPositionThriftService.class),
	        			acac.getBean(HRAccountThriftService.class),
	        			acac.getBean(WordpressDaoThriftService.class),
	        			acac.getBean(CompanyThriftService.class),
	        			acac.getBean(ThirdpartAccountThriftService.class)
	        	);
	            server.start();*/

	            synchronized (PassportServer.class) {
	                while (true) {
	                    try {
	                    	PassportServer.class.wait();
	                    } catch (Exception e) {
	                        LOGGER.error(" service provider BaseOrmServer error", e);
	                    }
	                }
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	            LOGGER.error("error", e);
	        }
	}

}
