package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.useraccounts.dao.UsersettingDao;;

@Repository
public class UsersettingDaoImpl extends BaseDaoImpl<UserSettingsRecord, UserSettings> implements UsersettingDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserSettings.USER_SETTINGS;
	}

	@Override
	public int updateProfileUpdateTime(List<Integer> idArray) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.USER_ID
							.in(create.select(UserSettings.USER_SETTINGS.USER_ID)
									.from(UserSettings.USER_SETTINGS)
									.where(UserSettings.USER_SETTINGS.ID.in(idArray))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int updateProfileUpdateTimeByUserId(List<Integer> idArray) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.USER_ID.in(idArray))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
