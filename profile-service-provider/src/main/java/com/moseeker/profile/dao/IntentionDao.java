package com.moseeker.profile.dao;

import java.util.HashSet;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;



public interface IntentionDao extends BaseDao<ProfileIntentionRecord> {

	int updateProfileUpdateTime(HashSet<Integer> intentionIds);

}
