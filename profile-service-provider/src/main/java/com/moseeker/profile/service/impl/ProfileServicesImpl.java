package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.Date;

import org.jooq.types.UInteger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileServicesImpl extends JOOQBaseServiceImpl<Profile, ProfileProfileRecord> implements Iface {

	@Autowired
	protected ProfileDao dao;
	
	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	public ProfileDao getDao() {
		return dao;
	}

	public void setDao(ProfileDao dao) {
		this.dao = dao;
	}
	
	@Test
	public void structTest() {
		Education education = new Education();
		java.lang.reflect.Field[] fields = education.getClass().getFields();
		for(int i=0; i< fields.length; i++) {
			System.out.println(fields[i].getName());
		}
	}

	@Override
	protected Profile DBToStruct(ProfileProfileRecord r) {
		Profile profile = null;
		if (r != null) {
			profile = new Profile();
			profile.setId(r.getId().intValue());
			profile.setUuid(r.getUuid());
			profile.setLang(r.getLang());
			profile.setSource(r.getSource());
			profile.setCompleteness(r.getCompleteness());
			profile.setUser_id(r.getUserId());
			if (r.getCreateTime() != null) {
				profile.setCreate_time(DateUtils.dateToNormalDate(new Date(r
						.getCreateTime().getTime())));
			}
			if (r.getUpdateTime() != null) {
				profile.setUpdate_time(DateUtils.dateToNormalDate(new Date(r
						.getUpdateTime().getTime())));
			}
		}
		return profile;
	}

	@Override
	protected ProfileProfileRecord structToDB(Profile profile)
			throws ParseException {
		ProfileProfileRecord record = new ProfileProfileRecord();
		if (profile.getId() != 0) {
			record.setId(UInteger.valueOf(profile.getId()));
		}
		if (!StringUtils.isNullOrEmpty(profile.getUuid())) {
			record.setUuid(profile.getUuid());
		} else {
			record.setUuid("");
		}
		record.setLang((byte) profile.getLang());
		record.setSource((byte) profile.getSource());
		if (profile.getCompleteness() == 0) {
			record.setCompleteness((byte) 10);
		} else {
			record.setCompleteness((byte) profile.getCompleteness());
		}
		record.setUserId(profile.getUser_id());
		return record;
	}
}
