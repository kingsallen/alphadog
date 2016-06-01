package com.moseeker.profile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.profile.service.impl.ProfileEducationServicesImpl;
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
public class ProfileEducationServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ProfileEducationServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			Server server = new Server(ProfileEducationServer.class,
					ServerNodeUtils.getPort(args),
					acac.getBean(ProfileEducationServicesImpl.class));
			server.start(); // 启动服务，非阻塞

			synchronized (ProfileEducationServer.class) {
				while (true) {
					try {
						ProfileEducationServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider ProfileEducationServer error", e);
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
		acac.scan("com.moseeker.profile");
		acac.refresh();
		return acac;
	}
}
