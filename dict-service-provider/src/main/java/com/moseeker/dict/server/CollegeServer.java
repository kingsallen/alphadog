package com.moseeker.dict.server;

import com.moseeker.dict.service.impl.CollegeServicesImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by chendi on 5/19/16.
 */
public class CollegeServer {

    private static Logger LOGGER = LoggerFactory.getLogger(CollegeServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    CollegeServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(CollegeServicesImpl.class)
            );
            server.start();

            synchronized (CollegeServer.class) {
                while (true) {
                    try {
                        CollegeServer.class.wait();
                    } catch (InterruptedException e) {
                        LOGGER.error("error", e);
                        e.printStackTrace();
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
