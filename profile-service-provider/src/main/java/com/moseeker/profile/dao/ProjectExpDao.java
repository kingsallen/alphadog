package com.moseeker.profile.dao;

import java.util.Set;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;



public interface ProjectExpDao extends BaseDao<ProfileProjectexpRecord> {

	int updateProfileUpdateTime(Set<Integer> projectExpIds);

	int delProjectExpByProfileId(int profileId);

}
