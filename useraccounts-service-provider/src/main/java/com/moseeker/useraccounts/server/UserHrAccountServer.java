package com.moseeker.useraccounts.server;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.useraccounts.service.impl.UserHrAccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * HR账号服务
 *
 * Created by zzh on 16/5/31.
 */
public class UserHrAccountServer {

    private static Logger LOGGER = LoggerFactory.getLogger(UserHrAccountServer.class);

    public static void main(String[] args) {

        try {
            AnnotationConfigApplicationContext acac = initSpring();
            Server server = new Server(UserHrAccountServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(UserHrAccountServiceImpl.class));

            server.start(); // 启动服务，非阻塞

            synchronized (UserHrAccountServer.class) {
                while (true) {
                    try {
                        UserHrAccountServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider UserHrAccountServer error", e);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

    private static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.scan("com.moseeker.useraccounts");
        acac.refresh();
        return acac;
    }
}
