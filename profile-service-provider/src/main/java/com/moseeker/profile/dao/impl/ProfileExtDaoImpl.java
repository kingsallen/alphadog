package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileExt;
import com.moseeker.db.profiledb.tables.records.ProfileExtRecord;
import com.moseeker.profile.dao.ProfileExtDao;

@Repository
public class ProfileExtDaoImpl
		extends BaseDaoImpl<ProfileExtRecord, ProfileExt>
		implements ProfileExtDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileExt.PROFILE_EXT;
	}
}
