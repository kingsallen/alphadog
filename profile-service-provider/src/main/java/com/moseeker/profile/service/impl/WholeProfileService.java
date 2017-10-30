package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.OrderBy;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.constants.StatisticsForChannelmportVO;
import com.moseeker.profile.service.impl.retriveprofile.RetriveProfile;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import org.apache.thrift.TException;
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

    private Query getProfileQuery(int profileId){
        return new Query.QueryBuilder().where("profile_id",profileId).setPageSize(Integer.MAX_VALUE).buildQuery();
    }

    public Response getResource(int userId, int profileId, String uuid) throws Exception {
        logger.info("WholeProfileService getResource start : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));
        Response response = new Response();
        HashMap<String, Object> profile = new HashMap<String, Object>();

        ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, profileId, uuid);

        logger.debug("WholeProfileService getResource after  profileDao.getProfileByIdOrUserIdOrUUID: {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

        if (profileRecord != null) {
            if (profileRecord.getCompleteness().intValue() == 0
                    || profileRecord.getCompleteness().intValue() == 10) {
                int completeness = profileEntity.getCompleteness(profileRecord.getUserId().intValue(),
                        profileRecord.getUuid(), profileRecord.getId().intValue());
                profileRecord.setCompleteness((byte) (completeness));
            }

            logger.debug("WholeProfileService getResource before  constantDao.getCitiesByParentCodes : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<DictConstantRecord> constantRecords = constantDao
                    .getCitiesByParentCodes(Arrays.asList(3109, 3105, 3102, 2105, 3120, 3115, 3114, 3119, 3120));

            logger.debug("WholeProfileService getResource after constantDao.getCitiesByParentCodes : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            Map<String, Object> profileprofile = buildProfile(profileRecord, getProfileQuery(profileRecord.getId()), constantRecords);
            profile.put("profile", profileprofile);

            logger.debug("WholeProfileService getResource after buildProfile : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            Future<Map<String, Object>> basicFuture = pool.startTast(() -> buildBasic(profileRecord, getProfileQuery(profileRecord.getId()), constantRecords));
            Future<List<Map<String, Object>>> workexpsFuture = pool.startTast(() -> buildWorkexps(profileRecord, getProfileQuery(profileRecord.getId())));
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

            logger.debug("WholeProfileService getResource basicFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> workexps = workexpsFuture.get();
            profile.put("workexps", workexps);

            logger.debug("WholeProfileService getResource workexpsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> educations = educationsFuture.get();
            profile.put("educations", educations);

            logger.debug("WholeProfileService getResource educationsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> projectexps = projectexpsFuture.get();
            profile.put("projectexps", projectexps);

            logger.debug("WholeProfileService getResource projectexpsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> languages = buildLanguageFuture.get();
            profile.put("languages", languages);

            logger.debug("WholeProfileService getResource buildLanguageFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> skills = buildskillsFuture.get();
            profile.put("skills", skills);

            logger.debug("WholeProfileService getResource buildskillsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> credentials = buildsCredentialsFuture.get();
            profile.put("credentials", credentials);

            logger.debug("WholeProfileService getResource buildsCredentialsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> awards = buildsAwardsFuture.get();
            profile.put("awards", awards);

            logger.debug("WholeProfileService getResource buildsAwardsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> works = buildsWorksFuture.get();
            profile.put("works", works);

            logger.debug("WholeProfileService getResource buildsWorksFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<Map<String, Object>> intentions = intentionsFuture.get();
            profile.put("intentions", intentions);

            logger.debug("WholeProfileService getResource intentionsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<ProfileAttachmentRecord> attachmentRecords = attachmentRecordsFuture.get();
            List<Map<String, Object>> attachments = profileUtils.buildAttachments(profileRecord, attachmentRecords);
            profile.put("attachments", attachments);

            logger.debug("WholeProfileService getResource attachmentRecordsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<ProfileImportRecord> importRecords = importRecordsFuture.get();
            List<Map<String, Object>> imports = profileUtils.buildImports(profileRecord, importRecords);
            profile.put("imports", imports);

            logger.debug("WholeProfileService getResource importRecordsFuture.get() : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

            List<ProfileOtherRecord> otherRecords = otherRecordsFuture.get();
            List<Map<String, Object>> others = profileUtils.buildOthers(profileRecord, otherRecords);

            logger.info("WholeProfileService getResource done : {}", new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

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
                basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"));
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
                        .mapToEducationRecords((List<Map<String, Object>>) resume.get("educations"));
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
                otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"));
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
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

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
        ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord);

        int id = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                userRecord, oldProfile);
        if (id > 0) {
            logger.info("importCV 添加成功");
            try {
                StatisticsForChannelmportVO statisticsForChannelmportVO = createStaticstics(id, userId, (byte) 1,
                        profilePojo.getImportRecords());
                profileUtils.logForStatistics("importCV", new JSONObject() {{
                    this.put("profile", profile);
                    this.put("userId", userId);
                }}.toJSONString(), statisticsForChannelmportVO);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
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
        if (userRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }

        ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord);

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
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return ResponseUtils.success(id);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
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
            ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord);
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
                profileEntity.improveOther(destOther, originProfileId);
                profileEntity.improveProjectexp(destProjectexps, originProfileId);
                profileEntity.improveSkill(destSkills, originProfileId);
                profileEntity.improveWorks(destWorks, originProfileId);
                profileEntity.improveWorkexp(destWorkxps, originProfileId);
                profileEntity.getCompleteness(0, null, originProfile.getId().intValue());
            }
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
                map.put("major_name", record.getMajorName());
                map.put("major_code", record.getMajorCode());
                map.put("degree", record.getDegree().intValue());
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

    private List<Map<String, Object>> buildWorkexps(ProfileProfileRecord profileRecord, Query query) {
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

    @Autowired
    ProfileEntity profileEntity;

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
}