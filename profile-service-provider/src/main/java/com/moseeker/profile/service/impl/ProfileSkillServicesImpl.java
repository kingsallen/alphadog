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

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.profile.dao.SkillDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.SkillServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Skill;

@Service
public class ProfileSkillServicesImpl extends JOOQBaseServiceImpl<Skill, ProfileSkillRecord> implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileSkillServicesImpl.class);

	@Autowired
	private SkillDao dao;

	@Autowired
	private ProfileCompletenessImpl completenessImpl;

	public SkillDao getDao() {
		return dao;
	}

	public void setDao(SkillDao dao) {
		this.dao = dao;
	}

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public Response postResources(List<Skill> structs) throws TException {
		Response response = super.postResources(structs);
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<>();
			structs.forEach(struct -> {
				profileIds.add(struct.getProfile_id());
			});
			profileIds.forEach(profileId -> {
				completenessImpl.reCalculateProfileSkill(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<Skill> structs) throws TException {
		Response response = super.putResources(structs);
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				completenessImpl.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Skill> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());

		List<ProfileSkillRecord> skillRecords = null;
		try {
			skillRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if (skillRecords != null && skillRecords.size() > 0) {
			skillRecords.forEach(skill -> {
				profileIds.add(skill.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
			profileIds.forEach(profileId -> {
				completenessImpl.reCalculateProfileSkill(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response postResource(Skill struct) throws TException {
		Response response = super.postResource(struct);
		if (response.getStatus() == 0) {
			completenessImpl.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response putResource(Skill struct) throws TException {
		Response response = super.putResource(struct);
		if (response.getStatus() == 0) {
			completenessImpl.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response delResource(Skill struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileSkillRecord skillRecord = null;
		try {
			skillRecord = dao.getResource(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(struct);
		if (response.getStatus() == 0 && skillRecord != null) {
			completenessImpl.reCalculateProfileSkill(skillRecord.getProfileId().intValue(),
					skillRecord.getId().intValue());
		}
		return response;
	}

	@Override
	protected Skill DBToStruct(ProfileSkillRecord r) {
		return (Skill) BeanUtils.DBToStruct(Skill.class, r);
	}

	@Override
	protected ProfileSkillRecord structToDB(Skill skill) throws ParseException {
		return (ProfileSkillRecord) BeanUtils.structToDB(skill, ProfileSkillRecord.class);
	}
}
