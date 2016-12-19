package test.com.moseeker.chaosService;

import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.function.thrift.service.ChaosThriftService;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;

public class ChaOsTest {
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.function");
		annConfig.refresh();
		return annConfig;
	}
	//测试调用orm服务
	@Test
	public void chaOsBindTest() throws Exception{
		ThirdPartParamer param=new ThirdPartParamer();
		param.setAppid(1);
		param.setChannel(1);
		param.setCompany_id(33);
		param.setMember_name("333");
		param.setPassword("333");
		param.setUser_id(33);
		param.setUsername("333");
		AnnotationConfigApplicationContext acc=initSpring();
		ChaosThriftService service=acc.getBean(ChaosThriftService.class);
		//System.out.println(service.sendParamForChaos(param));
	}

}
