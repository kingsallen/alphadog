package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAwards;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAwardsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileAwardsDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileAwardsDao extends JooqCrudImpl<ProfileAwardsDO, ProfileAwardsRecord> {

    public ProfileAwardsDao() {
        super(ProfileAwards.PROFILE_AWARDS, ProfileAwardsDO.class);
    }

    public ProfileAwardsDao(TableImpl<ProfileAwardsRecord> table, Class<ProfileAwardsDO> profileAwardsDOClass) {
        super(table, profileAwardsDOClass);
    }

    public int updateProfileUpdateTime(HashSet<Integer> awardIds) {

        int status = 0;

        List<Integer> profileIdList = create.select(ProfileAwards.PROFILE_AWARDS.PROFILE_ID)
                .from(ProfileAwards.PROFILE_AWARDS)
                .where(ProfileAwards.PROFILE_AWARDS.ID.in(awardIds))
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

    public int delAwardsByProfileId(int profileId) {
        int count = create.delete(ProfileAwards.PROFILE_AWARDS)
                .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.equal(profileId))
                .execute();

        return count;
    }
}
