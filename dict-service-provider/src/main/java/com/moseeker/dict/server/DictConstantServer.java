package com.moseeker.dict.server;

import com.moseeker.dict.service.impl.DictConstantServiceImpl;
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
public class DictConstantServer {

    private static Logger LOGGER = LoggerFactory.getLogger(DictConstantServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
            Server server = new Server(
                    DictConstantServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(DictConstantServiceImpl.class)
            );
            server.start();

            synchronized (DictConstantServer.class) {
                while (true) {
                    try {
                        DictConstantServer.class.wait();
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
        acac.scan("com.moseeker.dict");
        acac.refresh();
        return acac;
    }

}
