package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorks;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorksDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileWorksDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileWorksDao extends JooqCrudImpl<ProfileWorksDO, ProfileWorksRecord> {

    public ProfileWorksDao() {
        super(ProfileWorks.PROFILE_WORKS, ProfileWorksDO.class);
    }

    public ProfileWorksDao(TableImpl<ProfileWorksRecord> table, Class<ProfileWorksDO> profileWorksDOClass) {
        super(table, profileWorksDOClass);
    }

    public int updateProfileUpdateTime(Set<Integer> workIds) {
        int status = 0;

        List<Integer> profileIdList = create.select(ProfileWorks.PROFILE_WORKS.PROFILE_ID)
                .from(ProfileWorks.PROFILE_WORKS)
                .where(ProfileWorks.PROFILE_WORKS.ID.in(workIds))
                .stream()
                .map(integerRecord1 -> integerRecord1.value1())
                .collect(Collectors.toList());

        if (profileIdList != null && profileIdList.size() > 0) {
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID.in(profileIdList))
                    .execute();
        }

        return status;
    }

    public int delWorksByProfileId(int profileId) {
        int count = 0;
        count = create.delete(ProfileWorks.PROFILE_WORKS)
                .where(ProfileWorks.PROFILE_WORKS.PROFILE_ID.equal((int) (profileId))).execute();

        return count;
    }

    public List<ProfileWorksRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileWorks.PROFILE_WORKS)
                    .where(ProfileWorks.PROFILE_WORKS.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
