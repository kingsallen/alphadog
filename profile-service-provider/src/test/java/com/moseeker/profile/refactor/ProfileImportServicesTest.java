package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.profile.conf.AppConfig;
import com.moseeker.profile.service.impl.ProfileEducationService;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileImportServicesTest{

	@Autowired
	ProfileEducationService service;

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}

	@Test
	public void postResources() throws TException {
		response = service.postResources(null);
	}

	@Test
	public void putResources() throws TException {
		response = service.putResources(null);
	}

	@Test
	public void delResources() throws TException {
		response = service.delResources(null);
	}

	@Test
	public void delResource() throws TException {
		response = service.delResource(null);
	}

	@Test
	public void postResource() throws TException {
		response = service.postResource(null);
	}

	@Test
	public void putResource() throws TException {
		response = service.putResource(null);
	}

	@Test
	public void getResources() throws TException {
		response = service.getResources(null);
	}

	@Test
	public void getPagination() throws TException {
		response = service.getPagination(null);
	}

	@Test
	public void getResource() throws TException {
		response = service.getResource(null);
	}
}
