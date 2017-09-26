package com.moseeker.profile.entity;

import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 10/07/2017.
 */
@Component
public class ProfileEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProfileAttachmentDao attachmentDao;

    @Autowired
    private ProfileWorksDao worksDao;

    @Autowired
    private ProfileEducationDao educationDao;

    @Autowired
    private ProfileIntentionDao intentionDao;

    @Autowired
    private ProfileLanguageDao languageDao;

    @Autowired
    private ProfileOtherDao otherDao;

    @Autowired
    private ProfileBasicDao profileBasicDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileImportDao profileImportDao;

    @Autowired
    private ProfileProjectexpDao projectExpDao;

    @Autowired
    private ProfileSkillDao skillDao;

    @Autowired
    private ProfileWorkexpDao workExpDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    @Autowired
    private UserUserDao userDao;

    @Autowired
    private ProfileCredentialsDao credentialsDao;

    @Autowired
    private ProfileAwardsDao awardsDao;

    @Transactional
    public void improveUser(UserUserRecord userRecord) {
        if (userRecord != null) {
            userDao.updateRecord(userRecord);
        }
    }

    @Transactional
    public void improveWorks(List<ProfileWorksRecord> worksRecords, int profileId) {
        if (worksRecords != null && worksRecords.size() > 0) {
            worksDao.delWorksByProfileId(profileId);
            worksRecords.forEach(skill -> {
                skill.setId(null);
                skill.setProfileId((int) (profileId));
            });
            worksDao.addAllRecord(worksRecords);
        }
    }

    @Transactional
    public void improveWorkexp(List<ProfileWorkexpEntity> workexpRecords, int profileId) {
        if (workexpRecords != null && workexpRecords.size() > 0) {
            workExpDao.delWorkExpsByProfileId(profileId);
            List<ProfileWorkexpEntity> records = new ArrayList<>();

            workexpRecords.forEach(skill -> {
                skill.setId(null);
                skill.setProfileId((int) (profileId));
                records.add(skill);
            });
            workExpDao.postWordExps(records);
        }
    }

    @Transactional
    public void improveSkill(List<ProfileSkillRecord> skillRecords, int profileId) {
        if (skillRecords != null && skillRecords.size() > 0) {
            skillDao.delSkillByProfileId(profileId);
            skillRecords.forEach(skill -> {
                skill.setId(null);
                skill.setProfileId((int) (profileId));
            });
            skillDao.addAllRecord(skillRecords);
        }
    }

    @Transactional
    public void improveProjectexp(List<ProfileProjectexpRecord> projectExps, int profileId) {
        if (projectExps != null && projectExps.size() > 0) {
            projectExpDao.delProjectExpByProfileId(profileId);
            projectExps.forEach(language -> {
                language.setId(null);
                language.setProfileId((profileId));
            });
            projectExpDao.addAllRecord(projectExps);
        }
    }

    @Transactional
    public void improveOther(ProfileOtherRecord otherRecord, int profileId) {
        if (otherRecord != null && StringUtils.isNotNullOrEmpty(otherRecord.getOther())) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(profileId));
            ProfileOtherRecord record = otherDao.getRecord(qu);
            if (record == null && otherRecord != null) {
                otherRecord.setProfileId((int) (profileId));
                otherDao.addRecord(otherRecord);
            }
        }
    }

    @Transactional
    public void improveLanguage(List<ProfileLanguageRecord> languageRecords, int profileId) {
        if (languageRecords != null && languageRecords.size() > 0) {
            languageDao.delLanguageByProfileId(profileId);
            languageRecords.forEach(language -> {
                language.setId(null);
                language.setProfileId((int) (profileId));
            });
            languageDao.addAllRecord(languageRecords);
        }
    }

    @Transactional
    public void improveIntention(List<IntentionRecord> intentionRecords, int profileId) {
        if (intentionRecords != null && intentionRecords.size() > 0) {
            intentionDao.delIntentionsByProfileId(profileId);
            intentionRecords.forEach(intention -> {
                intention.setId(null);
                intention.setProfileId((int) (profileId));
            });
            intentionDao.postIntentions(intentionRecords);
        }
    }

    @Transactional
    public void improveEducation(List<ProfileEducationRecord> educationRecords, int profileId) {
        if (educationRecords != null && educationRecords.size() > 0) {
            educationDao.delEducationsByProfileId(profileId);
            educationRecords.forEach(education -> {
                education.setId(null);
                education.setProfileId((int) (profileId));
            });

            educationDao.saveEducations(educationRecords);
        }
    }

    @Transactional
    public void improveCredentials(List<ProfileCredentialsRecord> credentialsRecords, int profileId) {
        if (credentialsRecords != null && credentialsRecords.size() > 0) {
            credentialsDao.delCredentialsByProfileId(profileId);
            credentialsRecords.forEach(credential -> {
                credential.setId(null);
                credential.setProfileId((int) (profileId));
            });
            credentialsDao.addAllRecord(credentialsRecords);
        }
    }

    @Transactional
    public void improveAwards(List<ProfileAwardsRecord> awardsRecords, int profileId) {
        if (awardsRecords != null && awardsRecords.size() > 0) {
            awardsDao.delAwardsByProfileId(profileId);
            awardsRecords.forEach(award -> {
                award.setId(null);
                award.setProfileId((int) (profileId));
            });
            awardsDao.addAllRecord(awardsRecords);
        }
    }

    @Transactional
    public void improveAttachment(List<ProfileAttachmentRecord> attachmentRecords, int profileId) {
        if (attachmentRecords != null && attachmentRecords.size() > 0) {
            attachmentDao.delAttachmentsByProfileId(profileId);
            attachmentRecords.forEach(attachment -> {
                attachment.setId(null);
                attachment.setProfileId((int) (profileId));
            });
            attachmentDao.addAllRecord(attachmentRecords);
        }
    }

    /**
     * 完善基本信息
     *
     * @param basicRecord
     * @param profileId
     */
    @Transactional
    public void improveBasic(ProfileBasicRecord basicRecord, int profileId) {
        if (basicRecord != null) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(profileId));
            boolean flag = false;
            ProfileBasicRecord basic = profileBasicDao.getRecord(qu);
            if (basic != null) {
                if (StringUtils.isNotNullOrEmpty(basicRecord.getName()) && StringUtils.isNullOrEmpty(basic.getName())) {
                    basic.setName(basicRecord.getName());
                    flag = true;
                }
                if (basicRecord.getGender() != null && basic.getGender() == null) {
                    basic.setGender(basicRecord.getGender());
                    flag = true;
                }
                if (basicRecord.getNationalityCode() != null && basic.getNationalityCode() == null) {
                    basic.setNationalityCode(basicRecord.getNationalityCode());
                    flag = true;
                }
                if (StringUtils.isNotNullOrEmpty(basicRecord.getNationalityName()) && StringUtils.isNullOrEmpty(basic.getNationalityName())) {
                    basic.setNationalityName(basicRecord.getNationalityName());
                    flag = true;
                }
                if (basicRecord.getCityCode() != null && basic.getCityCode() == null) {
                    basic.setCityCode(basicRecord.getCityCode());
                    flag = true;
                }
                if (StringUtils.isNotNullOrEmpty(basicRecord.getCityName()) && StringUtils.isNullOrEmpty(basic.getCityName())) {
                    basic.setCityName(basicRecord.getCityName());
                    flag = true;
                }
                if (basicRecord.getBirth() != null && basic.getBirth() == null) {
                    basic.setBirth(basicRecord.getBirth());
                    flag = true;
                }
                if (StringUtils.isNotNullOrEmpty(basicRecord.getWeixin()) && StringUtils.isNullOrEmpty(basic.getWeixin())) {
                    basic.setWeixin(basicRecord.getWeixin());
                    flag = true;
                }
                if (StringUtils.isNotNullOrEmpty(basicRecord.getQq()) && StringUtils.isNullOrEmpty(basic.getQq())) {
                    basic.setQq(basicRecord.getQq());
                    flag = true;
                }
                if (StringUtils.isNotNullOrEmpty(basicRecord.getMotto()) && StringUtils.isNullOrEmpty(basic.getMotto())) {
                    basic.setMotto(basicRecord.getMotto());
                    flag = true;
                }
                if (StringUtils.isNotNullOrEmpty(basicRecord.getSelfIntroduction()) && StringUtils.isNullOrEmpty(basic.getSelfIntroduction())) {
                    basic.setSelfIntroduction(basicRecord.getSelfIntroduction());
                    flag = true;
                }
                if (flag) {
                    profileBasicDao.updateRecord(basic);
                }
            } else {
                basicRecord.setProfileId((int) (profileId));
                profileBasicDao.addRecord(basicRecord);
            }
        }
    }

    /**
     * 更新profile_profile数据
     *
     * @param profileRecord
     * @param record
     */
    @Transactional
    public void improveProfile(ProfileProfileRecord profileRecord, ProfileProfileRecord record) {
        if (profileRecord != null) {
            if (record != null) {
                boolean flag = false;
                try {
                    if (profileRecord.getOrigin() != null && record.getOrigin() != null && !profileRecord.getOrigin().equals(record.getOrigin())) {
                        record.setOrigin(profileRecord.getOrigin());
                        flag = true;
                    }
                    if (flag) {
                        profileDao.updateRecord(record);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
            }
        }
    }
}
