package com.moseeker.apps.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.apps.thrift.service.ProfileBSThriftService;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MultiRegServer;

/**
 * 
 * 应用层服务
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class AppBSServer {

    private static Logger LOGGER = LoggerFactory.getLogger(AppBSServer.class);
    
    public static void main(String[] args) {

        try {
        	AnnotationConfigApplicationContext acac = initSpring();
        	MultiRegServer server = new MultiRegServer(AppBSServer.class,
        			ServerNodeUtils.getPort(args),
					acac.getBean(ProfileBSThriftService.class));
			server.start(); // 启动服务，非阻塞

			synchronized (AppBSServer.class) {
				while (true) {
					try {
						AppBSServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider ProfileAttachmentServer error", e);
                    }
				}
			}
        } catch (Exception e) {
        	e.printStackTrace();
            LOGGER.error("error", e);
        }

    }
    
    private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker.apps");
		acac.scan("com.moseeker.common.aop.iface"); //开启接口统计
		acac.refresh();
		return acac;
	}
}
