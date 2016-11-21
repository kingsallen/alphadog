package com.moseeker.dict;

import java.util.HashMap;
import java.util.Map;

import org.jooq.tools.json.JSONObject;
import org.junit.Test;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictOccupationService;

public class DictOccupationTest {
	@Test
	public void getOccupation() throws Exception{
		DictOccupationService.Iface dictOccupationService = ServiceManager.SERVICEMANAGER
				.getService(DictOccupationService.Iface.class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("single_layer", true);
		map.put("channel", 1);
		map.put("level", 1);
		String param=JSONObject.toJSONString(map);
		Response result=dictOccupationService.getDictOccupation(param);
		System.out.println(result);
	}
}
