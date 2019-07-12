package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileSkill;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileSkillDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileSkillDao 实现类 （groovy 生成）
 *         2017-03-21
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

        List<Integer> profileIdList = create.select(ProfileSkill.PROFILE_SKILL.PROFILE_ID)
                .from(ProfileSkill.PROFILE_SKILL)
                .where(ProfileSkill.PROFILE_SKILL.ID.in(skillIds))
                .stream()
                .map(integerRecord1 -> integerRecord1.value1())
                .collect(Collectors.toList());

        if (profileIdList != null && profileIdList.size() > 0) {
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(profileIdList))
                    .execute();
        }

        return status;
    }

    public int delSkillByProfileId(int profileId) {
        int count = 0;
        count = create.delete(ProfileSkill.PROFILE_SKILL)
                .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.equal((int) (profileId)))
                .execute();
        return count;
    }

    public List<ProfileSkillRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileSkill.PROFILE_SKILL)
                    .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
