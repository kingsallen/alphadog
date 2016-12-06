package com.moseeker.profile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.profile.thrift.ProfileAttachmentServicesImpl;
import com.moseeker.profile.thrift.ProfileAwardsServicesImpl;
import com.moseeker.profile.thrift.ProfileBasicServicesImpl;
import com.moseeker.profile.thrift.ProfileCredentialsServicesImpl;
import com.moseeker.profile.thrift.ProfileCustomizeResumeServicesImpl;
import com.moseeker.profile.thrift.ProfileEducationServicesImpl;
import com.moseeker.profile.thrift.ProfileImportServicesImpl;
import com.moseeker.profile.thrift.ProfileIntentionServicesImpl;
import com.moseeker.profile.thrift.ProfileLanguageServicesImpl;
import com.moseeker.profile.thrift.ProfileProjectExpServicesImpl;
import com.moseeker.profile.thrift.ProfileServicesImpl;
import com.moseeker.profile.thrift.ProfileSkillServicesImpl;
import com.moseeker.profile.thrift.ProfileWorkExpServicesImpl;
import com.moseeker.profile.thrift.ProfileWorksServicesImpl;
import com.moseeker.profile.thrift.WholeProfileServicesImpl;
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
		acac.scan("com.moseeker.common.aop.iface");
		acac.refresh();
		return acac;
	}
}
