package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.ProfileEducationService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.EducationServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Education;

@Service
public class ProfileEducationServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileEducationServicesImpl.class);

	@Autowired
	private ProfileEducationService service;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;


	@Override
	public Response getResources(CommonQuery query) throws TException {
		return service.getResources(query);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return service.getResource(query);
	}

	@Override
	public Response postResource(Education education) throws TException {
		return service.postResource(education);
	}

	@Override
	public Response putResource(Education education) throws TException {
		return service.putResource(education);
	}

	@Override
	public Response postResources(List<Education> structs) throws TException {
		return service.postResources(structs);
	}

	@Override
	public Response putResources(List<Education> structs) throws TException {
		return service.putResources(structs);
	}

	@Override
	public Response delResources(List<Education> structs) throws TException {
		return service.delResources(structs);
	}

	@Override
	public Response delResource(Education struct) throws TException {
		return service.delResource(struct);
	}

	@Override
	public Response getPagination(CommonQuery query) throws TException {
		return service.getPagination(query);
	}
}
