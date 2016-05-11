package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileTrainingRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.dao.TrainingDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.thrift.gen.profile.service.WorkExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Training;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

@Service
public class ProfileWorkExpServicesImpl extends JOOQBaseServiceImpl<WorkExp, ProfileWorkexpRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileWorkExpServicesImpl.class);

	@Autowired
	private WorkExpDao dao;

	public WorkExpDao getDao() {
		return dao;
	}

	public void setDao(WorkExpDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected WorkExp DBToStruct(ProfileWorkexpRecord r) {
		return (WorkExp) BeanUtils.DBToStruct(WorkExp.class, r);
	}

	@Override
	protected ProfileWorkexpRecord structToDB(WorkExp workExp) throws ParseException {
		return (ProfileWorkexpRecord) BeanUtils.structToDB(workExp, ProfileWorkexpRecord.class);
	}
}
