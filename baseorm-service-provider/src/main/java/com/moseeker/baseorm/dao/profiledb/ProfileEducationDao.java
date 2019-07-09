package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xxx
 *         ProfileEducationDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileEducationDao extends JooqCrudImpl<ProfileEducationDO, ProfileEducationRecord> {

    public ProfileEducationDao() {
        super(ProfileEducation.PROFILE_EDUCATION, ProfileEducationDO.class);
    }

    public ProfileEducationDao(TableImpl<ProfileEducationRecord> table, Class<ProfileEducationDO> profileEducationDOClass) {
        super(table, profileEducationDOClass);
    }

    public int updateProfileUpdateTime(Set<Integer> educationIds) {
        int status = 0;

        List<Integer> profileIdList = create.select(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID)
                .from(ProfileEducation.PROFILE_EDUCATION)
                .where(ProfileEducation.PROFILE_EDUCATION.ID.in(educationIds))
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

    public int delEducationsByProfileId(int profileId) {
        int count = 0;
        count = create.delete(ProfileEducation.PROFILE_EDUCATION)
                .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.equal((int) (profileId)))
                .execute();

        return count;
    }

    public int saveEducations(List<ProfileEducationRecord> educationRecords) {
        int count = 0;
        if (educationRecords != null && educationRecords.size() > 0) {

//				ProfileCompletenessRecord completenessRecord = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS).where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID.equal(educationRecords.get(0).getProfileId())).fetchOne();
//				if(completenessRecord == null) {
//					completenessRecord = new ProfileCompletenessRecord();
//					completenessRecord.setProfileId(educationRecords.get(0).getProfileId());
//				}

            Timestamp now = new Timestamp(System.currentTimeMillis());
            Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
            educationRecords.forEach(educationRecord -> {
                educationRecord.setCreateTime(now);
                if (!StringUtils.isNullOrEmpty(educationRecord.getCollegeName())) {
                    for (DictCollegeRecord collegeRecord : colleges) {
                        if (educationRecord.getCollegeName().equals(collegeRecord.getName())) {
                            educationRecord.setCollegeCode(collegeRecord.getCode().intValue());
                            educationRecord.setCollegeLogo(collegeRecord.getLogo());
                            break;
                        }
                    }
                }
                create.attach(educationRecord);
                educationRecord.insert();
            });
//				int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
//				completenessRecord.setProfileEducation(educationCompleteness);
//				create.attach(completenessRecord);
//				completenessRecord.update();

        }
        return count;
    }

    public List<ProfileEducationRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileEducation.PROFILE_EDUCATION)
                    .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
