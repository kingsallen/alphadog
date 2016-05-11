package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.profile.dao.EducationDao;
import com.moseeker.thrift.gen.profile.service.EducationServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Education;

@Service
public class ProfileEducationServicesImpl extends JOOQBaseServiceImpl<Education, ProfileEducationRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileEducationServicesImpl.class);

	@Autowired
	private EducationDao dao;

	public EducationDao getDao() {
		return dao;
	}

	public void setDao(EducationDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Education DBToStruct(ProfileEducationRecord r) {
		return (Education) BeanUtils.DBToStruct(Education.class, r);
	}

	@Override
	protected ProfileEducationRecord structToDB(Education attachment) throws ParseException {
		return (ProfileEducationRecord) BeanUtils.structToDB(attachment, ProfileAttachmentRecord.class);
	}
}
