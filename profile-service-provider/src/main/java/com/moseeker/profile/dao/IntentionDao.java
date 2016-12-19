package com.moseeker.profile.dao;

import java.util.HashSet;
import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.profile.dao.impl.IntentionRecord;



public interface IntentionDao extends BaseDao<ProfileIntentionRecord> {

	int updateProfileUpdateTime(HashSet<Integer> intentionIds);

	int delIntentionsByProfileId(int profileId);

	int postIntentions(List<IntentionRecord> intentionRecords);

}
