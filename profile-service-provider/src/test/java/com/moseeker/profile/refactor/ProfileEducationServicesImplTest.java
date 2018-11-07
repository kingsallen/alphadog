package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileEducationService;
import com.moseeker.profile.thrift.ProfileEducationServicesImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Education;
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
public class ProfileEducationServicesImplTest{

	@Autowired
	ProfileEducationServicesImpl service;

	Response response;

	@After
	public void printResponse() {
		System.out.println(JSON.toJSONString(response));
	}

//	//@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResources(commonQuery);
	}

//	//@Test
	public void getResource() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getResource(commonQuery);
	}

//	//@Test
	public void postResource() throws TException {
		Education education = new Education();
		education.setProfile_id(170);
		education.setDescription("postResource");
		education.setCollege_name("resumeFileParser college name");
		education.setDegree(3);
		education.setStart_date("2005-09-01");
		education.setEnd_date("2008-06-01");
		education.setCollege_code(Integer.MAX_VALUE);
		response = service.postResource(education);
	}

//	//@Test
	public void putResource() throws TException {
		Education education = new Education();
		education.setId(220984);
		education.setCollege_name("河北正定中学putResource");
		response = service.putResource(education);
	}

//	//@Test
	public void postResources() throws TException {
		Education education = new Education();
		education.setProfile_id(170);
		education.setDescription("postResources");
		education.setCollege_name("resumeFileParser college name");
		education.setDegree(3);
		education.setStart_date("2005-09-01");
		education.setEnd_date("2008-06-01");
		response = service.postResources(new ArrayList<Education>(){{add(education);}});
	}

//	//@Test
	public void putResources() throws TException {
		Education education = new Education();
		education.setId(206);
		education.setCollege_name("河北正定中学");
		response = service.putResources(new ArrayList<Education>(){{add(education);}});
	}

//	//@Test
	public void delResources() throws TException {
		Education education = new Education();
		education.setId(220984);
		response = service.delResources(new ArrayList<Education>(){{add(education);}});
	}

//	//@Test
	public void delResource() throws TException {
		Education education = new Education();
		education.setId(220985);
		response = service.delResource(education);
	}

//	//@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
		response = service.getPagination(commonQuery);
	}
}
