package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileBasicService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Service
public class ProfileBasicServicesImpl implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicServicesImpl.class);
	
	@Autowired
	private ProfileBasicService service;
	
	@Override
	public Response getResources(CommonQuery query) throws TException {
		return service.getResources(query);
	}
	
	@Override
	public Response getResource(CommonQuery query) throws TException {
		return service.getResource(query);
	}

	@Override
	public Response postResource(Basic struct) throws TException {
		return service.postResource(struct);
	}

	@Override
	public Response putResource(Basic struct) throws TException {
		return service.putResource(struct);
	}

	@Override
	public Response postResources(List<Basic> structs) throws TException {
		return service.postResources(structs);
	}

	@Override
	public Response putResources(List<Basic> structs) throws TException {
		return service.putResources(structs);
	}

	@Override
	public Response delResources(List<Basic> structs) throws TException {
		return service.delResources(structs);
	}

	@Override
	public Response delResource(Basic struct) throws TException {
		return service.delResource(struct);
	}

	@Override
	public Response getPagination(CommonQuery query) throws TException {
		return service.getPagination(query);
	}

	@Override
	public Response reCalculateBasicCompleteness(int userId) throws TException {
		return service.reCalculateBasicCompleteness(userId);
	}
}
