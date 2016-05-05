package com.moseeker.profile.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;

import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.daoutils.BaseDaoImpl;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Repository
public class ProfileDaoImpl extends BaseDaoImpl<ProfileProfileRecord, ProfileProfile, Profile> implements ProfileDao<Profile> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProfile.PROFILE_PROFILE;
	}
	
	@Override
	protected Profile DBToStruct(ProfileProfileRecord r) {
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
	protected ProfileProfileRecord structToDB(Profile profile)
			throws ParseException {
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
}
