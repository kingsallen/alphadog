package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.profile.dao.ProjectExpDao;

@Repository
public class ProjectExpDaoImpl extends
		BaseDaoImpl<ProfileProjectexpRecord, ProfileProjectexp> implements
		ProjectExpDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProjectexp.PROFILE_PROJECTEXP;
	}
}
