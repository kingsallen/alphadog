package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

/**
* @author xxx
* ProfileEducationDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileEducationDao extends JooqCrudImpl<ProfileEducationDO, ProfileEducationRecord> {

    public ProfileEducationDao() {
        super(ProfileEducation.PROFILE_EDUCATION, ProfileEducationDO.class);
    }

    public ProfileEducationDao(TableImpl<ProfileEducationRecord> table, Class<ProfileEducationDO> profileEducationDOClass) {
        super(table, profileEducationDOClass);
    }

    public int updateProfileUpdateTime(HashSet<Integer> educationIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(create.select(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID)
                                    .from(ProfileEducation.PROFILE_EDUCATION)
                                    .where(ProfileEducation.PROFILE_EDUCATION.ID.in(educationIds))))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }

    public int delEducationsByProfileId(int profileId) {
        int count = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            count = create.delete(ProfileEducation.PROFILE_EDUCATION)
                    .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.equal((int)(profileId)))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return count;
    }

    public int saveEducations(List<ProfileEducationRecord> educationRecords) {
        int count = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return count;
    }
}
