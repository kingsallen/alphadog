package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.ProfileProjectExpService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;

@Service
public class ProfileProjectExpServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileProjectExpServicesImpl.class);

	@Autowired
	private ProfileProjectExpService service;


	@Override
	public Response postResources(List<ProjectExp> structs) throws TException {
		return service.postResources(structs);
	}

	@Override
	public Response putResources(List<ProjectExp> structs) throws TException {
		return service.putResources(structs);
	}

	@Override
	public Response delResources(List<ProjectExp> structs) throws TException {
		return service.delResources(structs);
	}

	@Override
	public Response postResource(ProjectExp struct) throws TException {
		return service.postResource(struct);
	}

	@Override
	public Response putResource(ProjectExp struct) throws TException {
		return service.putResource(struct);
	}

	@Override
	public Response delResource(ProjectExp struct) throws TException {
		return service.delResource(struct);
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
