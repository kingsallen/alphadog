package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileEducationExtRecord;
import com.moseeker.profile.dao.EducationExtDao;
import com.moseeker.thrift.gen.profile.service.EducationExtServices.Iface;
import com.moseeker.thrift.gen.profile.struct.EducationExt;

@Service
public class ProfileEducationExtServicesImpl extends JOOQBaseServiceImpl<EducationExt, ProfileEducationExtRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileEducationExtServicesImpl.class);

	@Autowired
	private EducationExtDao dao;


	public EducationExtDao getDao() {
		return dao;
	}

	public void setDao(EducationExtDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected EducationExt DBToStruct(ProfileEducationExtRecord r) {
		return (EducationExt) BeanUtils.DBToStruct(EducationExt.class, r);
	}

	@Override
	protected ProfileEducationExtRecord structToDB(EducationExt educationExt) throws ParseException {
		return (ProfileEducationExtRecord) BeanUtils.structToDB(educationExt, ProfileEducationExtRecord.class);
	}
}
