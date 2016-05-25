package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileDao;

@Repository
public class ProfileDaoImpl extends
		BaseDaoImpl<ProfileProfileRecord, ProfileProfile> implements
		ProfileDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProfile.PROFILE_PROFILE;
	}

	@Override
	public ProfileProfileRecord getProfileByIdOrUserId(int userId, int profileId) {
		ProfileProfileRecord record = null;
		Connection conn = null;
		try {
			if(userId > 0 || profileId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<ProfileProfileRecord> result = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
						.where((ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)))
						.or(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId))))
						.and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(UByte.valueOf(1)))
						.limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
			}
		} catch (SQLException e) {
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
