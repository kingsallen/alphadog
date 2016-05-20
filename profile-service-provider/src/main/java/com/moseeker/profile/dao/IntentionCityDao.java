package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;

public interface IntentionCityDao extends BaseDao<ProfileIntentionCityRecord> {

	List<ProfileIntentionCityRecord> getIntentionCities(List<Integer> intentionIds);

}
