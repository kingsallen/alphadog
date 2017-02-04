package service;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.service.ValidationService;
import com.moseeker.warn.utils.SendChannel;

public class WarnServiceTest {
	
	private ValidationService service;
	
	@SuppressWarnings("resource")
	@Before
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.warn");
		context.refresh();
		service = context.getBean(ValidationService.class);
	}
	
	@Test
	public void notifyTest() throws Exception{
		try {
			service.valid(new WarnBean("0", "REDIS_CONNECT_ERROR", null, "Redis 连接失败", getClass().getName().concat(":36")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void doLast(){
		SendChannel.threadPool.shutdown();
		int retry = 6;
		while(retry > 0){
			try {
				// 每10秒检查一次是否关闭
				if (SendChannel.threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
					break;
				}
				retry--;
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
