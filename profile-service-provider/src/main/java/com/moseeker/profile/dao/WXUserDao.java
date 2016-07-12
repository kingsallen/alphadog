package com.moseeker.profile.dao;

import java.sql.SQLException;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;

public interface WXUserDao extends BaseDao<UserWxUserRecord> {

	public UserWxUserRecord getWXUserByUserId(int userId) throws SQLException;
}
