package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileSkill;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileSkillDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

/**
* @author xxx
* ProfileSkillDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileSkillDao extends JooqCrudImpl<ProfileSkillDO, ProfileSkillRecord> {

    public ProfileSkillDao() {
        super(ProfileSkill.PROFILE_SKILL, ProfileSkillDO.class);
    }

    public ProfileSkillDao(TableImpl<ProfileSkillRecord> table, Class<ProfileSkillDO> profileSkillDOClass) {
        super(table, profileSkillDOClass);
    }

    public int updateProfileUpdateTime(Set<Integer> skillIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(create.select(ProfileSkill.PROFILE_SKILL.PROFILE_ID)
                                    .from(ProfileSkill.PROFILE_SKILL)
                                    .where(ProfileSkill.PROFILE_SKILL.ID.in(skillIds))))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }

    public int delSkillByProfileId(int profileId) {
        int count = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            count = create.delete(ProfileSkill.PROFILE_SKILL)
                    .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.equal((int)(profileId)))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return count;
    }
}
