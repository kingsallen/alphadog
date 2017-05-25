package com.moseeker.profile.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.*;
import com.moseeker.db.dictdb.tables.records.*;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.JobPositionExt;
import com.moseeker.db.jobdb.tables.JobResumeOther;
import com.moseeker.db.profiledb.tables.*;
import com.moseeker.db.profiledb.tables.ProfileImport;
import com.moseeker.db.profiledb.tables.ProfileOther;
import com.moseeker.db.profiledb.tables.records.*;
import com.moseeker.db.userdb.tables.*;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.entity.AtsProfileBasic;
import com.moseeker.profile.dao.entity.AtsProfileWorkexp;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.service.impl.serviceutils.CompletenessCalculator;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.profile.struct.*;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.joda.time.DateTime;
import org.jooq.*;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.net.ConnectException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static com.moseeker.common.util.BeanUtils.jooqMapfilter;
import static com.moseeker.common.util.BeanUtils.profilter;

@Repository
public class ProfileDaoImpl extends BaseDaoImpl<ProfileProfileRecord, ProfileProfile> implements ProfileDao {

    private CompletenessCalculator completenessCalculator = new CompletenessCalculator();

    @Override
    protected void initJOOQEntity() {
        this.tableLike = ProfileProfile.PROFILE_PROFILE;
    }

    @Override
    public ProfileProfileRecord getProfileByIdOrUserIdOrUUID(int userId, int profileId, String uuid) {
        ProfileProfileRecord record = null;
        Connection conn = null;
        try {
            if (userId > 0 || profileId > 0 || !StringUtils.isNullOrEmpty(uuid)) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Condition condition = null;

                create.select().from(ProfileProfile.PROFILE_PROFILE).groupBy(ProfileProfile.PROFILE_PROFILE.USER_ID);

                if (userId > 0) {
                    if (condition == null) {
                        condition = ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId));
                    }
                }
                if (profileId > 0) {
                    if (condition == null) {
                        condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
                    } else {
                        condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)));
                    }
                }
                if (!StringUtils.isNullOrEmpty(uuid)) {
                    if (condition == null) {
                        condition = ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid);
                    } else {
                        condition = condition.or(ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid));
                    }
                }

                if (condition != null) {
                    Result<ProfileProfileRecord> result = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                            .where(condition).and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(UByte.valueOf(1)))
                            .limit(1).fetch();
                    if (result != null && result.size() > 0) {
                        record = result.get(0);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }
        return record;
    }

    @Override
    public List<ProfileProfileRecord> getProfilesByIdOrUserIdOrUUID(int userId, int profileId, String uuid) {
        List<ProfileProfileRecord> records = null;
        Connection conn = null;
        try {
            if (userId > 0 || profileId > 0 || !StringUtils.isNullOrEmpty(uuid)) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Condition condition = null;
                if (userId > 0) {
                    if (condition == null) {
                        condition = ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId));
                    }
                }
                if (profileId > 0) {
                    if (condition == null) {
                        condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
                    } else {
                        condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)));
                    }
                }
                if (!StringUtils.isNullOrEmpty(uuid)) {
                    if (condition == null) {
                        condition = ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid);
                    } else {
                        condition = condition.or(ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid));
                    }
                }

                if (condition != null) {
                    Result<ProfileProfileRecord> result = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                            .where(condition).and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(UByte.valueOf(1)))
                            .fetch();
                    if (result != null && result.size() > 0) {
                        records = result;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }
        return records;
    }

    @Override
    public int deleteProfile(int profileId) {
        int result = 0;
        if (profileId > 0) {
            Connection conn = null;
            try {
                conn = DBConnHelper.DBConn.getConn();
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                StringBuffer sb = new StringBuffer("(");
                ResultSet resultSet = stmt
                        .executeQuery("select id from profiledb.profile_intention where profile_id = " + profileId);
                while (resultSet.next()) {
                    sb.append(resultSet.getLong("id") + ",");
                }
                if (sb.length() > 1) {
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");
                    stmt.executeUpdate("delete from profiledb.profile_intention_city where profile_intention_id in "
                            + sb.toString());
                    stmt.executeUpdate("delete from profiledb.profile_intention_position where profile_intention_id in "
                            + sb.toString());
                    stmt.executeUpdate("delete from profiledb.profile_intention_industry where profile_intention_id in "
                            + sb.toString());
                }
                stmt.executeUpdate("delete from profiledb.profile_attachment where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_awards where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_basic where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_credentials where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_education where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);

                stmt.executeUpdate("delete from profiledb.profile_intention where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_language where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_other where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_projectexp where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_skill where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_workexp where profile_id = " + profileId);
                stmt.executeUpdate("delete from profiledb.profile_works where profile_id = " + profileId);
                result = stmt.executeUpdate("delete from profiledb.profile_profile where id = " + profileId);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.rollback();
                    }
                } catch (SQLException e1) {
                    logger.error(e.getMessage(), e);
                }
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    // do nothing
                }
            }
        }
        return result;
    }

    @Override
    public int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
                           List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
                           List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
                           ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
                           List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
                           List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
                           List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords,
                           UserUserRecord userRecord) {
        int profileId = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            conn.setAutoCommit(false);
            Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
            Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
            Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();
            Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();
            if (profileRecord != null) {
                Timestamp now = new Timestamp(System.currentTimeMillis());
                profileRecord.setCreateTime(now);
                create.attach(profileRecord);
                profileRecord.insert();

				/* 计算profile完整度 */
                ProfileCompletenessRecord completenessRecord = new ProfileCompletenessRecord();
                Date birthDay = null;
                if (basicRecord != null) {
                    basicRecord.setProfileId(profileRecord.getId());
                    basicRecord.setCreateTime(now);
                    create.attach(basicRecord);
                    if (!StringUtils.isNullOrEmpty(basicRecord.getNationalityName())) {
                        DictCountryRecord countryRecord = create.selectFrom(DictCountry.DICT_COUNTRY)
                                .where(DictCountry.DICT_COUNTRY.NAME.equal(basicRecord.getNationalityName())).limit(1)
                                .fetchOne();
                        if (countryRecord != null) {
                            basicRecord.setNationalityCode(countryRecord.getId().intValue());
                        }
                    }
                    if (!StringUtils.isNullOrEmpty(basicRecord.getCityName())) {
                        DictCityRecord cityRecord = create.selectFrom(DictCity.DICT_CITY)
                                .where(DictCity.DICT_CITY.NAME.equal(basicRecord.getCityName())).limit(1).fetchOne();
                        if (cityRecord != null) {
                            basicRecord.setCityCode(cityRecord.getCode().intValue());
                        }
                    }
                    basicRecord.insert();
                    birthDay = basicRecord.getBirth();
                    int basicCompleteness = completenessCalculator.calculateProfileBasic(basicRecord, userRecord.getMobile());
                    completenessRecord.setProfileBasic(basicCompleteness);
                }
                if (attachmentRecords != null && attachmentRecords.size() > 0) {
                    attachmentRecords.forEach(attachmentRecord -> {
                        attachmentRecord.setProfileId(profileRecord.getId());
                        attachmentRecord.setCreateTime(now);
                        create.attach(attachmentRecord);
                        attachmentRecord.insert();
                    });
                }
                if (awardsRecords != null && awardsRecords.size() > 0) {
                    awardsRecords.forEach(awardsRecord -> {
                        awardsRecord.setProfileId(profileRecord.getId());
                        awardsRecord.setCreateTime(now);
                        create.attach(awardsRecord);
                        awardsRecord.insert();
                    });
                    int awardCompleteness = completenessCalculator.calculateAwards(awardsRecords);
                    completenessRecord.setProfileAwards(awardCompleteness);
                }
                if (credentialsRecords != null && credentialsRecords.size() > 0) {
                    credentialsRecords.forEach(credentialsRecord -> {
                        credentialsRecord.setProfileId(profileRecord.getId());
                        credentialsRecord.setCreateTime(now);
                        create.attach(credentialsRecord);
                        credentialsRecord.insert();
                    });
                    int credentialsCompleteness = completenessCalculator.calculateCredentials(credentialsRecords);
                    completenessRecord.setProfileCredentials(credentialsCompleteness);
                }
                if (educationRecords != null && educationRecords.size() > 0) {
                    educationRecords.forEach(educationRecord -> {
                        educationRecord.setProfileId(profileRecord.getId());
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
                    int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
                    completenessRecord.setProfileEducation(educationCompleteness);
                }
                if (importRecord != null && importRecord.size() > 0) {
                    create.attach(importRecord);
                    importRecord.setCreateTime(now);
                    importRecord.setProfileId(profileRecord.getId());
                    importRecord.insert();
                }
                if (intentionRecords != null && intentionRecords.size() > 0) {
                    List<ProfileIntentionCityRecord> intentionCityRecords = new ArrayList<>();
                    List<ProfileIntentionPositionRecord> intentionPositionRecords = new ArrayList<>();
                    intentionRecords.forEach(intentionRecord -> {
                        intentionRecord.setProfileId(profileRecord.getId());
                        intentionRecord.setCreateTime(now);
                        create.attach(intentionRecord);
                        intentionRecord.insert();
                        if (intentionRecord.getCities().size() > 0) {
                            intentionRecord.getCities().forEach(city -> {
                                city.setProfileIntentionId(intentionRecord.getId());
                                if (!StringUtils.isNullOrEmpty(city.getCityName())) {
                                    for (DictCityRecord cityRecord : cities) {
                                        if (city.getCityName().equals(cityRecord.getName())) {
                                            city.setCityCode(cityRecord.getCode());
                                            intentionCityRecords.add(city);
                                            break;
                                        }
                                    }
                                }
                                create.attach(city);
                                city.insert();
                            });
                        }
                        if (intentionRecord.getPositions().size() > 0) {
                            intentionRecord.getPositions().forEach(position -> {
                                position.setProfileIntentionId(intentionRecord.getId());
                                if (!StringUtils.isNullOrEmpty(position.getPositionName())) {
                                    for (DictPositionRecord positionRecord : positions) {
                                        if (positionRecord.getName().equals(position.getPositionName())) {
                                            position.setPositionCode(positionRecord.getCode());
                                            intentionPositionRecords.add(position);
                                            break;
                                        }
                                    }
                                }
                                create.attach(position);
                                position.insert();
                            });
                        }
                        if (intentionRecord.getIndustries().size() > 0) {
                            intentionRecord.getIndustries().forEach(industry -> {
                                industry.setProfileIntentionId(intentionRecord.getId());
                                if (!StringUtils.isNullOrEmpty(industry.getIndustryName())) {
                                    for (DictIndustryRecord industryRecord : industries) {
                                        if (industry.getIndustryName().equals(industryRecord.getName())) {
                                            industry.setIndustryCode(industryRecord.getCode());
                                            break;
                                        }
                                    }
                                }
                                create.attach(industry);
                                industry.insert();
                            });
                        }
                    });
                    int intentionCompleteness = completenessCalculator.calculateIntentions(intentionRecords,
                            intentionCityRecords, intentionPositionRecords);
                    completenessRecord.setProfileIntention(intentionCompleteness);
                }
                if (languages != null && languages.size() > 0) {
                    languages.forEach(language -> {
                        language.setProfileId(profileRecord.getId());
                        language.setCreateTime(now);
                        create.attach(language);
                        language.insert();
                    });
                    int languageCompleteness = completenessCalculator.calculateLanguages(languages);
                    completenessRecord.setProfileLanguage(languageCompleteness);
                }
                if (otherRecord != null) {
                    create.attach(otherRecord);
                    otherRecord.setCreateTime(now);
                    otherRecord.insert();
                }

                if (skillRecords != null && skillRecords.size() > 0) {
                    skillRecords.forEach(skill -> {
                        skill.setProfileId(profileRecord.getId());
                        skill.setCreateTime(now);
                        create.attach(skill);
                        skill.insert();
                    });
                    int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
                    completenessRecord.setProfileSkill(skillCompleteness);
                }
                if (workexpRecords != null && workexpRecords.size() > 0) {
                    List<HrCompanyRecord> companies = new ArrayList<>();
                    workexpRecords.forEach(workexp -> {
                        workexp.setProfileId(profileRecord.getId());
                        workexp.setCreateTime(now);
                        if (workexp.getCompany() != null
                                && !StringUtils.isNullOrEmpty(workexp.getCompany().getName())) {
                            HrCompanyRecord hc = create.selectFrom(HrCompany.HR_COMPANY)
                                    .where(HrCompany.HR_COMPANY.NAME.equal(workexp.getCompany().getName())).limit(1)
                                    .fetchOne();
                            if (hc != null) {
                                companies.add(hc);
                                workexp.setCompanyId(hc.getId());
                            } else {
                                HrCompanyRecord newCompany = workexp.getCompany();
                                create.attach(newCompany);
                                newCompany.insert();
                                workexp.setCompanyId(newCompany.getId());
                                companies.add(newCompany);
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(workexp.getIndustryName())) {
                            for (DictIndustryRecord industryRecord : industries) {
                                if (workexp.getIndustryName().equals(industryRecord.getName())) {
                                    workexp.setIndustryCode(industryRecord.getCode());
                                    break;
                                }
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(workexp.getCityName())) {
                            for (DictCityRecord cityRecord : cities) {
                                if (workexp.getCityName().equals(cityRecord.getName())) {
                                    workexp.setCityCode(cityRecord.getCode());
                                    break;
                                }
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(workexp.getPositionName())) {
                            for (DictPositionRecord positionRecord : positions) {
                                if (positionRecord.getName().equals(workexp.getPositionName())) {
                                    workexp.setPositionCode(positionRecord.getCode());
                                    break;
                                }
                            }
                        }

                        create.attach(workexp);
                        workexp.insert();
                    });
                    int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workexpRecords,
                            educationRecords, birthDay);
                    completenessRecord.setProfileWorkexp(workExpCompleteness);
                }
                if (projectExps != null && projectExps.size() > 0) {
                    projectExps.forEach(projectExp -> {
                        projectExp.setProfileId(profileRecord.getId());
                        projectExp.setCreateTime(now);
                        create.attach(projectExp);
                        projectExp.insert();
                    });
                    int projectExpCompleteness = completenessCalculator.calculateProjectexps(projectExps, workexpRecords);
                    completenessRecord.setProfileProjectexp(projectExpCompleteness);
                }

                if (worksRecords != null && worksRecords.size() > 0) {
                    worksRecords.forEach(worksRecord -> {
                        worksRecord.setProfileId(profileRecord.getId());
                        worksRecord.setCreateTime(now);
                        create.attach(worksRecord);
                        worksRecord.insert();
                    });
                    int worksCompleteness = completenessCalculator.calculateWorks(worksRecords);
                    completenessRecord.setProfileWorks(worksCompleteness);
                }
                if (userRecord != null) {
                    create.attach(userRecord);
                    userRecord.update();

					/* 计算简历完整度 */
                    completenessRecord.setProfileId(profileRecord.getId());
                    UserWxUserRecord wxuserRecord = create.selectFrom(UserWxUser.USER_WX_USER)
                            .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userRecord.getId().intValue())).limit(1)
                            .fetchOne();
                    UserSettingsRecord settingRecord = create.selectFrom(UserSettings.USER_SETTINGS)
                            .where(UserSettings.USER_SETTINGS.USER_ID.equal(userRecord.getId())).limit(1).fetchOne();
                    int userCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord,
                            wxuserRecord);
                    completenessRecord.setUserUser(userCompleteness);
                }

                int totalComplementness = (completenessRecord.getUserUser() == null ? 0
                        : completenessRecord.getUserUser())
                        + (completenessRecord.getProfileBasic() == null ? 0 : completenessRecord.getProfileBasic())
                        + (completenessRecord.getProfileWorkexp() == null ? 0 : completenessRecord.getProfileWorkexp())
                        + (completenessRecord.getProfileEducation() == null ? 0
                        : completenessRecord.getProfileEducation())
                        + (completenessRecord.getProfileProjectexp() == null ? 0
                        : completenessRecord.getProfileProjectexp())
                        + (completenessRecord.getProfileLanguage() == null ? 0
                        : completenessRecord.getProfileLanguage())
                        + (completenessRecord.getProfileSkill() == null ? 0 : completenessRecord.getProfileSkill())
                        + (completenessRecord.getProfileCredentials() == null ? 0
                        : completenessRecord.getProfileCredentials())
                        + (completenessRecord.getProfileAwards() == null ? 0 : completenessRecord.getProfileAwards())
                        + (completenessRecord.getProfileWorks() == null ? 0 : completenessRecord.getProfileWorks())
                        + (completenessRecord.getProfileIntention() == null ? 0
                        : completenessRecord.getProfileIntention());

                create.attach(completenessRecord);
                completenessRecord.insert();
                profileRecord.setCompleteness(UByte.valueOf(totalComplementness));
                profileRecord.update();
                profileId = profileRecord.getId().intValue();
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }
        return profileId;
    }

    @Override
    public int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
                           List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
                           List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
                           ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
                           List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
                           List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
                           List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords, UserUserRecord userRecord,
                           List<ProfileProfileRecord> oldProfile) {
        int profileId = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            conn.setAutoCommit(false);

            if (oldProfile != null && oldProfile.size() > 0) {
                for (ProfileProfileRecord record : oldProfile) {
                    clearProfile(record.getId().intValue(), conn);
                }
            }

            Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
            Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
            Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();
            Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();
            if (profileRecord != null) {
                Timestamp now = new Timestamp(System.currentTimeMillis());
                profileRecord.setCreateTime(now);
                create.attach(profileRecord);
                profileRecord.insert();

				/* 计算profile完整度 */
                ProfileCompletenessRecord completenessRecord = new ProfileCompletenessRecord();
                completenessRecord.setProfileId(profileRecord.getId());
                Date birthDay = null;
                if (basicRecord != null) {
                    basicRecord.setProfileId(profileRecord.getId());
                    basicRecord.setCreateTime(now);
                    create.attach(basicRecord);
                    if (!StringUtils.isNullOrEmpty(basicRecord.getNationalityName())) {
                        DictCountryRecord countryRecord = create.selectFrom(DictCountry.DICT_COUNTRY)
                                .where(DictCountry.DICT_COUNTRY.NAME.equal(basicRecord.getNationalityName())).limit(1)
                                .fetchOne();
                        if (countryRecord != null) {
                            basicRecord.setNationalityCode(countryRecord.getId().intValue());
                        }
                    }
                    if (!StringUtils.isNullOrEmpty(basicRecord.getCityName())) {
                        DictCityRecord cityRecord = create.selectFrom(DictCity.DICT_CITY)
                                .where(DictCity.DICT_CITY.NAME.equal(basicRecord.getCityName())).limit(1).fetchOne();
                        if (cityRecord != null) {
                            basicRecord.setCityCode(cityRecord.getCode().intValue());
                        }
                    }
                    basicRecord.insert();
                    birthDay = basicRecord.getBirth();
                    //计算basic完整度，由于修改规则，mobile或者微信号有一个即计入，为了不改变数据库表结构所以将mobile传入basic的完整度计算程序当中
                    int basicCompleteness = completenessCalculator.calculateProfileBasic(basicRecord, userRecord.getMobile());
                    completenessRecord.setProfileBasic(basicCompleteness);
                }
                if (attachmentRecords != null && attachmentRecords.size() > 0) {
                    attachmentRecords.forEach(attachmentRecord -> {
                        attachmentRecord.setProfileId(profileRecord.getId());
                        attachmentRecord.setCreateTime(now);
                        create.attach(attachmentRecord);
                        attachmentRecord.insert();
                    });
                }
                if (awardsRecords != null && awardsRecords.size() > 0) {
                    awardsRecords.forEach(awardsRecord -> {
                        awardsRecord.setProfileId(profileRecord.getId());
                        awardsRecord.setCreateTime(now);
                        create.attach(awardsRecord);
                        awardsRecord.insert();
                    });
                    // 计算奖项完整度
                    int awardCompleteness = completenessCalculator.calculateAwards(awardsRecords);
                    completenessRecord.setProfileAwards(awardCompleteness);
                }
                if (credentialsRecords != null && credentialsRecords.size() > 0) {
                    credentialsRecords.forEach(credentialsRecord -> {
                        credentialsRecord.setProfileId(profileRecord.getId());
                        credentialsRecord.setCreateTime(now);
                        create.attach(credentialsRecord);
                        credentialsRecord.insert();
                    });
                    //计算证书完整度
                    int credentialsCompleteness = completenessCalculator.calculateCredentials(credentialsRecords);
                    completenessRecord.setProfileCredentials(credentialsCompleteness);
                }
                if (educationRecords != null && educationRecords.size() > 0) {
                    educationRecords.forEach(educationRecord -> {
                        educationRecord.setProfileId(profileRecord.getId());
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
                    //计算教育经历完整度
                    int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
                    completenessRecord.setProfileEducation(educationCompleteness);
                }
                if (importRecord != null) {
                    create.attach(importRecord);
                    importRecord.setCreateTime(now);
                    importRecord.setProfileId(profileRecord.getId());
                    importRecord.insert();
                }
                if (intentionRecords != null && intentionRecords.size() > 0) {
                    List<ProfileIntentionCityRecord> intentionCityRecords = new ArrayList<>();
                    List<ProfileIntentionPositionRecord> intentionPositionRecords = new ArrayList<>();
                    intentionRecords.forEach(intentionRecord -> {
                        intentionRecord.setProfileId(profileRecord.getId());
                        intentionRecord.setCreateTime(now);
                        create.attach(intentionRecord);
                        intentionRecord.insert();
                        if (intentionRecord.getCities().size() > 0) {
                            intentionRecord.getCities().forEach(city -> {
                                city.setProfileIntentionId(intentionRecord.getId());
                                if (!StringUtils.isNullOrEmpty(city.getCityName())) {
                                    for (DictCityRecord cityRecord : cities) {
                                        if (city.getCityName().equals(cityRecord.getName())) {
                                            city.setCityCode(cityRecord.getCode());
                                            intentionCityRecords.add(city);
                                            break;
                                        }
                                    }
                                }
                                create.attach(city);
                                city.insert();
                            });
                        }
                        if (intentionRecord.getPositions().size() > 0) {
                            intentionRecord.getPositions().forEach(position -> {
                                position.setProfileIntentionId(intentionRecord.getId());
                                if (!StringUtils.isNullOrEmpty(position.getPositionName())) {
                                    for (DictPositionRecord positionRecord : positions) {
                                        if (positionRecord.getName().equals(position.getPositionName())) {
                                            position.setPositionCode(positionRecord.getCode());
                                            intentionPositionRecords.add(position);
                                            break;
                                        }
                                    }
                                }
                                create.attach(position);
                                position.insert();
                            });
                        }
                        if (intentionRecord.getIndustries().size() > 0) {
                            intentionRecord.getIndustries().forEach(industry -> {
                                industry.setProfileIntentionId(intentionRecord.getId());
                                if (!StringUtils.isNullOrEmpty(industry.getIndustryName())) {
                                    for (DictIndustryRecord industryRecord : industries) {
                                        if (industry.getIndustryName().equals(industryRecord.getName())) {
                                            industry.setIndustryCode(industryRecord.getCode());
                                            break;
                                        }
                                    }
                                }
                                create.attach(industry);
                                industry.insert();
                            });
                        }
                    });
                    //计算求职意向完整度
                    int intentionCompleteness = completenessCalculator.calculateIntentions(intentionRecords,
                            intentionCityRecords, intentionPositionRecords);
                    completenessRecord.setProfileIntention(intentionCompleteness);
                }
                if (languages != null && languages.size() > 0) {
                    languages.forEach(language -> {
                        language.setProfileId(profileRecord.getId());
                        language.setCreateTime(now);
                        create.attach(language);
                        language.insert();
                    });
                    //计算语言完整度
                    int languageCompleteness = completenessCalculator.calculateLanguages(languages);
                    completenessRecord.setProfileLanguage(languageCompleteness);
                }
                if (otherRecord != null) {
                    create.attach(otherRecord);
                    otherRecord.setCreateTime(now);
                    otherRecord.setProfileId(profileRecord.getId());
                    otherRecord.insert();
                }

                if (skillRecords != null && skillRecords.size() > 0) {
                    skillRecords.forEach(skill -> {
                        skill.setProfileId(profileRecord.getId());
                        skill.setCreateTime(now);
                        create.attach(skill);
                        skill.insert();
                    });
                    //计算技能完整度
                    int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
                    completenessRecord.setProfileSkill(skillCompleteness);
                }
                if (workexpRecords != null && workexpRecords.size() > 0) {
                    List<HrCompanyRecord> companies = new ArrayList<>();
                    workexpRecords.forEach(workexp -> {
                        workexp.setProfileId(profileRecord.getId());
                        workexp.setCreateTime(now);
                        if (workexp.getCompany() != null
                                && !StringUtils.isNullOrEmpty(workexp.getCompany().getName())) {
                            HrCompanyRecord hc = create.selectFrom(HrCompany.HR_COMPANY)
                                    .where(HrCompany.HR_COMPANY.NAME.equal(workexp.getCompany().getName())).limit(1)
                                    .fetchOne();
                            if (hc != null) {
                                workexp.setCompanyId(hc.getId());
                                companies.add(hc);
                            } else {
                                HrCompanyRecord newCompany = workexp.getCompany();
                                create.attach(newCompany);
                                newCompany.insert();
                                workexp.setCompanyId(newCompany.getId());
                                companies.add(newCompany);
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(workexp.getIndustryName())) {
                            for (DictIndustryRecord industryRecord : industries) {
                                if (workexp.getIndustryName().equals(industryRecord.getName())) {
                                    workexp.setIndustryCode(industryRecord.getCode());
                                    break;
                                }
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(workexp.getCityName())) {
                            for (DictCityRecord cityRecord : cities) {
                                if (workexp.getCityName().equals(cityRecord.getName())) {
                                    workexp.setCityCode(cityRecord.getCode());
                                    break;
                                }
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(workexp.getPositionName())) {
                            for (DictPositionRecord positionRecord : positions) {
                                if (positionRecord.getName().equals(workexp.getPositionName())) {
                                    workexp.setPositionCode(positionRecord.getCode());
                                    break;
                                }
                            }
                        }

                        create.attach(workexp);
                        workexp.insert();
                    });
                    //计算工作经历完整度
                    int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workexpRecords, educationRecords, birthDay);
                    completenessRecord.setProfileWorkexp(workExpCompleteness);
                }
                if (projectExps != null && projectExps.size() > 0) {
                    projectExps.forEach(projectExp -> {
                        projectExp.setProfileId(profileRecord.getId());
                        projectExp.setCreateTime(now);
                        create.attach(projectExp);
                        projectExp.insert();
                    });
                    //计算项目经历完整度
                    int projectExpCompleteness = completenessCalculator.calculateProjectexps(projectExps, workexpRecords);
                    completenessRecord.setProfileProjectexp(projectExpCompleteness);
                }
                if (worksRecords != null && worksRecords.size() > 0) {
                    worksRecords.forEach(worksRecord -> {
                        worksRecord.setProfileId(profileRecord.getId());
                        worksRecord.setCreateTime(now);
                        create.attach(worksRecord);
                        worksRecord.insert();
                    });
                    int worksCompleteness = completenessCalculator.calculateWorks(worksRecords);
                    completenessRecord.setProfileWorks(worksCompleteness);
                }
                //========================发现原来没有，现在添加上＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
                if (userRecord != null) {
                    create.attach(userRecord);
                    userRecord.update();

					/* 计算简历完整度 */
                    completenessRecord.setProfileId(profileRecord.getId());
                    UserWxUserRecord wxuserRecord = create.selectFrom(UserWxUser.USER_WX_USER)
                            .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userRecord.getId().intValue())).limit(1)
                            .fetchOne();
                    UserSettingsRecord settingRecord = create.selectFrom(UserSettings.USER_SETTINGS)
                            .where(UserSettings.USER_SETTINGS.USER_ID.equal(userRecord.getId())).limit(1).fetchOne();
                    int userCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord,
                            wxuserRecord);
                    completenessRecord.setUserUser(userCompleteness);
                }

                int totalComplementness = (completenessRecord.getUserUser() == null ? 0
                        : completenessRecord.getUserUser())
                        + (completenessRecord.getProfileBasic() == null ? 0 : completenessRecord.getProfileBasic())
                        + (completenessRecord.getProfileWorkexp() == null ? 0 : completenessRecord.getProfileWorkexp())
                        + (completenessRecord.getProfileEducation() == null ? 0
                        : completenessRecord.getProfileEducation())
                        + (completenessRecord.getProfileProjectexp() == null ? 0
                        : completenessRecord.getProfileProjectexp())
                        + (completenessRecord.getProfileLanguage() == null ? 0
                        : completenessRecord.getProfileLanguage())
                        + (completenessRecord.getProfileSkill() == null ? 0 : completenessRecord.getProfileSkill())
                        + (completenessRecord.getProfileCredentials() == null ? 0
                        : completenessRecord.getProfileCredentials())
                        + (completenessRecord.getProfileAwards() == null ? 0 : completenessRecord.getProfileAwards())
                        + (completenessRecord.getProfileWorks() == null ? 0 : completenessRecord.getProfileWorks())
                        + (completenessRecord.getProfileIntention() == null ? 0
                        : completenessRecord.getProfileIntention());

                profileRecord.setCompleteness(UByte.valueOf(totalComplementness));
                profileRecord.update();
                create.attach(completenessRecord);
                completenessRecord.insert();
                if (userRecord != null) {
                    create.attach(userRecord);
                    userRecord.update();
                }
                profileId = profileRecord.getId().intValue();
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }
        return profileId;
    }

    private int clearProfile(int profileId, Connection conn) throws Exception {
        int result = 0;
        Statement stmt = conn.createStatement();
        StringBuffer sb = new StringBuffer("(");
        ResultSet resultSet = stmt
                .executeQuery("select id from profiledb.profile_intention where profile_id = " + profileId);
        while (resultSet.next()) {
            sb.append(resultSet.getLong("id") + ",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            stmt.executeUpdate(
                    "delete from profiledb.profile_intention_city where profile_intention_id in " + sb.toString());
            stmt.executeUpdate(
                    "delete from profiledb.profile_intention_position where profile_intention_id in " + sb.toString());
            stmt.executeUpdate(
                    "delete from profiledb.profile_intention_industry where profile_intention_id in " + sb.toString());
        }
        stmt.executeUpdate("delete from profiledb.profile_attachment where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_awards where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_basic where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_credentials where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_education where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);

        stmt.executeUpdate("delete from profiledb.profile_intention where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_language where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_other where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_projectexp where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_skill where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_workexp where profile_id = " + profileId);
        stmt.executeUpdate("delete from profiledb.profile_works where profile_id = " + profileId);
        result = stmt.executeUpdate("delete from profiledb.profile_profile where id = " + profileId);
        return result;
    }

    @Override
    public Result<Record2<UInteger, String>> findRealName(List<Integer> profileIds) {
        Result<Record2<UInteger, String>> result = null;
        if (profileIds != null && profileIds.size() > 0) {
            try (Connection conn = DBConnHelper.DBConn.getConn();
                 DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
                conn.setAutoCommit(false);
                Condition condition = null;
                for (Integer profileId : profileIds) {
                    if (condition != null) {
                        condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)));
                    } else {
                        condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
                    }
                }
                result = create.select(ProfileProfile.PROFILE_PROFILE.ID, UserUser.USER_USER.NAME)
                        .from(ProfileProfile.PROFILE_PROFILE).join(UserUser.USER_USER)
                        .on(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UserUser.USER_USER.ID)).where(condition)
                        .fetch();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public String findRealName(int profileId) {
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            Condition condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
            Record1<String> username = create.select(UserUser.USER_USER.NAME).from(ProfileProfile.PROFILE_PROFILE)
                    .join(UserUser.USER_USER).on(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UserUser.USER_USER.ID))
                    .where(condition).limit(1).fetchOne();
            if (username != null) {
                return (String) username.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void updateRealName(int profileId, String name) {
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            ProfileProfileRecord record = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                    .where(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId))).fetchOne();
            if (record != null) {
                UserUserRecord userRecord = new UserUserRecord();
                userRecord.setId(record.getUserId());
                create.attach(userRecord);
                userRecord.setName(name);
                userRecord.update();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public int updateUpdateTime(Set<Integer> profileIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE).set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID.in(profileIds)).execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }


    private String getDownloadUrlByUserId(String downloadApi, String password, int userid) {
        String url = null;
        if (StringUtils.isNotNullOrEmpty(downloadApi)) {
            Map<String, Object> params = new HashMap<String, Object>() {{
                put("user_id", userid);
                put("password", password);
            }};
            try {
                logger.info("getDownloadUrlByUserId:{}:{}:{}", downloadApi, password, userid);
                String content = HttpClient.sendPost(downloadApi, JSON.toJSONString(params));
                logger.info("getDownloadUrlByUserId:{}:{}", userid, content);
                Map<String, Object> mp = JsonToMap.parseJSON2Map(content);
                Object link = mp.get("downloadlink");
                if (link != null) {
                    url = link.toString();
                }
            } catch (ConnectException e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return url;
    }


    private boolean showEmptyKey = true;

    private void buildMap(Map<String, List<String>> filter, Map map, String key, Map<String, Object> object) {
        //过滤指定字段
        if (object != null && filter != null && filter.containsKey(key)) {
            if (filter.get(key).contains("*")) {
                return;
            }
            filter.get(key).forEach(subKey -> object.remove(subKey));
        }
        if (showEmptyKey) {
            if (object == null) {
                map.put(key, new HashMap<>());
            } else {
                map.put(key, object);
            }
        } else if (object != null) {
            if ((object).size() > 0) {
                map.put(key, object);
            }
        }
    }

    private void buildMap(Map<String, List<String>> filter, Map map, String key, List<Map<String, Object>> object) {
        //过滤指定字段
        if (object != null && filter != null && filter.containsKey(key)) {
            if (filter.get(key).contains("*")) {
                return;
            }
            filter.get(key).forEach(subKey -> {
                object.forEach(item -> {
                    item.remove(subKey);
                });
            });
        }
        if (showEmptyKey) {
            if (object == null) {
                map.put(key, new ArrayList<>());
            } else {
                map.put(key, object);
            }
        } else if (object != null) {
            if (object.size() > 0) {
                map.put(key, object);
            }
        }
    }

    public boolean filterTable(Map<String, List<String>> filter, String key) {
        if (filter != null && filter.containsKey(key)) {
            if (filter.get(key).contains("*")) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Object> getRelatedDataByJobApplication(DSLContext create,
                                                              Map<String, Object> position,
                                                              Map<String, Object> application,
                                                              String downloadApi, String password,
                                                              boolean recommender,
                                                              boolean dl_url_required,
                                                              Map<String, List<String>> filter) {

        logger.info("profilesByApplication:application:{}", JSON.toJSONString(position));

        Map<String, Object> map = new HashMap<>();
        //all from jobdb.job_application
        if (!filterTable(filter, "job_application")) {
            buildMap(filter, map, "job_application", application);
        }

        //job_number and title from jobdb.job_position
        if (!filterTable(filter, "job_position")) {
            buildMap(filter, map, "job_position", position);
        }

        //extra from jobdb.job_position_ext # custom job fields in JSON format
        if (!filterTable(filter, "job_position_ext")) {
            Map<String, Object> positionExt = create
                    .select()
                    .from(JobPositionExt.JOB_POSITION_EXT)
                    .where(JobPositionExt.JOB_POSITION_EXT.PID.eq((Integer) position.get("id")))
                    .fetchAnyMap();
            if (positionExt != null && !StringUtils.isNullOrEmpty(positionExt.get("extra") + "")) {
                buildMap(filter, map, "job_position_ext", positionExt);
            }
        }

        //other from jobdb.job_resume_other # custom résumé fields in JSON format
        if (!filterTable(filter, "job_resume_other")) {
            Map<String, Object> resumeOther = create
                    .select()
                    .from(JobResumeOther.JOB_RESUME_OTHER)
                    .where(JobResumeOther.JOB_RESUME_OTHER.APP_ID.eq((UInteger) application.get("id")))
                    .fetchAnyMap();
            buildMap(filter, map, "job_resume_other", resumeOther);
        }

        if (((UInteger) application.get("applier_id")).intValue() != 0) {
            if (dl_url_required) {
                String url = getDownloadUrlByUserId(downloadApi, password, ((UInteger) application.get("applier_id")).intValue());
                map.put("download_url", url == null ? "" : url);
            }
            //all from userdb.user_user
            if (!filterTable(filter, "user_user")) {
                Map<String, Object> user = create
                        .select()
                        .from(UserUser.USER_USER)
                        .where(UserUser.USER_USER.ID.eq((UInteger) application.get("applier_id")))
                        .fetchAnyMap();
                buildMap(filter, map, "user_user", user);
            }

            //all from profiledb.user_thirdparty_user # ATS login
            if (!filterTable(filter, "user_thirdparty_user")) {
                Map<String, Object> thirdPartyUser = create
                        .select()
                        .from(UserThirdpartyUser.USER_THIRDPARTY_USER)
                        .where(UserThirdpartyUser.USER_THIRDPARTY_USER.USER_ID.eq(((UInteger) application.get("applier_id")).intValue()))
                        .fetchAnyMap();
                buildMap(filter, map, "user_thirdparty_user", thirdPartyUser);
            }

            //all from profiledb.profile_profile
            Map<String, Object> profile = create
                    .select()
                    .from(ProfileProfile.PROFILE_PROFILE)
                    .where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq((UInteger) application.get("applier_id")))
                    .fetchAnyMap();
            if (!filterTable(filter, "profile_profile")) {
                buildMap(filter, map, "profile_profile", profile);
            }

            if (profile != null) {
                //all from profiledb.profile_attachment
                if (!filterTable(filter, "profile_attachment")) {
                    Map<String, Object> profile_attachment = create
                            .select()
                            .from(ProfileAttachment.PROFILE_ATTACHMENT)
                            .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchAnyMap();
                    buildMap(filter, map, "profile_attachment", profile_attachment);
                }

                //all from profiledb.profile_basic
                if (!filterTable(filter, "profile_basic")) {
                    ProfileBasic basic = ProfileBasic.PROFILE_BASIC.as("pb");
                    DictCountry dictCountry = DictCountry.DICT_COUNTRY.as("dc");
                    Map<String, Object> profile_basic = create
                            .select(basic.PROFILE_ID,
                                    basic.NAME,
                                    basic.GENDER,
                                    basic.NATIONALITY_CODE,
                                    basic.NATIONALITY_NAME,
                                    basic.CITY_NAME,
                                    basic.CITY_CODE,
                                    basic.BIRTH,
                                    basic.WEIXIN,
                                    basic.QQ,
                                    basic.MOTTO,
                                    basic.SELF_INTRODUCTION,
                                    basic.CREATE_TIME,
                                    basic.UPDATE_TIME,
                                    dictCountry.CODE.as("country_code")
                            )
                            .from(basic.leftJoin(dictCountry).on("pb.nationality_code=dc.id"))
                            .where(basic.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchAnyMap();
                    buildMap(filter, map, "profile_basic", profile_basic);
                }

                //all from profiledb.profile_award
                if (!filterTable(filter, "profile_award")) {
                    List<Map<String, Object>> profile_award = create
                            .select()
                            .from(ProfileAwards.PROFILE_AWARDS)
                            .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchMaps();
                    buildMap(filter, map, "profile_award", profile_award);
                }

                //all from profiledb.profile_credentials ORDER most recent first by start date
                if (!filterTable(filter, "profile_credentials")) {
                    List<Map<String, Object>> profile_credentials = create
                            .select()
                            .from(ProfileCredentials.PROFILE_CREDENTIALS)
                            .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .orderBy(ProfileCredentials.PROFILE_CREDENTIALS.GET_DATE.desc())
                            .fetchMaps();
                    buildMap(filter, map, "profile_credentials", profile_credentials);
                }

                //all from profiledb.profile_educations ORDER most recent first by start date
                if (!filterTable(filter, "profile_educations")) {
                    List<Map<String, Object>> profile_educations = create
                            .select()
                            .from(ProfileEducation.PROFILE_EDUCATION)
                            .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .orderBy(ProfileEducation.PROFILE_EDUCATION.START.desc())
                            .fetchMaps();
                    buildMap(filter, map, "profile_educations", profile_educations);
                }

                //all from profiledb.profile_import
                if (!filterTable(filter, "profile_import")) {
                    Map<String, Object> profile_import = create
                            .select()
                            .from(ProfileImport.PROFILE_IMPORT)
                            .where(ProfileImport.PROFILE_IMPORT.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchAnyMap();
                    buildMap(filter, map, "profile_import", profile_import);
                }

                //all from profiledb.profile_intention
                Map<String, Object> profile_intention = create
                        .select()
                        .from(ProfileIntention.PROFILE_INTENTION)
                        .where(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.eq((UInteger) profile.get("id")))
                        .fetchAnyMap();
                if (!filterTable(filter, "profile_intention")) {
                    buildMap(filter, map, "profile_intention", profile_intention);
                }

                if (profile_intention != null) {
                    //all from profiledb.profile_intention_city
                    if (!filterTable(filter, "profile_intention_city")) {
                        List<Map<String, Object>> profile_intention_city = create
                                .select()
                                .from(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                                .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.eq((UInteger) profile_intention.get("id")))
                                .fetchMaps();
                        buildMap(filter, map, "profile_intention_city", profile_intention_city);
                    }

                    //all from profiledb.profile_intention_industry
                    if (!filterTable(filter, "profile_intention_industry")) {
                        List<Map<String, Object>> profile_intention_industry = create
                                .select()
                                .from(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                                .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.eq((UInteger) profile_intention.get("id")))
                                .fetchMaps();
                        buildMap(filter, map, "profile_intention_industry", profile_intention_industry);
                    }

                    //all from profiledb.profile_intention_position
                    if (!filterTable(filter, "profile_intention_position")) {
                        List<Map<String, Object>> profile_intention_position = create
                                .select()
                                .from(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                                .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.eq((UInteger) profile_intention.get("id")))
                                .fetchMaps();
                        buildMap(filter, map, "profile_intention_position", profile_intention_position);
                    }
                }


                //all from profiledb.profile_language
                if (!filterTable(filter, "profile_language")) {
                    List<Map<String, Object>> profile_language = create
                            .select()
                            .from(ProfileLanguage.PROFILE_LANGUAGE)
                            .where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchMaps();
                    buildMap(filter, map, "profile_language", profile_language);
                }

                //all from profiledb.profile_other
                if (!filterTable(filter, "profile_other")) {
                    Map<String, Object> profile_other = create
                            .select()
                            .from(ProfileOther.PROFILE_OTHER)
                            .where(ProfileOther.PROFILE_OTHER.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchAnyMap();
                    buildMap(filter, map, "profile_other", profile_other);
                }

                //all from profiledb.profile_projectexp ORDER most recent first by start date
                if (!filterTable(filter, "profile_projectexp")) {
                    List<Map<String, Object>> profile_projectexp = create
                            .select()
                            .from(ProfileProjectexp.PROFILE_PROJECTEXP)
                            .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchMaps();
                    buildMap(filter, map, "profile_projectexp", profile_projectexp);
                }

                //all from profiledb.profile_skills
                if (!filterTable(filter, "profile_skills")) {
                    List<Map<String, Object>> profile_skills = create
                            .select()
                            .from(ProfileSkill.PROFILE_SKILL)
                            .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchMaps();
                    buildMap(filter, map, "profile_skills", profile_skills);
                }

                //all from profiledb.profile_workexp
                if (!filterTable(filter, "profile_workexp")) {
                    ProfileWorkexp workexp = ProfileWorkexp.PROFILE_WORKEXP;
                    HrCompany company = HrCompany.HR_COMPANY;
                    List<Map<String, Object>> profile_workexp = create
                            .select(workexp.ID,
                                    workexp.PROFILE_ID,
                                    workexp.START,
                                    workexp.END,
                                    workexp.END_UNTIL_NOW,
                                    workexp.SALARY_CODE,
                                    workexp.INDUSTRY_CODE,
                                    workexp.INDUSTRY_NAME,
                                    workexp.COMPANY_ID,
                                    workexp.DEPARTMENT_NAME,
                                    workexp.POSITION_CODE,
                                    workexp.POSITION_NAME,
                                    workexp.DESCRIPTION,
                                    workexp.TYPE,
                                    workexp.CITY_CODE,
                                    workexp.CITY_NAME,
                                    workexp.REPORT_TO,
                                    workexp.UNDERLINGS,
                                    workexp.REFERENCE,
                                    workexp.RESIGN_REASON,
                                    workexp.ACHIEVEMENT,
                                    workexp.CREATE_TIME,
                                    workexp.UPDATE_TIME,
                                    workexp.JOB,
                                    company.NAME.as("company_name")
                            )
                            .from(workexp.leftJoin(company).on(workexp.COMPANY_ID.eq(company.ID)))
                            .where(workexp.PROFILE_ID.eq((UInteger) profile.get("id")))
                            .fetchMaps();
                    buildMap(filter, map, "profile_workexp", profile_workexp);
                }

            }


        }

        if (recommender && (Long) application.get("recommender_user_id") != 0) {
            //user_employee.disable=0, activation=0, status=0
            if (!filterTable(filter, "recommender")) {
                Map<String, Object> employee = create
                        .select()
                        .from(UserEmployee.USER_EMPLOYEE)
                        .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(((Long) application.get("recommender_user_id")).intValue()))
                        .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0))
                        .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                        .and(UserEmployee.USER_EMPLOYEE.STATUS.eq(0))
                        .fetchAnyMap();


                Map<String, Object> recommenderUser = create
                        .select()
                        .from(UserUser.USER_USER)
                        .where(UserUser.USER_USER.ID.eq(UInteger.valueOf((Long) application.get("recommender_user_id"))))
                        .fetchAnyMap();
                if (recommenderUser == null) {
                    recommenderUser = new HashMap<>();
                }
                if (employee != null) {
                    recommenderUser.put("employeeid", employee.get("employeeid"));
                    recommenderUser.put("custom_field", employee.get("custom_field"));
                }
                buildMap(filter, map, "recommender", recommenderUser);
            }
        }

        logger.info("profilesByApplication:application:{},result:{}", application.get("id"), JSON.toJSONString(application, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty));

        return map;

    }


    ValueFilter valueFilter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {
            if (value == null)
                return "";
            return value;
        }
    };

    public Response getResourceByApplication(String downloadApi, String password, int companyId, int sourceId, int atsStatus, boolean recommender, boolean dl_url_required, Map<String, List<String>> filter) {
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            JobPosition jobposition = JobPosition.JOB_POSITION.as("a");
            JobApplication jobApplication = JobApplication.JOB_APPLICATION.as("b");
            jobposition.newRecord();
            List<Map<String, Object>> datas = create
                    .select()
                    .from(jobposition.join(jobApplication).on("a.id=b.position_id"))
                    .where("b.email_status=0 and a.company_id=" + companyId + " and a.source_id=" + sourceId + " and b.ats_status=" + atsStatus)
                    .fetch()
                    .stream()
                    .map(record -> getRelatedDataByJobApplication(create, record.into(jobposition).intoMap(), record.into(jobApplication).intoMap(), downloadApi, password, recommender, dl_url_required, filter))
                    .collect(Collectors.toList());

            return ResponseUtils.successWithoutStringify(JSON.toJSONString(datas, new SerializeFilter[]{
                            valueFilter,
                            profilter,
                            jooqMapfilter},
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullListAsEmpty));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}