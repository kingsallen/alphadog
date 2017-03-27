package com.moseeker.profile.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileProjectExpServicesImpl.class);

	@Autowired
	private ProfileService service;

	@Autowired
	private ProfileCompletenessImpl completenessImpl;


	@Override
	public Response getResource(CommonQuery query) throws TException {
		return service.getResource(query);
	}

	@Override
	public Response postResource(Profile struct) throws TException {
		return service.postResource(struct);
	}

	@Override
	public Response getCompleteness(int userId, String uuid, int profileId) throws TException {
		return service.getCompleteness(userId, uuid, profileId);
	}

	@Override
	public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
		return service.reCalculateUserCompleteness(userId, mobile);
	}

	@Override
	public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
		return service.reCalculateUserCompletenessBySettingId(id);
	}

	@Override
	public Response getProfileByApplication(int companyId, int sourceId, int ats_status, boolean recommender, boolean dl_url_required) throws TException {
		return service.getProfileByApplication(companyId,sourceId,ats_status,recommender,dl_url_required);
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
	public Response postResources(List<Profile> resources) throws TException {
		return service.postResources(resources);
	}

	@Override
	public Response putResources(List<Profile> resources) throws TException {
		return service.putResources(resources);
	}

	@Override
	public Response delResources(List<Profile> resources) throws TException {
		return service.delResources(resources);
	}

	@Override
	public Response putResource(Profile profile) throws TException {
		return service.putResource(profile);
	}

	@Override
	public Response delResource(Profile profile) throws TException {
		return service.delResource(profile);
	}
}
