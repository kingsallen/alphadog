package com.moseeker.dict;

import com.moseeker.dict.config.AppConfig;
import com.moseeker.dict.thrift.*;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by chendi on 5/19/16.
 */
public class CityServer {

    private static Logger LOGGER = LoggerFactory.getLogger(CityServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
//        	MultiRegServer server = new MultiRegServer(
//                    CityServer.class,
//                    ServerNodeUtils.getPort(args),
//                    acac.getBean(CollegeServicesImpl.class),
//                    acac.getBean(DictConstantServiceImpl.class),
//                    acac.getBean(DictCountryServiceImpl.class),
//                    acac.getBean(IndusteryServiceImpl.class),
//                    acac.getBean(PositionServiceImpl.class),
//                    acac.getBean(CityServicesImpl.class),
//                    acac.getBean(DictOccupationServiceImpl.class)
//            );
//            server.start();
        	MoServer server = new MoServer(
                    acac,"",
                    acac.getBean(CollegeServicesImpl.class),
                    acac.getBean(DictConstantServiceImpl.class),
                    acac.getBean(DictCountryServiceImpl.class),
                    acac.getBean(IndusteryServiceImpl.class),
                    acac.getBean(PositionServiceImpl.class),
                    acac.getBean(CityServicesImpl.class),
                    acac.getBean(DictOccupationServiceImpl.class)
            );
        	server.startServer();
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
        acac.register(AppConfig.class);
        acac.refresh();
        return acac;
    }

}
