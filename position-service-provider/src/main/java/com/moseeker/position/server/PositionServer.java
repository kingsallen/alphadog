package com.moseeker.position.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.position.thrift.PositionServicesImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.rpccenter.main.Server;

public class PositionServer {

    private static Logger LOGGER = LoggerFactory.getLogger(PositionServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
//            Server server = new Server(
//                    PositionServer.class,
//                    ServerNodeUtils.getPort(args),
//                    acac.getBean(PositionServicesImpl.class)
//            );
//            server.start();
        	MoServer server = new MoServer(
                  acac,"server.properties",
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
        acac.scan("com.moseeker.position");
        acac.scan("com.moseeker.common.aop.iface");
        acac.refresh();
        return acac;
    }
}
