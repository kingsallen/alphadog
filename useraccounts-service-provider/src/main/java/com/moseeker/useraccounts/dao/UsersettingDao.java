package com.moseeker.useraccounts.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;;

public interface UsersettingDao extends BaseDao<UserSettingsRecord> {

	int updateProfileUpdateTime(List<Integer> idArray);

}
