package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.CredentialsServices;
import com.moseeker.thrift.gen.profile.struct.Credentials;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileCredentialsServicesImplTest {

	CredentialsServices.Iface service;

	@Before
	public void init() {
		service = ServiceManager.SERVICEMANAGER.getService(CredentialsServices.Iface.class);
	}

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}
	
	
	@Test
	public void postResources() throws TException {

		Credentials credentials = new Credentials();
		credentials.setProfile_id(170);
		credentials.setName("test credentials");

		response = service.postResources(new ArrayList<Credentials>(){{add(credentials);}});
	}

	@Test
	public void putResources() throws TException {
		Credentials credentials = new Credentials();
		credentials.setProfile_id(170);
		credentials.setName("test credentials 2");
		response = service.putResources(null);
	}

	@Test
	public void delResources() throws TException {
		response = service.delResources(null);
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
	public void delResource() throws TException {
		response = service.delResource(null);
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
