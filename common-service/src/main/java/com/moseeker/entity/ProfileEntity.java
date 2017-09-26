package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.biz.ProfileCompletenessImpl;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.moseeker.baseorm.db.profiledb.tables.ProfileProfile.PROFILE_PROFILE;

/**
 * 简历业务
 * Created by jack on 07/09/2017.
 */
@Service
@CounterIface
public class ProfileEntity {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 如果用户已经存在简历，那么则更新简历；如果不存在简历，那么添加简历。
     * @param profileParameter 简历信息
     * @return 格式化的简历信息
     */
    public ProfilePojo parseProfile(String profileParameter) {
        Map<String, Object> paramMap = JSON.parseObject(EmojiFilter.filterEmoji1(EmojiFilter.unicodeToUtf8(profileParameter)));
        return ProfilePojo.parseProfile(paramMap);
    }

    /**
     * 解析简历
     *
     * @param fileName 文件名字
     * @param file     文件
     * @return
     * @throws TException
     */
    public ResumeObj profileParser(String fileName, String file) throws TException, IOException {
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        try {
            propertiesReader.loadResource("common.properties");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Integer resumeUid = propertiesReader.get("resume.uid", Integer.class);
        String pwd = propertiesReader.get("resume.pwd", String.class);
        String resumeUrl = propertiesReader.get("resume.url", String.class);

        HttpPost httpPost = new HttpPost(resumeUrl);
        httpPost.setEntity(new StringEntity(file, Consts.UTF_8));

        // 设置头字段
        String authStr = "admin:2015";
        String authEncoded = Base64.encodeBase64String(authStr.getBytes());
        httpPost.setHeader("Authorization", "Basic " + authEncoded);
        httpPost.addHeader("content-type", "application/json");

        // 设置内容信息
        JSONObject json = new JSONObject();
        json.put("fname", fileName);    // 文件名
        json.put("base_cont", file); // 经base64编码过的文件内容
        json.put("uid", resumeUid);        // 用户id
        json.put("pwd", pwd);        // 用户密码
        StringEntity params = new StringEntity(json.toString());
        httpPost.setEntity(params);

        // 发送请求
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response = httpclient.execute(httpPost);
        // 处理返回结果
        String resCont = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
        logger.info(resCont);
        ResumeObj res = JSONObject.parseObject(resCont, ResumeObj.class);
        return res;
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
     * 根据用户编号查找简历信息
     * @param userId 用户编号
     * @return 简历profile_profile信息
     */
    public ProfileProfileDO getProfileByUserId(int userId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(PROFILE_PROFILE.USER_ID.getName(), userId);
        return profileDao.getData(queryBuilder.buildQuery());
    }

    public void reCalculateProfileAward(Integer profileId, int awardId) {
        completenessImpl.reCalculateProfileAward(profileId, awardId);
    }

    public void reCalculateUserUser(int profileId) {
        completenessImpl.reCalculateUserUser(profileId);
    }

    public void reCalculateProfileBasic(int profileId) {
        completenessImpl.reCalculateProfileBasic(profileId);
    }

    public void recalculateProfileCredential(int profileId, int credentialId) {
        completenessImpl.recalculateProfileCredential(profileId, credentialId);
    }

    public void reCalculateProfileEducation(int profileId, int educationId) {
        completenessImpl.reCalculateProfileEducation(profileId, educationId);
    }

    public void reCalculateProfileIntention(int profileId, int intentionId) {
        completenessImpl.reCalculateProfileIntention(profileId, intentionId);
    }

    public void reCalculateProfileWorks(int profileId, int worksId) {
        completenessImpl.reCalculateProfileWorks(profileId, worksId);
    }

    public int getCompleteness(int userId, String uuid, int profileId) {
        return completenessImpl.getCompleteness(userId, uuid, profileId);
    }

    public void recalculateprofileLanguage(Integer profileId, int languageId) {
        completenessImpl.recalculateprofileLanguage(profileId, languageId);
    }

    public void reCalculateProfileProjectExpByProfileId(Integer profileId) {
        completenessImpl.reCalculateProfileProjectExpByProfileId(profileId);
    }

    public void reCalculateProfileProjectExpByProjectExpId(Integer projectExpId) {
        completenessImpl.reCalculateProfileProjectExpByProjectExpId(projectExpId);
    }

    public void reCalculateProfileProjectExp(int profileId, int projectExpId) {
        completenessImpl.reCalculateProfileProjectExp(profileId, projectExpId);
    }

    public void reCalculateUserUserByUserIdOrMobile(int userId, String mobile) {
        completenessImpl.reCalculateUserUserByUserIdOrMobile(userId, mobile);
    }

    public void reCalculateProfileSkill(Integer profileId, int skillId) {
        completenessImpl.reCalculateProfileSkill(profileId, skillId);
    }

    public void reCalculateProfileWorkExp(int profileId, int workExpId) {
        completenessImpl.reCalculateProfileWorkExp(profileId, workExpId);
    }

    public void reCalculateProfileWorkExpUseWorkExpId(int id) {
        completenessImpl.reCalculateProfileWorkExp(id);
    }

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

    public void updateProfile(ProfilePojo profilePojo, ProfileProfileDO profileProfileDO) {
        int profileId = profileProfileDO.getId();
        ProfileProfileRecord record = BeanUtils.structToDB(profileProfileDO, ProfileProfileRecord.class);
        improveProfile(profilePojo.getProfileRecord(), record);
        improveBasic(profilePojo.getBasicRecord(), profileId);
        improveAttachment(profilePojo.getAttachmentRecords(), profileId);
        improveAwards(profilePojo.getAwardsRecords(), profileId);
        improveCredentials(profilePojo.getCredentialsRecords(), profileId);
        improveEducation(profilePojo.getEducationRecords(), profileId);
        improveIntention(profilePojo.getIntentionRecords(), profileId);
        improveLanguage(profilePojo.getLanguageRecords(), profileId);
        improveOther(profilePojo.getOtherRecord(), profileId);
        improveProjectexp(profilePojo.getProjectExps(), profileId);
        improveSkill(profilePojo.getSkillRecords(), profileId);
        improveWorkexp(profilePojo.getWorkexpRecords(), profileId);
        improveWorks(profilePojo.getWorksRecords(), profileId);
        getCompleteness(0, null, profileId);
    }

    public int createProfile(ProfilePojo profilePojo, UserUserDO userUserDO) {
        UserUserRecord userUserRecord = BeanUtils.structToDB(userUserDO, UserUserRecord.class);
        int id = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                userUserRecord, null);
        return id;
    }
}
