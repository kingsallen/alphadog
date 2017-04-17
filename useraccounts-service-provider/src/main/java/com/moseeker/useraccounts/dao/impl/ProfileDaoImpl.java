package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.useraccounts.dao.ProfileDao;

@Repository
public class ProfileDaoImpl extends BaseDaoImpl<ProfileProfileRecord, ProfileProfile> implements ProfileDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = ProfileProfile.PROFILE_PROFILE;
    }

    public ProfileProfileRecord getProfileByUserId(int userId) {
        Connection conn = null;
        ProfileProfileRecord record = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            record = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                    .where(ProfileProfile.PROFILE_PROFILE.USER_ID.equal((int)(userId)))
                    .fetchAny();
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
            //do nothing
        }
        return record;
    }

	@Override
	public int updateUpdateTimeByUserId(int userId) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq((int)(userId)))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
