package com.moseeker.searchengine;

import com.moseeker.searchengine.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.searchengine.thrift.SearchengineServiceImpl;

public class SearchengineServer {
	private static Logger LOGGER = LoggerFactory.getLogger(SearchengineServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			MoServer server=new MoServer(acac,"",acac.getBean(SearchengineServiceImpl.class));
			server.startServer();
			server.shutDownHook();
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
		acac.register(AppConfig.class);
		acac.refresh();
		return acac;
	}
}
