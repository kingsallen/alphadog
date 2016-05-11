package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileTrainingRecord;
import com.moseeker.profile.dao.TrainingDao;
import com.moseeker.thrift.gen.profile.service.TrainingServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Training;

@Service
public class ProfileTrainingServicesImpl extends JOOQBaseServiceImpl<Training, ProfileTrainingRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileTrainingServicesImpl.class);

	@Autowired
	private TrainingDao dao;

	public TrainingDao getDao() {
		return dao;
	}

	public void setDao(TrainingDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Training DBToStruct(ProfileTrainingRecord r) {
		return (Training) BeanUtils.DBToStruct(Training.class, r);
	}

	@Override
	protected ProfileTrainingRecord structToDB(Training training) throws ParseException {
		return (ProfileTrainingRecord) BeanUtils.structToDB(training, ProfileTrainingRecord.class);
	}
}
