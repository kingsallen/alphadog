package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileEducationService;
import com.moseeker.profile.thrift.ProfileEducationServicesImpl;
import com.moseeker.profile.thrift.ProfileImportServicesImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class ProfileImportServicesTest{

	@Autowired
	ProfileImportServicesImpl service;

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}

//	//@Test
	public void postResources() throws TException {
		ProfileImport profileImport = new ProfileImport();
		profileImport.setProfile_id(170);
		profileImport.setAccount_id("resumeFileParser add imports");
		response = service.postResources(new ArrayList<ProfileImport>(){{add(profileImport);}});
	}

//	//@Test
	public void putResources() throws TException {
		ProfileImport profileImport = new ProfileImport();
		profileImport.setProfile_id(170);
		profileImport.setAccount_id("resumeFileParser update imports");
		response = service.putResources(new ArrayList<ProfileImport>(){{add(profileImport);}});
	}

//	//@Test
	public void delResources() throws TException {
		ProfileImport profileImport = new ProfileImport();
		profileImport.setProfile_id(170);
		response = service.delResources(new ArrayList<ProfileImport>(){{add(profileImport);}});
	}

//	//@Test
	public void delResource() throws TException {
		ProfileImport profileImport = new ProfileImport();
		profileImport.setProfile_id(170);
		response = service.delResource(profileImport);
	}

//	//@Test
	public void postResource() throws TException {
		ProfileImport profileImport = new ProfileImport();
		profileImport.setProfile_id(170);
		profileImport.setAccount_id("resumeFileParser add imports");
		response = service.postResource(profileImport);
	}

//	//@Test
	public void putResource() throws TException {
		ProfileImport profileImport = new ProfileImport();
		profileImport.setProfile_id(170);
		profileImport.setAccount_id("resumeFileParser add imports");
		response = service.putResource(profileImport);
	}

//	//@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResources(commonQuery);
	}

//	//@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getPagination(commonQuery);
	}

//	//@Test
	public void getResource() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResource(commonQuery);
	}
}
