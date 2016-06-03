package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.UUID;

import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.common.struct.Response;
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
	
	@Override
	public Response postResource(Profile struct) throws TException {
		struct.setUuid(UUID.randomUUID().toString());
		return super.postResource(struct);
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
		return (Profile)BeanUtils.DBToStruct(Profile.class, r);
	}

	@Override
	protected ProfileProfileRecord structToDB(Profile profile)
			throws ParseException {
		return (ProfileProfileRecord)BeanUtils.structToDB(profile, ProfileProfileRecord.class);
	}
}
