package com.moseeker.position;

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
                  acac.getBean(PositionServicesImpl.class)
          );
        	server.startServer();
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

    private static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.register(AppConfig.class);
        acac.refresh();
        return acac;
    }
}
