package com.moseeker.useraccounts.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;

public interface ProfileDao extends BaseDao<ProfileProfileRecord> {

	ProfileProfileRecord getProfileByUserId(int intValue) throws Exception;

	int updateUpdateTimeByUserId(int id);
}
