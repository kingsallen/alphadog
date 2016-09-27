package com.moseeker.mq.server;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.mq.service.email.ConstantlyMail;
import com.moseeker.mq.thrift.ThriftService;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;

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
            Server server = new Server(
                    MqServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(ThriftService.class)
            );
            server.start();
            
            ConstantlyMail mailUtil = new ConstantlyMail();
    		try {
    			mailUtil.start();
    		} catch (IOException | MessagingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

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
        acac.scan("com.moseeker.mq");
        acac.refresh();
        return acac;
    }

}
