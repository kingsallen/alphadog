package com.moseeker.application.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;

/**
 * Created by zzh on 16/5/24.
 */
public class ToolServer {

    private static Logger LOGGER = LoggerFactory.getLogger(ToolServer.class);

    public static void main(String[] args) {

        try {
        	AnnotationConfigApplicationContext acac = initSpring();
			/*Server server = new Server(ToolServer.class,
					ServerNodeUtils.getPort(args),
					acac.getBean(ProfileAttachmentServicesImpl.class));
			server.start();*/ // 启动服务，非阻塞

			synchronized (ToolServer.class) {
				while (true) {
					try {
						ToolServer.class.wait();
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
		acac.scan("com.moseeker.profile");
		acac.refresh();
		return acac;
	}
}
