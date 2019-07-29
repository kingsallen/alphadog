package com.moseeker.chat;

import com.moseeker.chat.config.AppConfig;
import com.moseeker.chat.thriftservice.ChatThriftService;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ChatServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			MoServer server = new MoServer(acac,"",acac.getBean(ChatThriftService.class));
			// 启动服务，非阻塞
			try {
				server.startServer();
				server.shutDownHook();
				synchronized (ChatServer.class) {
                    while (true) {
                        try {
                            ChatServer.class.wait();
                        } catch (Exception e) {
                            LOGGER.error(" service provider ChatServer error", e);
                        }
                    }
                }
			} catch (IncompleteException | RpcException | RegisterException | BeansException e) {
				LOGGER.error(e.getMessage(), e);
				server.stopServer();
			} finally {
			}
		} catch (Exception e) {
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
