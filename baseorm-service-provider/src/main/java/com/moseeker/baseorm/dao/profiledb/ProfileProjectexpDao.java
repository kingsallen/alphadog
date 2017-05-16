package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProjectexpDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

/**
* @author xxx
* ProfileProjectexpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileProjectexpDao extends JooqCrudImpl<ProfileProjectexpDO, ProfileProjectexpRecord> {

    public ProfileProjectexpDao() {
        super(ProfileProjectexp.PROFILE_PROJECTEXP, ProfileProjectexpDO.class);
    }

    public ProfileProjectexpDao(TableImpl<ProfileProjectexpRecord> table, Class<ProfileProjectexpDO> profileProjectexpDOClass) {
        super(table, profileProjectexpDOClass);
    }

    public int updateProfileUpdateTime(Set<Integer> projectExpIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(create.select(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID)
                                    .from(ProfileProjectexp.PROFILE_PROJECTEXP)
                                    .where(ProfileProjectexp.PROFILE_PROJECTEXP.ID.in(projectExpIds))))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }

    public int delProjectExpByProfileId(int profileId) {
        int count = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            count = create.delete(ProfileProjectexp.PROFILE_PROJECTEXP)
                    .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.equal((int)(profileId)))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return count;
    }
}
