package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.dao.WorkExpDao;

@Repository
public class WorkExpDaoImpl extends
		BaseDaoImpl<ProfileWorkexpRecord, ProfileWorkexp> implements
		WorkExpDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileWorkexp.PROFILE_WORKEXP;
	}

	@Override
	public ProfileWorkexpRecord getLastWorkExp(int profileId) {
		ProfileWorkexpRecord record = null;
		Connection conn = null;
		try {
			if(profileId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<ProfileWorkexpRecord> result = create.selectFrom(ProfileWorkexp.PROFILE_WORKEXP)
						.where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.equal(UInteger.valueOf(profileId)))
						.orderBy(ProfileWorkexp.PROFILE_WORKEXP.END_UNTIL_NOW.desc(), ProfileWorkexp.PROFILE_WORKEXP.END.desc())
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

	@Override
	public int updateProfileUpdateTime(Set<Integer> workExpIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID)
									.from(ProfileWorkexp.PROFILE_WORKEXP)
									.where(ProfileWorkexp.PROFILE_WORKEXP.ID.in(workExpIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
