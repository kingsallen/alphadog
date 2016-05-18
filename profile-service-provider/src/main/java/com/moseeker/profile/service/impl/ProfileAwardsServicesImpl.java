package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.profile.dao.AwardsDao;
import com.moseeker.thrift.gen.profile.service.AwardsServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Awards;

@Service
public class ProfileAwardsServicesImpl extends JOOQBaseServiceImpl<Awards, ProfileAwardsRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileAwardsServicesImpl.class);

	@Autowired
	private AwardsDao dao;
	
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

	@Override
	protected Awards DBToStruct(ProfileAwardsRecord r) {
		return (Awards)BeanUtils.DBToStruct(Awards.class, r);
	}

	@Override
	protected ProfileAwardsRecord structToDB(Awards awards) throws ParseException {
		return (ProfileAwardsRecord)BeanUtils.structToDB(awards, ProfileAwardsRecord.class);
	}
}
