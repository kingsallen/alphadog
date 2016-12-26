package com.moseeker.profile.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;

@Service
public class WholeProfileServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(WholeProfileServicesImpl.class);
	
	ProfileUtils profileUtils = new ProfileUtils();
	
	@Autowired
	private WholeProfileService service;

	@Override
	public Response getResource(int userId, int profileId, String uuid) throws TException {
		return service.getResource(userId, profileId, uuid);
	}

	@Override
	public Response postResource(String profile, int userId) throws TException {
		return service.postResource(profile, userId);
	}

	@Override
	public Response importCV(String profile, int userId) throws TException {
		return service.importCV(profile, userId);
	}

	@Override
	public Response verifyRequires(int userId, int positionId) throws TException {
		return service.verifyRequires(userId, positionId);
	}

	@Override
	public Response createProfile(String profile) throws TException {
		return service.createProfile(profile);
	}

	@Override
	public Response improveProfile(String profile) throws TException {
		return service.improveProfile(profile);
	}

	@Override
	public Response moveProfile(int destUserId, int originUserId)
			throws TException {
		return service.improveProfile(destUserId, originUserId);
	}

}
