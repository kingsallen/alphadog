package com.moseeker.profile.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;

public interface CompletenessDao extends BaseDao<ProfileCompletenessRecord> {

	ProfileCompletenessRecord getCompletenessByProfileId(int profileId);

	void reCalculateProfileCompleteness(int profileId);

	int updateCompleteness(ProfileCompletenessRecord completenessRecord);

	int saveOrUpdate(ProfileCompletenessRecord completenessRecord);

	
}
