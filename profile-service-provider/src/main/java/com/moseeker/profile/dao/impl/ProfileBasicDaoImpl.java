package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.db.profiledb.tables.ProfileBasic;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.BasicDaoImpl;
import com.moseeker.profile.dao.ProfileBasicDao;

@Repository
public class ProfileBasicDaoImpl extends BasicDaoImpl<ProfileBasicRecord, ProfileBasic> implements
	ProfileBasicDao<ProfileBasicRecord> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileBasic.PROFILE_BASIC;
	}
}
