package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileCustomizeResumeService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.CustomizeResumeServices.Iface;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;

@Service
public class ProfileCustomizeResumeServicesImpl implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileCustomizeResumeServicesImpl.class);

	@Autowired
	private ProfileCustomizeResumeService service;


	@Override
	public Response postResources(List<CustomizeResume> structs) throws TException {
		return service.postResources(structs);
	}

	@Override
	public Response putResources(List<CustomizeResume> structs) throws TException {
		return service.putResources(structs);
	}

	@Override
	public Response delResources(List<CustomizeResume> structs) throws TException {
		return service.delResources(structs);
	}

	@Override
	public Response delResource(CustomizeResume struct) throws TException {
		return service.delResource(struct);
	}

	@Override
	public Response postResource(CustomizeResume struct) throws TException {
		return service.postResource(struct);
	}

	@Override
	public Response putResource(CustomizeResume struct) throws TException {
		return service.putResource(struct);
	}

	@Override
	public Response getResources(CommonQuery query) throws TException {
		return service.getResources(query);
	}

	@Override
	public Response getPagination(CommonQuery query) throws TException {
		return service.getPagination(query);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return service.getResource(query);
	}
}
