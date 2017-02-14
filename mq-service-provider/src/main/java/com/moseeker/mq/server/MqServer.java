package com.moseeker.mq.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.mq.service.email.ConstantlyMailConsumer;
import com.moseeker.mq.service.email.MandrillMailConsumer;
import com.moseeker.mq.service.schedule.Schedule;
import com.moseeker.mq.thrift.ThriftService;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MoServer;
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
//            Server server = new Server(
//                    MqServer.class,
//                    ServerNodeUtils.getPort(args),
//                    acac.getBean(ThriftService.class)
//            );
//            server.start();
        	MoServer server = new MoServer(
                  acac,"server.properties",
                  acac.getBean(ThriftService.class)
          );
        	server.startServer();
            ConstantlyMailConsumer mailUtil = new ConstantlyMailConsumer();
    		MandrillMailConsumer mandrill = new MandrillMailConsumer();
    		mandrill.start();            
            
    		try {
    			mailUtil.start();
    		} catch (IOException | MessagingException e) {
    			e.printStackTrace();
    			LOGGER.error(e.getMessage(), e);
    		}
    		
    		//启动消息模版延迟队列的定时搬运搬运任务
    		Schedule schedule = new Schedule(0,1,TimeUnit.MINUTES);
    		schedule.startListeningMessageDelayQueue();

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
