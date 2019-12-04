package com.moseeker.baseorm.dao.profiledb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileSaveResult;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.dictdb.tables.*;
import com.moseeker.baseorm.db.dictdb.tables.records.*;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.*;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationAtsRecord;
import com.moseeker.baseorm.db.profiledb.tables.*;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import org.jooq.Condition;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.userdb.tables.UserEmployee.USER_EMPLOYEE;
import static com.moseeker.baseorm.util.BeanUtils.jooqMapfilter;
import static com.moseeker.baseorm.util.BeanUtils.profilter;


/**
 * @author xxx
 *         ProfileProfileDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileProfileDao extends JooqCrudImpl<ProfileProfileDO, ProfileProfileRecord> {

    private static final Logger log = LoggerFactory.getLogger(ProfileProfileDao.class);

    public ProfileProfileDao() {
        super(ProfileProfile.PROFILE_PROFILE, ProfileProfileDO.class);
    }


    public ProfileProfileDao(TableImpl<ProfileProfileRecord> table, Class<ProfileProfileDO> profileProfileDOClass) {
        super(table, profileProfileDOClass);
    }

    public ProfileProfileRecord getProfileByIdOrUserIdOrUUID(int userId, int profileId, String uuid) {
        ProfileProfileRecord record = null;
        if (userId > 0 || profileId > 0 || !StringUtils.isNullOrEmpty(uuid)) {
            Condition condition = null;

            create.select().from(ProfileProfile.PROFILE_PROFILE).groupBy(ProfileProfile.PROFILE_PROFILE.USER_ID);

            if (userId > 0) {
                if (condition == null) {
                    condition = ProfileProfile.PROFILE_PROFILE.USER_ID.equal(userId);
                }
            }
            if (profileId > 0) {
                if (condition == null) {
                    condition = ProfileProfile.PROFILE_PROFILE.ID.equal(profileId);
                } else {
                    condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(profileId));
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
                        .where(condition).and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal((byte) (1)))
                        .limit(1).fetch();
                if (result != null && result.size() > 0) {
                    record = result.get(0);
                }
            }
        }
        return record;
    }

    public List<ProfileProfileRecord> getProfilesByIdOrUserIdOrUUID(int userId, int profileId, String uuid) {
        List<ProfileProfileRecord> records = null;
        if (userId > 0 || profileId > 0 || !StringUtils.isNullOrEmpty(uuid)) {
            Condition condition = null;
            if (userId > 0) {
                if (condition == null) {
                    condition = ProfileProfile.PROFILE_PROFILE.USER_ID.equal((int) (userId));
                }
            }
            if (profileId > 0) {
                if (condition == null) {
                    condition = ProfileProfile.PROFILE_PROFILE.ID.equal((int) (profileId));
                } else {
                    condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal((int) (profileId)));
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
                        .where(condition).and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal((byte) (1)))
                        .fetch();
                if (result != null && result.size() > 0) {
                    records = result;
                }
            }
        }

        return records;
    }

    public int deleteProfile(int profileId) {
        int result = 0;
        if (profileId > 0) {
            StringBuffer sb = new StringBuffer("(");
            List<Integer> ids = create.fetch("select id from profiledb.profile_intention where profile_id = " + profileId).into(Integer.class);

            for (int id : ids) {
                sb.append(id + ",");
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 1);
                sb.append(")");
                create.execute("delete from profiledb.profile_intention_city where profile_intention_id in "
                        + sb.toString());
                create.execute("delete from profiledb.profile_intention_position where profile_intention_id in "
                        + sb.toString());
                create.execute("delete from profiledb.profile_intention_industry where profile_intention_id in "
                        + sb.toString());
            }
            create.execute("delete from profiledb.profile_attachment where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_awards where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_basic where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_credentials where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_education where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_import where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_import where profile_id = " + profileId);

            create.execute("delete from profiledb.profile_intention where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_language where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_other where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_projectexp where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_skill where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_workexp where profile_id = " + profileId);
            create.execute("delete from profiledb.profile_works where profile_id = " + profileId);
            result = create.execute("delete from profiledb.profile_profile where id = " + profileId);
        }
        return result;
    }

    public int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
                           List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
                           List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
                           ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
                           List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
                           List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
                           List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords,
                           UserUserRecord userRecord) {
        int profileId = 0;
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
            java.util.Date birthDay = null;
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
                int basicCompleteness = CompletenessCalculator.calculateProfileBasic(basicRecord, userRecord.getMobile());
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
                int awardCompleteness = CompletenessCalculator.calculateAwards(awardsRecords);
                completenessRecord.setProfileAwards(awardCompleteness);
            }
            if (credentialsRecords != null && credentialsRecords.size() > 0) {
                credentialsRecords.forEach(credentialsRecord -> {
                    credentialsRecord.setProfileId(profileRecord.getId());
                    credentialsRecord.setCreateTime(now);
                    create.attach(credentialsRecord);
                    credentialsRecord.insert();
                });
                int credentialsCompleteness = CompletenessCalculator.calculateCredentials(credentialsRecords);
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
                int educationCompleteness = CompletenessCalculator.calculateProfileEducations(educationRecords);
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
                int intentionCompleteness = CompletenessCalculator.calculateIntentions(intentionRecords,
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
                int languageCompleteness = CompletenessCalculator.calculateLanguages(languages);
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
                int skillCompleteness = CompletenessCalculator.calculateSkills(skillRecords);
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
                int workExpCompleteness = CompletenessCalculator.calculateProfileWorkexps(workexpRecords,
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
                int projectExpCompleteness = CompletenessCalculator.calculateProjectexps(projectExps, workexpRecords);
                completenessRecord.setProfileProjectexp(projectExpCompleteness);
            }

            if (worksRecords != null && worksRecords.size() > 0) {
                worksRecords.forEach(worksRecord -> {
                    worksRecord.setProfileId(profileRecord.getId());
                    worksRecord.setCreateTime(now);
                    create.attach(worksRecord);
                    worksRecord.insert();
                });
                int worksCompleteness = CompletenessCalculator.calculateWorks(worksRecords);
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
                int userCompleteness = CompletenessCalculator.calculateUserUser(userRecord, settingRecord,
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
            profileRecord.setCompleteness((byte) (totalComplementness));
            profileRecord.update();
            profileId = profileRecord.getId().intValue();
//            String distinctId = profileRecord.getUserId().toString();
//            String property=String.valueOf(totalComplementness);
//            Map<String, Object> properties = new HashMap<String, Object>();
//            properties.put("totalComplementness", property);
//            sensorSend.profileSet(distinctId,"ProfileCompleteness",properties);
        }
        return profileId;
    }
    @CounterIface
    public ProfileSaveResult saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
                           List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
                           List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
                           ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
                           List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
                           List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
                           List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords, UserUserRecord userRecord,
                           List<ProfileProfileRecord> oldProfile) {
        ProfileSaveResult profileSaveResult = new ProfileSaveResult();

        if (oldProfile != null && oldProfile.size() > 0) {
            for (ProfileProfileRecord record : oldProfile) {
                clearProfile(record.getId().intValue());
            }
        }

        Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
        Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
        Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();
        Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();
        if (profileRecord != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            profileRecord.setCreateTime(now);
            logger.debug("saveProfile userId:{},   uuid:{},  source:{} ", profileRecord.getUserId(),  profileRecord.getUuid(), profileRecord.getSource());
            create.attach(profileRecord);
            profileRecord.insert();
            profileSaveResult.setProfileRecord(profileRecord);

				/* 计算profile完整度 */
            ProfileCompletenessRecord completenessRecord = new ProfileCompletenessRecord();
            completenessRecord.setProfileId(profileRecord.getId());
            java.util.Date birthDay = basicRecord==null?null:basicRecord.getBirth();
            ThreadPool tp = ThreadPool.Instance;
            CountDownLatch countDownLatch = new CountDownLatch(14);
            //计算basic完整度
            tp.startTast(()->{
                try{
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
                        profileSaveResult.setBasicRecord(basicRecord);

                        //计算basic完整度，由于修改规则，mobile或者微信号有一个即计入，为了不改变数据库表结构所以将mobile传入basic的完整度计算程序当中
                        int basicCompleteness = CompletenessCalculator.calculateProfileBasic(basicRecord, userRecord.getMobile());
                        completenessRecord.setProfileBasic(basicCompleteness);
                    }
                }catch(Exception e){
                    logger.error(e.getMessage(),e);
                    throw e;
                }finally{
                    countDownLatch.countDown();
                }
                return 0;
            });
            //插入附件
            tp.startTast(()->{

                try {
                    if (attachmentRecords != null && attachmentRecords.size() > 0) {
                        attachmentRecords.forEach(attachmentRecord -> {
                            attachmentRecord.setProfileId(profileRecord.getId());
                            attachmentRecord.setCreateTime(now);
                            create.attach(attachmentRecord);
                            attachmentRecord.insert();
                            profileSaveResult.setAttachmentRecord(attachmentRecord);
                        });
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            // 计算奖项完整度
            tp.startTast(()->{
                try {
                    if (awardsRecords != null && awardsRecords.size() > 0) {
                        awardsRecords.forEach(awardsRecord -> {
                            awardsRecord.setProfileId(profileRecord.getId());
                            awardsRecord.setCreateTime(now);
                            create.attach(awardsRecord);
                            awardsRecord.insert();
                            profileSaveResult.setAwardsRecords(awardsRecord);
                        });
                        // 计算奖项完整度
                        int awardCompleteness = CompletenessCalculator.calculateAwards(awardsRecords);
                        completenessRecord.setProfileAwards(awardCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            //计算证书完整度
            tp.startTast(()->{
                try {
                    if (credentialsRecords != null && credentialsRecords.size() > 0) {
                        credentialsRecords.forEach(credentialsRecord -> {
                            credentialsRecord.setProfileId(profileRecord.getId());
                            credentialsRecord.setCreateTime(now);
                            create.attach(credentialsRecord);
                            credentialsRecord.insert();
                            profileSaveResult.setCredentialsRecord(credentialsRecord);
                        });
                        //计算证书完整度
                        int credentialsCompleteness = CompletenessCalculator.calculateCredentials(credentialsRecords);
                        completenessRecord.setProfileCredentials(credentialsCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            //计算教育经历完整度
            tp.startTast(()->{
                try {
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
                            profileSaveResult.setEducationRecord(educationRecord);
                        });
                        //计算教育经历完整度
                        int educationCompleteness = CompletenessCalculator.calculateProfileEducations(educationRecords);
                        completenessRecord.setProfileEducation(educationCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            tp.startTast(()->{
                try {
                    if (importRecord != null) {
                        create.attach(importRecord);
                        importRecord.setCreateTime(now);
                        importRecord.setProfileId(profileRecord.getId());
                        importRecord.insert();
                        profileSaveResult.setImportRecord(importRecord);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            //计算求职意向完整度
            tp.startTast(()->{
                try {
                    if (intentionRecords != null && intentionRecords.size() > 0) {
                        List<ProfileIntentionCityRecord> intentionCityRecords = new ArrayList<>();
                        List<ProfileIntentionPositionRecord> intentionPositionRecords = new ArrayList<>();
                        intentionRecords.forEach(intentionRecord -> {
                            intentionRecord.setProfileId(profileRecord.getId());
                            intentionRecord.setCreateTime(now);
                            create.attach(intentionRecord);
                            intentionRecord.insert();
                            profileSaveResult.setIntentionRecord(intentionRecord);
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
                        int intentionCompleteness = CompletenessCalculator.calculateIntentions(intentionRecords,
                                intentionCityRecords, intentionPositionRecords);
                        completenessRecord.setProfileIntention(intentionCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            //计算语言完整度
            tp.startTast(()->{
                try {
                    if (languages != null && languages.size() > 0) {
                        languages.forEach(language -> {
                            language.setProfileId(profileRecord.getId());
                            language.setCreateTime(now);
                            create.attach(language);
                            language.insert();
                            profileSaveResult.setLanguages(language);
                        });
                        //计算语言完整度
                        int languageCompleteness = CompletenessCalculator.calculateLanguages(languages);
                        completenessRecord.setProfileLanguage(languageCompleteness);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            tp.startTast(()->{
                try {
                    if (otherRecord != null) {
                        create.attach(otherRecord);
                        otherRecord.setCreateTime(now);
                        otherRecord.setProfileId(profileRecord.getId());
                        otherRecord.insert();
                        profileSaveResult.setOtherRecord(otherRecord);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            tp.startTast(()->{
                try {
                    if (skillRecords != null && skillRecords.size() > 0) {
                        skillRecords.forEach(skill -> {
                            skill.setProfileId(profileRecord.getId());
                            skill.setCreateTime(now);
                            create.attach(skill);
                            skill.insert();
                            profileSaveResult.setSkillRecord(skill);
                        });
                        //计算技能完整度
                        int skillCompleteness = CompletenessCalculator.calculateSkills(skillRecords);
                        completenessRecord.setProfileSkill(skillCompleteness);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            //计算工作经历完整度
            tp.startTast(()->{
                try {
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
                            profileSaveResult.setWorkexpRecord(workexp);
                        });
                        //计算工作经历完整度
                        int workExpCompleteness = CompletenessCalculator.calculateProfileWorkexps(workexpRecords, educationRecords, birthDay);
                        completenessRecord.setProfileWorkexp(workExpCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            tp.startTast(()->{
                try {
                    if (projectExps != null && projectExps.size() > 0) {
                        projectExps.forEach(projectExp -> {
                            projectExp.setProfileId(profileRecord.getId());
                            projectExp.setCreateTime(now);
                            create.attach(projectExp);
                            projectExp.insert();
                            profileSaveResult.setProjectExp(projectExp);
                        });
                        //计算项目经历完整度
                        int projectExpCompleteness = CompletenessCalculator.calculateProjectexps(projectExps, workexpRecords);
                        completenessRecord.setProfileProjectexp(projectExpCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            tp.startTast(()->{
                try {
                    if (worksRecords != null && worksRecords.size() > 0) {
                        worksRecords.forEach(worksRecord -> {
                            worksRecord.setProfileId(profileRecord.getId());
                            worksRecord.setCreateTime(now);
                            create.attach(worksRecord);
                            worksRecord.insert();
                            profileSaveResult.setWorksRecord(worksRecord);
                        });
                        int worksCompleteness = CompletenessCalculator.calculateWorks(worksRecords);
                        completenessRecord.setProfileWorks(worksCompleteness);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });
            tp.startTast(()->{
                try {
                    if (userRecord != null) {
                        if (org.apache.commons.lang.StringUtils.isBlank(userRecord.getName())) {
                            userRecord.setName("未填写");
                        }
                        create.attach(userRecord);
                        userRecord.update();

                        /* 计算简历完整度 */
                        completenessRecord.setProfileId(profileRecord.getId());
                        UserWxUserRecord wxuserRecord = create.selectFrom(UserWxUser.USER_WX_USER)
                                .where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userRecord.getId().intValue())).limit(1)
                                .fetchOne();
                        UserSettingsRecord settingRecord = create.selectFrom(UserSettings.USER_SETTINGS)
                                .where(UserSettings.USER_SETTINGS.USER_ID.equal(userRecord.getId())).limit(1).fetchOne();
                        int userCompleteness = CompletenessCalculator.calculateUserUser(userRecord, settingRecord,
                                wxuserRecord);
                        completenessRecord.setUserUser(userCompleteness);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw e;
                } finally {
                    countDownLatch.countDown();
                }
                return 0;
            });


            try{
                //阻塞直到所有线程结束
                countDownLatch.await();
            }catch (InterruptedException e){
                logger.info(e.getMessage(),e);
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

            profileRecord.setCompleteness((byte) (totalComplementness));
            profileRecord.update();
            create.attach(completenessRecord);
            completenessRecord.insert();
            if (userRecord != null) {
                create.attach(userRecord);
                userRecord.update();
            }
//            String distinctId = profileRecord.getUserId().toString();
//            String property=String.valueOf(totalComplementness);
//            Map<String, Object> properties = new HashMap<String, Object>();
//            properties.put("totalComplementness", property);
//            sensorSend.profileSet(distinctId,"ProfileCompleteness",properties);
        }
        return profileSaveResult;
    }

    private int clearProfile(int profileId) {
        int result = 0;
        StringBuffer sb = new StringBuffer("(");
        List<Integer> ids = create.fetch("select id from profiledb.profile_intention where profile_id = " + profileId).into(Integer.class);

        for (int id : ids) {
            sb.append(id + ",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            create.execute(
                    "delete from profiledb.profile_intention_city where profile_intention_id in " + sb.toString());
            create.execute(
                    "delete from profiledb.profile_intention_position where profile_intention_id in " + sb.toString());
            create.execute(
                    "delete from profiledb.profile_intention_industry where profile_intention_id in " + sb.toString());
        }
        create.execute("delete from profiledb.profile_attachment where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_awards where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_basic where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_credentials where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_education where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_import where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_import where profile_id = " + profileId);

        create.execute("delete from profiledb.profile_intention where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_language where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_other where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_projectexp where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_skill where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_workexp where profile_id = " + profileId);
        create.execute("delete from profiledb.profile_works where profile_id = " + profileId);
        result = create.execute("delete from profiledb.profile_profile where id = " + profileId);
        return result;
    }

    public Result<Record2<Integer, String>> findRealName(List<Integer> profileIds) {
        Result<Record2<Integer, String>> result = null;
        if (profileIds != null && profileIds.size() > 0) {
            Condition condition = null;
            for (Integer profileId : profileIds) {
                if (condition != null) {
                    condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal((int) (profileId)));
                } else {
                    condition = ProfileProfile.PROFILE_PROFILE.ID.equal((int) (profileId));
                }
            }
            result = create.select(ProfileProfile.PROFILE_PROFILE.ID, UserUser.USER_USER.NAME)
                    .from(ProfileProfile.PROFILE_PROFILE).join(UserUser.USER_USER)
                    .on(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UserUser.USER_USER.ID)).where(condition)
                    .fetch();
        }
        return result;
    }

    public String findRealName(int profileId) {
        Condition condition = ProfileProfile.PROFILE_PROFILE.ID.equal((int) (profileId));
        Record1<String> username = create.select(UserUser.USER_USER.NAME).from(ProfileProfile.PROFILE_PROFILE)
                .join(UserUser.USER_USER).on(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UserUser.USER_USER.ID))
                .where(condition).limit(1).fetchOne();
        if (username != null) {
            return (String) username.get(0);
        }
        return null;
    }

    public void updateRealName(int profileId, String name) {
        ProfileProfileRecord record = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.ID.equal((int) (profileId))).fetchOne();
        if (record != null) {
            UserUserRecord userRecord = new UserUserRecord();
            userRecord.setId(record.getUserId());
            create.attach(userRecord);
            userRecord.setName(name);
            userRecord.update();

            create.update(USER_EMPLOYEE)
                    .set(USER_EMPLOYEE.CNAME, name)
                    .where(USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                    .and(USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                    .and(USER_EMPLOYEE.SYSUSER_ID.eq(record.getUserId()))
                    .execute();
        }
    }

    public int updateUpdateTime(Set<Integer> profileIds) {
        int status = 0;
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        status = create.update(ProfileProfile.PROFILE_PROFILE).set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                .where(ProfileProfile.PROFILE_PROFILE.ID.in(profileIds)).execute();

        return status;
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

    private void buildMap(Map map, String key, Map<String, Object> object) {
        if (object == null) {
            map.put(key, new HashMap<>());
        } else {
            map.put(key, object);
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

    ValueFilter valueFilter = (object, name, value) -> {
        if (value == null)
            return "";
        return value;
    };

    private Condition buildProfileCondition(Map<String, String> conditionsMap) {
        Condition condition = JobApplication.JOB_APPLICATION.EMAIL_STATUS.eq(0);

        if (conditionsMap == null || conditionsMap.size() == 0) {
            return condition;
        }

        //职位申请开始借书时间

        if (conditionsMap.get("apply_start") != null && FormCheck.isDateTime(conditionsMap.get("apply_start"))) {
            LocalDateTime start = LocalDateTime.parse(conditionsMap.get("apply_start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            condition = condition.and(JobApplication.JOB_APPLICATION._CREATE_TIME.ge(Timestamp.valueOf(start)));
        }

        if (conditionsMap.get("apply_end") != null && FormCheck.isDateTime(conditionsMap.get("apply_end"))) {
            LocalDateTime start = LocalDateTime.parse(conditionsMap.get("apply_end"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            condition = condition.and(JobApplication.JOB_APPLICATION._CREATE_TIME.ge(Timestamp.valueOf(start)));
        }


        return condition;
    }

    private int getProfilePage(Map<String, String> conditionsMap) {
        int page = 1;

        if (conditionsMap == null || conditionsMap.size() == 0) {
            return page;
        }

        String pageStr = conditionsMap.get("page");

        if (org.apache.commons.lang.StringUtils.isNumeric(pageStr)) {
            page = Integer.valueOf(pageStr);
        }

        if (page < 1) page = 1;

        return page;

    }

    private int getProfilePageSize(Map<String, String> conditionsMap) {
        int pageSize = 10;
        int maxPageSize = 100;

        if (conditionsMap == null || conditionsMap.size() == 0) {
            return pageSize;
        }

        String pageSizeStr = conditionsMap.get("page_size");

        if (org.apache.commons.lang.StringUtils.isNumeric(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }

        if (pageSize < 1) pageSize = 1;
        if (pageSize > maxPageSize) pageSize = maxPageSize;

        return pageSize;
    }



    public List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>>  getResourceByApplication(String downloadApi, String password, ProfileApplicationForm profileApplicationForm) {
        logger.debug("getResourceByApplication:=============={}:{}", "start", 0);
        long startTime = System.currentTimeMillis();

        int page = getProfilePage(profileApplicationForm.getConditions());
        int pageSize = getProfilePageSize(profileApplicationForm.getConditions());

        JobPosition jobposition = JobPosition.JOB_POSITION;
        JobApplication jobApplication = JobApplication.JOB_APPLICATION;
        List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> positionApplications = create.select()
                .from(jobposition.join(jobApplication).on(JobPosition.JOB_POSITION.ID.eq(JobApplication.JOB_APPLICATION.POSITION_ID)))
                .where(JobPosition.JOB_POSITION.COMPANY_ID.eq(profileApplicationForm.getCompany_id()))
                .and(JobPosition.JOB_POSITION.SOURCE_ID.eq(profileApplicationForm.getSource_id()))
                .and(JobApplication.JOB_APPLICATION.ATS_STATUS.eq(profileApplicationForm.getAts_status()))
                .and(buildProfileCondition(profileApplicationForm.getConditions()))
                .orderBy(JobApplication.JOB_APPLICATION._CREATE_TIME.desc())
                .limit(pageSize)
                .offset((page - 1) * pageSize)
                .fetch()
                .stream()
                .map(record -> new AbstractMap.SimpleEntry<>(record.into(jobposition).intoMap(), record.into(jobApplication).intoMap()))
                .collect(Collectors.toList());

        List<Integer> appIdList = positionApplications.stream().map(entry -> (Integer) entry.getValue().get("id")).collect(Collectors.toList());
        List<JobApplicationAtsRecord> atsRecordList = create.select()
                .from(JobApplicationAts.JOB_APPLICATION_ATS)
                .where(JobApplicationAts.JOB_APPLICATION_ATS.APP_ID.in(appIdList))
                .fetchInto(JobApplicationAts.JOB_APPLICATION_ATS);
        if (atsRecordList != null && atsRecordList.size() > 0) {
            Map<Integer, String> map = atsRecordList.stream().collect(Collectors.toMap(JobApplicationAtsRecord::getAppId, JobApplicationAtsRecord::getAtsAppId));
            positionApplications.stream().forEach(mapMapSimpleEntry -> {
                if (map.get(mapMapSimpleEntry.getValue().get("id")) != null) {
                    mapMapSimpleEntry.getValue().put("l_application_id", map.get(mapMapSimpleEntry.getValue().get("id")));
                }
            });
        }

        logger.debug("getResourceByApplication:=============={}:{}", "end", System.currentTimeMillis() - startTime);
        return positionApplications;
    }

    public Response handleResponse(List<Map<String, Object>> datas){
        return ResponseUtils.successWithoutStringify(JSON.toJSONString(datas, new SerializeFilter[]{
                        valueFilter,
                        profilter,
                        jooqMapfilter},
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.DisableCircularReferenceDetect));
    }

    public List<Map<String, Object>> getRelatedDataByJobApplication(List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> positonApplicatons,
                                                                    String downloadApi, String password,
                                                                    boolean recommender,
                                                                    boolean dl_url_required,
                                                                    Map<String, List<String>> filter) {

        long startTime = System.currentTimeMillis();

        Set<Integer> positionIds = new HashSet<>();
        Set<Integer> applicationIds = new HashSet<>();
        Set<Integer> applierIds = new HashSet<>();
        Set<Integer> recommenderIds = new HashSet<>();

        for (AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> entry : positonApplicatons) {
            positionIds.add((Integer) entry.getKey().get("id"));
            applicationIds.add((Integer) entry.getValue().get("id"));
            applierIds.add((Integer) entry.getValue().get("applier_id"));
            recommenderIds.add(((Integer) entry.getValue().get("recommender_user_id")));
        }

        positionIds.remove(Integer.valueOf(0));
        applicationIds.remove(Integer.valueOf(0));
        applierIds.remove(Integer.valueOf(0));
        recommenderIds.remove(Integer.valueOf(0));

        logger.debug("getResourceByApplication:==============:positionIds:{}", positionIds);
        logger.debug("getResourceByApplication:==============:applicationIds:{}", applicationIds);
        logger.debug("getResourceByApplication:==============:applierIds:{}", applierIds);
        logger.debug("getResourceByApplication:==============:recommenderIds:{}", recommenderIds);

        //所有的positionExt
        List<Map<String, Object>> allPositionExt = new ArrayList<>();
        if (!filterTable(filter, "job_position_ext") && positionIds.size() > 0) {
            allPositionExt = create
                    .select()
                    .from(JobPositionExt.JOB_POSITION_EXT)
                    .where(JobPositionExt.JOB_POSITION_EXT.PID.in(positionIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "positionExt耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的job_resume_other
        List<Map<String, Object>> allResumeOther = new ArrayList<>();
        if (!filterTable(filter, "job_resume_other") && applicationIds.size() > 0) {
            allResumeOther = create
                    .select()
                    .from(JobResumeOther.JOB_RESUME_OTHER)
                    .where(JobResumeOther.JOB_RESUME_OTHER.APP_ID.in(applicationIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allResumeOther耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的user_user
        List<Map<String, Object>> allUser = new ArrayList<>();
        if (!filterTable(filter, "user_user") && applierIds.size() > 0) {
            allUser = create
                    .select()
                    .from(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.in(applierIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allUser耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的user_user
        List<Map<String, Object>> allApplierEmployee = new ArrayList<>();
        if (!filterTable(filter, "user_employee") && applierIds.size() > 0) {
            allApplierEmployee = create
                    .select()
                    .from(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.in(applierIds))
                    .and(USER_EMPLOYEE.DISABLE.eq((byte) 0))
                    .and(USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                    .and(USER_EMPLOYEE.STATUS.eq(0))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allApplierEmployee耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的user_thirdparty_user
        List<Map<String, Object>> allThirdPartyUser = new ArrayList<>();
        if (!filterTable(filter, "user_thirdparty_user") && applierIds.size() > 0) {
            allThirdPartyUser = create
                    .select()
                    .from(UserThirdpartyUser.USER_THIRDPARTY_USER)
                    .where(UserThirdpartyUser.USER_THIRDPARTY_USER.USER_ID.in(applierIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allThirdPartyUser耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的候选人推荐记录
        Map<Integer, List<CandidateRecomRecordDO>> allCandidateRecomRecord = new HashMap<>();
        if (!filterTable(filter, "candidate_recom_record") && applicationIds.size() > 0) {
            List<CandidateRecomRecordDO> tempRecomRecords = create
                    .select()
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.in(applicationIds))
                    .fetchInto(CandidateRecomRecordDO.class);

            if(!StringUtils.isEmptyList(tempRecomRecords)){
                allCandidateRecomRecord = tempRecomRecords.stream().collect(Collectors.groupingBy(r->r.getAppId()));
            }

        }

        logger.debug("getResourceByApplication:=============={}:{}", "allThirdPartyUser耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        List<Integer> profileIds = new ArrayList<>();

        //所有的profiles
        List<Map<String, Object>> allProfile = new ArrayList<>();
        if (applierIds.size() > 0) {
            allProfile = create
                    .select()
                    .from(ProfileProfile.PROFILE_PROFILE)
                    .where(ProfileProfile.PROFILE_PROFILE.USER_ID.in(applierIds))
                    .fetchMaps()
                    .stream()
                    .map(mp -> {
                        profileIds.add((Integer) mp.get("id"));
                        return mp;
                    })
                    .collect(Collectors.toList());
        }
        profileIds.remove(Integer.valueOf(0));
        logger.debug("getResourceByApplication:==============:profileIds:{}", profileIds);

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_attachment
        List<Map<String, Object>> allProfile_attachment = new ArrayList<>();
        if (!filterTable(filter, "profile_attachment") && profileIds.size() > 0) {
            allProfile_attachment = create
                    .select()
                    .from(ProfileAttachment.PROFILE_ATTACHMENT)
                    .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_attachment耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_basic
        ProfileBasic basic = ProfileBasic.PROFILE_BASIC.as("pb");
        DictCountry dictCountry = DictCountry.DICT_COUNTRY.as("dc");
        List<Map<String, Object>> allProfile_basic = new ArrayList<>();
        if (!filterTable(filter, "profile_basic") && profileIds.size() > 0) {
            allProfile_basic = create
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
                    .where(basic.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_basic耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_awards
        List<Map<String, Object>> allProfile_award = new ArrayList<>();
        if (!filterTable(filter, "profile_award") && profileIds.size() > 0) {
            allProfile_award = create
                    .select()
                    .from(ProfileAwards.PROFILE_AWARDS)
                    .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }
        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_award耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_credentials
        List<Map<String, Object>> allProfile_credentials = new ArrayList<>();
        if (!filterTable(filter, "profile_credentials") && profileIds.size() > 0) {
            allProfile_credentials = create
                    .select()
                    .from(ProfileCredentials.PROFILE_CREDENTIALS)
                    .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.in(profileIds))
                    .orderBy(ProfileCredentials.PROFILE_CREDENTIALS.GET_DATE.desc())
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_credentials耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_educations
        List<Map<String, Object>> allProfile_educations = new ArrayList<>();
        if (!filterTable(filter, "profile_educations") && profileIds.size() > 0) {
            allProfile_educations = create
                    .select()
                    .from(ProfileEducation.PROFILE_EDUCATION)
                    .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.in(profileIds))
                    .orderBy(ProfileEducation.PROFILE_EDUCATION.START.desc())
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_educations耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_import
        List<Map<String, Object>> allProfile_import = new ArrayList<>();
        if (!filterTable(filter, "profile_import") && profileIds.size() > 0) {
            allProfile_import = create
                    .select()
                    .from(ProfileImport.PROFILE_IMPORT)
                    .where(ProfileImport.PROFILE_IMPORT.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_import耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        List<Integer> intentionIds = new ArrayList<>();

        //所有的profile_intention
        List<Map<String, Object>> allProfile_intention = new ArrayList<>();
        if (profileIds.size() > 0) {
            allProfile_intention = create
                    .select()
                    .from(ProfileIntention.PROFILE_INTENTION)
                    .where(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.in(profileIds))
                    .fetchMaps()
                    .stream()
                    .map(mp -> {
                        intentionIds.add((Integer) mp.get("id"));
                        return mp;
                    })
                    .collect(Collectors.toList());
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_intention耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_intention_city
        List<Map<String, Object>> allProfile_intention_city = new ArrayList<>();
        if (!filterTable(filter, "profile_intention_city") && intentionIds.size() > 0) {
            allProfile_intention_city = create
                    .select()
                    .from(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                    .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.in(intentionIds))
                    .fetchMaps();
        }
        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_intention_city耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_intention_industry
        List<Map<String, Object>> allProfile_intention_industry = new ArrayList<>();
        if (!filterTable(filter, "profile_intention_industry") && intentionIds.size() > 0) {
            allProfile_intention_industry = create
                    .select()
                    .from(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                    .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.in(intentionIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_intention_industry耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_intention_position
        List<Map<String, Object>> allProfile_intention_position = new ArrayList<>();
        if (!filterTable(filter, "profile_intention_position") && intentionIds.size() > 0) {
            allProfile_intention_position = create
                    .select()
                    .from(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                    .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.in(intentionIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_intention_position耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_languages
        List<Map<String, Object>> allProfile_language = new ArrayList<>();
        if (!filterTable(filter, "profile_language") && profileIds.size() > 0) {
            allProfile_language = create
                    .select()
                    .from(ProfileLanguage.PROFILE_LANGUAGE)
                    .where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_language耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_other
        List<Map<String, Object>> allProfile_other = new ArrayList<>();
        if (!filterTable(filter, "profile_other") && profileIds.size() > 0) {
            allProfile_other = create
                    .select()
                    .from(ProfileOther.PROFILE_OTHER)
                    .where(ProfileOther.PROFILE_OTHER.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_other耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //所有的profile_projectexp
        List<Map<String, Object>> allProfile_projectexp = new ArrayList<>();
        if (!filterTable(filter, "profile_projectexp") && profileIds.size() > 0) {
            allProfile_projectexp = create
                    .select()
                    .from(ProfileProjectexp.PROFILE_PROJECTEXP)
                    .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_projectexp耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //profile_skill
        List<Map<String, Object>> allProfile_skills = new ArrayList<>();
        if (!filterTable(filter, "profile_skill") && profileIds.size() > 0) {
            allProfile_skills = create
                    .select()
                    .from(ProfileSkill.PROFILE_SKILL)
                    .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_skills耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //profile_workexp
        ProfileWorkexp workexp = ProfileWorkexp.PROFILE_WORKEXP;
        HrCompany company = HrCompany.HR_COMPANY;
        List<Map<String, Object>> allProfile_workexp = new ArrayList<>();
        if (!filterTable(filter, "profile_workexp") && profileIds.size() > 0) {
            allProfile_workexp = create
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
                    .where(workexp.PROFILE_ID.in(profileIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allProfile_workexp耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //recommender employee
        List<Map<String, Object>> allRecommenderEmployee = new ArrayList<>();
        if (!filterTable(filter, "recommender") && recommenderIds.size() > 0) {
            allRecommenderEmployee = create
                    .select()
                    .from(USER_EMPLOYEE)
                    .where(USER_EMPLOYEE.SYSUSER_ID.in(recommenderIds))
                    .and(USER_EMPLOYEE.DISABLE.eq((byte) 0))
                    .and(USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                    .and(USER_EMPLOYEE.STATUS.eq(0))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "allEmployee耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //recommender user
        List<Map<String, Object>> allRecommenderUser = new ArrayList<>();
        if (!filterTable(filter, "recommender") && recommenderIds.size() > 0) {
            allRecommenderUser = create
                    .select()
                    .from(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.in(recommenderIds))
                    .fetchMaps();
        }

        logger.debug("getResourceByApplication:=============={}:{}", "profile_other耗时", System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        List<Map<String, Object>> result = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> entry : positonApplicatons) {

            Map<String, Object> map = new HashMap<>();

            Integer positionId = (Integer) entry.getKey().get("id");
            Integer applicationId = (Integer) entry.getValue().get("id");
            Integer recommenderId = (Integer) entry.getValue().get("recommender_user_id");
            Integer applierId = (Integer) entry.getValue().get("applier_id");


            //job_number and title from jobdb.job_position
            if (!filterTable(filter, "job_position")) {
                buildMap(filter, map, "job_position", entry.getKey());
            }

            //all from jobdb.job_application
            if (!filterTable(filter, "job_application")) {
                buildMap(filter, map, "job_application", entry.getValue());
            }

            //extra from jobdb.job_position_ext # custom job fields in JSON format
            if (!filterTable(filter, "job_position_ext")) {
                buildMap(filter, map, "job_position_ext", new HashMap<>());
                for (Map<String, Object> mp : allPositionExt) {
                    Integer pid = (Integer) mp.get("pid");
                    if (pid != null && pid.intValue() == positionId.intValue()) {
                        if (!StringUtils.isNullOrEmpty(mp.get("extra") + "")) {
                            buildMap(filter, map, "job_position_ext", mp);
                            break;
                        }
                    }
                }

            }

            //other from jobdb.job_resume_other # custom résumé fields in JSON format
            if (!filterTable(filter, "job_resume_other")) {
                buildMap(filter, map, "job_resume_other", new HashMap<>());
                for (Map<String, Object> mp : allResumeOther) {
                    Integer appid = (Integer) mp.get("app_id");
                    if (appid != null && appid.intValue() > 0 && appid.intValue() == applicationId.intValue()) {
                        buildMap(filter, map, "job_resume_other", mp);
                        break;
                    }
                }
            }

            //all from userdb.user_user
            if (!filterTable(filter, "user_user")) {
                buildMap(filter, map, "user_user", new HashMap<>());
                for (Map<String, Object> mp : allUser) {
                    Integer user_id = (Integer) mp.get("id");
                    if (user_id != null && user_id.intValue() > 0 && user_id.intValue() == applierId.intValue()) {
                        buildMap(filter, map, "user_user", mp);
                        break;
                    }
                }
            }

            //all from userdb.user_employee
            if (!filterTable(filter, "user_employee")) {
                buildMap(filter, map, "user_employee", new HashMap<>());
                for (Map<String, Object> mp : allApplierEmployee) {
                    Integer sysuser_id = (Integer) mp.get("sysuser_id");
                    if (sysuser_id != null && sysuser_id.intValue() > 0 && sysuser_id.intValue() == applierId.intValue()) {
                        buildMap(filter, map, "user_employee", mp);
                        break;
                    }
                }
            }


            //all from profiledb.user_thirdparty_user # ATS login
            if (!filterTable(filter, "user_thirdparty_user")) {
                List<Map<String, Object>> user_thirdparty_users = new ArrayList<>();
                for (Map<String, Object> mp : allThirdPartyUser) {
                    Integer user_id = (Integer) mp.get("user_id");
                    if (user_id != null && user_id > 0 && user_id == applierId.intValue()) {
                        user_thirdparty_users.add(mp);
                    }
                }
                buildMap(filter, map, "user_thirdparty_user", user_thirdparty_users);
            }

            Integer profileId = null;

            for (Map<String, Object> mp : allProfile) {
                Integer user_id = (Integer) mp.get("user_id");
                if (user_id != null && user_id.intValue() > 0 && user_id.intValue() == applierId.intValue()) {
                    profileId = (Integer) mp.get("id");
                    break;
                }
            }

            //all from profiledb.profile_profile
            if (!filterTable(filter, "profile_profile")) {
                buildMap(filter, map, "profile_profile", new HashMap<>());
                for (Map<String, Object> mp : allProfile) {
                    Integer user_id = (Integer) mp.get("user_id");
                    if (user_id != null && user_id.intValue() > 0 && user_id.intValue() == applierId.intValue()) {
                        profileId = (Integer) mp.get("id");
                        buildMap(filter, map, "profile_profile", mp);
                        break;
                    }
                }
            }

            if (recommender && recommenderId != null && recommenderId.intValue() != 0) {
                //user_employee.disable=0, activation=0, status=0
                if (!filterTable(filter, "recommender")) {

                    Map<String, Object> recommenderMap = new HashMap<>();

                    for (Map<String, Object> mp : allRecommenderUser) {
                        Integer user_id = (Integer) mp.get("id");
                        if (user_id != null && user_id.intValue() > 0 && user_id.intValue() == recommenderId.intValue()) {
                            recommenderMap.putAll(mp);
                            break;
                        }
                    }

                    for (Map<String, Object> mp : allRecommenderEmployee) {
                        Integer sysUserId = (Integer) mp.get("sysuser_id");
                        if (sysUserId != null && sysUserId > 0 && sysUserId == recommenderId.intValue()) {
                            recommenderMap.put("employeeid", mp.get("employeeid"));
                            recommenderMap.put("custom_field", mp.get("custom_field"));
                            recommenderMap.put("custom_field_values", mp.get("custom_field_values"));
                            recommenderMap.put("auth_method", mp.get("auth_method"));
                            recommenderMap.put("employee_email", mp.get("email"));
                            recommenderMap.put("is_first_post_user", false);

                            if(allCandidateRecomRecord.containsKey(applicationId)){
                                CandidateRecomRecordDO recomRecord = allCandidateRecomRecord.get(applicationId).get(0);

                                // 因为申请里的推荐人只会保存分享链路的第一个分享者，所以可能发生：张三(员工)-->李四-->王五-->申请人
                                // 此时申请的推荐人是张三，但是已经不是张三直接分享给他的，所以不算一度人脉
                                // 只有当：张三(员工)-->申请人，的时候，才是一度人脉
                                // 所以depth必须小于1，且申请人是员工

                                // 1）逻辑走到这里的时候就代表推荐人是员工了
                                // 2）所以只要确定是推荐者直接分享给申请人就行
                                // 用申请ID查到对应的candidate_recom_record，申请中的推荐人ID就是candidate_recom_record的post_user_id
                                // 如果对应的candidate_recom_record.depth（即分享链路深度），小于1，那就代表是，申请人看到的职位是推荐人直接分享给他的
                                if (recomRecord.getDepth() <= 1  && recommenderId ==  recomRecord.getPostUserId()){
                                    recommenderMap.put("is_first_post_user", true);
                                }
                            }

                            break;
                        }
                    }
                    if(!recommenderMap.containsKey("employeeid")) {
                        recommenderMap.clear();
                    }
                    buildMap(filter, map, "recommender", recommenderMap);
                }
            }

            if (profileId!=null) {
                //all from profiledb.profile_attachment
                if (!filterTable(filter, "profile_attachment")) {
                    buildMap(filter, map, "profile_attachment", new HashMap<>());
                    for (Map<String, Object> mp : allProfile_attachment) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            buildMap(filter, map, "profile_attachment", mp);
                            break;
                        }
                    }
                }

                //all from profiledb.profile_basic
                if (!filterTable(filter, "profile_basic")) {
                    buildMap(filter, map, "profile_basic", new HashMap<>());
                    for (Map<String, Object> mp : allProfile_basic) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            buildMap(filter, map, "profile_basic", mp);
                            break;
                        }
                    }
                }

                //all from profiledb.profile_award
                if (!filterTable(filter, "profile_award")) {

                    List<Map<String, Object>> profile_awards = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_award) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_awards.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_award", profile_awards);
                }

                //all from profiledb.profile_credentials ORDER most recent first by start date
                if (!filterTable(filter, "profile_credentials")) {

                    List<Map<String, Object>> profile_credentials = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_credentials) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_credentials.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_credentials", profile_credentials);
                }

                //all from profiledb.profile_educations ORDER most recent first by start date
                if (!filterTable(filter, "profile_educations")) {

                    List<Map<String, Object>> profile_educations = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_educations) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (map.get("start") == null) {
                            continue;
                        }
                        if (map.get("end") == null && (int) map.get("end_until_now") == 0) {
                            continue;
                        }
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_educations.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_educations", profile_educations);
                }

                //all from profiledb.profile_import
                if (!filterTable(filter, "profile_import")) {

                    buildMap(filter, map, "profile_import", new HashMap<>());
                    for (Map<String, Object> mp : allProfile_import) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            buildMap(filter, map, "profile_import", mp);
                            break;
                        }
                    }
                }

                //all from profiledb.profile_language
                if (!filterTable(filter, "profile_language")) {

                    List<Map<String, Object>> profile_language = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_language) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_language.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_language", profile_language);
                }

                //all from profiledb.profile_other
                if (!filterTable(filter, "profile_other")) {

                    buildMap(filter, map, "profile_other", new HashMap<>());
                    for (Map<String, Object> mp : allProfile_other) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            buildMap(filter, map, "profile_other", mp);
                            break;
                        }
                    }
                }

                //all from profiledb.profile_projectexp ORDER most recent first by start date
                if (!filterTable(filter, "profile_projectexp")) {

                    List<Map<String, Object>> profile_projectexp = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_projectexp) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (map.get("start") == null) {
                            continue;
                        }
                        if (map.get("end") == null && (int) map.get("end_until_now") == 0) {
                            continue;
                        }
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_projectexp.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_projectexp", profile_projectexp);
                }

                //all from profiledb.profile_skills
                if (!filterTable(filter, "profile_skills")) {

                    List<Map<String, Object>> profile_skills = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_skills) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_skills.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_skills", profile_skills);
                }

                //all from profiledb.profile_workexp
                if (!filterTable(filter, "profile_workexp")) {

                    List<Map<String, Object>> profile_workexp = new ArrayList<>();
                    for (Map<String, Object> mp : allProfile_workexp) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (map.get("start") == null) {
                            continue;
                        }
                        if (map.get("end") == null && (int) map.get("end_until_now") == 0) {
                            continue;
                        }
                        if (StringUtils.isNullOrEmpty((String) map.get("description"))) {
                            continue;
                        }
                        if (StringUtils.isNullOrEmpty((String) map.get("job"))) {
                            continue;
                        }
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profile_workexp.add(mp);
                        }
                    }
                    buildMap(filter, map, "profile_workexp", profile_workexp);
                }

                Integer profileIntentionId = null;
                //all from profiledb.profile_intention
                if (!filterTable(filter, "profile_intention")) {
                    buildMap(filter, map, "profile_intention", new HashMap<>());
                    for (Map<String, Object> mp : allProfile_intention) {
                        Integer pid = (Integer) mp.get("profile_id");
                        if (pid != null && pid.intValue() > 0 && pid.intValue() == profileId.intValue()) {
                            profileIntentionId = (Integer) mp.get("id");
                            buildMap(filter, map, "profile_intention", mp);
                            break;
                        }
                    }
                }

                if (profileIntentionId != null) {
                    if (!filterTable(filter, "profile_intention_city")) {

                        List<Map<String, Object>> profile_intention_city = new ArrayList<>();
                        for (Map<String, Object> mp : allProfile_intention_city) {
                            Integer piid = (Integer) mp.get("profile_intention_id");
                            if (piid != null && piid.intValue() > 0 && piid.intValue() == profileIntentionId.intValue()) {
                                profile_intention_city.add(mp);
                            }
                        }
                        buildMap(filter, map, "profile_intention_city", profile_intention_city);
                    }

                    //all from profiledb.profile_intention_industry
                    if (!filterTable(filter, "profile_intention_industry")) {

                        List<Map<String, Object>> profile_intention_industry = new ArrayList<>();
                        for (Map<String, Object> mp : allProfile_intention_industry) {
                            Integer piid = (Integer) mp.get("profile_intention_id");
                            if (piid != null && piid.intValue() > 0 && piid.intValue() == profileIntentionId.intValue()) {
                                profile_intention_industry.add(mp);
                            }
                        }
                        buildMap(filter, map, "profile_intention_industry", profile_intention_industry);
                    }

                    //all from profiledb.profile_intention_position
                    if (!filterTable(filter, "profile_intention_position")) {

                        List<Map<String, Object>> profile_intention_position = new ArrayList<>();
                        for (Map<String, Object> mp : allProfile_intention_position) {
                            Integer piid = (Integer) mp.get("profile_intention_id");
                            if (piid != null && piid.intValue() > 0 && piid.intValue() == profileIntentionId.intValue()) {
                                profile_intention_position.add(mp);
                            }
                        }
                        buildMap(filter, map, "profile_intention_position", profile_intention_position);
                    }
                }
            }

            result.add(map);
        }

        return result;

    }

    /**
     * filter为全量时调用
     *
     * "filter" : {"profile_language":["*"],"user_user":["*"],"profile_intention_position":["*"],"profile_intention":["*"],"profile_skills":["*"],"profile_basic":["*"],"user_thirdparty_user":["*"],"profile_credentials":["*"],"profile_workexp":["*"],"profile_award":["*"],"job_resume_other":["*"],"profile_projectexp":["*"],"job_position_ext":["*"],"profile_profile":["*"],"profile_attachment":["*"],"profile_other":["*"],"profile_intention_industry":["*"],"profile_intention_city":["*"],"profile_educations":["*"],"profile_import":["*"]}
     * @param  positonApplicatons
     * @param recommender
     * @return List
     * @Author lee
     * @Date 2019/11/1 11:00
     */
    public List<Map<String, Object>> getRelatedDataByJobApplicationFullFilter(List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> positonApplicatons,
                                                                              boolean recommender) {
        Set<Integer> positionIds = new HashSet<>();
        Set<Integer> applicationIds = new HashSet<>();
        Set<Integer> applierIds = new HashSet<>();
        Set<Integer> recommenderIds = new HashSet<>();

        for (AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> entry : positonApplicatons) {
            positionIds.add((Integer) entry.getKey().get("id"));
            applicationIds.add((Integer) entry.getValue().get("id"));
            applierIds.add((Integer) entry.getValue().get("applier_id"));
            recommenderIds.add(((Integer) entry.getValue().get("recommender_user_id")));
        }

        positionIds.remove(Integer.valueOf(0));
        applicationIds.remove(Integer.valueOf(0));
        applierIds.remove(Integer.valueOf(0));
        recommenderIds.remove(Integer.valueOf(0));

        //所有的user_user
        List<Map<String, Object>> allApplierEmployee = new ArrayList<>();
        if (applierIds.size() > 0) {
            allApplierEmployee = create
                    .select()
                    .from(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.in(applierIds))
                    .and(USER_EMPLOYEE.DISABLE.eq((byte) 0))
                    .and(USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                    .and(USER_EMPLOYEE.STATUS.eq(0))
                    .fetchMaps();
        }

        //所有的候选人推荐记录
        Map<Integer, List<CandidateRecomRecordDO>> allCandidateRecomRecord = new HashMap<>();
        if (applicationIds.size() > 0) {
            List<CandidateRecomRecordDO> tempRecomRecords = create
                    .select()
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.in(applicationIds))
                    .fetchInto(CandidateRecomRecordDO.class);

            if(!StringUtils.isEmptyList(tempRecomRecords)){
                allCandidateRecomRecord = tempRecomRecords.stream().collect(Collectors.groupingBy(r->r.getAppId()));
            }

        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> entry : positonApplicatons) {

            Map<String, Object> map = new HashMap<>();

            Integer applicationId = (Integer) entry.getValue().get("id");
            Integer recommenderId = (Integer) entry.getValue().get("recommender_user_id");
            Integer applierId = (Integer) entry.getValue().get("applier_id");

            buildMap(map, "job_position", entry.getKey());
            buildMap(map, "job_application", entry.getValue());
            buildMap(map, "user_employee", new HashMap<>());
            for (Map<String, Object> mp : allApplierEmployee) {
                Integer sysuser_id = (Integer) mp.get("sysuser_id");
                if (sysuser_id != null && sysuser_id.intValue() > 0 && sysuser_id.intValue() == applierId.intValue()) {
                    buildMap(map, "user_employee", mp);
                    break;
                }
            }

            if (recommender && recommenderId != null && recommenderId.intValue() != 0) {

                //recommender employee
                List<Map<String, Object>> allRecommenderEmployee = new ArrayList<>();
                //recommender user
                List<Map<String, Object>> allRecommenderUser = new ArrayList<>();
                if (recommenderIds.size() > 0) {
                    allRecommenderEmployee = create
                            .select()
                            .from(USER_EMPLOYEE)
                            .where(USER_EMPLOYEE.SYSUSER_ID.in(recommenderIds))
                            .and(USER_EMPLOYEE.DISABLE.eq((byte) 0))
                            .and(USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                            .and(USER_EMPLOYEE.STATUS.eq(0))
                            .fetchMaps();
                    allRecommenderUser = create
                            .select()
                            .from(UserUser.USER_USER)
                            .where(UserUser.USER_USER.ID.in(recommenderIds))
                            .fetchMaps();
                }

                Map<String, Object> recommenderMap = new HashMap<>();

                for (Map<String, Object> mp : allRecommenderUser) {
                    Integer user_id = (Integer) mp.get("id");
                    if (user_id != null && user_id > 0 && user_id.intValue() == recommenderId.intValue()) {
                        recommenderMap.putAll(mp);
                        break;
                    }
                }

                for (Map<String, Object> mp : allRecommenderEmployee) {
                    Integer sysUserId = (Integer) mp.get("sysuser_id");
                    if (sysUserId != null && sysUserId > 0 && sysUserId == recommenderId.intValue()) {
                        recommenderMap.put("employeeid", mp.get("employeeid"));
                        recommenderMap.put("custom_field", mp.get("custom_field"));
                        recommenderMap.put("custom_field_values", mp.get("custom_field_values"));
                        recommenderMap.put("auth_method", mp.get("auth_method"));
                        recommenderMap.put("employee_email", mp.get("email"));
                        recommenderMap.put("is_first_post_user", false);

                        if(allCandidateRecomRecord.containsKey(applicationId)){
                            CandidateRecomRecordDO recomRecord = allCandidateRecomRecord.get(applicationId).get(0);
                            if (recomRecord.getDepth() <= 1  && recommenderId ==  recomRecord.getPostUserId()){
                                recommenderMap.put("is_first_post_user", true);
                            }
                        }
                        break;
                    }
                }
                if(!recommenderMap.containsKey("employeeid")) {
                    recommenderMap.clear();
                }
                buildMap(map, "recommender", recommenderMap);
            }
            result.add(map);
        }
        return result;
    }

    public ProfileProfileRecord getProfileByUserId(int userId) {
        return create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(userId))
                .limit(1)
                .fetchAny();
    }

    public int updateUpdateTimeByUserId(int userId) {
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        return create.update(ProfileProfile.PROFILE_PROFILE)
                .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq(userId))
                .execute();
    }

    public List<Record2<Integer,Integer>> fetchUserProfile(List<Integer> userIdList) {
        Result<Record2<Integer,Integer>> result = create.select(ProfileProfile.PROFILE_PROFILE.USER_ID, ProfileProfile.PROFILE_PROFILE.ID)
                .from(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.in(userIdList))
                .fetch();
        if (result != null) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 迁移简历的所有人
     * @param record 简历数据
     * @param newUserId 简历的新的所有人
     */
    public int changUserId(ProfileProfileRecord record, int newUserId) {
        return create.update(ProfileProfile.PROFILE_PROFILE)
                .set(ProfileProfile.PROFILE_PROFILE.USER_ID, newUserId)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq(record.getUserId()))
                .and(ProfileProfile.PROFILE_PROFILE.ID.eq(record.getId()))
                .andNotExists(
                        create.selectOne().from(
                                create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                                        .where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq(newUserId))
                        )
                ).execute();
    }

    /**
     * 根据用户编号获取简历。优先获取可用的简历
     * @param userId 用户编号
     * @return 简历
     */
    public ProfileProfileRecord getProfileOrderByActiveByUserId(int userId) {
        return create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(userId))
                .orderBy(ProfileProfile.PROFILE_PROFILE.DISABLE.desc())
                .limit(1)
                .fetchAny();
    }

    public List<ProfileProfileDO> getProfileByUidList(Set<Integer> userIdList) {
        return create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.in(userIdList))
                .fetchInto(ProfileProfileDO.class);
    }

    public List<ProfileProfileDO> getProfileByProfileList(List<Integer> profileIdList) {
        return create.selectFrom(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.ID.in(profileIdList))
                .fetchInto(ProfileProfileDO.class);
    }
}
