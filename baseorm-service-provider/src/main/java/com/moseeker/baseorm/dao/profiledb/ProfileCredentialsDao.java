package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCredentials;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCredentialsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileCredentialsDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileCredentialsDao extends JooqCrudImpl<ProfileCredentialsDO, ProfileCredentialsRecord> {

    public ProfileCredentialsDao() {
        super(ProfileCredentials.PROFILE_CREDENTIALS, ProfileCredentialsDO.class);
    }

    public ProfileCredentialsDao(TableImpl<ProfileCredentialsRecord> table, Class<ProfileCredentialsDO> profileCredentialsDOClass) {
        super(table, profileCredentialsDOClass);
    }

    public int updateProfileUpdateTime(Set<Integer> credentialIds) {

        int status = 0;

        List<Integer> profileIdList = create.select(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID)
                .from(ProfileCredentials.PROFILE_CREDENTIALS)
                .where(ProfileCredentials.PROFILE_CREDENTIALS.ID.in(credentialIds))
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

    public int delCredentialsByProfileId(int profileId) {
        int count = create.delete(ProfileCredentials.PROFILE_CREDENTIALS)
                .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.equal((int) (profileId)))
                .execute();

        return count;
    }

    public List<ProfileCredentialsRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileCredentials.PROFILE_CREDENTIALS)
                    .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
