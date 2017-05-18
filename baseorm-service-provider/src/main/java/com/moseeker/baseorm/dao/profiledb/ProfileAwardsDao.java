package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAwards;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAwardsDO;
import com.moseeker.thrift.gen.profile.struct.Awards;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashSet;

/**
* @author xxx
* ProfileAwardsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileAwardsDao extends JooqCrudImpl<ProfileAwardsDO, ProfileAwardsRecord> {

    public ProfileAwardsDao() {
        super(ProfileAwards.PROFILE_AWARDS, ProfileAwardsDO.class);
    }

    public ProfileAwardsDao(TableImpl<ProfileAwardsRecord> table, Class<ProfileAwardsDO> profileAwardsDOClass) {
        super(table, profileAwardsDOClass);
    }

    protected Awards DBToStruct(ProfileAwardsRecord r) {
        return (Awards) BeanUtils.DBToStruct(Awards.class, r);
    }

    protected ProfileAwardsRecord structToDB(Awards attachment)
            throws ParseException {
        return (ProfileAwardsRecord)BeanUtils.structToDB(attachment, ProfileAwardsRecord.class);
    }

    public int updateProfileUpdateTime(HashSet<Integer> awardIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(create.select(ProfileAwards.PROFILE_AWARDS.PROFILE_ID)
                                    .from(ProfileAwards.PROFILE_AWARDS)
                                    .where(ProfileAwards.PROFILE_AWARDS.ID.in(awardIds))))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }

    public int delAwardsByProfileId(int profileId) {
        int count= 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            count = create.delete(ProfileAwards.PROFILE_AWARDS)
                    .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.equal((int)(profileId)))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return count;
    }
}
