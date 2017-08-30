package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.entity.ProfileEntity;
import com.moseeker.profile.pojo.profile.Education;
import com.moseeker.profile.pojo.profile.ProfileObj;
import com.moseeker.profile.pojo.profile.Projectexps;
import com.moseeker.profile.pojo.resume.EducationObj;
import com.moseeker.profile.pojo.resume.ProjectexpObj;
import com.moseeker.profile.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ProfileCompletenessImpl completenessImpl;

    @Autowired
    private ProfileEntity profileEntity;

    public Response getResource(Query query) throws TException {
        ProfileProfileRecord record = null;
        record = dao.getRecord(query);
        if (record != null) {
            Profile s = dao.recordToData(record, Profile.class);
            if (record.getCompleteness().intValue() == 0 || record.getCompleteness().intValue() == 10) {
                int completeness = completenessImpl.getCompleteness(record.getUserId().intValue(), record.getUuid(),
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
        int totalComplementness = completenessImpl.getCompleteness(userId, uuid, profileId);
        return ResponseUtils.success(totalComplementness);
    }

    public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
        completenessImpl.reCalculateUserUserByUserIdOrMobile(userId, mobile);
        int totalComplementness = completenessImpl.getCompleteness(userId, null, 0);
        return ResponseUtils.success(totalComplementness);
    }

    public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(id));
        UserSettingsRecord record = settingDao.getRecord(qu);
        if (record != null) {
            completenessImpl.reCalculateUserUserByUserIdOrMobile(record.getUserId().intValue(), null);
            int totalComplementness = completenessImpl.getCompleteness(record.getUserId().intValue(), null, 0);
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

        return ResponseUtils.success(ProfileUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
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
            // 调用成功,开始转换对象
            if (resumeObj.getStatus().getCode() == 200) {
                // 项目经验
                List<Projectexps> projectexps = new ArrayList<>();
                if (resumeObj.getResult().getProj_exp_objs() != null && resumeObj.getResult().getProj_exp_objs().size() > 0) {
                    for (ProjectexpObj projectexpObj : resumeObj.getResult().getProj_exp_objs()) {
                        Projectexps project = new Projectexps();
                        if (projectexpObj.getEnd_date().equals("至今")) {
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

                // 教育经历
                List<Education> educationList = new ArrayList<>();
                if (resumeObj.getResult().getEducation_objs() != null && resumeObj.getResult().getEducation_objs().size() > 0) {
                    for (EducationObj educationObj : resumeObj.getResult().getEducation_objs()) {
                        Education education = new Education();
                        education.setDegree(educationObj.getEdu_degree());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtils.success(profileObj);
    }


}
