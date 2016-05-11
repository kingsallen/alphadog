package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileSchooljob;
import com.moseeker.db.profiledb.tables.records.ProfileSchooljobRecord;
import com.moseeker.profile.dao.SchoolJobDao;

@Repository
public class SchoolJobDaoImpl extends
		BaseDaoImpl<ProfileSchooljobRecord, ProfileSchooljob> implements
		SchoolJobDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileSchooljob.PROFILE_SCHOOLJOB;
	}
}
