package com.moseeker.dict.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.dict.service.impl.CityServicesImpl;
import com.moseeker.dict.service.impl.CollegeServicesImpl;
import com.moseeker.dict.service.impl.DictConstantServiceImpl;
import com.moseeker.dict.service.impl.DictCountryServiceImpl;
import com.moseeker.dict.service.impl.IndusteryServiceImpl;
import com.moseeker.dict.service.impl.PositionServiceImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MultiRegServer;

/**
 * Created by chendi on 5/19/16.
 */
public class CityServer {

    private static Logger LOGGER = LoggerFactory.getLogger(CityServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
        	MultiRegServer server = new MultiRegServer(
                    CityServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(CollegeServicesImpl.class),
                    acac.getBean(DictConstantServiceImpl.class),
                    acac.getBean(DictCountryServiceImpl.class),
                    acac.getBean(IndusteryServiceImpl.class),
                    acac.getBean(PositionServiceImpl.class),
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
