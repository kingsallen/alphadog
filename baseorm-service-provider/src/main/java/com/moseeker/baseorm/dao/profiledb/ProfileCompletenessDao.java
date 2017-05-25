package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCompletenessDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author xxx
 *         ProfileCompletenessDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileCompletenessDao extends JooqCrudImpl<ProfileCompletenessDO, ProfileCompletenessRecord> {

    public ProfileCompletenessDao() {
        super(ProfileCompleteness.PROFILE_COMPLETENESS, ProfileCompletenessDO.class);
    }

    public ProfileCompletenessDao(TableImpl<ProfileCompletenessRecord> table, Class<ProfileCompletenessDO> profileCompletenessDOClass) {
        super(table, profileCompletenessDOClass);
    }

    public ProfileCompletenessRecord getCompletenessByProfileId(int profileId) {
        ProfileCompletenessRecord record = null;
        record = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS)
                .where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID.equal((int) (profileId)))
                .fetchOne();
        return record;
    }

    public int updateCompleteness(ProfileCompletenessRecord completenessRecord) {
        int result = 0;
        if (completenessRecord != null) {

            create.attach(completenessRecord);
            result = completenessRecord.update();

            ProfileProfileRecord profileRecord = new ProfileProfileRecord();
            profileRecord.setId(completenessRecord.getProfileId());
            create.attach(profileRecord);

            int completeness = calculatorTotalCompleteness(completenessRecord);

            if (profileRecord.getCompleteness() == null
                    || completeness != profileRecord.getCompleteness().intValue()) {
                profileRecord.setCompleteness((byte) (completeness));
                profileRecord.update();
            }
        }

        return result;
    }

    private int calculatorTotalCompleteness(ProfileCompletenessRecord completenessRecord) {
        int completeness = 0;
        completeness += completenessRecord.getUserUser() == null ? 0 : completenessRecord.getUserUser();
        completeness += completenessRecord.getProfileBasic() == null ? 0 : completenessRecord.getProfileBasic();
        completeness += completenessRecord.getProfileWorkexp() == null ? 0 : completenessRecord.getProfileWorkexp();
        completeness += completenessRecord.getProfileEducation() == null ? 0 : completenessRecord.getProfileEducation();
        completeness += completenessRecord.getProfileProjectexp() == null ? 0
                : completenessRecord.getProfileProjectexp();
        completeness += completenessRecord.getProfileLanguage() == null ? 0 : completenessRecord.getProfileLanguage();
        completeness += completenessRecord.getProfileSkill() == null ? 0 : completenessRecord.getProfileSkill();
        completeness += completenessRecord.getProfileCredentials() == null ? 0
                : completenessRecord.getProfileCredentials();
        completeness += completenessRecord.getProfileAwards() == null ? 0 : completenessRecord.getProfileAwards();
        completeness += completenessRecord.getProfileWorks() == null ? 0 : completenessRecord.getProfileWorks();
        completeness += completenessRecord.getProfileIntention() == null ? 0 : completenessRecord.getProfileIntention();
        return completeness;
    }

    public int saveOrUpdate(ProfileCompletenessRecord completenessRecord) {
        int result = 0;
        if (completenessRecord != null && completenessRecord.getProfileId() != null
                && completenessRecord.getProfileId().intValue() > 0) {
            ProfileCompletenessRecord toBeUpdate = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS)
                    .where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID
                            .equal(completenessRecord.getProfileId()))
                    .fetchOne();
            if (toBeUpdate != null) {
                toBeUpdate.setUserUser(completenessRecord.getUserUser());
                toBeUpdate.setProfileBasic(completenessRecord.getProfileBasic());
                toBeUpdate.setProfileWorkexp(completenessRecord.getProfileWorkexp());
                toBeUpdate.setProfileEducation(completenessRecord.getProfileEducation());
                toBeUpdate.setProfileProjectexp(completenessRecord.getProfileProjectexp());
                toBeUpdate.setProfileLanguage(completenessRecord.getProfileLanguage());
                toBeUpdate.setProfileSkill(completenessRecord.getProfileSkill());
                toBeUpdate.setProfileCredentials(completenessRecord.getProfileCredentials());
                toBeUpdate.setProfileAwards(completenessRecord.getProfileAwards());
                toBeUpdate.setProfileWorks(completenessRecord.getProfileWorks());
                toBeUpdate.setProfileIntention(completenessRecord.getProfileIntention());
                create.attach(toBeUpdate);
                result = toBeUpdate.update();
            } else {
                create.attach(completenessRecord);
                result = completenessRecord.insert();
            }
        }
        return result;
    }
}
