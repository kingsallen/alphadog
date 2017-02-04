package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moseeker.apps.service.ProfileProcessBS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.moseeker.apps.service.ProfileProcessBS.class)
public class ProfileProcessTest {
	
	@Autowired
	private ProfileProcessBS service;
	
	@Test
	public void testNotifySeeker() {
		service.sendTplToSeeker(1122611, "测试", 2046, 12, "千寻信息科技", "java 开发", 1);
	}
	
}
