package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.AttachmentServices;
import com.moseeker.thrift.gen.profile.struct.Attachment;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileAttachmentServicesImplTest{

	AttachmentServices.Iface service;

	@Before
	public void init(){
		service = ServiceManager.SERVICEMANAGER.getService(AttachmentServices.Iface.class);
	}
	
	@Test
	public void getResources() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("id","1");
		response = service.getResources(commonQuery);
	}

	@Test
	public void postResources() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("test");
		attachment.setName("testname");
		attachment.setProfile_id(170);

		response = service.postResources(new ArrayList<Attachment>(){{add(attachment);}});
	}

	@Test
	public void putResources() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("test----------");
		attachment.setName("testname-------------");
		attachment.setId(19413);

		response = service.putResources(new ArrayList<Attachment>(){{add(attachment);}});


	}

	@Test
	public void delResources() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("test----------");
		attachment.setName("testname-------------");
		attachment.setId(19413);

		response = service.delResources(new ArrayList<Attachment>(){{add(attachment);}});
	}
	
	@Test
	public void postResource() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("test");
		attachment.setName("testname");
		attachment.setProfile_id(170);
		response = service.postResource(attachment);
	}

	@Test
	public void putResource() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("test----------");
		attachment.setName("testname-------------");
		attachment.setId(19415);
		response = service.putResource(attachment);
	}

	@Test
	public void delResource() throws TException {
		Attachment attachment = new Attachment();
		attachment.setDescription("test----------");
		attachment.setName("testname-------------");
		attachment.setId(19415);
		response = service.delResource(attachment);
	}

	@Test
	public void getPagination() throws TException {
		CommonQuery commonQuery = new CommonQuery();
		commonQuery.setEqualFilter(new HashMap<>());
		commonQuery.getEqualFilter().put("id","1");
		response = service.getPagination(commonQuery);
	}

	@Test
	public void getResource() throws TException {
		response = service.getResource(null);
	}

	Response response;
	@After
	public void printResponse(){
		System.out.println(JSON.toJSONString(response));
	}
}
