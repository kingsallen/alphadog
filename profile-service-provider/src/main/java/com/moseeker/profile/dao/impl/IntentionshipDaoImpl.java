package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileInternship;
import com.moseeker.db.profiledb.tables.records.ProfileInternshipRecord;
import com.moseeker.profile.dao.InternshipDao;

@Repository
public class IntentionshipDaoImpl extends
		BaseDaoImpl<ProfileInternshipRecord, ProfileInternship> implements
		InternshipDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileInternship.PROFILE_INTERNSHIP;
	}
}
