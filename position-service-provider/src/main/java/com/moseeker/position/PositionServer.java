package com.moseeker.position;

import com.moseeker.position.thrift.ReferralPositionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.thrift.PositionServicesImpl;
import com.moseeker.rpccenter.main.MoServer;

public class PositionServer {

    private static Logger LOGGER = LoggerFactory.getLogger(PositionServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
        	MoServer server = new MoServer(
                  acac,"",
                  acac.getBean(PositionServicesImpl.class),acac.getBean(ReferralPositionServiceImpl.class)
          );
        	server.startServer();
            server.shutDownHook();
            synchronized (PositionServer.class) {
                while (true) {
                    try {
                        PositionServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider PositionServer error", e);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }

    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    private static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.register(AppConfig.class);
        acac.refresh();
        return acac;
    }
}
