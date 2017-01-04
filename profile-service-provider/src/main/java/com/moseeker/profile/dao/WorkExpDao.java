package com.moseeker.profile.dao;

import java.util.List;
import java.util.Set;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;



public interface WorkExpDao extends BaseDao<ProfileWorkexpRecord> {

	ProfileWorkexpRecord getLastWorkExp(int intValue);

	int updateProfileUpdateTime(Set<Integer> workExpIds);

	int delWorkExpsByProfileId(int profileId);

	int postWordExps(List<ProfileWorkexpEntity> records);

}