package com.moseeker.baseorm.server;

import com.moseeker.baseorm.Thriftservice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.rpccenter.main.MultiRegServer;
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
//	        	MultiRegServer server  = new MultiRegServer(
//	        			BaseOrmServer.class,
//	        			ServerNodeUtils.getPort(args),
//	        			acac.getBean(HRAccountThriftService.class),
//	        			acac.getBean(WordpressDaoThriftService.class),
//	        			acac.getBean(CompanyThriftService.class),
//	        			acac.getBean(PositionDaoThriftService.class),
//	        			acac.getBean(UserDBDaoThriftService.class),
//	        			acac.getBean(DictDaoThriftService.class),
//	        			acac.getBean(DictDaoMapThriftService.class),
//	        			acac.getBean(JobDBDaoThriftService.class),
//	        			acac.getBean(PositionThriftService.class),
//	        			acac.getBean(ApplicationDaoThriftService.class),
//	        			acac.getBean(ConfigDBDaoThriftService.class),
//	        			acac.getBean(HrDBThriftService.class),
//	        			acac.getBean(WxUserDaoThriftService.class)
//	        	);
//	            server.start();
	            
	        	MoServer server  = new MoServer(
	        			acac,"",
	        			acac.getBean(HRAccountDaoThriftService.class),
	        			acac.getBean(WordpressDaoThriftService.class),
	        			acac.getBean(CompanyThriftService.class),
	        			acac.getBean(PositionDaoThriftService.class),
	        			acac.getBean(UserDBDaoThriftService.class),
	        			acac.getBean(DictDaoThriftService.class),
	        			acac.getBean(DictDaoMapThriftService.class),
	        			acac.getBean(JobDBDaoThriftService.class),
	        			acac.getBean(ApplicationDaoThriftService.class),
	        			acac.getBean(ConfigDBDaoThriftService.class),
	        			acac.getBean(HrDBThriftService.class),
						acac.getBean(CandidateDaoThriftService.class),
	        			acac.getBean(WxUserDaoThriftService.class)
	        	);
	        	server.startServer();
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
	        	e.printStackTrace();
	            LOGGER.error("error", e);
	        }
	}

}
