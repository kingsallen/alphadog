package com.moseeker.profile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.profile.service.impl.ProfileAttachmentServicesImpl;
import com.moseeker.profile.service.impl.ProfileAwardsServicesImpl;
import com.moseeker.profile.service.impl.ProfileBasicServicesImpl;
import com.moseeker.profile.service.impl.ProfileCredentialsServicesImpl;
import com.moseeker.profile.service.impl.ProfileCustomizeResumeServicesImpl;
import com.moseeker.profile.service.impl.ProfileEducationServicesImpl;
import com.moseeker.profile.service.impl.ProfileImportServicesImpl;
import com.moseeker.profile.service.impl.ProfileIntentionServicesImpl;
import com.moseeker.profile.service.impl.ProfileLanguageServicesImpl;
import com.moseeker.profile.service.impl.ProfileProjectExpServicesImpl;
import com.moseeker.profile.service.impl.ProfileServicesImpl;
import com.moseeker.profile.service.impl.ProfileSkillServicesImpl;
import com.moseeker.profile.service.impl.ProfileWorkExpServicesImpl;
import com.moseeker.profile.service.impl.ProfileWorksServicesImpl;
import com.moseeker.profile.service.impl.WholeProfileServicesImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MultiRegServer;

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
			MultiRegServer server = new MultiRegServer(ProfileServer.class,
					ServerNodeUtils.getPort(args), 
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
					acac.getBean(ProfileAttachmentServicesImpl.class));
			server.start(); // 启动服务，非阻塞

			synchronized (ProfileServer.class) {
				while (true) {
					try {
						ProfileServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider ProfileServer error", e);
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
