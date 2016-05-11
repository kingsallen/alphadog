package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.thrift.gen.profile.service.IntentionServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Intention;

@Service
public class ProfileIntentionServicesImpl extends JOOQBaseServiceImpl<Intention, ProfileIntentionRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileIntentionServicesImpl.class);

	@Autowired
	private IntentionDao dao;

	public IntentionDao getDao() {
		return dao;
	}

	public void setDao(IntentionDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Intention DBToStruct(ProfileIntentionRecord r) {
		return (Intention) BeanUtils.DBToStruct(Intention.class, r);
	}

	@Override
	protected ProfileIntentionRecord structToDB(Intention intention) throws ParseException {
		return (ProfileIntentionRecord) BeanUtils.structToDB(intention, ProfileIntentionRecord.class);
	}
}
