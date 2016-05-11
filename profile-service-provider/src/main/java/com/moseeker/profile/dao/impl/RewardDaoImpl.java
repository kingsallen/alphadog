package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileReward;
import com.moseeker.db.profiledb.tables.records.ProfileRewardRecord;
import com.moseeker.profile.dao.RewardDao;

@Repository
public class RewardDaoImpl extends
		BaseDaoImpl<ProfileRewardRecord, ProfileReward> implements
		RewardDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileReward.PROFILE_REWARD;
	}
}
