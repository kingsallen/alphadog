package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.thrift.gen.warn.service.WarnSetService;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.server.WarnServer;
import com.moseeker.warn.thrift.WarnThriftService;

public class WarnServiceTest {
	
	private WarnSetService.Iface warn = null;
	
	private AnnotationConfigApplicationContext annConfig;
	
	private Server server;
	
	@Before
	public void doFast() throws ClassNotFoundException, RpcException{
		annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.warn");
		annConfig.refresh();
		server = new Server(WarnServer.class, 19200, annConfig.getBean(WarnThriftService.class));
		server.start();
		warn = ServiceManager.SERVICEMANAGER.getService(WarnSetService.Iface.class);
	}
	
	@Test
	public void notifyTest() throws Exception{
		int val = 3;
		while(val > 0){
			new Thread(() -> {
				try {
					warn.sendOperator(new WarnBean("1", "REDIS_CONNECT_ERROR", "Redis 连接失败", getClass().getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			val--;
		}
		Thread.currentThread().join(2000);
	}
	
	@After
	public void doLast(){
		if (server != null) {
			server.close();
		}
	}
}
