package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileBasic;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;

@Repository
public class ProfileBasicDaoImpl extends BaseDaoImpl<ProfileBasicRecord, ProfileBasic> implements
	ProfileBasicDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileBasic.PROFILE_BASIC;
	}
}
