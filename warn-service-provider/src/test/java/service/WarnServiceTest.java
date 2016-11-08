package service;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.warn.service.WarnSetService;
import com.moseeker.thrift.gen.warn.struct.WarnBean;

public class WarnServiceTest {
	
	private WarnSetService.Iface warn = null;
	
	@Before
	public void doBefore(){
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.warn");
		annConfig.refresh();
		warn = ServiceManager.SERVICEMANAGER.getService(WarnSetService.Iface.class);
	}
	
	@Test
	public void notifyTest() throws TException{
		
		warn.sendOperator(new WarnBean("0", "REDIS_CONNECT_ERROR", "Redis 连接失败", getClass().getName()));
	}

}
