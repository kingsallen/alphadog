package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;

public interface IntentionPositionDao extends BaseDao<ProfileIntentionPositionRecord> {

	List<ProfileIntentionPositionRecord> getIntentionPositions(List<Integer> intentionIds);

}
