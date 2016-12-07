package test.com.moseeker.position;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.JobOccupationService;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;

public class JobCustomFieldsTest {
	
	@Test
	public void getCustomFields() throws Exception{
//		PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
//		HashMap<String,Object> map=new HashMap<String,Object>();
//		map.put("company_id", 1);
//		String param=JSONObject.toJSONString(map);
//		Response result=positonServices.CustomField(param);
//		System.out.println(result);
	}
	private JobOccupationService position;
	@Test
//   public void test() throws Exception{
//		init();
//		HashMap<String,Object> map=new HashMap<String,Object>();
//    	map.put("company_id", 8);
//    	String param=JSONObject.toJSONString(map);
//		Response result=position.getCustomField(param);
//		System.out.println(result);
//   }
	
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.position");
		context.refresh();
		position= context.getBean(JobOccupationService.class);
	}
}
