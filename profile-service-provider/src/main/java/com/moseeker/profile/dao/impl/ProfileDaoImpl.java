package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.BasicDaoImpl;
import com.moseeker.profile.dao.ProfileDao;

@Repository
public class ProfileDaoImpl extends BasicDaoImpl<ProfileProfileRecord, ProfileProfile> implements ProfileDao<ProfileProfileRecord> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProfile.PROFILE_PROFILE;
	}

}
