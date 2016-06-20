package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserWxUser;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.profile.dao.WXUserDao;

@Repository
public class WXUserDaoImpl extends BaseDaoImpl<UserWxUserRecord, UserWxUser> implements WXUserDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserWxUser.USER_WX_USER;
	}

	@Override
	public UserWxUserRecord getWXUserByUserId(int userId) throws SQLException {
		UserWxUserRecord wxuser = null;
		try (
				Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			) {
			
		} catch (SQLException e) {
			throw e;
		}
		return wxuser;
	}
}
