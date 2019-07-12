package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProjectexpDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileProjectexpDao 实现类 （groovy 生成）
 *         2017-03-21
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

        List<Integer> profileIdList = create.select(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID)
                .from(ProfileProjectexp.PROFILE_PROJECTEXP)
                .where(ProfileProjectexp.PROFILE_PROJECTEXP.ID.in(projectExpIds))
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

    public int delProjectExpByProfileId(int profileId) {
        int count = create.delete(ProfileProjectexp.PROFILE_PROJECTEXP)
                .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.equal(profileId))
                .execute();

        return count;
    }

    public List<ProfileProjectexpRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileProjectexp.PROFILE_PROJECTEXP)
                    .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
