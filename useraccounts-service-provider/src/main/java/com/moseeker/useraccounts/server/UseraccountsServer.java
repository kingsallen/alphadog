package com.moseeker.useraccounts.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.profile.service.impl.ProfileServicesImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;

/**
 * 
 * 服务启动入口。服务启动依赖所需的配置文件serviceprovider.properties中的配置信息。务必保证配置信息正确
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
					acac.getBean(ProfileServicesImpl.class),
					ServerNodeUtils.getPort(args));
			server.start(); // 启动服务，非阻塞

			synchronized (UseraccountsServer.class) {
				while (true) {
					try {
						UseraccountsServer.class.wait();
					} catch (InterruptedException e) {
						LOGGER.error("error", e);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("error", e);
			e.printStackTrace();
		}
	}

	private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker.useraccounts");
		acac.refresh();
		return acac;
	}
}
