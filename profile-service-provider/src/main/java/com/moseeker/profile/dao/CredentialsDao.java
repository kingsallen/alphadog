package com.moseeker.profile.dao;

import java.util.Set;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;



public interface CredentialsDao extends BaseDao<ProfileCredentialsRecord> {

	int updateProfileUpdateTime(Set<Integer> credentialIds);

}
