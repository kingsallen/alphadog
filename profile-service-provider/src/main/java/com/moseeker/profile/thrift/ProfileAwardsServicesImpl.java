package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileAwardsService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.AwardsServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Awards;

@Service
public class ProfileAwardsServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileAwardsServicesImpl.class);
	
	@Autowired
	private ProfileAwardsService service;

	@Override
	public Response postResources(List<Awards> structs) throws TException {
		return service.postResources(structs);
	}

	@Override
	public Response putResources(List<Awards> structs) throws TException {
		return service.putResources(structs);
	}

	@Override
	public Response delResources(List<Awards> structs) throws TException {
		return service.delResources(structs);
	}

	@Override
	public Response postResource(Awards struct) throws TException {
		return service.postResource(struct);
	}

	@Override
	public Response putResource(Awards struct) throws TException {
		return service.putResource(struct);
	}

	@Override
	public Response delResource(Awards struct) throws TException {
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
