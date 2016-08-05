package com.moseeker.dict.server;

import com.moseeker.dict.service.impl.DictCountryServiceImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 常量字典服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
public class DictCountryServer {

    private static Logger LOGGER = LoggerFactory.getLogger(DictCountryServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    DictCountryServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(DictCountryServiceImpl.class)
            );
            server.start();

            synchronized (DictCountryServer.class) {
                while (true) {
                    try {
                        DictCountryServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider DictCountryServer error", e);
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
