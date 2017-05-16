package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCredentials;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCredentialsDO;
import com.moseeker.thrift.gen.profile.struct.Credentials;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Set;

/**
* @author xxx
* ProfileCredentialsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileCredentialsDao extends JooqCrudImpl<ProfileCredentialsDO, ProfileCredentialsRecord> {

    public ProfileCredentialsDao() {
        super(ProfileCredentials.PROFILE_CREDENTIALS, ProfileCredentialsDO.class);
    }

    public ProfileCredentialsDao(TableImpl<ProfileCredentialsRecord> table, Class<ProfileCredentialsDO> profileCredentialsDOClass) {
        super(table, profileCredentialsDOClass);
    }

    protected Credentials DBToStruct(ProfileCredentialsRecord r) {
        return (Credentials) BeanUtils.DBToStruct(Credentials.class, r);
    }

    protected ProfileCredentialsRecord structToDB(Credentials credentials) throws ParseException {
        return (ProfileCredentialsRecord) BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
    }

    public int updateProfileUpdateTime(Set<Integer> credentialIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(create.select(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID)
                                    .from(ProfileCredentials.PROFILE_CREDENTIALS)
                                    .where(ProfileCredentials.PROFILE_CREDENTIALS.ID.in(credentialIds))))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }

    public int delCredentialsByProfileId(int profileId) {
        int count = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            count = create.delete(ProfileCredentials.PROFILE_CREDENTIALS)
                    .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.equal((int)(profileId)))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return count;
    }
}
