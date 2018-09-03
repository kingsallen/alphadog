package com.moseeker.company;

import com.moseeker.company.config.AppConfig;
import com.moseeker.company.thrift.CompanyServicesImpl;
import com.moseeker.company.thrift.HrTeamThriftServicesImpl;
import com.moseeker.company.thrift.TalentpoolNewThriftServiceImpl;
import com.moseeker.company.thrift.TalentpoolThriftServiceImpl;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
			MoServer server = new MoServer(
					acac,"",
					acac.getBean(CompanyServicesImpl.class),
					acac.getBean(HrTeamThriftServicesImpl.class),
					acac.getBean(TalentpoolThriftServiceImpl.class),
                    acac.getBean(TalentpoolNewThriftServiceImpl.class)

			);
			server.startServer();
			server.shutDownHook();
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
		acac.register(AppConfig.class);
		acac.refresh();
		return acac;
	}
}
