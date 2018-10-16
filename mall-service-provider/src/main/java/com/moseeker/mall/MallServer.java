package com.moseeker.mall;

import com.moseeker.mall.config.AppConfig;
import com.moseeker.mall.thrift.GoodsThriftServiceImpl;
import com.moseeker.mall.thrift.MallThriftServiceImpl;
import com.moseeker.mall.thrift.OrderThriftServiceImpl;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author cjm
 */
public class MallServer {

    private static Logger LOGGER = LoggerFactory.getLogger(MallServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
        	MoServer server = new MoServer(
                    acac,"",
                    acac.getBean(GoodsThriftServiceImpl.class),
                    acac.getBean(OrderThriftServiceImpl.class),
                    acac.getBean(MallThriftServiceImpl.class)
            );
        	server.startServer();
            server.shutDownHook();
            synchronized (MallServer.class) {
                while (true) {
                    try {
                        MallServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider MallServer error", e);
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
