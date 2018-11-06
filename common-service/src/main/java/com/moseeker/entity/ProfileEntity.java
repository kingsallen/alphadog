package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.dao.logdb.LogResumeDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserReferralRecordDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserReferralRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.EmployeeOperationEntrance;
import com.moseeker.common.constants.EmployeeOperationIsSuccess;
import com.moseeker.common.constants.EmployeeOperationType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.biz.ProfileCompletenessImpl;
import com.moseeker.entity.biz.ProfileMailUtil;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.exception.ProfileException;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.profile.struct.UserProfile;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.TException;
import org.jooq.Record2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.moseeker.baseorm.db.profiledb.tables.ProfileProfile.PROFILE_PROFILE;

/**
 * 简历业务
 * Created by jack on 07/09/2017.
 */
@Service
@CounterIface
public class ProfileEntity {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProfileParseUtil profileParseUtil;

    @Autowired
    private LogResumeDao resumeDao;

    @Autowired
    private ProfileMailUtil profileMailUtil;

    @Autowired
    LogEmployeeOperationLogEntity logEmployeeOperationLogEntity;

    /**
     * 如果用户已经存在简历，那么则更新简历；如果不存在简历，那么添加简历。
     * @param profileParameter 简历信息
     * @return 格式化的简历信息
     */
    public ProfilePojo parseProfile(String profileParameter) {
        Map<String, Object> paramMap = EmojiFilter.filterEmoji(profileParameter);
        return ProfilePojo.parseProfile(paramMap, profileParseUtil.initParseProfileParam());
    }

    /**
     * 为了不影响重构，提出一个方法封装原方法
     * @param
     * @author  cjm
     * @date  2018/6/26
     * @return
     */
    public ResumeObj profileParserAdaptor(String fileName, String file) throws TException, IOException {
        ResumeObj resumeObj =  profileParser(fileName, file);
        // 验证ResumeSDK解析剩余调用量是否大于1000，如果小于1000则发送预警邮件
        if(resumeObj != null && resumeObj.getAccount() != null && resumeObj.getAccount().getUsage_remaining() < 1000){
            profileMailUtil.sendProfileParseWarnMail(resumeObj.getAccount());
        }
        return resumeObj;
    }

    /**
     * 如果存在简历则合并，不存在则添加
     * @param profilePojo 简历数据
     * @param userId 用户编号
     */
    public int mergeProfile(ProfilePojo profilePojo, int userId) {

        ProfileProfileRecord profileDB = profileDao.getProfileOrderByActiveByUserId(userId);
        if (profileDB != null) {
            improveProfile(profilePojo.getProfileRecord(), profileDB);
            improveBasic(profilePojo.getBasicRecord(), profileDB.getId());
            improveAttachment(profilePojo.getAttachmentRecords(), profileDB.getId());
            improveAwards(profilePojo.getAwardsRecords(), profileDB.getId());
            improveCredentials(profilePojo.getCredentialsRecords(), profileDB.getId());
            improveEducation(profilePojo.getEducationRecords(), profileDB.getId());
            improveIntention(profilePojo.getIntentionRecords(), profileDB.getId());
            improveLanguage(profilePojo.getLanguageRecords(), profileDB.getId());
            improveOther(profilePojo.getOtherRecord(), profileDB.getId());
            improveProjectexp(profilePojo.getProjectExps(), profileDB.getId());
            improveSkill(profilePojo.getSkillRecords(), profileDB.getId());
            improveWorkexp(profilePojo.getWorkexpRecords(), profileDB.getId());
            improveWorks(profilePojo.getWorksRecords(), profileDB.getId());
            completenessImpl.reCalculateProfileBasic(profileDB.getId());
            return profileDB.getId();
        } else {
            return storeProfile(profilePojo);
        }
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
        if(StringUtils.isNullOrEmpty(resCont) || !resCont.startsWith("{")){     // 调用ResumeSDK会返回错误，即非Json格式结果，入库，并抛出异常
            LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
            logResumeRecordRecord.setErrorLog("call ResumeSDK error");
            logResumeRecordRecord.setFileName(fileName);
            logResumeRecordRecord.setResultData(resCont);
            logResumeRecordRecord.setText(file);
            logResumeRecordRecord = resumeDao.addRecord(logResumeRecordRecord);
            logger.error("call ResumeSDK error log id:{}",logResumeRecordRecord.getId());
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_CALL_RESUMESDK_RESULT_ERROR.replace("{}",logResumeRecordRecord.getId().toString()));
        }
        // 参考博客：http://loveljy119.iteye.com/blog/2366623  反序列化的ASM代码问题：https://github.com/alibaba/fastjson/issues/383
        ParserConfig.getGlobalInstance().setAsmEnable(false);
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
                    }
                    record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    profileDao.updateRecord(record);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
            }
        }
    }
    @Transactional
    public void upsertProfileProfile(ProfileProfileRecord profileRecord, int  profileId) {
        if(profileRecord==null){
            profileRecord=new ProfileProfileRecord();
        }
        if (profileRecord != null) {
            profileRecord.setId(profileId);
            profileRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            profileDao.updateRecord(profileRecord);
        }
    }
    @Transactional
    public void upsertProfileBasic(ProfileBasicRecord profileBasicRecord, int  profileId) {
        if (profileBasicRecord != null) {
            Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
            ProfileBasicRecord basic = profileBasicDao.getRecord(query);
            profileBasicRecord.setProfileId(profileId);
            if(basic!=null){
                profileBasicRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                profileBasicDao.updateRecord(profileBasicRecord);
            }else{
                profileBasicDao.addRecord(profileBasicRecord);
            }

        }
    }
    @Transactional
    public void upsertProfileOther(ProfileOtherRecord record, int  profileId) {
        if (record != null) {
            otherDao.delOtherByProfileId(profileId);
            record.setProfileId(profileId);
            otherDao.addRecord(record);

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
            logger.info("+++++++++++++++++++++++++++");
            logger.info(records.toString());
            logger.info("+++++++++++++++++++++++++++");
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
    public void mergeOther(ProfileOtherRecord otherRecord, int profileId) {
        if (otherRecord != null && StringUtils.isNotNullOrEmpty(otherRecord.getOther())) {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("profile_id", String.valueOf(profileId));
            ProfileOtherRecord record = otherDao.getRecord(query.buildQuery());
            if (record == null && otherRecord != null) {
                otherRecord.setProfileId((int) (profileId));
                otherDao.addRecord(otherRecord);
            } else if (record != null && otherRecord != null) {
                /**
                 * 自定义合并逻辑：oldOther没有或为空的字段且存在newOther中 -> 将newOther中的字段补填到oldOther里
                 */
                Map<String, Object> oldOtherMap = JSONObject.parseObject(otherRecord.getOther(), Map.class);
                Map<String, Object> newOtherMap = JSONObject.parseObject(record.getOther(), Map.class);
                oldOtherMap.entrySet().stream().filter(f -> (StringUtils.isNullOrEmpty(String.valueOf(f.getValue())) || "[]".equals(String.valueOf(f.getValue())))  && newOtherMap.containsKey(f.getKey())).forEach(e -> e.setValue(newOtherMap.get(e.getKey())));
                newOtherMap.putAll(oldOtherMap);
                otherRecord.setOther(JSONObject.toJSONString(newOtherMap));
                otherDao.updateRecord(otherRecord);
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

    public void reCalculateProfileCompleteness(int profileId){
        completenessImpl.reCalculateProfileCompleteness(profileId);
    }

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
        //先前只是根据表的记录简单叠加完整度，这在修改是不准确，需要重新计算，所以改成这样，
        //getCompleteness(0, null, profileId);
        reCalculateProfileCompleteness(profileId);
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

    /**
     * 持久化简历数据
     * @param profilePojo 简历数据
     * @return 简历编号
     */
    public int storeProfile(ProfilePojo profilePojo) {

        logger.info("ProfileEntity storeProfile source:{}, origin:{}, uuid:{}", profilePojo.getProfileRecord().getSource(),
                profilePojo.getProfileRecord().getOrigin(), profilePojo.getProfileRecord().getUuid());
        logger.info("ProfileEntity storeProfile userId:{}", profilePojo.getUserRecord().getId());
        return profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                profilePojo.getUserRecord(), null);
    }

    /**
     * 查找用户是否有简历
     * @param userIdList 用户编号集合
     * @return
     */
    public List<UserProfile> fetchUserProfile(List<Integer> userIdList) {
        List<UserProfile> userProfileList = new ArrayList<>();

        List<Record2<Integer, Integer>> result = profileDao.fetchUserProfile(userIdList);

        for (Integer integer : userIdList) {

            UserProfile userProfile = new UserProfile();

            Optional<Record2<Integer, Integer>> record2Optional = result
                    .stream()
                    .filter(integerIntegerRecord2 -> integerIntegerRecord2.value1().intValue() == integer.intValue())
                    .findAny();
            if (record2Optional.isPresent()) {
                userProfile.setHaveProfile(true);
            } else {
                userProfile.setHaveProfile(false);
            }
            userProfile.setUserId(integer);
            userProfileList.add(userProfile);
        }
        return userProfileList;
    }

    /**
     * 将用户信息持久化到数据库中
     * todo 应该要移到用户实体中
     * todo 内推业务已改移出仟寻用户生成的业务之外
     * @param profilePojo 简历数据
     * @param reference 推荐人
     * @param companyId 公司编号
     * @param source
     * @return 用户编号
     */
    @Transactional
    public UserUserRecord storeChatBotUser(ProfilePojo profilePojo, int reference, int companyId, UserSource source,int appid)
            throws ProfileException {

        UserEmployeeRecord employeeRecord = employeeDao.getActiveEmployeeByUserId(reference);
        if (employeeRecord == null) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        //获取companyUser
        //Redis 同步锁

        if (profilePojo.getUserRecord() != null) {
            UserReferralRecordRecord referralRecordRecord = userReferralRecordDao.insertIfNotExist(reference, companyId,
                    profilePojo.getUserRecord().getMobile(),
                            ReferralScene.ChatBot);

            UserUserRecord userUserRecord1 = storeUserRecord(profilePojo, source,appid,companyId,reference);
            if (referralRecordRecord != null && userUserRecord1 != null) {
                referralRecordRecord.setUserId(userUserRecord1.getId());
                userReferralRecordDao.updateRecord(referralRecordRecord);
            }
            return userUserRecord1;

        } else {
            throw ProfileException.PROFILE_USER_CREATE_FAILED;
        }
    }


    public UserUserRecord storeReferralUser(ProfilePojo profilePojo, int reference, int companyId) throws ProfileException {

        UserReferralRecordRecord referralRecordRecord  = userReferralRecordDao.insertReferralTypeIfNotExist(reference,
                companyId, profilePojo.getUserRecord().getMobile(),
                ReferralScene.Referral);
        if (referralRecordRecord != null) {
            UserUserRecord userUserRecord1 = storeUserRecord(profilePojo, UserSource.EMPLOYEE_REFERRAL,null,null,null);
            if (referralRecordRecord != null && userUserRecord1 != null) {
                referralRecordRecord.setUserId(userUserRecord1.getId());
                userReferralRecordDao.updateRecord(referralRecordRecord);
            }
            return userUserRecord1;
        } else {
            throw ProfileException.PROGRAM_DOUBLE_CLICK;
        }
    }

    private UserUserRecord storeUserRecord(ProfilePojo profilePojo, UserSource source,Integer appid,Integer referenceId,Integer companyId) {
        if (org.apache.commons.lang.StringUtils.isBlank(profilePojo.getUserRecord().getPassword())) {
            profilePojo.getUserRecord().setPassword("");
        }
        short shortSource = 0;
        if (source != null) {
            shortSource = (short) source.getValue();
        }
        profilePojo.getUserRecord().setSource(shortSource);
        UserUserRecord userUserRecord = userDao.addRecord(profilePojo.getUserRecord());

        logger.info("mergeProfile userId:{}", userUserRecord.getId());
        profilePojo.setUserRecord(userUserRecord);
        profilePojo.getProfileRecord().setUserId(userUserRecord.getId());
        int profileId = storeProfile(profilePojo);
        if(appid == EmployeeOperationEntrance.IMEMPLOYEE.getKey()){
            logEmployeeOperationLogEntity.insertEmployeeOperationLog(referenceId,appid, EmployeeOperationType.RESUMERECOMMEND.getKey(), EmployeeOperationIsSuccess.SUCCESS.getKey(),companyId,profileId);
        }

        return userUserRecord;
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

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private UserReferralRecordDao userReferralRecordDao;

    public static void main(String[] args) {
        String profile = "{\"attachments\":[{\"path\":\"/mnt/nfs/attachment/profile_attachement/201810/6ad420b7-660a-4107-96ed-0a5006f0e276.docx\",\"name\":\"智联-003-曾志香.docx\",\"description\":\"用户简历数据\"}],\"languages\":[{\"level\":0,\"name\":\"英语\"}],\"credentials\":[{\"name\":\"大学英语4级\"}],\"profile\":{\"user_id\":0,\"disable\":1,\"origin\":\"100000000000000000000000000000\",\"source\":222,\"uuid\":\"15d70b11-be87-48ad-abf7-0b8d01477be2\"},\"educations\":[{\"endDate\":\"2005-07-01\",\"degree\":5,\"description\":\"无\",\"collegeCode\":0,\"isStudyAbroad\":0,\"collegeName\":\"东华理工学院\",\"endUntilNow\":0,\"isFull\":0,\"majorName\":\"计算机科学与技术\",\"majorCode\":0,\"startDate\":\"2001-09-01\",\"isUnified\":0}],\"intentions\":[{\"cities\":[{\"cityName\":\"上海\",\"cityCode\":310000}],\"workstate\":0,\"worktype\":1,\"positions\":[{\"positionName\":\"技术总监,技术经理,架构师,资深java开发\",\"positionCode\":0}],\"salaryCode\":8}],\"projectexps\":[{\"endUntilNow\":1,\"responsibility\":\"责任描述:负责商家平台的功能开发。\",\"name\":\"电子商务平台\",\"description\":\"项目简介:电子商务平台及后台功能管理\\\\n责任描述:负责商家平台的功能开发。\",\"startDate\":\"2016-03-01\"},{\"endUntilNow\":0,\"endDate\":\"2016-03-01\",\"responsibility\":\"责任描述:负责整个it部门的任务分配及参与开发卡券平台系统。\",\"name\":\"卡券销售app\",\"description\":\"项目简介:流量卡的销售及营销类及支付购买。\\\\n责任描述:负责整个it部门的任务分配及参与开发卡券平台系统。\",\"startDate\":\"2015-06-01\"},{\"endUntilNow\":0,\"endDate\":\"2015-05-01\",\"responsibility\":\"责任描述:负责整个it部门的任务分配及参与开发短信平台系统。\",\"name\":\"短信平台系统\",\"description\":\"项目简介:短信群发单发平台。数据处理量大,响应速度快,适合各行业的接口开发,包括http,socket,webservice等,日处理3k万条短信的能力\\\\n责任描述:负责整个it部门的任务分配及参与开发短信平台系统。\",\"startDate\":\"2012-08-01\"},{\"endUntilNow\":0,\"endDate\":\"2012-03-01\",\"name\":\"积分管理系统\",\"description\":\"软件环境:oracle+linux+weblogic+jquery\\\\n开发工具:eclipse\\\\n工作描述:需求的整理,和客户沟通业务,积分管理系统设计,开发。\\\\n1、负责项目的需求和开发。\\\\n2、编写核心的java类。\\\\n3、负责重要模块的设计和实现。\\\\n4、指导初级开发人员进行开发。\\\\n5、项目组成员工作进度跟踪。\\\\n项目简介:会员积分的采及,积分消费。\\\\n\",\"startDate\":\"2011-03-01\"},{\"endUntilNow\":0,\"endDate\":\"2010-12-01\",\"name\":\"政府采购软件\",\"description\":\"软件环境:java+resin+oracle\\\\n开发工具:eclipse\\\\n工作描述:负责需求的整理,重要模块的升级和开发。\\\\n项目简介:该系统整体框架采用j2ee设计思想,严格的用户权限管理,分布式数据库技术、工作流引擎技术,安全识别技术,数字签名等,采用先进的加密技术,解决了信息在网络上传输、身份认证等安全问题。用户可以一次性在网上实现所有采购流程操作。\\\\n围绕政府采购业务,采购单位、采购中心、财政部门、监察审计等部门实现了在线联合办公,以实现更好的解决方案。\\\\n(二)设计和开发:\\\\n1. 开发语言:java、html、ftl、javascript\\\\n2. 会用flash mx、dreamweaver、frontpage等基本的网页制作工具\\\\n3.使用工具软件、容器、组件、架构:jbuilder、oracal、weblogic、jsp、javabean及spring\\\\n4. 设计思路:采用spring框架结构,通过jsp访问控制层servlet,控制层将数据临时保存于bean中,同时实例化action,并通过ejb访问数据库持久化相关数据,同时转发相应的视图jsp给客户端。\\\\n5. 本系统开发采用b/s结构,应用j2ee标签、javasript脚本语言验证机制,使用日志、cvs得以维护和扩展。\\\\n\",\"startDate\":\"2010-06-01\"},{\"endUntilNow\":0,\"endDate\":\"2010-05-01\",\"name\":\"惠通陆华gms\",\"description\":\"软件环境:tomcat5.5+oracle10g+extjs\\\\n开发工具:eclipse、cvs、powerdesigner、pl/sql\\\\n工作描述:1、负责制定extjs框架使用规范,并基于extjs框架编写公用的js,对extjs框架进一步封装,降低开发人员使用extjs框架的难度。\\\\n2、编写公用的java工具类。\\\\n项目简介:汽车行业管理系统\\\\n\",\"startDate\":\"2009-08-01\"},{\"endUntilNow\":0,\"endDate\":\"2009-06-01\",\"responsibility\":\"责任描述:、担任项目经理,负责开发团队的管理和协调。\\\\n2、负责重要模块的设计和实现。\\\\n3、指导初级开发人员进行开发。\\\\n4、项目组成员工作进度跟踪。\\\\n5、系统上线支持。\\\\n6、系统后期维护。\",\"name\":\"中进汽贸业务管理系统\",\"description\":\"软件环境:tomcat5.5+oracle10g\\\\n开发工具:eclipse、cvs、powerdesigner、pl/sql\\\\n项目简介:中国进口汽车贸易业务系统,包括报检报关、入库、pi、pdi、发车、出库、经销商收车等业务,客户现场开发。\\\\n责任描述:、担任项目经理,负责开发团队的管理和协调。\\\\n2、负责重要模块的设计和实现。\\\\n3、指导初级开发人员进行开发。\\\\n4、项目组成员工作进度跟踪。\\\\n5、系统上线支持。\\\\n6、系统后期维护。\",\"startDate\":\"2008-08-01\"},{\"endUntilNow\":0,\"endDate\":\"2008-06-01\",\"name\":\"银川社区医疗系统\",\"description\":\"软件环境:tomcat4.0+oracle9+windows2003\\\\n工作描述:负责系统运行期的维护、书写操作手册以及新增需求的实现。实现的模块有\\\\\"信息发布\\\\\"模块和“死亡记录”模块,参与需求分析和业务建模,建立数据模型,代码实现及测试。\\\\n项目简介:区医疗系统是区域性卫生信息系统的前端系统,主要是对社区居民的保健、护理、疾病预防等健康信息实行有效的管理。社区医疗机构为社区居民建立完整的健康档案,安排体检以及实施免疫等预防措施\\\\n\",\"startDate\":\"2007-05-01\"},{\"endUntilNow\":0,\"endDate\":\"2007-02-01\",\"name\":\"oa协同办公软件人力资源模块和资产管理模块的开发\",\"description\":\"软件环境:tomcat5.0+oracle+linux\\\\n开发工具:eclipse、cvs、powerdesigner\\\\n工作描述:产管理主要对资产的请购、入库、验收、领用、归还、维修、销售、报废等资产的使用和流动情况进行管理查看和统计,对于普通员工角色的用户,可以查看个人领用资产信息 和个人交接资产信息。\\\\n1、 参与需求调研和需求分析。\\\\n2、 业务建模、系统分析和业务流程建模。\\\\n3、 根据系统分析建立数据模型,独立实现前后台代码。\\\\n4、 系统测试期间的维护。\\\\n项目简介:这是公司oa产品中的两个模块,项目基于struts+hibernian+spring开发,运用了ajax、xml、el、jstl,使系统运行效率更高,jsp页面简洁易读。\\\\n人力资源模块中包括员工的招聘、应聘、转正、试用、人员调动、辞退、离休等审批工作。可建立员工的个人信息资料,并由人力资源专员对员工个人信息的变更情况进行记录,以及对数据的管理和维护,实现按权限查询、查看、统计。\\\\n\",\"startDate\":\"2006-06-01\"},{\"endUntilNow\":0,\"endDate\":\"2006-04-01\",\"name\":\"效能信息管理系统中效能目标管理模块的开发\",\"description\":\"软件环境:tomcat5.0+oracle+windows2003\\\\n硬件环境:一般的硬件配置\\\\n开发工具:eclipse、cvs、powerdesigner\\\\n工作描述:1、按照uml图和需求说明完成本部门目标和下级部门目标的信息管理、扣分、目标分解的增、删、查、改及操作权限控制功能的开发。\\\\n2、实现效能目标数据由excel文档导入系统数据库的功能。\\\\n项目简介:效能信息管理系统实现绩效目标到工作任务层层分解的自动化和可回溯性,实现工作任务完成情况逐级反馈及逐级评价,实现工作日志维护方便、易用和易汇总查询,实现绩效的 计算机考评。\\\\n\",\"startDate\":\"2005-08-01\"}],\"skills\":[{\"month\":0,\"level\":0,\"name\":\"jsp\"},{\"month\":0,\"level\":0,\"name\":\"servlet\"},{\"month\":0,\"level\":0,\"name\":\"jdbc\"},{\"month\":0,\"level\":0,\"name\":\"多线程\"},{\"month\":0,\"level\":0,\"name\":\"jstl\"},{\"month\":0,\"level\":0,\"name\":\"javaee\"},{\"month\":0,\"level\":0,\"name\":\"分布式\"},{\"month\":0,\"level\":0,\"name\":\"架构设计\"},{\"month\":0,\"level\":0,\"name\":\"struts\"},{\"month\":0,\"level\":0,\"name\":\"hibernate\"},{\"month\":0,\"level\":0,\"name\":\"mybatis\"},{\"month\":0,\"level\":0,\"name\":\"ibatis\"},{\"month\":0,\"level\":0,\"name\":\"spring\"},{\"month\":0,\"level\":0,\"name\":\"spring mvc\"},{\"month\":0,\"level\":0,\"name\":\"freemarker\"},{\"month\":0,\"level\":0,\"name\":\"memcache\"},{\"month\":0,\"level\":0,\"name\":\"zookeeper\"},{\"month\":0,\"level\":0,\"name\":\"redis\"},{\"month\":0,\"level\":0,\"name\":\"dubbo\"},{\"month\":0,\"level\":0,\"name\":\"rabbitmq\"},{\"month\":0,\"level\":0,\"name\":\"javascript\"},{\"month\":0,\"level\":0,\"name\":\"jquery\"},{\"month\":0,\"level\":0,\"name\":\"html5\"},{\"month\":0,\"level\":0,\"name\":\"css\"},{\"month\":0,\"level\":0,\"name\":\"ajax\"},{\"month\":0,\"level\":0,\"name\":\"前端技术\"}],\"basic\":{\"cityName\":\"上海\",\"gender\":1,\"cityCode\":310000,\"name\":\"曾志香\",\"birth\":\"1981-01-01\",\"selfIntroduction\":\"1、 学士教育背景和十年以上的相关工作经验\\\\n2、在不同工作领域的经验,熟悉采购系统运作和短信平台系统\\\\n3、十年以上相关it实战和管理经验和软件开发经验\"},\"workexps\":[{\"endUntilNow\":1,\"description\":\"工作描述:1. 负责公司商家平台建设,包括架构设计、项目跟进、核心开发;\\\\n2. 负责部门技术重点技术方案架构设计和技术支持;\\\\n3.带领技术团队构架、研发、设计 完成网站技术平台及产品开发,以及日常维护、升级管理,保证内外系统稳定运行;\\\\n4.建立规范、高效的各种研发部门流程(如产品开发流程、产品发布流程等)及过程的质量控制;\\\\n5. 负责重点项目管理,进度跟进,日常协调及成员日常工作分配及代码review;\\\\n6.参与公司系统架构设计及核心代码编写;\\\\n项目职责:负责平台整体架构设计,核心模块详细设计,技术难点攻关,接口性能调优,缓存优化\\\\n主要技术:jira,sonar, jenkens,nginx ,tomcat ,java ,memcache,redis,rabitmq,dubbo\\\\n\",\"company\":{\"companyScale\":\"1\",\"companyName\":\"江苏钱旺集团上海分公司\",\"companyIndustry\":\"互联网/电子商务\",\"companyProperty\":0},\"job\":\"teamleader\",\"type\":0,\"startDate\":\"2016-03-01\"},{\"endUntilNow\":0,\"endDate\":\"2016-03-01\",\"description\":\"负责公司整个it相关的工作,并负责公司短信平台的整体架构设计及核心代码的编写。\\\\n短信平台的特点:大并发,大数据量,安全性高,速度快。\\\\n系统所采用的技术为前端:spring,sitemesh,mybatis,jstl等\\\\n后端采用:mina.netty,memcached(缓存),sqs4j(队列),ecache,多线程等及bloomfilter算法等。\\\\n对应用服务器的管理,集群搭建,熟悉linux服务器管理\\\\n项目职责:负责基础平台整体架构设计,核心模块详细设计,技术难点攻关,接口性能调优,分库分表支持,缓存优化,微信端开发等\\\\n主要技术:nginx ,tomcat ,java ,memcache,redis,ecache,sqs4j\\\\n关键指标:配置服务支持容量千万级,单机qps支持10万;对sqs4j队列进行二开发,以提升队列处理性能,1秒钟处理1w条件数据。\\\\n\",\"company\":{\"companyScale\":\"3\",\"companyName\":\"上海助通信息科技有限公司\",\"companyProperty\":0},\"job\":\"技术总监\",\"type\":0,\"startDate\":\"2012-08-01\"},{\"endUntilNow\":0,\"endDate\":\"2012-08-01\",\"description\":\"工作描述:开发过电信相关的项目。\\\\n运用了struts,spring,jsp,tomcat,jasperreport报表等技术!\\\\n还运用了动态数据源及fusioncharts的报表工具!\\\\n做过金融行业的积分卡相关项目。\\\\n\",\"company\":{\"companyScale\":\"5\",\"companyName\":\"道晨胜咨询(中国)管理有限公司上海分公司\",\"companyIndustry\":\"计算机软件\",\"companyProperty\":0},\"job\":\"高级java开发工程师\",\"type\":0,\"startDate\":\"2009-08-01\"},{\"endUntilNow\":0,\"endDate\":\"2009-08-01\",\"description\":\"工作描述:1、开发公司的餐饮待业产品的软件开发工作,及设计产品的规划。\\\\n2、主要负责产品的前期设计和编码实现及后期的测试和部署后的客房服务。\\\\n3、产品主要运用的技术是:java,eclipse,spring技术,hibernate,freemarker模板技术及dwr,sql server数据库。\\\\n\",\"company\":{\"companyScale\":\"3\",\"companyName\":\"上海石川科技有限公司\",\"companyIndustry\":\"it服务(系统/数据/维护)\",\"companyProperty\":0},\"job\":\"中级java开发\",\"type\":0,\"startDate\":\"2006-08-01\"},{\"endUntilNow\":0,\"endDate\":\"2006-08-01\",\"description\":\"工作描述:1、运用java技术开发软件项目模块\\\\n2、软件售后维护和产品开发\\\\n\",\"company\":{\"companyScale\":\"3\",\"companyName\":\"上海远瞩计算机信息科技有限公司\",\"companyIndustry\":\"计算机软件\",\"companyProperty\":0},\"job\":\"软件开发工程师\",\"type\":0,\"startDate\":\"2005-08-01\"}],\"user\":{\"name\":\"曾志香\",\"mobile\":\"13472883437\",\"source\":105}}";
        Map<String, Object> paramMap = JSON.parseObject(EmojiFilter.filterEmoji1(EmojiFilter.unicodeToUtf8(profile)));
        System.out.println(paramMap);
    }
}
