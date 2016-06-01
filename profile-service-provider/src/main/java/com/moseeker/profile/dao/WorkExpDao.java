package com.moseeker.profile.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;



public interface WorkExpDao extends BaseDao<ProfileWorkexpRecord> {

	ProfileWorkexpRecord getLastWorkExp(int intValue);

}
