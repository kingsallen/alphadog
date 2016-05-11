package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileSchooljobRecord;
import com.moseeker.profile.dao.SchoolJobDao;
import com.moseeker.thrift.gen.profile.service.SchoolJobServices.Iface;
import com.moseeker.thrift.gen.profile.struct.SchoolJob;

@Service
public class ProfileSchoolJobServicesImpl extends JOOQBaseServiceImpl<SchoolJob, ProfileSchooljobRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileSchoolJobServicesImpl.class);

	@Autowired
	private SchoolJobDao dao;

	public SchoolJobDao getDao() {
		return dao;
	}

	public void setDao(SchoolJobDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected SchoolJob DBToStruct(ProfileSchooljobRecord r) {
		return (SchoolJob) BeanUtils.DBToStruct(SchoolJob.class, r);
	}

	@Override
	protected ProfileSchooljobRecord structToDB(SchoolJob schoolJob) throws ParseException {
		return (ProfileSchooljobRecord) BeanUtils.structToDB(schoolJob, ProfileSchooljobRecord.class);
	}
}
