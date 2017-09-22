package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrAppCvConfDao;
import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.pojo.profile.*;
import com.moseeker.entity.pojo.resume.*;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.utils.DegreeSource;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.PositionEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppCvConfDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import com.sun.org.apache.regexp.internal.RE;
import java.util.*;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@CounterIface
public class ProfileService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ProfileProfileDao dao;

    @Autowired
    protected UserUserDao userDao;

    @Autowired
    protected ProfileCompletenessDao completenessDao;

    @Autowired
    private UserSettingsDao settingDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private HrAppCvConfDao hrAppCvConfDao;

    @Autowired
    private PositionEntity positionEntity;

    @Autowired
    private ProfileOtherDao profileOtherDao;

    public Response getResource(Query query) throws TException {
        ProfileProfileRecord record = null;
        record = dao.getRecord(query);
        if (record != null) {
            Profile s = dao.recordToData(record, Profile.class);
            if (record.getCompleteness().intValue() == 0 || record.getCompleteness().intValue() == 10) {
                int completeness = profileEntity.getCompleteness(record.getUserId().intValue(), record.getUuid(),
                        record.getId().intValue());
                s.setCompleteness(completeness);
            }
            return ResponseUtils.success(s);
        }

        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    @Transactional
    public Response postResource(Profile struct) throws TException {
        struct.setUuid(UUID.randomUUID().toString());
        if (!struct.isSetDisable()) {
            struct.setDisable((short) Constant.ENABLE);
        }
        if (struct.getUser_id() > 0) {
            UserUserRecord user = userDao.getUserById(struct.getUser_id());
            if (user == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
            }
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }

        ProfileProfileRecord record = BeanUtils.structToDB(struct, ProfileProfileRecord.class);
        record = dao.addRecord(record);

        return ResponseUtils.success(String.valueOf(record.getId()));
    }

    public Response getCompleteness(int userId, String uuid, int profileId) throws TException {
        int totalComplementness = profileEntity.getCompleteness(userId, uuid, profileId);
        return ResponseUtils.success(totalComplementness);
    }

    public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
        profileEntity.reCalculateUserUserByUserIdOrMobile(userId, mobile);
        int totalComplementness = profileEntity.getCompleteness(userId, null, 0);
        return ResponseUtils.success(totalComplementness);
    }

    public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(id));
        UserSettingsRecord record = settingDao.getRecord(qu);
        if (record != null) {
            profileEntity.reCalculateUserUserByUserIdOrMobile(record.getUserId().intValue(), null);
            int totalComplementness = profileEntity.getCompleteness(record.getUserId().intValue(), null, 0);
            return ResponseUtils.success(totalComplementness);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getResources(Query query) throws TException {
        List<Profile> datas = dao.getDatas(query, Profile.class);
        if (datas != null && datas.size() > 0) {
            return ResponseUtils.success(datas);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ResponseUtils.success(ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
    }

    @Transactional
    public Response postResources(List<Profile> structs) throws TException {
        List<ProfileProfileRecord> records = dao.addAllRecord(BeanUtils.structToDB(structs, ProfileProfileRecord.class));

        return ResponseUtils.success("1");
    }

    @Transactional
    public Response putResources(List<Profile> structs) throws TException {
        int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileProfileRecord.class));
        if (ArrayUtils.contains(result, 1)) {
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResources(List<Profile> structs) throws TException {
        int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileProfileRecord.class));
        if (ArrayUtils.contains(result, 1)) {
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    @Transactional
    public Response putResource(Profile struct) throws TException {
        int result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileProfileRecord.class));
        if (result > 0) {
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResource(Profile struct) throws TException {
        int result = dao.deleteRecord(BeanUtils.structToDB(struct, ProfileProfileRecord.class));
        if (result > 0) {
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);

    }

    public Response getProfileByApplication(ProfileApplicationForm profileApplicationForm) throws TException {
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        try {
            propertiesUtils.loadResource("setting.properties");
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        String downloadUrl = propertiesUtils.get("GENERATE_USER_ID", String.class);
        String password = propertiesUtils.get("GENERATE_USER_PASSWORD", String.class);
        logger.info("profilesByApplication:{}", JSON.toJSONString(profileApplicationForm));
        return dao.getResourceByApplication(downloadUrl, password, profileApplicationForm);
    }


    /**
     * 解析简历
     *
     * @param uid
     * @param fileName
     * @param file
     * @return
     * @throws TException
     */
    public Response profileParser(int uid, String fileName, String file) throws TException {
        ProfileObj profileObj = new ProfileObj();
        try {
            // 调用SDK得到结果
            ResumeObj resumeObj = profileEntity.profileParser(fileName, file);
            logger.info("profileParser resumeObj:{}", JSON.toJSONString(resumeObj));
            // 调用成功,开始转换对象
            if (resumeObj.getStatus().getCode() == 200) {
                // 项目经验
                logger.info("profileParser resumeObj.getResult().getProj_exp_objs():{}", JSON.toJSONString(resumeObj.getResult().getProj_exp_objs()));
                List<Projectexps> projectexps = new ArrayList<>();
                if (resumeObj.getResult().getProj_exp_objs() != null && resumeObj.getResult().getProj_exp_objs().size() > 0) {
                    for (ProjectexpObj projectexpObj : resumeObj.getResult().getProj_exp_objs()) {
                        Projectexps project = new Projectexps();
                        if (projectexpObj.getEnd_date() != null && projectexpObj.getEnd_date().equals("至今")) {
                            project.setEndUntilNow(1);
                        } else {
                            project.setEndDate(projectexpObj.getEnd_date());
                        }
                        project.setName(projectexpObj.getProj_name());
                        project.setStartDate(projectexpObj.getStart_date());
                        // 职责
                        project.setResponsibility(projectexpObj.getProj_resp());
                        project.setDescription(projectexpObj.getProj_content());
                        projectexps.add(project);
                    }
                }
                profileObj.setProjectexps(projectexps);
                logger.info("profileParser getProjectexps:{}", JSON.toJSONString(profileObj.getProjectexps()));

                logger.info("profileParser resumeObj.getResult().getEducation_objs():{}", JSON.toJSONString(resumeObj.getResult().getEducation_objs()));
                // 教育经历
                List<Education> educationList = new ArrayList<>();
                if (resumeObj.getResult().getEducation_objs() != null && resumeObj.getResult().getEducation_objs().size() > 0) {
                    for (EducationObj educationObj : resumeObj.getResult().getEducation_objs()) {
                        Education education = new Education();
                        if (educationObj.getEdu_degree() != null) {
                            if (DegreeSource.intToEnum.get(educationObj.getEdu_degree()) != null) {
                                education.setDegree(DegreeSource.intToEnum.get(educationObj.getEdu_degree()));
                            } else {
                                education.setDegree(0);
                            }
                        }
                        // 学校名称
                        education.setCollegeName(educationObj.getEdu_college());
                        // 专业名称
                        education.setMajorName(educationObj.getEdu_major());
                        education.setStartDate(educationObj.getStart_date());
                        education.setEndDate(educationObj.getEnd_date());
                        educationList.add(education);
                    }
                }
                profileObj.setEducations(educationList);
                logger.info("profileParser getEducations:{}", JSON.toJSONString(profileObj.getEducations()));
                // 技能
                logger.info("profileParser resumeObj.getResult().getSkills_objs():{}", JSON.toJSONString(resumeObj.getResult().getSkills_objs()));
                List<Skill> skills = new ArrayList<>();
                if (resumeObj.getResult().getSkills_objs() != null && resumeObj.getResult().getSkills_objs().size() > 0) {
                    for (SkillsObjs skillsObjs : resumeObj.getResult().getSkills_objs()) {
                        Skill skill = new Skill();
                        skill.setName(skillsObjs.getSkills_name());
                        skills.add(skill);
                    }
                }
                profileObj.setSkills(skills);
                logger.info("profileParser getSkills:{}", JSON.toJSONString(profileObj.getSkills()));

                // 工作经验
                logger.info("profileParser resumeObj.getResult().getJob_exp_objs():{}", JSON.toJSONString(resumeObj.getResult().getJob_exp_objs()));
                List<Workexps> workexpsList = new ArrayList<>();
                if (resumeObj.getResult().getJob_exp_objs() != null && resumeObj.getResult().getJob_exp_objs().size() > 0) {
                    for (JobExpObj jobExpObj : resumeObj.getResult().getJob_exp_objs()) {
                        Workexps workexps = new Workexps();
                        Company company = new Company();
                        company.setCompanyIndustry(jobExpObj.getJob_industry());
                        company.setCompanyName(jobExpObj.getJob_cpy());
                        company.setCompanyScale(Integer.valueOf(jobExpObj.getJob_cpy_size() == null ? "0" : jobExpObj.getJob_cpy_size()));
                        workexps.setCompany(company);
                        workexps.setDescription(jobExpObj.getJob_nature());
                        workexps.setStartDate(jobExpObj.getStart_date());
                        workexps.setEndDate(jobExpObj.getEnd_date());
                        workexps.setJob(jobExpObj.getJob_position());
                        workexpsList.add(workexps);
                    }
                }
                profileObj.setWorkexps(workexpsList);
                logger.info("profileParser getWorkexps:{}", JSON.toJSONString(profileObj.getWorkexps()));
                // 语言
                logger.info("profileParser resumeObj.getResult().getLang_objs():{}", JSON.toJSONString(resumeObj.getResult().getLang_objs()));
                List<Language> languageList = new ArrayList<>();
                if (resumeObj.getResult().getLang_objs() != null && resumeObj.getResult().getLang_objs().size() > 0) {
                    for (LangObj langObj : resumeObj.getResult().getLang_objs()) {
                        Language language = new Language();
                        language.setName(langObj.getLanguage_name());
                        languageList.add(language);
                    }
                }
                profileObj.setLanguages(languageList);
                logger.info("profileParser getLanguages:{}", JSON.toJSONString(profileObj.getLanguages()));

                // 查询
                UserUserRecord userUser = userDao.getUserById(uid);
                if (userUser != null) {
                    User user = new User();
                    user.setEmail(userUser.getEmail());
                    user.setMobile(String.valueOf(userUser.getMobile()));
                    user.setUid(String.valueOf(uid));
                    user.setName(userUser.getName());
                    profileObj.setUser(user);
                }
                logger.info("profileParser getUser:{}", JSON.toJSONString(profileObj.getUser()));

                // 证书
                logger.info("profileParser resumeObj.getResult().getCert_objs():{}", JSON.toJSONString(resumeObj.getResult().getCert_objs()));
                List<Credential> credentialList = new ArrayList<>();
                if (resumeObj.getResult().getCert_objs() != null && resumeObj.getResult().getCert_objs().size() > 0) {
                    for (CertObj certObj : resumeObj.getResult().getCert_objs()) {
                        Credential credential = new Credential();
                        credential.setName(certObj.getLangcert_name());
                        credentialList.add(credential);
                    }
                }
                profileObj.setCredentials(credentialList);
                logger.info("profileParser getCredentials:{}", JSON.toJSONString(profileObj.getCredentials()));

                // basic信息
                logger.info("profileParser resumeObj.getResult().getCity():{}", resumeObj.getResult().getCity());
                logger.info("profileParser resumeObj.getResult().getGender():{}", resumeObj.getResult().getGender());
                logger.info("profileParser resumeObj.getResult().getName():{}", resumeObj.getResult().getName());
                logger.info("profileParser resumeObj.getResult().getCont_my_desc():{}", resumeObj.getResult().getCont_my_desc());
                logger.info("profileParser resumeObj.getResult().getBirthday():{}", resumeObj.getResult().getBirthday());
                Basic basic = new Basic();
                basic.setCityName(resumeObj.getResult().getCity());
                basic.setGender(resumeObj.getResult().getGender());
                basic.setName(resumeObj.getResult().getName());
                basic.setSelfIntroduction(resumeObj.getResult().getCont_my_desc());
                basic.setBirth(resumeObj.getResult().getBirthday());
                profileObj.setBasic(basic);
                logger.info("profileParser getBasic:{}", JSON.toJSONString(profileObj.getBasic()));

                profileObj.setResumeObj(resumeObj);
                logger.info("profileParser getResumeObj:{}", JSON.toJSONString(profileObj.getResumeObj()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("profileParser:{}", JSON.toJSONString(profileObj));
        return ResponseUtils.success(profileObj);
    }

    public Response checkProfileOther(int userId, int positionId) {
        int appCvConfigId = positionEntity.getAppCvConfigIdByPosition(positionId);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("id", appCvConfigId);
        HrAppCvConfDO hrAppCvConfDO = hrAppCvConfDao.getData(queryBuilder.buildQuery());
        if (hrAppCvConfDO == null || StringUtils.isNullOrEmpty(hrAppCvConfDO.getFieldValue())) {
            return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",true);put("resultMsg","");}});
        } else {
            queryBuilder.clear();
            queryBuilder.where("user_id", userId);
            ProfileProfileDO profileProfile = dao.getData(queryBuilder.buildQuery());
            if (profileProfile == null || profileProfile.getId() == 0) {
                return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",false); put("resultMsg","获取简历失败");}});
            }
            queryBuilder.clear();
            queryBuilder.where("profile_id", profileProfile.getId());
            ProfileOtherDO profileOther = profileOtherDao.getData(queryBuilder.buildQuery());
            if (profileOther == null || StringUtils.isNullOrEmpty(profileOther.getOther())) {
                return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",false);put("resultMsg","自定义简历为空");}});
            }
            JSONObject profileOtherJson = JSONObject.parseObject(profileOther.getOther());
            List<JSONObject> appCvConfigJson = JSONArray.parseArray(hrAppCvConfDO.getFieldValue()).getJSONObject(0).getJSONArray("fields").stream().
                    map(m -> JSONObject.parseObject(String.valueOf(m))).filter(f -> f.getIntValue("required") == 0).collect(Collectors.toList());
            Object customResult = null;
            for (JSONObject appCvConfig : appCvConfigJson) {
                if (appCvConfig.containsKey("map") && StringUtils.isNotNullOrEmpty(appCvConfig.getString("map"))) {
                    // 复合字段校验
                    String mappingFiled = appCvConfig.getString("map");
                    if (mappingFiled.contains("&")) {
                        String[] mapStr = mappingFiled.split("&", 2);
                        String[] mapLeft = mapStr[0].split("\\.", 2);
                        String[] mapRight = mapStr[1].split("\\.", 2);
                        customResult = profileOtherDao.customSelect(mapLeft[0], mapLeft[1], "profile_id", profileProfile.getId());
                        customResult = profileOtherDao.customSelect(mapRight[0], mapRight[1], mapStr[0].replace(".", "_"), NumberUtils.toInt(String.valueOf(customResult), 0));
                    } else if (mappingFiled.contains(".")) {
                        String[] mappingStr = mappingFiled.split("\\.", 2);
                        customResult = mappingStr[0].startsWith("user") ? (userDao.customSelect(mappingStr[0], mappingStr[1], profileProfile.getUserId())) : (profileOtherDao.customSelect(mappingStr[0], mappingStr[1], "profile_id", profileProfile.getId()));
                    } else {
                        return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",false);put("resultMsg","自定义字段"+appCvConfig.getString("field_name")+"为空");}});
                    }
                } else {
                    // 普通字段校验
                    if (profileOtherJson.containsKey(appCvConfig.getString("field_name"))) {
                        customResult = profileOtherJson.get(appCvConfig.getString("field_name"));
                    } else {
                        return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",false);put("resultMsg","自定义字段"+appCvConfig.getString("field_name")+"为空");}});
                    }
                }
                if (!Pattern.matches(appCvConfig.getString("validate_re"), String.valueOf(customResult))) {
                    return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",false);put("resultMsg","自定义字段"+appCvConfig.getString("field_name")+"校验失败");}});
                }
            }
        }
        return ResponseUtils.success(new HashMap<String, Object>(){{put("result:",true);put("resultMsg","");}});
    }


    public Response getProfileOther(String params) {
        List<JSONObject> paramsStream = JSONArray.parseArray(params).parallelStream().map(m -> JSONObject.parseObject(String.valueOf(m))).collect(Collectors.toList());
        // 批量获取职位自定义配置&简历自定义字段
        List<Integer> positionIds = paramsStream.parallelStream().map(m -> m.getIntValue("positionId")).collect(Collectors.toList());
        List<Integer> profileIds = paramsStream.parallelStream().map(m -> m.getIntValue("profileId")).collect(Collectors.toList());
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition("profile_id", profileIds, ValueOp.IN));
        List<ProfileOtherDO> profileOtherDOList = profileOtherDao.getDatas(queryBuilder.buildQuery());
        Map<Integer, String> profileOtherMap = (profileOtherDOList == null || profileOtherDOList.isEmpty()) ? new HashMap<>() :
                profileOtherDOList.parallelStream().collect(Collectors.toMap(k -> k.getProfileId(), v -> v.getOther(), (oldKey, newKey) -> newKey));
        Map<Integer, Integer> positionCustomConfigMap =  positionEntity.getAppCvConfigIdByPositions(positionIds);
        queryBuilder.clear();
        queryBuilder.where(new Condition("id", new ArrayList<>(positionCustomConfigMap.values()), ValueOp.IN));
        List<HrAppCvConfDO> hrAppCvConfDOList = hrAppCvConfDao.getDatas(queryBuilder.buildQuery());
        Map<Integer, String> positionOtherMap = (hrAppCvConfDOList == null || hrAppCvConfDOList.isEmpty()) ? new HashMap<>() :
                hrAppCvConfDOList.parallelStream().collect(Collectors.toMap(k -> k.getId(), v -> v.getFieldValue()));
        paramsStream.stream().forEach(e -> {
            int positionId = e.getIntValue("positionId");
            int profileId = e.getIntValue("profileId");
            e.put("other", "");
            if (positionCustomConfigMap.containsKey(positionId)) {
                JSONObject profileOtherJson = JSONObject.parseObject(profileOtherMap.get(profileId));
                Map<String, String> otherMap = JSONArray.parseArray(positionOtherMap.get(positionCustomConfigMap.get(positionId))).getJSONObject(0).getJSONArray("fields").stream().
                        map(m -> JSONObject.parseObject(String.valueOf(m))).map(mm -> mm.getString("field_name")).collect(Collectors.toMap(k -> k, v -> org.apache.commons.lang.StringUtils.defaultIfBlank(profileOtherJson.getString(v), ""), (oldKey, newKey) -> newKey));
                e.put("other", otherMap);
            }
        });
        return ResponseUtils.success(paramsStream);
    }
}
