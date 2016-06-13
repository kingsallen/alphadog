package com.moseeker.useraccounts.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.useraccounts.dao.ProfileDao;
import org.jooq.DSLContext;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

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
            UByte disable = UByte.valueOf(0);
            record = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                    .where(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId)))
                    .and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(disable))
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
}
