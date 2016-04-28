package com.moseeker.profile.service.impl;

import java.sql.Timestamp;

import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.db.dqv4.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileServicesImpl extends BasicServiceImpl<ProfileProfileRecord, Profile> implements Iface {

	@Autowired
	private ProfileDao<com.moseeker.db.dqv4.tables.records.ProfileProfileRecord> dao;
	
	@Override
	protected void initDao() {
		this.basicDao = dao;
	}
	
	@Override
	protected Profile dbToStruct(com.moseeker.db.dqv4.tables.records.ProfileProfileRecord r) {
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
		record.setId(UInteger.valueOf(profile.getId()));
		record.setUuid(profile.getUuid());
		record.setLang((byte)profile.getLang());
		record.setSource((byte)profile.getSource());
		record.setCompleteness((byte)profile.getCompleteness());
		record.setUserId(profile.getUser_id());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		record.setCreateTime(timestamp);
		return record;
	}
	
	public ProfileDao<com.moseeker.db.dqv4.tables.records.ProfileProfileRecord> getDao() {
		return dao;
	}

	public void setDao(
			ProfileDao<com.moseeker.db.dqv4.tables.records.ProfileProfileRecord> dao) {
		this.dao = dao;
	}
}
