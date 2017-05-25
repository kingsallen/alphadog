package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileCredentialsService;
import com.moseeker.profile.service.impl.ProfileCustomizeResumeService;
import com.moseeker.profile.thrift.ProfileCustomizeResumeServicesImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileCustomizeResumeServicesImplTest{

	@Autowired
	ProfileCustomizeResumeServicesImpl service;

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}

	//@Test
	public void postResources() throws TException {

		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setProfile_id(170);
		customizeResume.setOther("{\"basicinfo\": {}}");

		CustomizeResume customizeResume1 = new CustomizeResume();
		customizeResume1.setProfile_id(171);
		customizeResume1.setOther("{\"basicinfo\": {\"idnumber\": \"421083199012052934\", \"residence\": \"\\u6b66\\u6c49\"}}");

		response = service.postResources(new ArrayList<CustomizeResume>(){{add(customizeResume);add(customizeResume1);}});
	}

//	@Test
	public void putResources() throws TException {

		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setProfile_id(170);
		customizeResume.setOther("{\"basicinfo\": {}}");

		CustomizeResume customizeResume1 = new CustomizeResume();
		customizeResume1.setProfile_id(171);
		customizeResume1.setOther("{\"basicinfo\": {\"idnumber\": \"421083199012052934\", \"residence\": \"\\u6b66\\u6c49\"}}");


		response = service.putResources(new ArrayList<CustomizeResume>(){{add(customizeResume);add(customizeResume1);}});
	}

//	@Test
	public void delResources() throws TException {
		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setProfile_id(170);

		CustomizeResume customizeResume1 = new CustomizeResume();
		customizeResume1.setProfile_id(171);
		response = service.delResources(new ArrayList<CustomizeResume>(){{add(customizeResume);add(customizeResume1);}});
	}

//	@Test
	public void delResource() throws TException {
		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setProfile_id(170);
		response = service.delResource(customizeResume);
	}

//	@Test
	public void postResource() throws TException {
		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setProfile_id(170);
		customizeResume.setOther("{\"basicinfo\": {}}");
		response = service.postResource(customizeResume);

		CustomizeResume customizeResume1 = new CustomizeResume();
		customizeResume1.setProfile_id(171);
		customizeResume1.setOther("{\"basicinfo\": {}}");
		response = service.postResource(customizeResume1);
	}

//	@Test
	public void putResource() throws TException {
		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setProfile_id(170);
		customizeResume.setOther("{\"basicinfo\": {}}");
		response = service.putResource(customizeResume);
	}

//	@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResources(commonQuery);
	}

//	@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getPagination(commonQuery);
	}

//	@Test
	public void getResource() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResource(commonQuery);
	}
}
