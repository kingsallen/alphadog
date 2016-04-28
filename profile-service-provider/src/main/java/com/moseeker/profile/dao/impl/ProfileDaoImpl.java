package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.db.profileDB.tables.Profile;
import com.moseeker.db.profileDB.tables.records.ProfileRecord;
import com.moseeker.profile.dao.BasicDaoImpl;
import com.moseeker.profile.dao.ProfileDao;

@Repository
public class ProfileDaoImpl extends BasicDaoImpl<ProfileRecord, Profile> implements ProfileDao<ProfileRecord> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = Profile.PROFILE;
	}

}
