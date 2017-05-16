package com.moseeker.function.server;

import com.moseeker.function.service.chaos.PositionRefreshConsumer;
import com.moseeker.function.service.chaos.PositionSyncConsumer;
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
//        	MultiRegServer server = new MultiRegServer(FunctionServer.class,
//        			ServerNodeUtils.getPort(args),
//					acac.getBean(FunctionService.class),
//					acac.getBean(ChaosThriftService.class),
//					acac.getBean(WordpressThriftService.class),
//					acac.getBean(HRAccountThriftService.class));
//			server.start(); // 启动服务，非阻塞
        	MoServer server=new MoServer(
        			acac,"",
					acac.getBean(FunctionService.class),
					acac.getBean(ChaosThriftService.class),
					acac.getBean(WordpressThriftService.class)
        			);
			server.startServer();
			//开启监听同步完成任务
			PositionSyncConsumer listener = new PositionSyncConsumer();
			listener.startTask();
			
			PositionRefreshConsumer refreshListener = new PositionRefreshConsumer();
			refreshListener.startTask();

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
		acac.scan("com.moseeker.function");
		acac.scan("com.moseeker.common.aop.iface"); //开启接口统计
		acac.refresh();
		return acac;
	}
}
