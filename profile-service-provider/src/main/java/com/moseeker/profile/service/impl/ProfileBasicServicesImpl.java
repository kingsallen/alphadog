package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Service
public class ProfileBasicServicesImpl extends JOOQBaseServiceImpl<Basic, ProfileBasicRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicServicesImpl.class);

	@Autowired
	private ProfileBasicDao dao;
	
	public ProfileBasicDao getDao() {
		return dao;
	}

	public void setDao(ProfileBasicDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Basic DBToStruct(ProfileBasicRecord r) {
		return (Basic)BeanUtils.DBToStruct(Basic.class, r);
	}

	@Override
	protected ProfileBasicRecord structToDB(Basic basic)
			throws ParseException {
		return (ProfileBasicRecord)BeanUtils.structToDB(basic, ProfileBasicRecord.class);
	}
}
