package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.BasicServices;
import com.moseeker.thrift.gen.profile.struct.Basic;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ProfileBasicServicesImplTest {

	BasicServices.Iface service;

	@Before
	public void init() {
		service = ServiceManager.SERVICEMANAGER.getService(BasicServices.Iface.class);
	}

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}
	

	@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("profile_id","1");
		response = service.getResources(commonQuery);
	}
	
	@Test
	public void getResource() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("profile_id","-1");
		response = service.getResource(commonQuery);
	}

	@Test
	public void postResource() throws TException {
		Basic basic = new Basic();
		basic.setNationality_name("中国");
		basic.setBirth("");
		basic.setCity_name("");
		basic.setProfile_id(170);
		response = service.postResource(basic);
	}

	@Test
	public void putResource() throws TException {
		Basic basic = new Basic();
		basic.setNationality_name("中国2");
		basic.setBirth("");
		basic.setCity_name("");
		basic.setProfile_id(170);
		response = service.putResource(basic);
	}

	@Test
	public void postResources() throws TException {
		Basic basic = new Basic();
		basic.setNationality_name("中国3");
		basic.setBirth("");
		basic.setCity_name("");
		basic.setProfile_id(170);
		response = service.postResources(new ArrayList<Basic>(){{add(basic);}});
	}

	@Test
	public void putResources() throws TException {
		Basic basic = new Basic();
		basic.setNationality_name("中国4");
		basic.setBirth("");
		basic.setCity_name("");
		basic.setProfile_id(170);
		response = service.putResources(new ArrayList<Basic>(){{add(basic);}});
	}

	@Test
	public void delResources() throws TException {
		Basic basic = new Basic();
		basic.setProfile_id(170);
		response = service.delResources(new ArrayList<Basic>(){{add(basic);}});
	}

	@Test
	public void delResource() throws TException {
		Basic basic = new Basic();
		basic.setProfile_id(170);
		response = service.delResource(basic);
	}

	@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("gender","1");
		response = service.getPagination(commonQuery);
	}

	@Test
	public void reCalculateBasicCompleteness() throws TException {
		response = service.reCalculateBasicCompleteness(0);
	}
}
