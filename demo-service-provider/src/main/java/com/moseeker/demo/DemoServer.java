package com.moseeker.demo;

import com.moseeker.demo.userinterface.DemoThriftServiceImpl;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class DemoServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DemoServer.class);
	
	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext acac = initSpring();
			MoServer server = new MoServer(acac,
					"", DemoThriftServiceImpl.class);
			// 启动服务，非阻塞
			try {
				server.startServer();
				server.shutDownHook();
				synchronized (DemoServer.class) {
                    while (true) {
                        try {
                            DemoServer.class.wait();
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
		acac.scan("com.moseeker.demo");
  		acac.scan("com.moseeker.baseorm");
		acac.scan("com.moseeker.common.aop.iface");
		acac.refresh();
		return acac;
	}
}
