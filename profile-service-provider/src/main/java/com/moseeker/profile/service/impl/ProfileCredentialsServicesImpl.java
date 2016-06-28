package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.CredentialsServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Credentials;

@Service
public class ProfileCredentialsServicesImpl extends JOOQBaseServiceImpl<Credentials, ProfileCredentialsRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileCredentialsServicesImpl.class);

	@Autowired
	private CredentialsDao dao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;
	
	@Override
	public Response postResources(List<Credentials> structs) throws TException {
		Response response = super.postResources(structs);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<>();
			structs.forEach(struct -> {
				if(struct.getProfile_id() > 0)
					profileIds.add(struct.getProfile_id());
			});
			profileIds.forEach(profileId -> {
				completenessImpl.recalculateProfileCredential(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<Credentials> structs) throws TException {
		Response response = super.putResources(structs);
		/* 计算profile完整度 */
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Credentials> structs) throws TException {
		Response response = super.delResources(structs);
		/* 计算profile完整度 */
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response postResource(Credentials struct) throws TException {
		Response response = super.postResource(struct);
		/* 计算profile完整度 */
		if(response.getStatus() == 0 && struct != null) {
			completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response putResource(Credentials struct) throws TException {
		Response response = super.putResource(struct);
		/* 计算profile完整度 */
		if(response.getStatus() == 0 && struct != null) {
			completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response delResource(Credentials struct) throws TException {
		Response response = super.delResource(struct);
		/* 计算profile完整度 */
		if(response.getStatus() == 0 && struct != null) {
			completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	public CredentialsDao getDao() {
		return dao;
	}

	public void setDao(CredentialsDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Credentials DBToStruct(ProfileCredentialsRecord r) {
		return (Credentials)BeanUtils.DBToStruct(Credentials.class, r);
	}

	@Override
	protected ProfileCredentialsRecord structToDB(Credentials credentials) throws ParseException {
		return (ProfileCredentialsRecord)BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
	}
}
