package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.thrift.gen.profile.service.LanguageServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Language;

@Service
public class ProfileLanguageServicesImpl extends JOOQBaseServiceImpl<Language, ProfileLanguageRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileLanguageServicesImpl.class);

	@Autowired
	private LanguageDao dao;
	
	public LanguageDao getDao() {
		return dao;
	}

	public void setDao(LanguageDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Language DBToStruct(ProfileLanguageRecord r) {
		return (Language) BeanUtils.DBToStruct(Language.class, r);
	}

	@Override
	protected ProfileLanguageRecord structToDB(Language language) throws ParseException {
		return (ProfileLanguageRecord) BeanUtils.structToDB(language, ProfileLanguageRecord.class);
	}
}
