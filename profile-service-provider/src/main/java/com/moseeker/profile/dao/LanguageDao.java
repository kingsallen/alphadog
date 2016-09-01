package com.moseeker.profile.dao;

import java.util.Set;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;



public interface LanguageDao extends BaseDao<ProfileLanguageRecord> {

	int updateProfileUpdateTime(Set<Integer> intentionIds);

}
