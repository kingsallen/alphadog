package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
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
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.pojo.profile.*;
import com.moseeker.entity.pojo.resume.*;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.utils.DegreeSource;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import org.apache.commons.lang.ArrayUtils;
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
            logger.info("profileParser resumeObj:{}", resumeObj);
            // 调用成功,开始转换对象
            if (resumeObj.getStatus().getCode() == 200) {
                // 项目经验
                List<Projectexps> projectexps = new ArrayList<>();
                if (resumeObj.getResult().getProj_exp_objs() != null && resumeObj.getResult().getProj_exp_objs().size() > 0) {
                    for (ProjectexpObj projectexpObj : resumeObj.getResult().getProj_exp_objs()) {
                        Projectexps project = new Projectexps();
                        if (projectexpObj.getEnd_date() != null && projectexpObj.getEnd_date().equals("至今")) {
                            project.setEndUntilNow(1);
                        } else {
                            project.setEndDate(projectexpObj.getEnd_date());
                        }
                        project.setStartDate(projectexpObj.getStart_date());
                        // 职责
                        project.setResponsibility(projectexpObj.getProj_resp());
                        project.setDescription(projectexpObj.getProj_content());
                        projectexps.add(project);
                    }
                }
                profileObj.setProjectexps(projectexps);

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
                        educationList.add(education);
                    }
                }
                profileObj.setEducations(educationList);
                // 技能
                List<Skill> skills = new ArrayList<>();
                if (resumeObj.getResult().getSkills_objs() != null && resumeObj.getResult().getSkills_objs().size() > 0) {
                    for (SkillsObjs skillsObjs : resumeObj.getResult().getSkills_objs()) {
                        Skill skill = new Skill();
                        skill.setName(skillsObjs.getSkills_name());
                        skills.add(skill);
                    }
                }
                profileObj.setSkills(skills);

                // 工作经验
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
                // 语言
                List<Language> languageList = new ArrayList<>();
                if (resumeObj.getResult().getLang_objs() != null && resumeObj.getResult().getLang_objs().size() > 0) {
                    for (LangObj langObj : resumeObj.getResult().getLang_objs()) {
                        Language language = new Language();
                        language.setName(langObj.getLanguage_name());
                        languageList.add(language);
                    }
                }
                profileObj.setLanguages(languageList);

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


                // 证书
                List<Credential> credentialList = new ArrayList<>();
                if (resumeObj.getResult().getCert_objs() != null && resumeObj.getResult().getCert_objs().size() > 0) {
                    for (CertObj certObj : resumeObj.getResult().getCert_objs()) {
                        Credential credential = new Credential();
                        credential.setName(certObj.getLangcert_name());
                        credentialList.add(credential);
                    }
                }
                profileObj.setCredentials(credentialList);

                // basic信息
                Basic basic = new Basic();
                basic.setCityName(resumeObj.getResult().getCity());
                basic.setGender(resumeObj.getResult().getGender());
                basic.setName(resumeObj.getResult().getName());
                basic.setSelfIntroduction(resumeObj.getResult().getCont_my_desc());
                basic.setBirth(resumeObj.getResult().getBirthday());
                profileObj.setBasic(basic);

                profileObj.setResumeObj(resumeObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("profileParser:{}", JSON.toJSONString(profileObj));
        return ResponseUtils.success(profileObj);
    }

}
