package com.moseeker.profile.service.impl;

import java.sql.Timestamp;

import org.apache.thrift.TException;
import org.jooq.types.UInteger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfilePagination;

@Service
public class ProfileServicesImpl extends BasicServiceImpl<ProfileProfileRecord, Profile> implements Iface {

	@Autowired
	private ProfileDao<ProfileProfileRecord> profileDao;
	
	@Override
	protected void initDao() {
		this.dao = profileDao;
	}
	
	@Override
	public ProfilePagination getProfilePagination(CommonQuery query,
			Profile profile) throws TException {
		Pagination<Profile> pagination = this.getPagination(query, profile);
		ProfilePagination bp = new ProfilePagination();
		/*bp.setPage_number(pagination.getPageNo());
		bp.setPage_size(pagination.getPageSize());
		bp.setTotal_page(pagination.getTotalPage());
		bp.setTotal_row(pagination.getTotalRow());
		bp.setProfiles(pagination.getResults());*/
		BeanUtils.copyProperties(pagination, bp);
		return bp;
	}
	
	@Override
	protected Profile dbToStruct(ProfileProfileRecord r) {
		Profile profile = null;
		if(r != null) {
			profile = new Profile();
			profile.setId(r.getId().intValue());
			profile.setUuid(r.getUuid());
			profile.setLang(r.getLang());
			profile.setSource(r.getSource());
			profile.setCompleteness(r.getCompleteness());
			profile.setUser_id(r.getUserId());
		}
		return profile;
	}

	@Override
	protected ProfileProfileRecord formToDB(Profile profile) {
		ProfileProfileRecord record = new ProfileProfileRecord();
		if(profile.getId() != 0) {
			record.setId(UInteger.valueOf(profile.getId()));
		}
		if(!StringUtils.isNullOrEmpty(profile.getUuid())) {
			record.setUuid(profile.getUuid());
		} else {
			record.setUuid("");
		}
		record.setLang((byte)profile.getLang());
		record.setSource((byte)profile.getSource());
		if(profile.getCompleteness() == 0) {
			record.setCompleteness((byte)10);
		} else {
			record.setCompleteness((byte)profile.getCompleteness());
		}
		record.setUserId(profile.getUser_id());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		record.setCreateTime(timestamp);
		return record;
	}

	public ProfileDao<ProfileProfileRecord> getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(
			ProfileDao<ProfileProfileRecord> profileDao) {
		this.profileDao = profileDao;
	}
}
