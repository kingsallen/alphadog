package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileTraining;
import com.moseeker.db.profiledb.tables.records.ProfileTrainingRecord;
import com.moseeker.profile.dao.TrainingDao;

@Repository
public class TrainingDaoImpl extends
		BaseDaoImpl<ProfileTrainingRecord, ProfileTraining> implements
		TrainingDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileTraining.PROFILE_TRAINING;
	}
}
