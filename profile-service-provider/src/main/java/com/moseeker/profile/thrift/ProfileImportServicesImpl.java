package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileImportService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileImportServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;

@Service
public class ProfileImportServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileImportServicesImpl.class);

	@Autowired
	private ProfileImportService service;

	@Override
	public Response postResources(List<ProfileImport> structs) throws TException {
		return service.postResources(structs);
	}

	@Override
	public Response putResources(List<ProfileImport> structs) throws TException {
		return service.putResources(structs);
	}

	@Override
	public Response delResources(List<ProfileImport> structs) throws TException {
		return service.delResources(structs);
	}

	@Override
	public Response delResource(ProfileImport struct) throws TException {
		return service.delResource(struct);
	}

	@Override
	public Response postResource(ProfileImport struct) throws TException {
		return service.postResource(struct);
	}

	@Override
	public Response putResource(ProfileImport struct) throws TException {
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
