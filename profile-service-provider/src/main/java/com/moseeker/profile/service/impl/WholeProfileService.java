package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.OrderBy;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.SensorSend;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfileExtParam;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.constants.StatisticsForChannelmportVO;
import com.moseeker.profile.service.impl.retriveprofile.RetriveProfile;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.utils.ConstellationUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Future;


@Service
@CounterIface
public class WholeProfileService {

    Logger logger = LoggerFactory.getLogger(WholeProfileService.class);
    ProfileExtUtils profileUtils = new ProfileExtUtils();

    ThreadPool pool = ThreadPool.Instance;
    @Autowired
    ProfileEntity profileEntity;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private TalentPoolEntity talentPoolEntity;

    @Autowired
    private DictIndustryDao dictIndustryDao;

    @Autowired
    private DictPositionDao dictPositionDao;

    @Autowired
    private DictCityDao dictCityDao;

    @Autowired
    private UserWxUserDao wxuserDao;

    @Autowired
    private DictConstantDao constantDao;

    @Autowired
    private ProfileOtherDao customizeResumeDao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private UserSettingsDao userSettingsDao;

    @Autowired
    private ProfileIntentionCityDao intentionCityDao;

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private ProfileIntentionPositionDao intentionPositionDao;

    @Autowired
    private ProfileIntentionIndustryDao intentionIndustryDao;

    @Autowired
    private ProfileAwardsDao awardsDao;

    @Autowired
    private DictCollegeDao collegeDao;

    @Autowired
    private ProfileCredentialsDao credentialsDao;

    @Autowired
    private DictCountryDao countryDao;

    @Autowired
    private UserUserDao userDao;

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
    RetriveProfile retriveProfile;

    @Autowired
    ProfileParseUtil profileParseUtil;

    @Autowired
    private SensorSend sensorSend;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICEMANAGER.getService(UseraccountsServices.Iface.class);

    TalentpoolServices.Iface talentpoolService = ServiceManager.SERVICEMANAGER.getService(TalentpoolServices.Iface.class);

    private Query getProfileQuery(int profileId){
        return new Query.QueryBuilder().where("profile_id",profileId).setPageSize(Integer.MAX_VALUE).buildQuery();
    }

    public Response getResource(int userId, int profileId, String uuid) throws Exception {
        //logger.info("WholeProfileService getResource start : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));
        Response response = new Response();
        HashMap<String, Object> profile = new HashMap<String, Object>();

        ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, profileId, uuid);

        //logger.debug("WholeProfileService getResource after  profileDao.getProfileByIdOrUserIdOrUUID: {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

        if (profileRecord != null) {
            if (profileRecord.getCompleteness().intValue() == 0
                    || profileRecord.getCompleteness().intValue() == 10) {
                int completeness = profileEntity.getCompleteness(profileRecord.getUserId().intValue(),
                        profileRecord.getUuid(), profileRecord.getId().intValue());
                profileRecord.setCompleteness((byte) (completeness));
            }

            //logger.debug("WholeProfileService getResource before  constantDao.getCitiesByParentCodes : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<DictConstantRecord> constantRecords = constantDao
                    .getCitiesByParentCodes(Arrays.asList(3109, 3105, 3102, 2105, 3120, 3115, 3114, 3119, 3120, 1102, 1103));

            //logger.debug("WholeProfileService getResource after constantDao.getCitiesByParentCodes : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            Map<String, Object> profileprofile = buildProfile(profileRecord, getProfileQuery(profileRecord.getId()), constantRecords);
            profile.put("profile", profileprofile);

            //logger.debug("WholeProfileService getResource after buildProfile : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            Future<Map<String, Object>> basicFuture = pool.startTast(() -> buildBasic(profileRecord, getProfileQuery(profileRecord.getId()), constantRecords));
            Future<List<Map<String, Object>>> workexpsFuture = pool.startTast(() -> buildWorkexps(profileRecord, getProfileQuery(profileRecord.getId()), constantRecords));
            Future<List<Map<String, Object>>> educationsFuture = pool.startTast(() -> buildEducations(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> projectexpsFuture = pool.startTast(() -> buildProjectexps(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> buildLanguageFuture = pool.startTast(() -> buildLanguage(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> buildskillsFuture = pool.startTast(() -> buildskills(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> buildsCredentialsFuture = pool.startTast(() -> buildsCredentials(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> buildsAwardsFuture = pool.startTast(() -> buildsAwards(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> buildsWorksFuture = pool.startTast(() -> buildsWorks(profileRecord, getProfileQuery(profileRecord.getId())));
            Future<List<Map<String, Object>>> intentionsFuture = pool.startTast(() -> profileUtils.buildsIntentions(profileRecord, getProfileQuery(profileRecord.getId()),
                    constantRecords, intentionDao, intentionCityDao, intentionIndustryDao, intentionPositionDao,
                    dictCityDao, dictIndustryDao, dictPositionDao));
            Future<List<ProfileAttachmentRecord>> attachmentRecordsFuture = pool.startTast(() -> attachmentDao.getRecords(getProfileQuery(profileRecord.getId())));
            Future<List<ProfileImportRecord>> importRecordsFuture = pool.startTast(() -> profileImportDao.getRecords(getProfileQuery(profileRecord.getId())));
            Future<List<ProfileOtherRecord>> otherRecordsFuture = pool.startTast(() -> customizeResumeDao.getRecords(getProfileQuery(profileRecord.getId())));

            Map<String, Object> basic = basicFuture.get();
            String countryCode = "";
            if (profileRecord.getUserId().intValue() != 0) {
                countryCode = org.apache.commons.lang.StringUtils.defaultIfBlank(userDao.getUser(profileRecord.getUserId().intValue()).getCountryCode(), "");
            } else if (userId != 0) {
                countryCode = org.apache.commons.lang.StringUtils.defaultIfBlank(userDao.getUser(userId).getCountryCode(), "");
            }
            basic.put("country_code", countryCode);
            profile.put("basic", basic);

            //logger.debug("WholeProfileService getResource basicFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> workexps = workexpsFuture.get();
            profile.put("workexps", workexps);

            //logger.debug("WholeProfileService getResource workexpsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> educations = educationsFuture.get();
            profile.put("educations", educations);

            //logger.debug("WholeProfileService getResource educationsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> projectexps = projectexpsFuture.get();
            profile.put("projectexps", projectexps);

            //logger.debug("WholeProfileService getResource projectexpsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> languages = buildLanguageFuture.get();
            profile.put("languages", languages);

            //logger.debug("WholeProfileService getResource buildLanguageFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> skills = buildskillsFuture.get();
            profile.put("skills", skills);

            //logger.debug("WholeProfileService getResource buildskillsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> credentials = buildsCredentialsFuture.get();
            profile.put("credentials", credentials);

            //logger.debug("WholeProfileService getResource buildsCredentialsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> awards = buildsAwardsFuture.get();
            profile.put("awards", awards);

            //logger.debug("WholeProfileService getResource buildsAwardsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> works = buildsWorksFuture.get();
            profile.put("works", works);

            //logger.debug("WholeProfileService getResource buildsWorksFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> intentions = intentionsFuture.get();
            profile.put("intentions", intentions);

            //logger.debug("WholeProfileService getResource intentionsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<ProfileAttachmentRecord> attachmentRecords = attachmentRecordsFuture.get();
            List<Map<String, Object>> attachments = profileUtils.buildAttachments(profileRecord, attachmentRecords);
            profile.put("attachments", attachments);

            //logger.debug("WholeProfileService getResource attachmentRecordsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<ProfileImportRecord> importRecords = importRecordsFuture.get();
            List<Map<String, Object>> imports = profileUtils.buildImports(profileRecord, importRecords);
            profile.put("imports", imports);

            //logger.debug("WholeProfileService getResource importRecordsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<ProfileOtherRecord> otherRecords = otherRecordsFuture.get();
            profileParseUtil.handerSortOtherList(otherRecords);
            List<Map<String, Object>> others = profileUtils.buildOthers(profileRecord, otherRecords);

            //logger.info("WholeProfileService getResource done : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            profile.put("others", others);

            return ResponseUtils.success(profile);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }





    @SuppressWarnings("unchecked")
    @Transactional
    public Response postResource(String profile, int userId) throws TException {
        if (!StringUtils.isNullOrEmpty(profile)) {
            Map<String, Object> resume = JSON.parseObject(profile);

            ProfileExtParam extParam = profileParseUtil.initParseProfileParam();

            ProfileProfileRecord profileRecord = profileUtils
                    .mapToProfileRecord((Map<String, Object>) resume.get("profile"));
            UserUserRecord userRecord = userDao.getUserById(userId);
            if (profileRecord == null) {
                profileRecord = new ProfileProfileRecord();
            }
            if (userRecord == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
            }
            profileRecord.setUuid(UUID.randomUUID().toString());
            profileRecord.setUserId(userRecord.getId());
            profileRecord.setDisable((byte) (Constant.ENABLE));

            ProfileProfileRecord repeatProfileRecord = profileDao
                    .getProfileByIdOrUserIdOrUUID(profileRecord.getUserId().intValue(), 0, null);
            if (repeatProfileRecord != null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ALLREADY_EXIST);
            }
            UserUserRecord crawlerUser = null;
            try {
                crawlerUser = profileUtils.mapToUserUserRecord((Map<String, Object>) resume.get("user"));
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
            if ((userRecord.getMobile() == null || userRecord.getMobile() == 0) && crawlerUser != null
                    && crawlerUser.getMobile() != null) {
                userRecord.setMobile(crawlerUser.getMobile());
            }
            if (StringUtils.isNullOrEmpty(userRecord.getName()) && crawlerUser != null
                    && !StringUtils.isNullOrEmpty(crawlerUser.getName())) {
                userRecord.setName(crawlerUser.getName());
            }
            if (StringUtils.isNullOrEmpty(userRecord.getHeadimg()) && crawlerUser != null
                    && !StringUtils.isNullOrEmpty(crawlerUser.getHeadimg())) {
                userRecord.setHeadimg(crawlerUser.getHeadimg());
            }
            if (StringUtils.isNullOrEmpty(userRecord.getEmail()) && crawlerUser != null
                    && !StringUtils.isNullOrEmpty(crawlerUser.getEmail())) {
                userRecord.setEmail(crawlerUser.getEmail());
            }
            ProfileBasicRecord basicRecord = null;
            try {
                basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"), extParam);
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
            if (StringUtils.isNullOrEmpty(userRecord.getName()) && basicRecord != null
                    && !StringUtils.isNullOrEmpty(basicRecord.getName())) {
                userRecord.setName(basicRecord.getName());
            }
            List<ProfileAttachmentRecord> attachmentRecords = null;
            try {
                attachmentRecords = profileUtils
                        .mapToAttachmentRecords((List<Map<String, Object>>) resume.get("attachments"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileAwardsRecord> awardsRecords = null;
            try {
                awardsRecords = profileUtils.mapToAwardsRecords((List<Map<String, Object>>) resume.get("awards"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileCredentialsRecord> credentialsRecords = null;
            try {
                credentialsRecords = profileUtils
                        .mapToCredentialsRecords((List<Map<String, Object>>) resume.get("credentials"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileEducationRecord> educationRecords = null;
            try {
                educationRecords = profileUtils
                        .mapToEducationRecords((List<Map<String, Object>>) resume.get("educations"), extParam);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            ProfileImportRecord importRecords = null;
            try {
                importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<IntentionRecord> intentionRecords = null;
            try {
                intentionRecords = profileUtils
                        .mapToIntentionRecord((List<Map<String, Object>>) resume.get("intentions"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileLanguageRecord> languages = null;
            try {
                languages = profileUtils.mapToLanguageRecord((List<Map<String, Object>>) resume.get("languages"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            ProfileOtherRecord otherRecord = null;
            try {
                otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"), extParam);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileProjectexpRecord> projectExps = null;
            try {
                projectExps = profileUtils
                        .mapToProjectExpsRecords((List<Map<String, Object>>) resume.get("projectexps"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileSkillRecord> skillRecords = null;
            try {
                skillRecords = profileUtils.mapToSkillRecords((List<Map<String, Object>>) resume.get("skills"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileWorkexpEntity> workexpRecords = null;
            try {
                workexpRecords = profileUtils.mapToWorkexpRecords((List<Map<String, Object>>) resume.get("workexps"),
                        profileRecord.getSource().intValue());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            List<ProfileWorksRecord> worksRecords = null;
            try {
                worksRecords = profileUtils.mapToWorksRecords((List<Map<String, Object>>) resume.get("works"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            int id = profileDao.saveProfile(profileRecord, basicRecord, attachmentRecords, awardsRecords,
                    credentialsRecords, educationRecords, importRecords, intentionRecords, languages, otherRecord,
                    projectExps, skillRecords, workexpRecords, worksRecords, userRecord);
            if (id > 0) {
                try {
                    StatisticsForChannelmportVO statisticsForChannelmportVO = createStaticstics(id, userId, (byte) 0, importRecords);
                    profileUtils.logForStatistics("postResource", new JSONObject() {{
                        this.put("profile", profile);
                        this.put("userId", userId);
                    }}.toJSONString(), statisticsForChannelmportVO);
                    String distinctId = profileRecord.getUserId().toString();
                    String property=String.valueOf(profileRecord.getCompleteness());
                    logger.info("WholeProfileService.postResource483  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
                    sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                profileCompanyTagService.handlerCompanyTag(0,userId);

                return ResponseUtils.success(String.valueOf(id));
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public Response importCV(String profile, int userId) throws TException {

        logger.info("importCV profile:" + profile);
        Map<String, Object> resume = JSON.parseObject(profile);
        logger.info("resume:" + resume);
        ProfileProfileRecord profileRecord = profileUtils
                .mapToProfileRecord((Map<String, Object>) resume.get("profile"));
        if (profileRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
        }
        UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
        if (userRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }
        logger.info("importCV user_id:" + userRecord.getId());
        // ProfileProfileRecord profileRecord =
        // profileUtils.mapToProfileRecord((Map<String, Object>)
        // resume.get("user_user"));
        List<ProfileProfileRecord> oldProfile = profileDao.getProfilesByIdOrUserIdOrUUID(userId, 0, null);

        if (oldProfile != null && oldProfile.size() > 0 && StringUtils.isNotNullOrEmpty(oldProfile.get(0).getUuid())) {
            logger.info("importCV oldProfile:" + oldProfile.get(0).getId());
            profileRecord.setUuid(oldProfile.get(0).getUuid());
        } else {
            profileRecord.setUuid(UUID.randomUUID().toString());
        }
        ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord, profileParseUtil.initParseProfileParam());

        logger.info("开始 importCV");
        int id=0;
        try {
            id = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                    profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                    profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                    profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                    profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                    userRecord, oldProfile);
        }catch (Exception e){
          logger.error(e.getMessage(),e);
        };
        if (id > 0) {
            logger.info("importCV 添加成功");
            try {
                StatisticsForChannelmportVO statisticsForChannelmportVO = createStaticstics(id, userId, (byte) 1,
                        profilePojo.getImportRecords());
                profileUtils.logForStatistics("importCV", new JSONObject() {{
                    this.put("profile", profile);
                    this.put("userId", userId);
                }}.toJSONString(), statisticsForChannelmportVO);
                String distinctId = profilePojo.getUserRecord().getId().toString();
                String property=String.valueOf(profilePojo.getProfileRecord().getCompleteness());
                logger.info("WholeProfileService.importCV543  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
                sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            profileCompanyTagService.handlerCompanyTag(id,userId);
            return ResponseUtils.success(id);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
        }
    }

    /**
     * 创建简历
     *
     * @param profile 简历json格式的数据
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public Response createProfile(String profile) {
        Map<String, Object> resume = JSON.parseObject(profile);
        ProfileProfileRecord profileRecord = profileUtils
                .mapToProfileRecord((Map<String, Object>) resume.get("profile"));
        if (profileRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
        }
        UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
        int id=this.createProfileItem(userRecord,resume,profile);
        if(id==-1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }else if(id==0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
        }else{
            profileCompanyTagService.handlerCompanyTag(id,userRecord.getId());
            return ResponseUtils.success(id);
        }
    }

    private int createProfileItem(UserUserRecord userRecord,Map<String, Object> resume,String profile){
        if (userRecord == null) {
            return -1;
        }
        ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord, profileParseUtil.initParseProfileParam());

        int id = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                userRecord, null);
        if (id > 0) {

            try {
                StatisticsForChannelmportVO statisticsForChannelmportVO = createStaticstics(id,
                        userRecord.getId().intValue(), (byte) 0, profilePojo.getImportRecords());
                profileUtils.logForStatistics("importCV", new JSONObject() {{
                    this.put("profile", profile);
                }}.toJSONString(), statisticsForChannelmportVO);
                String distinctId = profilePojo.getUserRecord().getId().toString();
                String property=String.valueOf(profilePojo.getProfileRecord().getCompleteness());
                logger.info("WholeProfileService.createProfileItem611  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
                sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return id;
        } else {
            return 0;
        }
    }

    // 完善简历
    @SuppressWarnings("unchecked")
    @Transactional
    public Response improveProfile(String profile) {
        Map<String, Object> resume = JSON.parseObject(profile);
        ProfileProfileRecord profileRecord = profileUtils
                .mapToProfileRecord((Map<String, Object>) resume.get("profile"));
        if (profileRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
        }
        UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
        if (userRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }
        ProfileProfileRecord profileDB = profileDao.getProfileByIdOrUserIdOrUUID(userRecord.getId().intValue(), 0, null);

        if (profileDB != null) {
            ((Map<String, Object>) resume.get("profile")).put("origin", profileDB.getOrigin());
            ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord, profileParseUtil.initParseProfileParam());
            int profileId = profileDB.getId().intValue();
            profileEntity.improveUser(userRecord);
            profileEntity.improveProfile(profilePojo.getProfileRecord(), profileDB);
            profileEntity.improveBasic(profilePojo.getBasicRecord(), profileId);
            profileEntity.improveAttachment(profilePojo.getAttachmentRecords(), profileId);
            profileEntity.improveAwards(profilePojo.getAwardsRecords(), profileId);
            profileEntity.improveCredentials(profilePojo.getCredentialsRecords(), profileId);
            profileEntity.improveEducation(profilePojo.getEducationRecords(), profileId);
            profileEntity.improveIntention(profilePojo.getIntentionRecords(), profileId);
            profileEntity.improveLanguage(profilePojo.getLanguageRecords(), profileId);
            profileEntity.improveOther(profilePojo.getOtherRecord(), profileId);
            profileEntity.improveProjectexp(profilePojo.getProjectExps(), profileId);
            profileEntity.improveSkill(profilePojo.getSkillRecords(), profileId);
            profileEntity.improveWorkexp(profilePojo.getWorkexpRecords(), profileId);
            profileEntity.improveWorks(profilePojo.getWorksRecords(), profileId);
//            profileEntity.getCompleteness(0, null, profileId);
            profileEntity.reCalculateProfileCompleteness(profileId);

            try {
                StatisticsForChannelmportVO statisticsForChannelmportVO = createStaticstics(profileDB.getId().intValue(), profileDB.getUserId().intValue(), (byte) 2,
                        profilePojo.getImportRecords());
                profileUtils.logForStatistics("importCV", new JSONObject() {{
                    this.put("profile", profile);
                }}.toJSONString(), statisticsForChannelmportVO);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            profileCompanyTagService.handlerCompanyTag(profileId,userRecord.getId());
            return ResponseUtils.success(null);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ALLREADY_NOT_EXIST);
        }
    }

    @Transactional
    public Response improveProfile(int destUserId, int originUserId) {
        ProfileProfileRecord destProfile = profileDao.getProfileByIdOrUserIdOrUUID(destUserId, 0, null);
        ProfileProfileRecord originProfile = profileDao.getProfileByIdOrUserIdOrUUID(originUserId, 0, null);
        try {
            if (originProfile == null && destProfile != null && userDao.getUserById(originUserId) != null) {
                destProfile.setUserId((int) (originUserId));
                profileDao.updateRecord(destProfile);
            }
            if (originProfile != null && destProfile != null) {
                QueryUtil queryUtil = new QueryUtil();
                HashMap<String, String> eqs = new HashMap<String, String>();
                eqs.put("profile_id", String.valueOf(destProfile.getId()));
                queryUtil.setEqualFilter(eqs);
                queryUtil.setPageSize(Integer.MAX_VALUE);
                // dest 简历信息查询
                ProfileBasicRecord destRecord = profileBasicDao.getRecord(queryUtil);
                List<ProfileAttachmentRecord> destAttachments = attachmentDao.getRecords(queryUtil);
                List<ProfileAwardsRecord> destAwards = awardsDao.getRecords(queryUtil);
                List<ProfileCredentialsRecord> destCredentials = credentialsDao.getRecords(queryUtil);
                List<ProfileEducationRecord> destEducations = educationDao.getRecords(queryUtil);
                List<IntentionRecord> destIntentions = new ArrayList<IntentionRecord>();
                intentionDao.getRecords(queryUtil).forEach(intention -> {
                    IntentionRecord irecodr = new IntentionRecord(intention);
                    Query query = new Query.QueryBuilder().where("profile_intention_id",intention.getId()).buildQuery();
                    try {
                        irecodr.setCities(intentionCityDao.getRecords(query));
                        irecodr.setPositions(intentionPositionDao.getRecords(query));
                        irecodr.setIndustries(intentionIndustryDao.getRecords(query));
                        destIntentions.add(irecodr);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                });
                List<ProfileLanguageRecord> destLanguages = languageDao.getRecords(queryUtil);
                ProfileOtherRecord destOther = otherDao.getRecord(queryUtil);
                List<ProfileProjectexpRecord> destProjectexps = projectExpDao.getRecords(queryUtil);
                List<ProfileSkillRecord> destSkills = skillDao.getRecords(queryUtil);
                List<ProfileWorkexpEntity> destWorkxps = new ArrayList<>();
                workExpDao.getRecords(queryUtil).forEach(workexp -> {
                    ProfileWorkexpEntity workexpEntity = new ProfileWorkexpEntity(workexp);
                    destWorkxps.add(workexpEntity);
                });
                List<ProfileWorksRecord> destWorks = worksDao.getRecords(queryUtil);
                int originProfileId = originProfile.getId().intValue();
                profileEntity.improveBasic(destRecord, originProfileId);
                profileEntity.improveAttachment(destAttachments, originProfileId);
                profileEntity.improveAwards(destAwards, originProfileId);
                profileEntity.improveCredentials(destCredentials, originProfileId);
                profileEntity.improveEducation(destEducations, originProfileId);
                profileEntity.improveIntention(destIntentions, originProfileId);
                profileEntity.improveLanguage(destLanguages, originProfileId);
                profileEntity.mergeOther(destOther, originProfileId);
                profileEntity.improveProjectexp(destProjectexps, originProfileId);
                profileEntity.improveSkill(destSkills, originProfileId);
                profileEntity.improveWorks(destWorks, originProfileId);
                profileEntity.improveWorkexp(destWorkxps, originProfileId);
                profileEntity.getCompleteness(0, null, originProfile.getId().intValue());

            }
            profileCompanyTagService.handlerCompanyTag(0,destUserId);
            profileCompanyTagService.handlerCompanyTag(0,originUserId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.success(null);
    }

    /**
     * 简历回收
     * @param parameter 参数
     * @return 执行结果
     * @throws CommonException 业务异常
     */
    @CounterIface
    public boolean retrieveProfile(String parameter) throws CommonException {
        return retriveProfile.retrieve(parameter);
    }

    private static StatisticsForChannelmportVO createStaticstics(int profileId, int userId, byte operation, ProfileImportRecord record) {
        StatisticsForChannelmportVO statisticsForChannelmportVO = new StatisticsForChannelmportVO();
        statisticsForChannelmportVO.setProfile_operation((byte) 0);
        statisticsForChannelmportVO.setProfile_id(profileId);
        statisticsForChannelmportVO.setUser_id(userId);
        if (record != null) {
            if (record.getCreateTime() != null) {
                statisticsForChannelmportVO.setImport_time(record.getCreateTime().getTime());
            }
            if (record.getSource() != null) {
                statisticsForChannelmportVO.setImport_channel(record.getSource().byteValue());
            }
        }
        return statisticsForChannelmportVO;
    }

    public Response verifyRequires(int userId, int positionId) throws TException {
        UserUserRecord userRecord = userDao.getUserById(userId);
        JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);
        if (userRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }
        if (positionRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
        }
        if (positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
            return ResponseUtils.success(true);
        } else {
            return ResponseUtils.success(false);
        }
    }

    public JobPositionDO getPositionById(int accountId, int positionId) throws TException {
        Query query = new Query.QueryBuilder().where(JobPosition.JOB_POSITION.PUBLISHER.getName(), accountId)
                .and(JobPosition.JOB_POSITION.ID.getName(), positionId).buildQuery();
        JobPositionDO positionDO = jobPositionDao.getData(query);
        return positionDO;
    }

    public JobApplicationDO getApplicationByposition(int applier_id, int positionId) throws TException {
        Query query = new Query.QueryBuilder().where(JobApplication.JOB_APPLICATION.POSITION_ID.getName(), positionId)
                .and(JobApplication.JOB_APPLICATION.APPLIER_ID.getName(), applier_id).buildQuery();
        JobApplicationDO positionDO = jobApplicationDao.getData(query);
        return positionDO;
    }

    private List<Map<String, Object>> buildsWorks(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<ProfileWorksRecord> records = worksDao.getRecords(query);
        if (records != null && records.size() > 0) {
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("name", record.getName());
                map.put("cover", record.getCover());
                map.put("url", record.getUrl());
                map.put("description", record.getDescription());
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildsAwards(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<ProfileAwardsRecord> records = awardsDao.getRecords(query);
        if (records != null && records.size() > 0) {
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("name", record.getName());
                if (record.getRewardDate() != null) {
                    map.put("reward_date", DateUtils.dateToNormalDate(record.getRewardDate()));
                }
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildsCredentials(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<ProfileCredentialsRecord> records = credentialsDao.getRecords(query);
        if (records != null && records.size() > 0) {
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("name", record.getName());
                map.put("organization", record.getOrganization());
                map.put("code", record.getCode());
                map.put("url", record.getUrl());
                if (record.getGetDate() != null) {
                    map.put("get_date", DateUtils.dateToNormalDate(record.getGetDate()));
                }
                map.put("score", record.getScore());
                if (record.getCreateTime() != null) {
                    map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
                }
                if (record.getUpdateTime() != null) {
                    map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
                }
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildskills(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<ProfileSkillRecord> records = skillDao.getRecords(query);
        if (records != null && records.size() > 0) {
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("name", record.getName());
                map.put("level", record.getLevel().intValue());
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildLanguage(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<ProfileLanguageRecord> records = languageDao.getRecords(query);
        if (records != null && records.size() > 0) {
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("name", record.getName());
                map.put("level", record.getLevel().intValue());
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildProjectexps(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 按照结束时间倒序
        query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
        query.getOrders().add(new OrderBy("start", Order.DESC));
        List<ProfileProjectexpRecord> records = projectExpDao.getRecords(query);
        if (records != null && records.size() > 0) {
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("name", record.getName());
                map.put("company_name", record.getCompanyName());
                if (record.getStart() != null) {
                    map.put("start_date", DateUtils.dateToNormalDate(record.getStart()));
                }
                if (record.getEnd() != null) {
                    map.put("end_date", DateUtils.dateToNormalDate(record.getEnd()));
                }
                map.put("end_until_now", record.getEndUntilNow().intValue());
                map.put("description", record.getDescription());
                map.put("achievement", record.getAchievement());
                map.put("responsibility",record.getResponsibility());
                map.put("member", record.getMember());
                map.put("role", record.getRole());
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildEducations(ProfileProfileRecord profileRecord, Query query) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 按照结束时间倒序
        query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
        query.getOrders().add(new OrderBy("start", Order.DESC));
        List<ProfileEducationRecord> records = educationDao.getRecords(query);

        if (records != null && records.size() > 0) {
            List<Integer> collegeCodes = new ArrayList<>();
            records.forEach(record -> {
                collegeCodes.add(record.getCollegeCode());
            });
            List<DictCollegeDO> collegeRecords = collegeDao.getCollegesByIDs(collegeCodes);
            List<DictCountryDO> countryDOList = countryDao.getAll();
            List<Integer> parentCodes = new ArrayList<>();
            parentCodes.add(Constant.DICT_CONSTANT_DEGREE_USER);
            List<DictConstantRecord> constantDOS = constantDao.getCitiesByParentCodes(parentCodes);
            records.forEach(record -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", record.getId().intValue());
                map.put("college_name", record.getCollegeName());
                map.put("college_code", record.getCollegeCode());
                map.put("college_logo", record.getCollegeLogo());
                    /* 如果college_code合法，有限选择字典里的图片 */
                if (record.getCollegeCode() != null && record.getCollegeCode().intValue() > 0
                        && collegeRecords.size() > 0) {
                    for (DictCollegeDO collegeRecord : collegeRecords) {
                        if (collegeRecord.getCode() == record.getCollegeCode().intValue()
                                && !StringUtils.isNullOrEmpty(collegeRecord.getLogo())) {
                            map.put("college_logo", collegeRecord.getLogo());
                            map.put("college_name", collegeRecord.getName());
                            break;
                        }
                    }
                }
                if(!StringUtils.isEmptyList(countryDOList)){
                    for(DictCountryDO country : countryDOList){
                        if(record.getCountryId().intValue() == country.getId()){
                            map.put("country_id", record.getCountryId().intValue());
                            map.put("country_name", country.getName());
                            break;
                        }else if(record.getCountryId().intValue() == Constant.HKAMTW){
                            map.put("country_id", record.getCountryId().intValue());
                            map.put("country_name", "港澳台地区");
                            break;
                        }
                    }
                }
                map.put("major_name", record.getMajorName());
                map.put("major_code", record.getMajorCode());
                map.put("degree", record.getDegree().intValue());
                if(!StringUtils.isEmptyList(constantDOS)){
                    for(DictConstantRecord record1 : constantDOS) {
                        if (record.getDegree().intValue() == record1.getCode()){
                            map.put("degree_name", record1.getName());
                            break;
                        }
                    }
                }
                if (record.getStart() != null) {
                    map.put("start_date", DateUtils.dateToNormalDate(record.getStart()));
                }
                if (record.getEnd() != null) {
                    map.put("end_date", DateUtils.dateToNormalDate(record.getEnd()));
                }
                map.put("end_until_now", record.getEndUntilNow().intValue());
                map.put("description", record.getDescription());
                list.add(map);
            });
        }
        return list;
    }

    private List<Map<String, Object>> buildWorkexps(ProfileProfileRecord profileRecord, Query query, List<DictConstantRecord> constantRecords) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // 按照结束时间倒序
            query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
            query.getOrders().add(new OrderBy("start", Order.DESC));
            List<ProfileWorkexpRecord> records = workExpDao.getRecords(query);
            if (records != null && records.size() > 0) {
                List<Integer> companyIds = new ArrayList<>();
                records.forEach(record -> {
                    companyIds.add(record.getCompanyId().intValue());
                });
                List<HrCompanyRecord> companyRecords = companyDao.getCompaniesByIds(companyIds);
                records.forEach(record -> {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", record.getId().intValue());
                    if (companyRecords != null && companyRecords.size() > 0) {
                        for (HrCompanyRecord company : companyRecords) {
                            if (record.getCompanyId().intValue() == company.getId().intValue()) {
                                map.put("company_name", company.getName());
                                map.put("company_logo", company.getLogo());
                                map.put("company_id", company.getId().intValue());

                                map.put("company_scale", company.getScale().intValue());
                                map.put("company_property", company.getProperty().intValue());
                                map.put("company_scale_name", "");
                                map.put("company_property_name", "");
                                for (DictConstantRecord constantRecord : constantRecords) {
                                    if (constantRecord.getParentCode().intValue() == 1102
                                            && constantRecord.getCode().intValue() == company.getScale().intValue()) {
                                        map.put("company_scale_name", constantRecord.getName());
                                        break;
                                    }
                                    if (constantRecord.getParentCode().intValue() == 1103
                                            && constantRecord.getCode().intValue() == company.getProperty().intValue()) {
                                        map.put("company_property_name", constantRecord.getName());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    map.put("position_name", record.getPositionName());
                    map.put("position_code", record.getPositionCode().intValue());
                    map.put("department_name", record.getDepartmentName());
                    map.put("job", record.getJob());
                    // map.put("logo", record.getlo)
                    if (record.getStart() != null) {
                        map.put("start_date", DateUtils.dateToNormalDate(record.getStart()));
                    }
                    if (record.getEnd() != null) {
                        map.put("end_date", DateUtils.dateToNormalDate(record.getEnd()));
                    }
                    map.put("end_until_now", record.getEndUntilNow().intValue());
                    map.put("description", record.getDescription());
                    map.put("achievement", record.getAchievement());
                    list.add(map);
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return list;
    }

    private Map<String, Object> buildBasic(ProfileProfileRecord profileRecord, Query query,
                                           List<DictConstantRecord> constantRecords) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
            /*ProfileBasicRecord basicRecord = profileBasicDao.getResource(query);
            UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
			ProfileWorkexpRecord lastWorkExp = workExpDao.getLastWorkExp(profileRecord.getId().intValue());
			UserSettingsRecord userSettingsRecord = userSettingsDao
					.getUserSettingsById(profileRecord.getUserId().intValue());*/

        Future<ProfileBasicRecord> basicRecordFuture = pool.startTast(() -> profileBasicDao.getRecord(query));
        Future<UserUserRecord> userRecordFuture = pool.startTast(() -> userDao.getUserById(profileRecord.getUserId().intValue()));
        Future<ProfileWorkexpRecord> lastWorkExpFuture = pool.startTast(() -> workExpDao.getLastWorkExp(profileRecord.getId().intValue()));
        Future<UserSettingsRecord> userSettingsRecordFuture = pool.startTast(() -> userSettingsDao.getUserSettingsById(profileRecord.getUserId().intValue()));

        ProfileBasicRecord basicRecord = basicRecordFuture.get();
        UserUserRecord userRecord = userRecordFuture.get();
        ProfileWorkexpRecord lastWorkExp = lastWorkExpFuture.get();
        UserSettingsRecord userSettingsRecord = userSettingsRecordFuture.get();

        HrCompanyRecord company = null;
        if (lastWorkExp != null) {
            QueryUtil queryUtils = new QueryUtil();
            queryUtils.addEqualFilter("id", lastWorkExp.getCompanyId().toString());
            company = companyDao.getRecord(queryUtils);
        }
        if (userSettingsRecord != null) {
            map.put("banner_url", userSettingsRecord.getBannerUrl());
            map.put("privacy_policy", userSettingsRecord.getPrivacyPolicy().intValue());
        }
        UserWxUserRecord wxuserRecord = null;
        if (userRecord != null) {
            wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
            if (!StringUtils.isNullOrEmpty(userRecord.getHeadimg())) {
                map.put("headimg", userRecord.getHeadimg());
            } else {
                if (wxuserRecord != null) {
                    map.put("headimg", wxuserRecord.getHeadimgurl());
                }
            }
            map.put("mobile", userRecord.getMobile());
            map.put("email", userRecord.getEmail());
            map.put("name", userRecord.getName());
            map.put("nickname",userRecord.getNickname());
        }
        if (lastWorkExp != null) {
            if (company != null) {
                map.put("company_id", company.getId().intValue());
                map.put("company_name", company.getName());
                map.put("company_logo", company.getLogo());
                map.put("company_scale", company.getScale().intValue());
            }
            map.put("industry_name", lastWorkExp.getIndustryName());
            map.put("industry_code", lastWorkExp.getIndustryCode().intValue());
            map.put("position_name", lastWorkExp.getPositionName());
            map.put("current_job", lastWorkExp.getJob());
        }
        if (basicRecord != null) {
            map.put("update_time", DateUtils.dateToShortTime(profileRecord.getUpdateTime()));
            map.put("completeness", profileRecord.getCompleteness().intValue());
            map.put("uuid", profileRecord.getUuid());
            map.put("city_name", basicRecord.getCityName());
            map.put("city_code", basicRecord.getCityCode().intValue());
            if (basicRecord.getGender() != null) {
                map.put("gender", basicRecord.getGender().intValue());
                for (DictConstantRecord constantRecord : constantRecords) {
                    if (constantRecord.getParentCode().intValue() == 3109
                            && constantRecord.getCode().intValue() == basicRecord.getGender().intValue()) {
                        map.put("gender_name", constantRecord.getName());
                        break;
                    }
                }
            }
            map.put("nationality_name", basicRecord.getNationalityName());
            map.put("nationality_code", basicRecord.getNationalityCode().intValue());
            DictCountryRecord countryRecord = countryDao.getCountryByID(basicRecord.getNationalityCode());
            if (countryRecord != null) {
                map.put("icon_class", countryRecord.getIconClass());
            }
            map.put("motto", basicRecord.getMotto());
            if (basicRecord.getBirth() != null) {
                map.put("birth", DateUtils.dateToNormalDate(basicRecord.getBirth()));
                String constellation = ConstellationUtil.getConstellation(basicRecord.getBirth());
                map.put("constellation",constellation);
            }
            map.put("self_introduction", basicRecord.getSelfIntroduction());

            map.put("qq", basicRecord.getQq());
            map.put("weixin", basicRecord.getWeixin());
            map.put("profile_id", basicRecord.getProfileId().intValue());
        }
        return map;
    }

    private Map<String, Object> buildProfile(ProfileProfileRecord profileRecord, Query query,
                                             List<DictConstantRecord> constantRecords) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", profileRecord.getId().intValue());
        if (profileRecord.getUuid() != null) {
            map.put("uuid", profileRecord.getUuid());
        }
        if (profileRecord.getLang() != null) {
            map.put("lang", profileRecord.getLang().intValue());
        }
        if (profileRecord.getSource() != null) {
            map.put("source", profileRecord.getSource().intValue());
            if (constantRecords != null && constantRecords.size() > 0) {
                for (DictConstantRecord constantRecord : constantRecords) {
                    if (constantRecord.getParentCode().intValue() == 3119) {
                        if (profileRecord.getSource().intValue() == constantRecord.getCode().intValue()) {
                            map.put("source_name", constantRecord.getName());
                            break;
                        }
                    }
                }
            }
        }
        if (profileRecord.getCompleteness() != null) {
            map.put("completeness", profileRecord.getCompleteness().intValue());
        }
        if (profileRecord.getUserId() != null) {
            map.put("user_id", profileRecord.getUserId().intValue());
        }
        if (profileRecord.getCreateTime() != null) {
            map.put("create_time", DateUtils.dateToShortTime(profileRecord.getCreateTime()));
        }
        if (profileRecord.getUpdateTime() != null) {
            map.put("update_time", DateUtils.dateToShortTime(profileRecord.getUpdateTime()));
        }
        //在profile中添加origin字段的获取，用于获取第三方简历来源
        map.put("origin", profileRecord.getOrigin());
        return map;
    }

    /*
     合并上传的简历
     */
    @CounterIface
    public Response combinationProfile(String params,int companyId ) throws TException {
        params = EmojiFilter.filterEmoji1(params);
        Map<String, Object> resume = JSON.parseObject(params);
        Map<String, Object> map = (Map<String, Object>) resume.get("user");
        Map<String,Object> basic=(Map<String, Object>)resume.get("basic");
        String mobile = ((String) map.get("mobile"));
        String countryCode="86";
        if(basic.get("country_code")!=null){
            countryCode=String.valueOf(basic.get("country_code"));
        }else{
            if(map.get("country_code")!=null){
                countryCode=String.valueOf(map.get("country_code"));
            }
        }
        if(StringUtils.isNullOrEmpty(mobile)){
            this.handlerWorkExpData(resume);
            handleResumeMap(resume);
            return ResponseUtils.success(StringUtils.underscoreNameMap(resume));
        }
        UserUserRecord userRecord=talentPoolEntity.getTalentUploadUser(mobile,companyId, UserSource.TALENT_UPLOAD.getValue(),countryCode);
        if(userRecord==null){
            this.handlerWorkExpData(resume);
            handleResumeMap(resume);
            return ResponseUtils.success(StringUtils.underscoreNameMap(resume));
        }
        Map<String, Object> profileMap = (Map<String, Object>) resume.get("profile");
        Map<String, Object> profileProfileMap=getProfileByUserId(userRecord.getId());
        if(profileMap==null||profileMap.isEmpty()){
            resume.put("profile",profileProfileMap);
        }

        if (profileProfileMap != null&&!profileProfileMap.isEmpty()) {
            int profileId = (int)profileProfileMap.get("id");
            this.combinationProfile(resume,profileId);
            this.handlerWorkExpData(resume);
            this.handleResumeMap(resume);
            resume=StringUtils.underscoreNameMap(resume);
            return ResponseUtils.success(resume);
        }
        return ResponseUtils.success(StringUtils.underscoreNameMap(resume));
    }
    /*
    通过userId获取profileId
    */
    private Map<String,Object> getProfileByUserId(int userId){
        Query query=new Query.QueryBuilder().where("user_id",userId).buildQuery();
        Map<String,Object> result=profileDao.getMap(query);
        return result;
    }

    /*
     修改工作经历中公司的数据格式
     */
    private void handlerWorkExpData(Map<String,Object> resume){
        if(resume!=null&&!resume.isEmpty()){
            List<Map<String,Object>> workExps= (List<Map<String, Object>>) resume.get("workexps");
            if(!StringUtils.isEmptyList(workExps)){
                for(Map<String,Object> map:workExps){
                    Map<String,Object> company= (Map<String, Object>) map.get("company");
                    if(company!=null&&!company.isEmpty()){
                        for(String key:company.keySet()){
                            map.put(key,company.get(key));
                        }
                    }
                    if((map.get("position_name")==null||StringUtils.isNullOrEmpty(String.valueOf(map.get("position_name"))))&&map.get("job")!=null){
                        map.put("position_name",map.get("job"));
                    }
                }
            }
        }
    }
    /*
     将user的一些信息放到basic中
     */
    public void handleResumeMap( Map<String,Object> result){
        if(result!=null&&!result.isEmpty()){
            Map<String,Object> basic= (Map<String, Object>) result.get("basic");
            if(basic==null){
                basic=new HashMap<>();
            }
            Map<String,Object> user= (Map<String, Object>) result.get("user");
            if(user!=null&&!user.isEmpty()){
                for(String key :user.keySet()){
                    basic.put(key,user.get(key));
                }
            }

        }
    }

    /*
     保存上传的简历 upload 表示是否上传的，目前简历搬家传来的upload为0 ，非上传
     */
    public Response preserveProfile(String params,String fileName,int hrId,int companyId,int userId,int source) throws TException {
        params = EmojiFilter.filterEmoji1(params);
        Map<String, Object> resume = JSON.parseObject(params);
        Map<String, Object> map = (Map<String, Object>) resume.get("user");
        Map<String,Object> basic=(Map<String, Object>)resume.get("basic");
        map=this.handlerBasicAndUser(basic,map,source);
        this.handleWorkExps(resume);
        String mobile = String.valueOf(map.get("mobile")) ;
        if(StringUtils.isNullOrEmpty(mobile)){
            return ResponseUtils.fail(1,"手机号不能为空");
        }
        if(!org.apache.commons.lang.StringUtils.isNumeric(mobile)){
            return ResponseUtils.fail(1,"手机号必须全部为数字");
        }
//        if(mobile.length()!=11){
//            return ResponseUtils.fail(1,"手机号必须为11位");
//        }

        String countryCode="86";
        if(map.get("country_code")!=null){
            countryCode=String.valueOf(map.get("country_code"));
        }

        UserUserRecord userRecord=talentPoolEntity.getTalentUploadUser(mobile,companyId, source,countryCode);
        int newUerId=0;
        if(userRecord!=null){
            newUerId=userRecord.getId();
        }
        if(userId==0&&newUerId==0){
            newUerId=this.saveNewProfile(resume,map,source);
        }else{
            userRecord=userAccountEntity.combineAccount(userId,newUerId);
            if(userRecord==null){
                return ResponseUtils.fail(1,"修改的简历的user_id不存在");
            }
            newUerId=userRecord.getId();
            userRecord.setMobile(Long.parseLong(String.valueOf(map.get("mobile"))));
            if(map.get("name")!=null){
                userRecord.setName(String.valueOf(map.get("name")));
            }
            if(map.get("email")!=null){
                userRecord.setEmail(String.valueOf(map.get("email")));
            }
            if(map.get("country_code")!=null){
                userRecord.setCountryCode(String.valueOf(map.get("country_code")));
            }

            Response res=this.upsertProfile(resume,userRecord,userId,newUerId);
            if(res.getStatus()!=0){
                return res;
            }
        }

        //此处应该考虑账号合并导致的问题
        talentPoolEntity.addUploadTalent(userId,newUerId,hrId,companyId,fileName,source);
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(newUerId);
        talentPoolEntity.realTimeUpload(userIdList,1);
        pool.startTast(() -> {
            talentpoolService.handlerCompanyTagAndProfile(userIdList,companyId);
            return 0;
        });
        return ResponseUtils.success("success");
    }
    /*
     将工作经历进行格式转换
     */
    private void handleWorkExps(Map<String,Object> resume){
        List<Map<String,Object>> workExps= (List<Map<String, Object>>) resume.get("workexps");
        if(!StringUtils.isEmptyList(workExps)){
            for(Map<String,Object> map:workExps){
                Map<String,Object> company=new HashMap<>();
                if(map.get("company_name")!=null){
                    company.put("company_name",map.get("company_name"));
                }
                if(map.get("company_industry")!=null){
                    company.put("company_industry",map.get("company_industry"));
                }
                if(map.get("company_introduction")!=null){
                    company.put("company_introduction",map.get("company_introduction"));
                }
                if(map.get("scale")!=null){
                    company.put("scale",map.get("scale"));
                }
                if(map.get("company_property")!=null){
                    company.put("company_property",map.get("company_property"));
                }
                map.put("company",company);
                if(map.get("position_name")!=null){
                    map.put("job",map.get("position_name"));
                } else {
                    map.put("job",map.get("job"));
                }
            }
        }
    }

    /*
     将上传的basic的内容组合到user当中
     */
    private Map<String,Object> handlerBasicAndUser(Map<String,Object> basicMap,Map<String,Object> userMap,int source){
        if(userMap==null||userMap.isEmpty()){
            userMap=new HashMap<>();
        }
        if(basicMap==null||basicMap.isEmpty()){
            return null;
        }
        if(basicMap.get("name")!=null){
            userMap.put("name",basicMap.get("name"));
        }
        if(basicMap.get("mobile")!=null){
            userMap.put("mobile",String.valueOf(basicMap.get("mobile")));
        }
        if(basicMap.get("nationality_code")!=null){
            userMap.put("national_code_id",basicMap.get("nationality_code"));
        }
        if(basicMap.get("email")!=null){
            userMap.put("email",basicMap.get("email"));
        }
        if(basicMap.get("country_code")!=null){
            userMap.put("country_code",basicMap.get("country_code"));
        }else{
            if(userMap.get("country_code")==null){
                userMap.put("country_code","86");
            }
        }
        userMap.put("source", source);
        return userMap;
    }

    /*
      保存上传简历
      */
    @Transactional(rollbackFor = Exception.class)
    protected int saveNewProfile(Map<String, Object> resume,Map<String, Object> map,int source) throws TException {
        UserUserDO user1 = BeanUtils.MapToRecord(map, UserUserDO.class);
        logger.info("talentpool upload new  user:{}", user1);
        user1.setSource((byte) source);
        int userId = useraccountsServices.createRetrieveProfileUser(user1);
        logger.info("talentpool userId:{}", userId);
        if (userId > 0) {
            map.put("id", userId);
            HashMap<String, Object> profileProfile = new HashMap<String, Object>();
            profileProfile.put("user_id", userId);
            profileProfile.put("origin", resume.get("origin"));
            resume.put("profile", profileProfile);
            user1.setId(userId);
            UserUserRecord userRecord=BeanUtils.structToDB(user1,UserUserRecord.class);
            int id= this.createProfileItem(userRecord,resume,JSON.toJSONString(resume));
            if(id==0){
                throw new TException();
            }
            return userId;
        }else{
            throw new TException();
        }
    }
    /*
     更新上传简历
     */
    @Transactional(rollbackFor = Exception.class)
    protected Response upsertProfile(Map<String, Object> resume,UserUserRecord userRecord,int userId,int newUserId){

        ProfileProfileRecord profileRecord = profileUtils.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
        if (profileRecord == null) {
            if(userId!=0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
            }else{
                Map<String,Object> profileMap=new HashMap<>();
                profileMap.put("user_id",newUserId);
                profileMap.put("origin","0");
                resume.put("profile",profileMap);
            }
        }
        ProfileProfileRecord profileDB = profileDao.getProfileByIdOrUserIdOrUUID(userRecord.getId().intValue(), 0, null);

        if (profileDB != null) {
            String origin1=(String)resume.get("origin");
            String origin=profileDB.getOrigin();
            String originResult=convertToChannelString(origin,origin1);
            ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord, profileParseUtil.initParseProfileParam());
            /*
             合并profile_profile.origin
             */
            profilePojo.getProfileRecord().setOrigin(originResult);
            int profileId = profileDB.getId().intValue();
            profileEntity.improveUser(userRecord);
            profileEntity.upsertProfileProfile(profilePojo.getProfileRecord(), profileId);
            profileEntity.upsertProfileBasic(profilePojo.getBasicRecord(), profileId);
            profileEntity.improveAttachment(profilePojo.getAttachmentRecords(), profileId);
            profileEntity.improveAwards(profilePojo.getAwardsRecords(), profileId);
            profileEntity.improveCredentials(profilePojo.getCredentialsRecords(), profileId);
            profileEntity.improveEducation(profilePojo.getEducationRecords(), profileId);
            profileEntity.improveIntention(profilePojo.getIntentionRecords(), profileId);
            profileEntity.improveLanguage(profilePojo.getLanguageRecords(), profileId);
            profileEntity.upsertProfileOther(profilePojo.getOtherRecord(), profileId);
            profileEntity.improveProjectexp(profilePojo.getProjectExps(), profileId);
            profileEntity.improveSkill(profilePojo.getSkillRecords(), profileId);
            profileEntity.improveWorkexp(profilePojo.getWorkexpRecords(), profileId);
            profileEntity.improveWorks(profilePojo.getWorksRecords(), profileId);
            profileEntity.reCalculateProfileCompleteness(profileId);

            try {
                StatisticsForChannelmportVO statisticsForChannelmportVO = createStaticstics(profileDB.getId().intValue(), profileDB.getUserId().intValue(), (byte) 2,
                        profilePojo.getImportRecords());
                profileUtils.logForStatistics("upsertProfile", new JSONObject() {{
                    this.put("profile", resume);
                }}.toJSONString(), statisticsForChannelmportVO);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return ResponseUtils.success("");
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ALLREADY_NOT_EXIST);
        }
    }
    /*
     channelType.value和origin之间的关系
     */

    private String convertToChannelString(String origin,String origin1){
        int type=0;
        if(StringUtils.isNullOrEmpty(origin1)){
            return origin;
        }
        if(origin1.length()==20){
            type=20;
        }else if(origin1.length()==21){
            type=21;
        }else if(origin1.length()==22){
            type=22;
        }else if(origin1.length()==23){
            type=23;
        }else if(origin1.length()==24){
            type=24;
        }else if(origin1.length()==25){
            type=25;
        }else if(origin1.length()==28){
            type=28;
        }else if(origin1.length()==31){
            type=31;
        }else if(origin1.length()==32){
            type=32;
        }else if(origin1.length()==33){
            type=33;
        }else if(origin1.length()==34){
            type=34;
        }
        if(type==0){
            return origin;
        }
        ChannelType channelType = ChannelType.instaceFromInteger(type);
        return channelType.getOrigin(origin);
    }
    /*
      合并简历
     */
    private void combinationProfile(Map<String,Object> resume,int profileId) throws TException {
        Map<String,Object> profileBasic=this.combinationBasic((Map<String,Object>) resume.get("basic"),profileId);
        if(profileBasic!=null){
            resume.put("basic",profileBasic);
        }
        Map<String,Object> profileOtherRecord=this.combinationProfileOther((Map<String,Object>)profileBasic.get("others"),profileId);
        if(profileOtherRecord!=null){
            resume.put("others",profileOtherRecord);
        }
        if(resume.get("projectexps")==null||StringUtils.isEmptyList((List)resume.get("projectexps"))){
            resume.put("projectexps",this.getProjectExpById(profileId));
        }else{
            List<Map<String,Object>> projectList=(List)resume.get("projectexps");
            if(projectList.size()==1){
                Map<String,Object> map=projectList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("projectexps",this.getProjectExpById(profileId));
                }
            }
        }
        if(resume.get("skills")==null||StringUtils.isEmptyList((List)resume.get("skills"))){
            resume.put("skills",this.getSkillExpById(profileId));
        }else{
            List<Map<String,Object>> skillList=(List)resume.get("skills");
            if(skillList.size()==1){
                Map<String,Object> map=skillList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("skills",this.getSkillExpById(profileId));
                }
            }
        }
        if(resume.get("workexps")==null||StringUtils.isEmptyList((List)resume.get("workexps"))){
            resume.put("workexps",this.getWorkExpsById(profileId));
        }else{
            List<Map<String,Object>> workExpsList=(List)resume.get("workexps");
            if(workExpsList.size()==1){
                Map<String,Object> map=workExpsList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("workexps",this.getWorkExpsById(profileId));
                }
            }
        }
        if(resume.get("educations")==null||StringUtils.isEmptyList((List)resume.get("educations"))){
            resume.put("educations",this.getEducationsById(profileId));
        }else{
            List<Map<String,Object>> educationsList=(List)resume.get("educations");
            if(educationsList.size()==1){
                Map<String,Object> map=educationsList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("educations",this.getEducationsById(profileId));
                }
            }
        }
        if(resume.get("languages")==null||StringUtils.isEmptyList((List)resume.get("languages"))){
            resume.put("languages",this.getLanguagesById(profileId));
        }else{
            List<Map<String,Object>> languagesList=(List)resume.get("languages");
            if(languagesList.size()==1){
                Map<String,Object> map=languagesList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("languages",this.getLanguagesById(profileId));
                }
            }
        }
        if(resume.get("intentions")==null||StringUtils.isEmptyList((List)resume.get("intentions"))){
            resume.put("intentions",this.getIntentions(profileId));
        }else{
            List<Map<String,Object>> intentionsList=(List)resume.get("intentions");
            if(intentionsList.size()==1){
                Map<String,Object> map=intentionsList.get(0);
                if(this.isCombineIntention(map)){
                    resume.put("intentions",this.getIntentions(profileId));
                }
            }
        }

        if(resume.get("credentials")==null||StringUtils.isEmptyList((List)resume.get("credentials"))){
            resume.put("credentials",this.getCredentialsById(profileId));
        }else{
            List<Map<String,Object>> credentialsList=(List)resume.get("credentials");
            if(credentialsList.size()==1){
                Map<String,Object> map=credentialsList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("credentials",this.getCredentialsById(profileId));
                }
            }
        }
        if(resume.get("awards")==null||StringUtils.isEmptyList((List)resume.get("awards"))){
            resume.put("awards",this.getAwardsById(profileId));
        }else{
            List<Map<String,Object>> awardsList=(List)resume.get("awards");
            if(awardsList.size()==1){
                Map<String,Object> map=awardsList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("awards",this.getAwardsById(profileId));
                }
            }
        }
        if(resume.get("works")==null||StringUtils.isEmptyList((List)resume.get("works"))){
            resume.put("works",this.getWorksById(profileId));
        }else{
            List<Map<String,Object>> getWorksByIdList=(List)resume.get("works");
            if(getWorksByIdList.size()==1){
                Map<String,Object> map=getWorksByIdList.get(0);
                if(map==null||map.isEmpty()){
                    resume.put("works",this.getWorksById(profileId));
                }
            }
        }

    }
    /*
     判断是否需要合并库中的求职意向
     */
    private boolean isCombineIntention(Map<String,Object> intention){
        if(intention==null||intention.isEmpty()){
            return true;
        }
        int flag=0;
        for(String key:intention.keySet()){
            if(!"workstate".equals(key)&&!"worktype".equals(key)){
                flag=1;
                break;
            }
        }
        if(flag==0){
            return false;
        }
        return true;
    }
    /*
     根据id获取ProjectExp
     */
    private List<Map<String,Object>> getProjectExpById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileProjectexpDO> list=projectExpDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileProjectexpDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取Skill
     */
    private List<Map<String,Object>> getSkillExpById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileSkillDO> list=skillDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileSkillDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取WorkExps
     */
    private List<Map<String,Object>> getWorkExpsById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileWorkexpDO> list=workExpDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileWorkexpDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取Languages
     */
    private List<Map<String,Object>> getLanguagesById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileLanguageDO> list=languageDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileLanguageDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取Educations
     */
    private List<Map<String,Object>> getEducationsById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileEducationDO> list=educationDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileEducationDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取Credentials
     */
    private List<Map<String,Object>> getCredentialsById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileCredentialsDO> list=credentialsDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileCredentialsDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取Awards
     */
    private List<Map<String,Object>> getAwardsById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileAwardsDO> list=awardsDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileAwardsDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
    根据id获取Works
    */
    private List<Map<String,Object>> getWorksById(int profileId) throws TException {
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        List<ProfileWorksDO> list=worksDao.getDatas(query);
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(ProfileWorksDO DO:list){
                String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                Map<String,Object> data= JSON.parseObject(DOs, Map.class);
                data.remove("id");
                result.add(data);
            }
        }
        return result;
    }
    /*
     根据id获取Intention
     */
    private List<Map<String,Object>> getIntentions(int profileId){
        List<DictConstantRecord> constantRecords = constantDao
                .getCitiesByParentCodes(Arrays.asList(3109, 3105, 3102, 2105, 3120, 3115, 3114, 3119, 3120));
        ProfileProfileRecord profileRecord=profileDao.getProfileByIdOrUserIdOrUUID(0,profileId,null);
        List<Map<String, Object>> list=profileUtils.buildsIntentions(profileRecord, getProfileQuery(profileId),
                constantRecords, intentionDao, intentionCityDao, intentionIndustryDao, intentionPositionDao,
                dictCityDao, dictIndustryDao, dictPositionDao);
        return list;
    }
    /*
     合并profile_basic
     */
    public Map<String,Object>  combinationBasic(Map<String,Object> basicMap, int profileId) throws TException {
        if (basicMap != null) {
            Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
            ProfileBasicDO basic = profileBasicDao.getData(query,ProfileBasicDO.class);
            if (basic != null) {
                if (StringUtils.isNotNullOrEmpty((String)basicMap.get("name")) && StringUtils.isNullOrEmpty(basic.getName())) {
                    basic.setName((String)basicMap.get("name"));
                }
                if (basicMap.get("gender") != null && basic.getGender()==0) {
                    int gender=Integer.parseInt(String.valueOf(basicMap.get("gender")));
                    basic.setGender((byte)gender);
                }
                if(basicMap.get("nationalityCode") != null&& basic.getNationalityCode()==0){
                    int nationalityCode=Integer.parseInt(String.valueOf(basicMap.get("nationalityCode")));
                    basic.setNationalityCode(nationalityCode);
                }
                if (basicMap.get("nationalityName") != null && StringUtils.isNullOrEmpty(basic.getNationalityName())) {
                    basic.setNationalityName((String)basicMap.get("nationalityName"));
                }
                if (basicMap.get("citycode") != null && basic.getCityCode()==0) {
                    int citycode=Integer.parseInt(String.valueOf(basicMap.get("citycode")));
                    basic.setCityCode(citycode);
                }
                if (basicMap.get("cityName") != null && StringUtils.isNullOrEmpty(basic.getCityName())) {
                    basic.setCityName((String)basicMap.get("cityName"));
                }
                if (basicMap.get("birth") != null && StringUtils.isNullOrEmpty(basic.getBirth())) {
                    basic.setBirth((String)basicMap.get("birth") );
                }
                if (basicMap.get("weixin") != null && StringUtils.isNullOrEmpty(basic.getWeixin())) {
                    basic.setWeixin((String)basicMap.get("weixin") );
                }
                if (basicMap.get("qq") != null && StringUtils.isNullOrEmpty(basic.getQq())) {
                    basic.setQq((String)basicMap.get("qq"));
                }
                if (basicMap.get("motto") != null && StringUtils.isNullOrEmpty(basic.getMotto())) {
                    basic.setMotto((String)basicMap.get("motto"));
                }
                if (basicMap.get("selfIntroduction") != null &&  StringUtils.isNullOrEmpty(basic.getSelfIntroduction())) {
                    basic.setSelfIntroduction((String)basicMap.get("selfIntroduction"));
                }
                String basicDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(basic);
                Map<String,Object> basicData= JSON.parseObject(basicDOs, Map.class);
                return basicData;
            } else {
                return basicMap;
            }
        }
        return null;
    }

    /*
     合并Profile_Other
     */
    public Map<String,Object> combinationProfileOther(Map<String,Object> otherRecord, int profileId) {
        if (otherRecord != null &&otherRecord.get("other")!=null) {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("profile_id", String.valueOf(profileId));
            ProfileOtherRecord record = otherDao.getRecord(query.buildQuery());
            if (record == null && otherRecord != null) {
                otherRecord.put("profileId",profileId);
            } else if (record != null && otherRecord != null) {
                /**
                 * 自定义合并逻辑：oldOther没有或为空的字段且存在newOther中 -> 将newOther中的字段补填到oldOther里
                 */
                Map<String, Object> oldOtherMap = (Map<String, Object>) otherRecord.get("other");
                Map<String, Object> newOtherMap = JSONObject.parseObject(record.getOther(), Map.class);
                oldOtherMap.entrySet().stream().filter(f -> (StringUtils.isNullOrEmpty(String.valueOf(f.getValue())) || "[]".equals(String.valueOf(f.getValue())))  && newOtherMap.containsKey(f.getKey())).forEach(e -> e.setValue(newOtherMap.get(e.getKey())));
                newOtherMap.putAll(oldOtherMap);
                otherRecord.put("other",JSONObject.toJSONString(newOtherMap));

            }
        }
        return otherRecord;
    }

    @CounterIface
    public Response validateUpLoadHr(int companyId,int hrId,int userId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该公司");
        }
        int result=talentPoolEntity.ValidateUploadProfileIsHr(userId,companyId,hrId);
        if(result==0){
            return ResponseUtils.success(false);
        }
        return  ResponseUtils.success(true);
    }

    @CounterIface
    public Response getProfileUpload(int userId) throws Exception {
        Response res=this.getResource(userId,0,null);
        if(res.getStatus()==0&&StringUtils.isNotNullOrEmpty(res.getData())){
            String result=res.getData();
            Map<String,Object> resume=JSON.parseObject(result);
            Map<String,Object> userMap=this.getUserUserMap(userId);
            resume.put("user",userMap);
            resume.put("workexps",this.addCompanyMap((List<Map<String,Object>>)resume.get("workexps")));
            return ResponseUtils.success(resume);

        }
        return res;

    }
    /*
     获取个人信息
     */
    private Map<String,Object> getUserUserMap(int userId){
        Query query=new Query.QueryBuilder().where("id",userId).and("is_disable",0).and("source",UserSource.TALENT_UPLOAD.getValue()).buildQuery();
        Map<String,Object>  result=userDao.getMap(query);
        return result;
    }
    /*
     处理公司信息
     */
    private List<Map<String,Object>> addCompanyMap(List<Map<String,Object>> workExp){
        if(StringUtils.isEmptyList(workExp)){
            return null;
        }
        for(Map<String,Object> map:workExp){
            Map<String,Object> company=new HashMap<>();
            if(map.get("company_property")!=null){
                company.put("company_property",map.get("company_property"));
            }
            if(map.get("company_industry")!=null){
                company.put("company_industry",map.get("company_property"));
            }
            if(map.get("company_scale")!=null){
                company.put("company_scale",map.get("company_scale"));
            }
            if(map.get("company_name")!=null){
                company.put("company_name",map.get("company_name"));
            }
            map.put("company",company);
            if((map.get("position_name")==null||StringUtils.isNullOrEmpty(String.valueOf(map.get("position_name"))))&&map.get("job")!=null){
                map.put("position_name",map.get("job"));
            }

        }

        return workExp;
    }


}