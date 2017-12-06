package com.moseeker.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.config.AppConfig;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.dict.service.impl.DictOccupationService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.jooq.tools.json.JSONObject;
import org.junit.Test;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictOccupationTest {

	@Autowired
	TestService service;

	@Test
	public void test() throws BIZException {
		service.test();
	}



	//@Test
	public void getOccupation() throws Exception{
//		DictOccupationService.Iface dictOccupationService = ServiceManager.SERVICEMANAGER
//				.getService(DictOccupationService.Iface.class);
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("single_layer", true);
//		map.put("channel", 1);
//		map.put("level", 1);
//		String param=JSONObject.toJSONString(map);
//		Response result=dictOccupationService.getDictOccupation(param);
//		System.out.println(result);
	}
}
