package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.CredentialsServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Credentials;

@Service
public class ProfileCredentialsServicesImpl extends JOOQBaseServiceImpl<Credentials, ProfileCredentialsRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileCredentialsServicesImpl.class);

	@Autowired
	private CredentialsDao dao;
	
	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private ProfileCompletenessImpl completenessImpl;

	@Override
	public Response postResources(List<Credentials> structs) throws TException {
		Response response = super.postResources(structs);
		/* 重新计算profile完整度 */
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<>();
			structs.forEach(struct -> {
				if (struct.getProfile_id() > 0)
					profileIds.add(struct.getProfile_id());
			});
			profileDao.updateUpdateTime(profileIds);
			
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
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<>();
			if (structs != null && structs.size() > 0) {
				structs.forEach(credential -> {
					profileIds.add(credential.getProfile_id());
				});
			}
			updateUpdateTime(structs);
			structs.forEach(struct -> {
				completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Credentials> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());

		List<ProfileCredentialsRecord> credentialRecords = null;
		try {
			credentialRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if (credentialRecords != null && credentialRecords.size() > 0) {
			credentialRecords.forEach(credential -> {
				profileIds.add(credential.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		/* 计算profile完整度 */
		if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
			updateUpdateTime(structs);
			profileIds.forEach(profileId -> {
				completenessImpl.recalculateProfileCredential(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response postResource(Credentials struct) throws TException {
		Response response = super.postResource(struct);
		/* 计算profile完整度 */
		if (response.getStatus() == 0 && struct != null) {
			Set<Integer> profileIds = new HashSet<>();
			profileIds.add(struct.getProfile_id());
			profileDao.updateUpdateTime(profileIds);
			
			completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response putResource(Credentials struct) throws TException {
		Response response = super.putResource(struct);
		/* 计算profile完整度 */
		if (response.getStatus() == 0 && struct != null) {
			updateUpdateTime(struct);
			completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response delResource(Credentials struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileCredentialsRecord credentialRecord = null;
		try {
			credentialRecord = dao.getResource(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(struct);
		/* 计算profile完整度 */
		if (response.getStatus() == 0 && credentialRecord != null) {
			updateUpdateTime(struct);
			completenessImpl.recalculateProfileCredential(credentialRecord.getProfileId().intValue(),
					credentialRecord.getId().intValue());
		}
		return response;
	}

	public CredentialsDao getDao() {
		return dao;
	}

	public void setDao(CredentialsDao dao) {
		this.dao = dao;
	}

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Credentials DBToStruct(ProfileCredentialsRecord r) {
		return (Credentials) BeanUtils.DBToStruct(Credentials.class, r);
	}

	@Override
	protected ProfileCredentialsRecord structToDB(Credentials credentials) throws ParseException {
		return (ProfileCredentialsRecord) BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
	}

	private void updateUpdateTime(List<Credentials> credentials) {
		Set<Integer> credentialIds = new HashSet<>();
		credentials.forEach(Credential -> {
			credentialIds.add(Credential.getId());
		});
		dao.updateProfileUpdateTime(credentialIds);
	}

	private void updateUpdateTime(Credentials credential) {
		List<Credentials> credentials = new ArrayList<>();
		credentials.add(credential);
		updateUpdateTime(credentials);
	}
}
