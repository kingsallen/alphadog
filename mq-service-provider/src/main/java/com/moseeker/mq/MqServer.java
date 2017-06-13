package com.moseeker.mq;

import com.moseeker.mq.config.AppConfig;
import com.moseeker.mq.thrift.ThriftService;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 消息队列服务
 *
 * Created by zzh on 16/8/3.
 */
public class MqServer {

    private static Logger LOGGER = LoggerFactory.getLogger(MqServer.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext acac = initSpring();
        try {
        	MoServer server = new MoServer(
                  acac,"",
                  acac.getBean(ThriftService.class)
             );
        	server.startServer();
        	server.shutDownHook();
            synchronized (MqServer.class) {
                while (true) {
                    try {
                        MqServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider MqServer error", e);
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
