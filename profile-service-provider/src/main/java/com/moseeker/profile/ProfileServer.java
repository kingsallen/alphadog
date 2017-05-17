package com.moseeker.profile;

import com.moseeker.profile.conf.AppConfig;
import com.moseeker.profile.thrift.*;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
	
	private static Logger LOGGER = LoggerFactory.getLogger(ProfileServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			MoServer server = new MoServer(acac,
					"",
					acac.getBean(ProfileServicesImpl.class),
					acac.getBean(ProfileAwardsServicesImpl.class),
					acac.getBean(ProfileBasicServicesImpl.class),
					acac.getBean(ProfileCredentialsServicesImpl.class),
					acac.getBean(ProfileCustomizeResumeServicesImpl.class),
					acac.getBean(ProfileEducationServicesImpl.class),
					acac.getBean(ProfileImportServicesImpl.class),
					acac.getBean(ProfileIntentionServicesImpl.class),
					acac.getBean(ProfileLanguageServicesImpl.class),
					acac.getBean(ProfileProjectExpServicesImpl.class),
					acac.getBean(ProfileSkillServicesImpl.class),
					acac.getBean(ProfileWorkExpServicesImpl.class),
					acac.getBean(ProfileWorksServicesImpl.class),
					acac.getBean(WholeProfileServicesImpl.class),
					acac.getBean(ProfileAttachmentServicesImpl.class),
					acac.getBean(ProfileOtherThriftServiceImpl.class));
			// 启动服务，非阻塞
			try {
				server.startServer();

				synchronized (ProfileServer.class) {
                    while (true) {
                        try {
                            ProfileServer.class.wait();
                        } catch (Exception e) {
                            LOGGER.error(" service provider ProfileServer error", e);
                        }
                    }
                }
			} catch (IncompleteException | RpcException | RegisterException | BeansException e) {
				e.printStackTrace();
				server.stopServer();
			} finally {
			}
		} catch (Exception e) {
			LOGGER.error("error", e);
			e.printStackTrace();
		}
	}

	private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.register(AppConfig.class);
		acac.refresh();
		return acac;
	}
}
