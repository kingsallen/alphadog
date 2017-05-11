package com.moseeker.candidate;

import com.moseeker.candidate.config.AppConfig;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class CandidateServer {

    private static Logger LOGGER = LoggerFactory.getLogger(CandidateServer.class);

    public static void main(String[] args) {

        try {
            AnnotationConfigApplicationContext acac = initSpring();
            MoServer server = new MoServer(acac,
                    "");
            // 启动服务，非阻塞
            try {
                server.startServer();

                synchronized (CandidateServer.class) {
                    while (true) {
                        try {
                            CandidateServer.class.wait();
                        } catch (Exception e) {
                            LOGGER.error(" service provider CandidateServer error", e);
                        }
                    }
                }
            } catch (IncompleteException | RpcException | RegisterException | BeansException e) {
                e.printStackTrace();
                server.stopServer();
            } finally {
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
