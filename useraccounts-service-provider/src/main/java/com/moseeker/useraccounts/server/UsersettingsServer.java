package com.moseeker.useraccounts.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.useraccounts.service.impl.UsersettingsServicesImpl;

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
public class UsersettingsServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UsersettingsServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			Server server = new Server(UsersettingsServer.class,
					ServerNodeUtils.getPort(args),
					acac.getBean(UsersettingsServicesImpl.class));
			
			server.start(); // 启动服务，非阻塞

			synchronized (UsersettingsServer.class) {
				while (true) {
					try {
						UsersettingsServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider UsersettingsServer error", e);
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
