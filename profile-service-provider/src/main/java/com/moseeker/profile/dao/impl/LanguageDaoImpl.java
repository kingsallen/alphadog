package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileLanguage;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.profile.dao.LanguageDao;

@Repository
public class LanguageDaoImpl extends
		BaseDaoImpl<ProfileLanguageRecord, ProfileLanguage> implements
		LanguageDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileLanguage.PROFILE_LANGUAGE;
	}
}
