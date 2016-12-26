package com.moseeker.profile.dao;

import java.util.HashSet;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;



public interface AwardsDao extends BaseDao<ProfileAwardsRecord> {

	int updateProfileUpdateTime(HashSet<Integer> awardIds);

	int delAwardsByProfileId(int profileId);

}
