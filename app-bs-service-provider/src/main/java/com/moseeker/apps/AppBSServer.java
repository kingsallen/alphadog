package com.moseeker.apps;

import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.apps.config.AppConfig;
import com.moseeker.apps.thrift.service.PositionBSThriftService;
import com.moseeker.apps.thrift.service.ProfileBSThriftService;
import com.moseeker.apps.thrift.service.UserBSThriftService;

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

    private static Logger logger = LoggerFactory.getLogger(AppBSServer.class);
    
    public static void main(String[] args) {


		try {
			AnnotationConfigApplicationContext acac = initSpring();
			MoServer server = new MoServer(
					acac,"",
					acac.getBean(PositionBSThriftService.class),
					acac.getBean(ProfileBSThriftService.class),
					acac.getBean(UserBSThriftService.class));
			server.startServer();
			// 启动服务，非阻塞
			synchronized (AppBSServer.class) {
                while (true) {
                    try {
                        AppBSServer.class.wait();
                    } catch (Exception e) {
						logger.error(" service provider ProfileAttachmentServer error", e);
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
    
    private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.register(AppConfig.class);
		acac.refresh();
		return acac;
	}
}
