package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.dao.logdb.LogResumeDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileSaveResult;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserReferralRecordDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import static com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment.PROFILE_ATTACHMENT;
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
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
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
import java.util.*;

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

    @Autowired
    private SensorSend sensorSend;

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
        int profileId= mergeProfileCommon(profilePojo, profileDB);
        if (profileDB != null) {
            improveAttachment(profilePojo.getAttachmentRecords(), profileDB.getId());
            completenessImpl.reCalculateProfileBasic(profileDB.getId());
            profileId = profileDB.getId();
        }
        return profileId;
    }

    public int mergeProfileReferral(ProfilePojo profilePojo, int userId, int attachmentId) {

        ProfileProfileRecord profileDB = profileDao.getProfileOrderByActiveByUserId(userId);
        int profileId= mergeProfileCommon(profilePojo, profileDB);
        if (profileDB != null) {
            int id = improveAttachmentReferral(profilePojo.getAttachmentRecords(), attachmentId, profileId);
            completenessImpl.reCalculateProfileBasic(profileId);
            attachmentId = id;
        }
        return attachmentId;
    }

    /**
     * 如果存在简历则合并，不存在则添加
     * @param profilePojo 简历数据
     * @param profileDB 用户编号
     */
    public int mergeProfileCommon(ProfilePojo profilePojo, ProfileProfileRecord profileDB) {

        if (profileDB != null) {
            improveProfile(profilePojo.getProfileRecord(), profileDB);
            improveBasic(profilePojo.getBasicRecord(), profileDB.getId());
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
        logger.info("intentionRecords:{}", intentionRecords);
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
    @Transactional
    public int  improveAttachmentReferral(List<ProfileAttachmentRecord> attachmentRecords, int attachmentId, int profileId) {
        if (attachmentRecords != null && attachmentRecords.size() > 0) {
            ProfileAttachmentRecord record = attachmentDao.fetchAttachmentById(attachmentId);
            ProfileAttachmentRecord atta = attachmentRecords.get(attachmentRecords.size()-1);
            if(record != null){
                atta.setId(record.getId());
                atta.setProfileId(record.getProfileId());
                attachmentDao.updateRecord(attachmentRecords.get(attachmentRecords.size()-1));
            }else{
                atta.setProfileId(profileId);
                return attachmentDao.addRecord(atta).getId();
            }
        }
        return attachmentId;
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

    public ProfileAttachmentDO getProfileAttachmentByProfileId(int profileId){
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(PROFILE_ATTACHMENT.PROFILE_ID.getName(), profileId);
        return attachmentDao.getData(queryBuilder.buildQuery());
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
        ProfileSaveResult result = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                userUserRecord, null);
        String distinctId = profilePojo.getProfileRecord().getUserId().toString();
        String property=String.valueOf(profilePojo.getProfileRecord().getCompleteness());
        logger.info("ProfileEntity.createProfile661  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
        sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
        return result.getProfileRecord().getId();
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
        ProfileSaveResult result= profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                profilePojo.getUserRecord(), null);
        String distinctId = profilePojo.getUserRecord().getId().toString();
        String property=String.valueOf(profilePojo.getProfileRecord().getCompleteness());
        logger.info("ProfileEntity.storeProfile684  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
        sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
        return result.getProfileRecord().getId();
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

    public ProfileSaveResult storeReferralUser(ProfilePojo profilePojo, int reference, int companyId, ReferralScene referralScene) throws ProfileException {

        UserSource userSource = referralScene.getScene() == ReferralScene.Referral.getScene() ? UserSource.EMPLOYEE_REFERRAL : UserSource.EMPLOYEE_REFERRAL_CHATBOT;
        UserReferralRecordRecord referralRecordRecord  = userReferralRecordDao.insertReferralTypeIfNotExist(reference,
                companyId, profilePojo.getUserRecord().getMobile(),
                referralScene, userSource);
        if (referralRecordRecord != null) {
            ProfileSaveResult profileSaveResult = storeUserRecordForReferral(profilePojo, userSource,null,null,null);
            if (profileSaveResult != null && profileSaveResult.getProfileRecord() != null) {
                referralRecordRecord.setUserId(profileSaveResult.getProfileRecord().getUserId());
                userReferralRecordDao.updateRecord(referralRecordRecord);
            }
            return profileSaveResult;
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
        profilePojo.getUserRecord().setEmailVerified((byte)0);
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



    private ProfileSaveResult storeUserRecordForReferral(ProfilePojo profilePojo, UserSource source,Integer appid,Integer referenceId,Integer companyId) {
        if (org.apache.commons.lang.StringUtils.isBlank(profilePojo.getUserRecord().getPassword())) {
            profilePojo.getUserRecord().setPassword("");
        }
        short shortSource = 0;
        if (source != null) {
            shortSource = (short) source.getValue();
        }
        profilePojo.getUserRecord().setSource(shortSource);
        profilePojo.getUserRecord().setEmailVerified((byte)0);
        UserUserRecord userUserRecord = userDao.addRecord(profilePojo.getUserRecord());

        logger.info("mergeProfile userId:{}", userUserRecord.getId());
        profilePojo.setUserRecord(userUserRecord);
        profilePojo.getProfileRecord().setUserId(userUserRecord.getId());
        ProfileSaveResult result= profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                profilePojo.getUserRecord(), null);
        String distinctId = profilePojo.getUserRecord().getId().toString();
        String property=String.valueOf(result.getProfileRecord() != null ? result.getProfileRecord().getCompleteness().intValue():0);
        sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
        if(appid == EmployeeOperationEntrance.IMEMPLOYEE.getKey()){
            logEmployeeOperationLogEntity.insertEmployeeOperationLog(referenceId, appid,
                    EmployeeOperationType.RESUMERECOMMEND.getKey(),
                    EmployeeOperationIsSuccess.SUCCESS.getKey(),
                    companyId,
                    result.getProfileRecord().getId());
        }

        return result;
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
}
