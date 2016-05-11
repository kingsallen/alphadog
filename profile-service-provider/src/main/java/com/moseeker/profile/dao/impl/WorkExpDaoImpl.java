package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.dao.WorkExpDao;

@Repository
public class WorkExpDaoImpl extends
		BaseDaoImpl<ProfileWorkexpRecord, ProfileWorkexp> implements
		WorkExpDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileWorkexp.PROFILE_WORKEXP;
	}
}
