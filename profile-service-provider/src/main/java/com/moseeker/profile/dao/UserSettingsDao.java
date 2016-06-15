package com.moseeker.profile.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;

public interface UserSettingsDao extends BaseDao<UserSettingsRecord> {

	UserSettingsRecord getUserSettingsById(int intValue);

}
