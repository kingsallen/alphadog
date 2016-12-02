package test.com.moseeker.position;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
	private PositionService position;
	@Test
   public void test() throws Exception{
		init();
		Response result=position.getPositionById(382);
		System.out.println(result);
   }
	
	public void init() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.moseeker.position");
		context.refresh();
		position= context.getBean(PositionService.class);
	}
}
