package com.moseeker.dict.server;

import com.moseeker.dict.service.impl.CityServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by chendi on 5/19/16.
 */
public class CityServer {

    private static Logger LOGGER = LoggerFactory.getLogger(CityServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    CityServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(CityServicesImpl.class)
            );
            server.start();

            synchronized (CityServer.class) {
                while (true) {
                    try {
                        CityServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider CityServer error", e);
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
