package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.thrift.ProfileAttachmentServicesImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Attachment;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileAttachmentServicesImplTest{

	@Autowired
	ProfileAttachmentServicesImpl service;

	Response response;
	@After
	public void printResponse(){
		System.out.println(JSON.toJSONString(response));
	}
	
	////@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("id","1");
		response = service.getResources(commonQuery);
	}

	////@Test
	public void postResources() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("resumeFileParser");
		attachment.setName("testname");
		attachment.setProfile_id(170);

		response = service.postResources(new ArrayList<Attachment>(){{add(attachment);}});
	}

	////@Test
	public void putResources() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("resumeFileParser----------");
		attachment.setName("testname-------------");
		attachment.setId(19417);

		response = service.putResources(new ArrayList<Attachment>(){{add(attachment);}});
	}

//	//@Test
	public void delResources() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("resumeFileParser----------");
		attachment.setName("testname-------------");
		attachment.setId(19417);

		response = service.delResources(new ArrayList<Attachment>(){{add(attachment);}});
	}
	
	////@Test
	public void postResource() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("resumeFileParser");
		attachment.setName("testname");
		attachment.setProfile_id(170);
		response = service.postResource(attachment);
	}

	////@Test
	public void putResource() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("resumeFileParser----------");
		attachment.setName("testname-------------");
		attachment.setId(19418);
		response = service.putResource(attachment);
	}

	////@Test
	public void delResource() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("resumeFileParser----------");
		attachment.setName("testname-------------");
		attachment.setId(19415);
		response = service.delResource(attachment);
	}

	////@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("id","1");
		response = service.getPagination(commonQuery);
	}

	////@Test
	public void getResource() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("id","1");
		response = service.getResource(commonQuery);
	}
}
