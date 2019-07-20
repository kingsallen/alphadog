package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileLanguage;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileLanguageDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileLanguageDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileLanguageDao extends JooqCrudImpl<ProfileLanguageDO, ProfileLanguageRecord> {

    public ProfileLanguageDao() {
        super(ProfileLanguage.PROFILE_LANGUAGE, ProfileLanguageDO.class);
    }

    public ProfileLanguageDao(TableImpl<ProfileLanguageRecord> table, Class<ProfileLanguageDO> profileLanguageDOClass) {
        super(table, profileLanguageDOClass);
    }

    public int updateProfileUpdateTime(Set<Integer> languageIds) {
        int status = 0;

        List<Integer> profileIdList = create.select(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID)
                .from(ProfileLanguage.PROFILE_LANGUAGE)
                .where(ProfileLanguage.PROFILE_LANGUAGE.ID.in(languageIds))
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

    public int delLanguageByProfileId(int profileId) {
        int count = 0;
        count = create.delete(ProfileLanguage.PROFILE_LANGUAGE)
                .where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.equal((int) (profileId)))
                .execute();

        return count;
    }

    public List<ProfileLanguageRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileLanguage.PROFILE_LANGUAGE)
                    .where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
