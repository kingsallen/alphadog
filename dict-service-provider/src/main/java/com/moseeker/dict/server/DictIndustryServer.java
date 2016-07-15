package com.moseeker.dict.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.dict.service.impl.IndusteryServiceImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;

/**
 * Created by chendi on 5/19/16.
 */
public class DictIndustryServer {

    private static Logger LOGGER = LoggerFactory.getLogger(DictIndustryServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    DictIndustryServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(IndusteryServiceImpl.class)
            );
            server.start();

            synchronized (DictIndustryServer.class) {
                while (true) {
                    try {
                        DictIndustryServer.class.wait();
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
