package service;

import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.config.AppConfig;
import com.moseeker.warn.service.ValidationService;
import com.moseeker.warn.utils.SendChannel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class WarnServiceTest {

    @Autowired
	private ValidationService service;
	
	//@Test
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
