package com.moseeker.profile.dao.impl;

import com.moseeker.db.profileDB.tables.ProfileBasic;
import com.moseeker.db.profileDB.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.BasicDaoImpl;
import com.moseeker.profile.dao.ProfileDao;

public class ProfileBasicDaoImpl extends BasicDaoImpl<ProfileBasicRecord, ProfileBasic> implements
		ProfileDao<ProfileBasicRecord> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileBasic.PROFILE_BASIC;
	}
}
