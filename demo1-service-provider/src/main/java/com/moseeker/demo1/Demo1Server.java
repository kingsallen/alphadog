package com.moseeker.demo1;

import com.moseeker.demo1.userinterface.Demo1ThriftServiceImpl;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by jack on 2018/4/18.
 */
public class Demo1Server {

    private static Logger LOGGER = LoggerFactory.getLogger(Demo1Server.class);

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext acac = initSpring();
            MoServer server = new MoServer(acac,
                    "", Demo1ThriftServiceImpl.class);
            // 启动服务，非阻塞
            try {
                server.startServer();
                server.shutDownHook();
                synchronized (Demo1Server.class) {
                    while (true) {
                        try {
                            Demo1Server.class.wait();
                        } catch (Exception e) {
                            LOGGER.error(" service provider DemoServer error", e);
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
        acac.scan("com.moseeker.demo1");
        acac.refresh();
        return acac;
    }
}
