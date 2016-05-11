package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileExtRecord;
import com.moseeker.profile.dao.ProfileExtDao;
import com.moseeker.thrift.gen.profile.service.ProfileExtServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProfileExt;

@Service
public class ProfileExtServicesImpl extends JOOQBaseServiceImpl<ProfileExt, ProfileExtRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileExtServicesImpl.class);

	@Autowired
	private ProfileExtDao dao;
	
	public ProfileExtDao getDao() {
		return dao;
	}

	public void setDao(ProfileExtDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected ProfileExt DBToStruct(ProfileExtRecord r) {
		return (ProfileExt) BeanUtils.DBToStruct(ProfileExt.class, r);
	}

	@Override
	protected ProfileExtRecord structToDB(ProfileExt profileExt) throws ParseException {
		return (ProfileExtRecord) BeanUtils.structToDB(profileExt, ProfileExtRecord.class);
	}
}
