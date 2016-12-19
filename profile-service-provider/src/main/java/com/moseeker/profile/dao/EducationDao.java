package com.moseeker.profile.dao;

import java.util.HashSet;
import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;



public interface EducationDao extends BaseDao<ProfileEducationRecord> {

	int updateProfileUpdateTime(HashSet<Integer> educationIds);

	int delEducationsByProfileId(int profileId);

	int saveEducations(List<ProfileEducationRecord> educationRecords);

}
