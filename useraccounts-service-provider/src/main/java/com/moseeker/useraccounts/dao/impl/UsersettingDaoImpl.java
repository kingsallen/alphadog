package com.moseeker.useraccounts.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.useraccounts.dao.UserDao;
import com.moseeker.useraccounts.dao.UsersettingDao;;

@Repository
public class UsersettingDaoImpl extends BaseDaoImpl<UserSettingsRecord, UserSettings> implements UsersettingDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserSettings.USER_SETTINGS;
	}
}
