package com.moseeker.profile.dao;

import java.util.Set;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;



public interface WorksDao extends BaseDao<ProfileWorksRecord> {

	int updateProfileUpdateTime(Set<Integer> workIds);

	int delWorksByProfileId(int profileId);

}
