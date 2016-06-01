package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileIntention;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.profile.dao.IntentionDao;

@Repository
public class IntentionDaoImpl extends
		BaseDaoImpl<ProfileIntentionRecord, ProfileIntention> implements
		IntentionDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileIntention.PROFILE_INTENTION;
	}
}
