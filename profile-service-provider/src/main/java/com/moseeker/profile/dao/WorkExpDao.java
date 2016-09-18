package com.moseeker.profile.dao;

import java.util.Set;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;



public interface WorkExpDao extends BaseDao<ProfileWorkexpRecord> {

	ProfileWorkexpRecord getLastWorkExp(int intValue);

	int updateProfileUpdateTime(Set<Integer> workExpIds);

}
