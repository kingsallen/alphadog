package com.moseeker.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.application.config.AppConfig;
import com.moseeker.application.thrift.JobApplicataionServicesImpl;
import com.moseeker.rpccenter.main.MoServer;

/**
 * Created by zzh on 16/5/24.
 */
public class JobApplicationServer {
    private static Logger LOGGER = LoggerFactory.getLogger(JobApplicationServer.class);
    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {           
            MoServer server = new MoServer(
                    acac,"",
                    acac.getBean(JobApplicataionServicesImpl.class)
            );
            server.startServer();
            server.shutDownHook();
            synchronized (JobApplicationServer.class) {
                while (true) {
                    try {
                        JobApplicationServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider JobApplicationServer error", e);
                    }
                }
            }
        } catch (Exception e) {
        		e.printStackTrace();
            LOGGER.error("error", e);
        }

    }

    private static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.register(AppConfig.class);
        acac.refresh();
        return acac;
    }
}
