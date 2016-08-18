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
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.profile.dao.AwardsDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.AwardsServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Awards;

@Service
public class ProfileAwardsServicesImpl extends JOOQBaseServiceImpl<Awards, ProfileAwardsRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileAwardsServicesImpl.class);

	@Autowired
	private AwardsDao dao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;
	
	@Override
	public Response postResources(List<Awards> structs) throws TException {
		Response response = super.postResources(structs);
		/* 计算profile完成度 */
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<>();
			structs.forEach(struct -> {
				profileIds.add(struct.getProfile_id());
			});
			profileIds.forEach(profileId -> {
				completenessImpl.reCalculateProfileAward(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<Awards> structs) throws TException {
		Response response = super.putResources(structs);
		/* 计算profile完成度 */
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				completenessImpl.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Awards> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());
		
		List<ProfileAwardsRecord> awardsRecords = null;
		try {
			awardsRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if(awardsRecords != null && awardsRecords.size() > 0) {
			awardsRecords.forEach(award -> {
				profileIds.add(award.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		/* 计算profile完成度 */
		if(response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
			profileIds.forEach(profileId -> {
				completenessImpl.reCalculateProfileAward(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response postResource(Awards struct) throws TException {
		Response response = super.postResource(struct);
		/* 计算profile完成度 */
		if(response.getStatus() == 0 && struct != null) {
			completenessImpl.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response putResource(Awards struct) throws TException {
		Response response = super.putResource(struct);
		/* 计算profile完成度 */
		if(response.getStatus() == 0 && struct != null) {
			completenessImpl.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response delResource(Awards struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileAwardsRecord award = null;
		try {
			award = dao.getResource(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(struct);
		/* 计算profile完成度 */
		if(response.getStatus() == 0 && award != null) {
			completenessImpl.reCalculateProfileAward(award.getProfileId().intValue(), award.getId().intValue());
		}
		return response;
	}

	public AwardsDao getDao() {
		return dao;
	}

	public void setDao(AwardsDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	@Override
	protected Awards DBToStruct(ProfileAwardsRecord r) {
		return (Awards)BeanUtils.DBToStruct(Awards.class, r);
	}

	@Override
	protected ProfileAwardsRecord structToDB(Awards awards) throws ParseException {
		return (ProfileAwardsRecord)BeanUtils.structToDB(awards, ProfileAwardsRecord.class);
	}
	
	public void updateUpdateTime(List<Awards> Awards, Response response) {
		if(response.getStatus() == 0) {
			HashSet<Integer> awardIds = new HashSet<>();
			Awards.forEach(award -> {
				awardIds.add(award.getId());
			});
			dao.updateProfileUpdateTime(awardIds);
		}
	}
	
	public void updateUpdateTime(Awards Award, Response response) {
		if(response.getStatus() == 0) {
			List<Awards> awards = new ArrayList<>();
			awards.add(Award);
			updateUpdateTime(awards, response);
		}
	}
}
