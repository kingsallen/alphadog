package test.com.moseeker.position;

import java.util.HashMap;

import org.jooq.tools.json.JSONObject;
import org.junit.Test;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;

public class JobCustomFieldsTest {
	
	@Test
	public void getCustomFields() throws Exception{
		PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("company_id", 1);
		String param=JSONObject.toJSONString(map);
		Response result=positonServices.CustomField(param);
		System.out.println(result);
	}

}
