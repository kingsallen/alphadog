package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileCredentialsService;
import com.moseeker.profile.thrift.ProfileCredentialsServicesImpl;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.CredentialsServices;
import com.moseeker.thrift.gen.profile.struct.Credentials;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class ProfileCredentialsServicesImplTest {

	@Autowired
	ProfileCredentialsServicesImpl service;

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}

	////@Test
	public void getResource() throws TException {

		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});

		response = service.getResource(commonQuery);
	}

	////@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResources(commonQuery);
	}

	////@Test
	public void postResources() throws TException {

		Credentials credentials = new Credentials();
		credentials.setProfile_id(170);
		credentials.setName("resumeFileParser credentials");

		response = service.postResources(new ArrayList<Credentials>(){{add(credentials);}});
	}

	////@Test
	public void putResources() throws TException {
		Credentials credentials = new Credentials();
		credentials.setId(69406);
		credentials.setName("resumeFileParser credentials 2");
		Credentials credentials2 = new Credentials();
		credentials2.setId(69407);
		credentials2.setName("resumeFileParser credentials 3");
		response = service.putResources(new ArrayList<Credentials>(){{add(credentials);add(credentials2);}});
	}

	////@Test
	public void delResources() throws TException {
		Credentials credentials = new Credentials();
		credentials.setId(69406);
		Credentials credentials2 = new Credentials();
		credentials2.setId(69407);
		response = service.delResources(new ArrayList<Credentials>(){{add(credentials);add(credentials2);}});
	}

	////@Test
	public void postResource() throws TException {
		Credentials credentials = new Credentials();
		credentials.setProfile_id(170);
		credentials.setName("resumeFileParser credentials  180");
		response = service.postResource(credentials);
	}

	////@Test
	public void putResource() throws TException {
		Credentials credentials = new Credentials();
		credentials.setId(69406);
		credentials.setName("resumeFileParser credentials 24184");
		response = service.putResource(credentials);
	}

	////@Test
	public void delResource() throws TException {
		Credentials credentials = new Credentials();
		credentials.setId(69408);
		response = service.delResource(credentials);
	}

	////@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getPagination(commonQuery);
	}

}
