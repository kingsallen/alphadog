package com.moseeker.application.server;

import com.moseeker.application.service.impl.JobApplicataionServicesImpl;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by zzh on 16/5/24.
 */
public class JobApplicationServer {

    private static Logger LOGGER = LoggerFactory.getLogger(JobApplicationServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    JobApplicationServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(JobApplicataionServicesImpl.class)
            );
            server.start();

            synchronized (JobApplicationServer.class) {
                while (true) {
                    try {
                        JobApplicationServer.class.wait();
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
