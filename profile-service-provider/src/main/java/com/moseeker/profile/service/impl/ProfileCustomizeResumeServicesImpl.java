package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.profile.dao.CustomizeResumeDao;
import com.moseeker.thrift.gen.profile.service.CustomizeResumeServices.Iface;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;

@Service
public class ProfileCustomizeResumeServicesImpl extends JOOQBaseServiceImpl<CustomizeResume, ProfileOtherRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileCustomizeResumeServicesImpl.class);

	@Autowired
	private CustomizeResumeDao dao;

	public CustomizeResumeDao getDao() {
		return dao;
	}

	public void setDao(CustomizeResumeDao dao) {
		this.dao = dao;	
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected CustomizeResume DBToStruct(ProfileOtherRecord r) {
		return (CustomizeResume)BeanUtils.DBToStruct(CustomizeResume.class, r);
	}

	@Override
	protected ProfileOtherRecord structToDB(CustomizeResume customizeResume) throws ParseException {
		return (ProfileOtherRecord)BeanUtils.structToDB(customizeResume, ProfileOtherRecord.class);
	}
}
