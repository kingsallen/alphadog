package com.moseeker.dict.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.dict.service.impl.PositionServiceImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;

/**
 * Created by chendi on 5/19/16.
 */
public class DictPositionServer {

    private static Logger LOGGER = LoggerFactory.getLogger(DictPositionServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
            		DictPositionServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(PositionServiceImpl.class)
            );
            server.start();

            synchronized (DictPositionServer.class) {
                while (true) {
                    try {
                        DictPositionServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider CollegeServer error", e);
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
        acac.scan("com.moseeker.dict");
        acac.refresh();
        return acac;
    }

}
