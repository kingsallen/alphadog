package com.moseeker.searchengine.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.searchengine.thrift.SearchengineServiceImpl;

public class SearchengineServer {
	private static Logger LOGGER = LoggerFactory.getLogger(SearchengineServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			Server server = new Server(SearchengineServer.class,
					ServerNodeUtils.getPort(args),
					acac.getBean(SearchengineServiceImpl.class));
			
			server.start(); // 启动服务，非阻塞

			synchronized (SearchengineServer.class) {
				while (true) {
					try {
						SearchengineServer.class.wait();
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
		acac.scan("com.moseeker.searchengine");
		acac.scan("com.moseeker.common.aop.iface");
		acac.refresh();
		return acac;
	}
}
