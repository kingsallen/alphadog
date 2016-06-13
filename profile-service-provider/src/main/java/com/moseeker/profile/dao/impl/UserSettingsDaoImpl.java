package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.profile.dao.UserSettingsDao;

@Repository
public class UserSettingsDaoImpl extends
		BaseDaoImpl<UserSettingsRecord, UserSettings> implements
		UserSettingsDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserSettings.USER_SETTINGS;
	}

	@Override
	public UserSettingsRecord getUserSettingsById(int userId) {
		UserSettingsRecord record = null;
		Connection conn = null;
		try {
			if(userId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<UserSettingsRecord> result = create.selectFrom(UserSettings.USER_SETTINGS)
						.where(UserSettings.USER_SETTINGS.USER_ID.equal(UInteger.valueOf(userId)))
						.limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return record;
	}
}
