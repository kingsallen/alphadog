package com.moseeker.profile.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;



public interface ProfileDao extends BaseDao<ProfileProfileRecord> {

	ProfileProfileRecord getProfileByIdOrUserId(int userId, int profileId);

}
