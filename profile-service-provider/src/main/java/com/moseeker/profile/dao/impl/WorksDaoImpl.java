package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileWorks;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.WorksDao;

@Repository
public class WorksDaoImpl extends
		BaseDaoImpl<ProfileWorksRecord, ProfileWorks> implements
		WorksDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileWorks.PROFILE_WORKS;
	}
}
