package com.moseeker.profile.server;

import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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


public class DemoServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DemoServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			MoServer server = new MoServer(acac,
					"server.properties"));
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
		acac.scan("com.moseeker.demo");
		acac.scan("com.moseeker.common.aop.iface");
		acac.refresh();
		return acac;
	}
}
