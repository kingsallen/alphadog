package com.moseeker.profile.server;

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
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version Beta
 */
public class ProfileServer {

	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();

			Server server = new Server(ProfileServer.class,
					acac.getBean(ProfileServicesImpl.class),
					ServerNodeUtils.getPort(args));
			server.start(); // 启动服务，非阻塞

			synchronized (ProfileServer.class) {
				while (true) {
					try {
						System.out.println("release thread pool before");
						ProfileServer.class.wait();
						System.out.println("release thread pool after");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker");
		acac.refresh();
		return acac;
	}
}
