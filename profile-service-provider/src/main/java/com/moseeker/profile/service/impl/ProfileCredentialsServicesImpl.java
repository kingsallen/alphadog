package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.thrift.gen.profile.service.CredentialsServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Awards;
import com.moseeker.thrift.gen.profile.struct.Credentials;

@Service
public class ProfileCredentialsServicesImpl extends JOOQBaseServiceImpl<Credentials, ProfileCredentialsRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileCredentialsServicesImpl.class);

	@Autowired
	private CredentialsDao dao;
	
	public CredentialsDao getDao() {
		return dao;
	}

	public void setDao(CredentialsDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Credentials DBToStruct(ProfileCredentialsRecord r) {
		return (Credentials)BeanUtils.DBToStruct(Credentials.class, r);
	}

	@Override
	protected ProfileCredentialsRecord structToDB(Credentials credentials) throws ParseException {
		return (ProfileCredentialsRecord)BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
	}
}
