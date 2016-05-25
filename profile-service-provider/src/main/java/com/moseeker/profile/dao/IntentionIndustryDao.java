package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionIndustryRecord;

public interface IntentionIndustryDao extends BaseDao<ProfileIntentionIndustryRecord> {

	List<ProfileIntentionIndustryRecord> getIntentionIndustries(List<Integer> intentionIds);

}
