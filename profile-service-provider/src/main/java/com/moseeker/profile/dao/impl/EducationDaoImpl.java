package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileEducation;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.profile.dao.EducationDao;

@Repository
public class EducationDaoImpl extends
		BaseDaoImpl<ProfileEducationRecord, ProfileEducation> implements
		 EducationDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileEducation.PROFILE_EDUCATION;
	}
}
