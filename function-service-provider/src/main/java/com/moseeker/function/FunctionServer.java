package com.moseeker.function;

import com.moseeker.function.config.AppConfig;
import com.moseeker.function.thrift.service.ChaosThriftService;
import com.moseeker.function.thrift.service.FunctionService;
import com.moseeker.function.thrift.service.WordpressThriftService;
import com.moseeker.rpccenter.main.MoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 
 * 通用功能服务
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class FunctionServer {

    private static Logger LOGGER = LoggerFactory.getLogger(FunctionServer.class);
    
    public static void main(String[] args) {

        try {
        	AnnotationConfigApplicationContext acac = initSpring();
        	MoServer server=new MoServer(
        			acac,"",
					acac.getBean(FunctionService.class),
					acac.getBean(ChaosThriftService.class),
					acac.getBean(WordpressThriftService.class)
        			);
			server.startServer();
			server.shutDownHook();
			synchronized (FunctionServer.class) {
				while (true) {
					try {
						FunctionServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider ProfileAttachmentServer error", e);
                    }
				}
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
