package com.moseeker.company.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.company.thrift.ThriftCompanyService;
import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;

/**
 * 
 * 服务启动入口。
 * 
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Mar 27, 2016
 * </p>
 * 
 * @author yaofeng
 * @version Beta
 */
public class CompanyServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CompanyServer.class);
	
	public static void main(String[] args) {

		try {
			AnnotationConfigApplicationContext acac = initSpring();
			Server server = new Server(CompanyServer.class,
					ServerNodeUtils.getPort(args),
					acac.getBean(ThriftCompanyService.class));
			
			server.start(); // 启动服务，非阻塞

			synchronized (CompanyServer.class) {
				while (true) {
					try {
						CompanyServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider CompanyServer error", e);
                    }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("error", e);
		}
	}

	private static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker.company");
		acac.scan("com.moseeker.common.aop.iface");
		acac.refresh();
		return acac;
	}
}
