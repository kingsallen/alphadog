package com.moseeker.application.server;

import com.moseeker.application.service.impl.ApplicataionServicesImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zzh on 16/5/24.
 */
public class ApplicationServer {

    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    ApplicationServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(ApplicataionServicesImpl.class)
            );
            server.start();

            synchronized (ApplicationServer.class) {
                while (true) {
                    try {
                        ApplicationServer.class.wait();
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
        acac.scan("com.moseeker.application");
        acac.refresh();
        return acac;
    }
}
