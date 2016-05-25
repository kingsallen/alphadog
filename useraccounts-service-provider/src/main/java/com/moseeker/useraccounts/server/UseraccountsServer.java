package com.moseeker.useraccounts.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MultiRegServer;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.useraccounts.service.impl.UseraccountsServiceImpl;

/**
 * 
 * 服务启动入口。
 * 
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Mar 27, 2016
 * </p>
 * 
 * @author yaofeng
 * @version Beta
 */
public class UseraccountsServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UseraccountsServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			Server server = new Server(UseraccountsServer.class,
					ServerNodeUtils.getPort(args),
					acac.getBean(UseraccountsServiceImpl.class));
			
			server.start(); // 启动服务，非阻塞

			synchronized (UseraccountsServer.class) {
				while (true) {
					try {
						UseraccountsServer.class.wait();
					} catch (InterruptedException e) {
						LOGGER.error("error", e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
	}

	private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker.useraccounts");
		acac.refresh();
		return acac;
	}
}
