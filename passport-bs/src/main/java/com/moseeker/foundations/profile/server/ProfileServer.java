package com.moseeker.foundations.profile.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * profile业务处理服务
 * @author wjf
 *
 */
public class ProfileServer {

    private static Logger LOGGER = LoggerFactory.getLogger(ProfileServer.class);
    
    public static void main(String[] args) {

        try {
        	AnnotationConfigApplicationContext acac = initSpring();
        	/*MultiRegServer server = new MultiRegServer(ProfileServer.class,
        			ServerNodeUtils.getPort(args),
					acac.getBean(FunctionService.class),
					acac.getBean(ChaosThriftService.class),
					acac.getBean(WordpressThriftService.class),
					acac.getBean(HRAccountThriftService.class));
			server.start(); // 启动服务，非阻塞
*/
			synchronized (ProfileServer.class) {
				while (true) {
					try {
						ProfileServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider ProfileAttachmentServer error", e);
                    }
				}
			}
        } catch (Exception e) {
            LOGGER.error("error", e);
        }

    }
    
    private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker.foundations");
		acac.scan("com.moseeker.common.aop.iface"); //开启接口统计
		acac.refresh();
		return acac;
	}
}
