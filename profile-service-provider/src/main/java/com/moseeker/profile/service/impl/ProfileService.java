package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysCvTplDao;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrAppCvConfDao;
import com.moseeker.baseorm.dao.logdb.LogResumeDao;
import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
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
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.PositionEntity;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.pojo.profile.*;
import com.moseeker.entity.pojo.resume.*;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.utils.DegreeSource;
import com.moseeker.profile.utils.DictCode;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysCvTplDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppCvConfDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Autowired
    private LogResumeDao resumeDao;

    @Autowired
    private DictCityDao dictCityDao;

    @Autowired
    private DictPositionDao dictPositionDao;

    @Autowired
    private ConfigSysCvTplDao configSysCvTplDao;
    @Autowired
    private TalentPoolEntity talentPoolEntity;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

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
    /*
      整体处理数据
     */
    private List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> handlerApplicationData(List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> datas){
        if(StringUtils.isEmptyList(datas)){
            return null;
        }
        Map<String,Object> paramData=this.getUserPositionData(datas);
        if(paramData==null||paramData.isEmpty()){
            return null;
        }
        //获取投递人的id
        List<Integer> userIdList= (List<Integer>) paramData.get("userIdList");
        //获取职位和投递人之间的关系
        Map<Integer,Set<Integer>> pidUserIdMap= (Map<Integer, Set<Integer>>) paramData.get("pidUserIdMap");
        if(StringUtils.isEmptyList(userIdList)||pidUserIdMap==null||pidUserIdMap.isEmpty()){
            return null;
        }
        //获取投递人的具体信息
        List<UserUserDO> userList=this.getUserUserByIdList(userIdList);
        if(StringUtils.isEmptyList(userList)){
            return null;
        }
        Map<Integer,Set<Integer>> userPositionData=this.handleUserPosition(userList,pidUserIdMap);
        if(userPositionData==null||userPositionData.isEmpty()){
            return null;
        }
        List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> result=this.filteroriginData(datas,userPositionData);
        return result;
    }
    /*
      根据数据获取userId的集合和pid和userid的map
     */
    private Map<String,Object> getUserPositionData(List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> datas){

        if(StringUtils.isEmptyList(datas)){
            return null;
        }
        Map<Integer,Set<Integer>> pidUserIdMap=new HashMap<>();
        List<Integer> userIdList=new ArrayList<>();
        List<Integer> userIds=new ArrayList<>();
        //遍历查询得到的data数据
        //获取用户的集合和<positionid,投递此position的人>的集合
        for (AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> entry : datas) {
            int userId=(int)entry.getValue().get("applier_id");
            int positionId=(int)entry.getKey().get("id");
            userIdList.add(userId);
            Set<Integer> userIdSet=pidUserIdMap.get(positionId);
            if(userIdSet==null||userIdSet.size()==0){
                userIdSet=new HashSet<>();
            }
            userIdSet.add(userId);
            pidUserIdMap.put(positionId,userIdSet);
        }
        if(StringUtils.isEmptyList(userIdList)||pidUserIdMap==null||pidUserIdMap.isEmpty()){
            return null;
        }
        //加入到自定义的map中，返回

        Map<String,Object> result=new HashMap<>();
        result.put("userIdList",userIdList);
        result.put("pidUserIdMap",pidUserIdMap);
        return result;
    }
    /*
      根据userid的集合获取userid的数据
     */
    private List<UserUserDO> getUserUserByIdList(List<Integer> userIdList){
        com.moseeker.common.util.query.Query query=new Query.QueryBuilder().where(new com.moseeker.common.util.query.Condition("id",userIdList.toArray(),ValueOp.IN))
                .buildQuery();
        List<UserUserDO> list=userDao.getDatas(query);
        return list;
    }

    /*
     处理每个position的申请中user的mobile有重复的数据
     */
    private Map<Integer,Set<Integer>> handleUserPosition(List<UserUserDO> list,Map<Integer,Set<Integer>> datas){
        Map<Integer,Set<Integer>> result=new HashMap<>();
        for(Integer key:datas.keySet()){
            //获取投递该职位的人的id
            Set<Integer> userIdSet=datas.get(key);
            //创建set存放处理后的结果
            Set<Integer> userList=new HashSet<>();
            Map<Long,UserUserDO> map=new HashMap<>();
            for(Integer userId:userIdSet){
                for(UserUserDO userDO:list){
                    if(userDO.getId()!=userId){
                        continue;
                    }
                    long mobile=userDO.getMobile();
                    //将userId为空的直接存放
                    if(mobile==0L){
                        userList.add(userId);
                        break;
                    }
                    //如果mobile没有，那么直接存放
                    if(map.get(mobile)==null){
                        map.put(mobile,userDO);
                    }else{
                        //如果存在，那么选出有username的
                        String userName=userDO.getUsername();
                        UserUserDO DO=map.get(mobile);
                        String nowUserName=DO.getUsername();
                        if(StringUtils.isNotNullOrEmpty(userName)){
                            if(userName.equals(String.valueOf(mobile))){
                                if(StringUtils.isNotNullOrEmpty(nowUserName)){
                                    if(!nowUserName.equals(String.valueOf(mobile))){
                                        map.put(mobile,userDO);
                                    }
                                }else{
                                    //如果现在存储的user的username为空，那么替换成最新的
                                    map.put(mobile,userDO);
                                }
                            }else{
                                if(StringUtils.isNullOrEmpty(nowUserName)){
                                    //如果现存的userDo中username为空，那么替换
                                    map.put(mobile,userDO);
                                }
                            }
                        }
                        break;

                    }

                }
            }
            if(map!=null&&!map.isEmpty()){
                for(Long key1:map.keySet()){
                    UserUserDO userUserDO=map.get(key1);
                    int userId=userUserDO.getId();
                    userList.add(userId);
                }
            }
            if(userList!=null&&userList.size()>0){
                result.put(key,userList);
            }


        }
        return result;
    }
    //过滤数据
    private List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>>  filteroriginData(List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> datas,
                                  Map<Integer,Set<Integer>> map){
        //根据先前的获得的职位和员工的对应关系，过滤掉老数据中无用的数据
        List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>> result=new ArrayList<>();
        for(AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> entry : datas){
            int positionId=(int)entry.getKey().get("id");
            int applierId=(int)entry.getValue().get("applier_id");
            for(Integer key:map.keySet()){
                if(key==positionId){
                    Set<Integer> userIdSet=map.get(key);
                    if(userIdSet!=null&&!userIdSet.isEmpty()){
                        if(userIdSet.contains(applierId)){
                            AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>> data=
                                    new AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>(entry.getKey(),entry.getValue());
                            result.add(data);

                        }
                    }
                }
            }

        }
        return result;

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
        List<AbstractMap.SimpleEntry<Map<String, Object>, Map<String, Object>>>   positionApplications = dao.getResourceByApplication(downloadUrl, password, profileApplicationForm);
        logger.info("原始数据=======================================");
        logger.info(JSON.toJSONString(positionApplications));
        logger.info("处理后的数据=======================================");
        positionApplications=handlerApplicationData(positionApplications);
        if(StringUtils.isEmptyList(positionApplications)){
            return ResponseUtils.success("");
        }

        logger.info("=================================================");
        List<Map<String, Object>> datas =dao.getRelatedDataByJobApplication( positionApplications, downloadUrl, password, profileApplicationForm.isRecommender(), profileApplicationForm.isDl_url_required(), profileApplicationForm.getFilter());
        return dao.handleResponse(datas);
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
            // 调用成功,开始转换对象,我把它单独独立出来
            profileObj=handlerParseData(resumeObj,uid,fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("profileParser:{}", JSON.toJSONString(profileObj));
        return ResponseUtils.success(profileObj);
    }


    private ProfileObj handlerParseData(ResumeObj resumeObj,int uid,String fileName ){
        ProfileObj profileObj = new ProfileObj();
        if (resumeObj.getStatus().getCode() == 200) {
            // 项目经验
            logger.info("profileParser resumeObj.getResult().getProj_exp_objs():{}", JSON.toJSONString(resumeObj.getResult().getProj_exp_objs()));
            List<Projectexps> projectexps = new ArrayList<>();
            if (resumeObj.getResult().getProj_exp_objs() != null && resumeObj.getResult().getProj_exp_objs().size() > 0) {
                for (ProjectexpObj projectexpObj : resumeObj.getResult().getProj_exp_objs()) {
                    Projectexps project = new Projectexps();
                    try {
                        if (projectexpObj.getEnd_date() != null && projectexpObj.getEnd_date().equals("至今")) {
                            project.setEndUntilNow(1);
                        } else {
                            project.setEndDate(DateUtils.dateRepair(projectexpObj.getEnd_date(), "\\."));
                        }
                        project.setStartDate(DateUtils.dateRepair(projectexpObj.getStart_date(), "\\."));
                    } catch (ParseException e) {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("日期转换异常: " + e.getMessage());
                        logResumeRecordRecord.setFieldValue("projectexp: {\"startDate\": " + projectexpObj.getStart_date() + "\", \"endDate\":" + projectexpObj.getEnd_date()+"}");
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                        resumeDao.addRecord(logResumeRecordRecord);
                        logger.error(e.getMessage(), e);
                    }
                    // 职责
                    project.setResponsibility(projectexpObj.getProj_resp());
                    project.setDescription(projectexpObj.getProj_content());
                    project.setName(projectexpObj.getProj_name());
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
                    try {
                        education.setStartDate(DateUtils.dateRepair(educationObj.getStart_date(), "\\."));
                        if (educationObj.getEnd_date() != null && educationObj.getEnd_date().equals("至今")) {
                            education.setEndUntilNow(1);
                        } else {
                            education.setEndDate(DateUtils.dateRepair(educationObj.getEnd_date(), "\\."));
                        }
                    } catch (ParseException e) {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("日期转换异常: " + e.getMessage());
                        logResumeRecordRecord.setFieldValue("education: {\"startDate\": " + educationObj.getStart_date() + "\", \"endDate\":" + educationObj.getEnd_date()+"}");
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                        resumeDao.addRecord(logResumeRecordRecord);
                        logger.error(e.getMessage(), e);
                    }
                    // 学校名称
                    education.setCollegeName(educationObj.getEdu_college());
                    // 专业名称
                    education.setMajorName(educationObj.getEdu_major());
                    if (StringUtils.isNotNullOrEmpty(educationObj.getEdu_recruit())) {
                        education.setIsUnified(educationObj.getEdu_recruit().equals("统招") ? 1 : 2);
                    }
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
                    if (StringUtils.isNotNullOrEmpty(jobExpObj.getJob_cpy())) {
                        company.setCompanyName(jobExpObj.getJob_cpy());
                    } else {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("公司名称为空");
                        logResumeRecordRecord.setFieldValue("job_exp_obj: " + JSONObject.toJSONString(jobExpObj));
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                        resumeDao.addRecord(logResumeRecordRecord);
                    }
                    int companyScaleMaxValue = 0;
                    try {
                        companyScaleMaxValue = Arrays.stream(org.apache.commons.lang.StringUtils.defaultIfBlank(jobExpObj.getJob_cpy_size() == null ? "0-0" :
                                jobExpObj.getJob_cpy_size().replaceAll("[\\u4E00-\\u9FA5]", ""), "0-0").split("-", 2)).max(String::compareTo).map(m -> Integer.valueOf(m)).get();
                    } catch (Exception e) {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("公司规模转换异常: " + e.getMessage());
                        logResumeRecordRecord.setFieldValue("companyScale: " + jobExpObj.getJob_cpy_size());
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                        resumeDao.addRecord(logResumeRecordRecord);
                        logger.error(e.getMessage(), e);
                    }
                    company.setCompanyScale(String.valueOf(DictCode.companyScale(companyScaleMaxValue)));
                    workexps.setCompany(company);
                    workexps.setDescription(org.apache.commons.lang.StringUtils.defaultIfBlank(jobExpObj.getJob_cpy_desc(), jobExpObj.getJob_content()));
                    try {
                        workexps.setStartDate(DateUtils.dateRepair(jobExpObj.getStart_date(), "\\."));
                        if (jobExpObj.getEnd_date() != null && jobExpObj.getEnd_date().equals("至今")) {
                            workexps.setEndUntilNow(1);
                        } else {
                            workexps.setEndDate(DateUtils.dateRepair(jobExpObj.getEnd_date(), "\\."));
                        }
                    } catch (Exception e) {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("日期转换异常: " + e.getMessage());
                        logResumeRecordRecord.setFieldValue("workexp: {\"startDate\": " + jobExpObj.getStart_date() + "\", \"endDate\":" + jobExpObj.getEnd_date()+"}");
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                        resumeDao.addRecord(logResumeRecordRecord);
                        logger.error(e.getMessage(), e);
                    }
                    if (StringUtils.isNullOrEmpty(jobExpObj.getJob_position())) {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("工作职位为空: ");
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                        resumeDao.addRecord(logResumeRecordRecord);
                    } else {
                        workexps.setJob(jobExpObj.getJob_position());
                    }
                    if (StringUtils.isNotNullOrEmpty(jobExpObj.getJob_nature())) {
                        workexps.setType(DictCode.workType(jobExpObj.getJob_nature()));
                    }
                    workexps.setDepartmentName(jobExpObj.getJob_cpy_dept());
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
            }else{
                User user = new User();
                user.setEmail(resumeObj.getResult().getEmail());
                user.setMobile(resumeObj.getResult().getPhone());
                if(uid!=0){
                    user.setUid(String.valueOf(uid));
                }
                user.setName(resumeObj.getResult().getName());
                profileObj.setUser(user);
            }
            logger.info("profileParser getUser:{}", JSON.toJSONString(profileObj.getUser()));

            // 期望
            Intentions intentions = new Intentions();
            if (resumeObj.getResult().getExpect_jnature() != null) {
                intentions.setWorktype(DictCode.workType(resumeObj.getResult().getExpect_jnature()));
            }

            if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getExpect_salary())) {
                try{
                    int topSalary = Arrays.stream(resumeObj.getResult().getExpect_salary().replaceAll("[\\u4E00-\\u9FA5|/]", "").split("-|~", 2)).max(String::compareTo).map(m -> Integer.valueOf(m)).get();
                    intentions.setSalaryCode(String.valueOf(DictCode.salary(topSalary)));
                } catch (Exception e) {
                    LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                    logResumeRecordRecord.setErrorLog("期望薪资转换异常: " + e.getMessage());
                    logResumeRecordRecord.setFieldValue("expectSalary: " + resumeObj.getResult().getExpect_salary());
                    logResumeRecordRecord.setUserId(uid);
                    logResumeRecordRecord.setFileName(fileName);
                    logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                    resumeDao.addRecord(logResumeRecordRecord);
                    logger.error(e.getMessage(), e);
                }
            }

            // 期望城市
            if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getExpect_jlocation())) {
                Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
                queryBuilder.where("name", resumeObj.getResult().getExpect_jlocation());
                DictCityDO dictCityDO = dictCityDao.getData(queryBuilder.buildQuery());
                if (dictCityDO == null || dictCityDO.getCode() == 0) {
                    LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                    logResumeRecordRecord.setErrorLog("期望城市转换异常: ");
                    logResumeRecordRecord.setFieldValue("expectJlocation: " + resumeObj.getResult().getExpect_jlocation());
                    logResumeRecordRecord.setUserId(uid);
                    logResumeRecordRecord.setFileName(fileName);
                    logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                    resumeDao.addRecord(logResumeRecordRecord);
                } else {
                    City city = new City();
                    city.setCityCode(dictCityDO.getCode());
                    city.setCityName(dictCityDO.getName());
                    intentions.setCities(new ArrayList<City>(){{add(city);}});
                }
            }

            // 期望职能
            if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getExpect_job())) {
                Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
                queryBuilder.where(new Condition("name", resumeObj.getResult().getExpect_job(), ValueOp.LIKE));
                DictPositionDO dictPositionDO = dictPositionDao.getData(queryBuilder.buildQuery());
                if (dictPositionDO == null || dictPositionDO.getCode() == 0) {
                    LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                    logResumeRecordRecord.setErrorLog("期望职能转换异常: ");
                    logResumeRecordRecord.setFieldValue("expectJob: " + resumeObj.getResult().getExpect_job());
                    logResumeRecordRecord.setUserId(uid);
                    logResumeRecordRecord.setFileName(fileName);
                    logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                    resumeDao.addRecord(logResumeRecordRecord);
                } else {
                    Positions positions = new Positions();
                    positions.setPositionCode(dictPositionDO.getCode());
                    positions.setPositionName(dictPositionDO.getName());
                    intentions.setPositions(new ArrayList<Positions>(){{add(positions);}});
                }
            }

            List<Intentions> intentionsList = new ArrayList<>();
            intentionsList.add(intentions);
            profileObj.setIntentions(intentionsList);

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

            if (credentialList.isEmpty() && StringUtils.isNotNullOrEmpty(resumeObj.getResult().getCont_certificate())) {
                LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                logResumeRecordRecord.setErrorLog("证书为空，证书内容却不为空 ");
                logResumeRecordRecord.setFieldValue("contCertificate: " + resumeObj.getResult().getCont_certificate());
                logResumeRecordRecord.setUserId(uid);
                logResumeRecordRecord.setFileName(fileName);
                logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                resumeDao.addRecord(logResumeRecordRecord);
            }

            // basic信息
            logger.info("profileParser resumeObj.getResult().getCity():{}", resumeObj.getResult().getCity());
            logger.info("profileParser resumeObj.getResult().getGender():{}", resumeObj.getResult().getGender());
            logger.info("profileParser resumeObj.getResult().getName():{}", resumeObj.getResult().getName());
            logger.info("profileParser resumeObj.getResult().getCont_my_desc():{}", resumeObj.getResult().getCont_my_desc());
            logger.info("profileParser resumeObj.getResult().getBirthday():{}", resumeObj.getResult().getBirthday());
            Basic basic = new Basic();
            basic.setCityName(resumeObj.getResult().getCity());
            if(StringUtils.isNotNullOrEmpty(resumeObj.getResult().getGender())){
                basic.setGender(String.valueOf(DictCode.gender(resumeObj.getResult().getGender())));
            }
            basic.setName(resumeObj.getResult().getName());
            basic.setSelfIntroduction(resumeObj.getResult().getCont_my_desc());
            if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getBirthday())) {
                try {
                    basic.setBirth(DateUtils.dateRepair(resumeObj.getResult().getBirthday(), "\\."));
                } catch (Exception e) {
                    LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                    logResumeRecordRecord.setErrorLog("出生日期转换异常: " + e.getMessage());
                    logResumeRecordRecord.setFieldValue("birthday: " + resumeObj.getResult().getBirthday());
                    logResumeRecordRecord.setUserId(uid);
                    logResumeRecordRecord.setFileName(fileName);
                    logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                    resumeDao.addRecord(logResumeRecordRecord);
                    logger.error(e.getMessage(), e);
                }
            }
            profileObj.setBasic(basic);
            logger.info("profileParser getBasic:{}", JSON.toJSONString(profileObj.getBasic()));
        }
        return profileObj;
    }

    /**
     * 自定义简历数据校验
     * @param userId
     * @param positionId
     * @return
     */
    public Response checkProfileOther(int userId, int positionId) {
        int appCvConfigId = positionEntity.getAppCvConfigIdByPosition(positionId);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("id", appCvConfigId);
        HrAppCvConfDO hrAppCvConfDO = hrAppCvConfDao.getData(queryBuilder.buildQuery());
        if (hrAppCvConfDO == null || StringUtils.isNullOrEmpty(hrAppCvConfDO.getFieldValue())) {
            return ResponseUtils.success(new HashMap<String, Object>(){{put("result",true);put("resultMsg","");}});
        } else {
            queryBuilder.clear();
            queryBuilder.where("user_id", userId);
            ProfileProfileDO profileProfile = dao.getData(queryBuilder.buildQuery());
            if (profileProfile == null || profileProfile.getId() == 0) {
                return ResponseUtils.success(new HashMap<String, Object>(){{put("result",false); put("resultMsg","获取简历失败");}});
            }
            queryBuilder.clear();
            queryBuilder.where("profile_id", profileProfile.getId());
            ProfileOtherDO profileOther = profileOtherDao.getData(queryBuilder.buildQuery());
            if (profileOther == null || StringUtils.isNullOrEmpty(profileOther.getOther())) {
                profileOther = new ProfileOtherDO();
//                return ResponseUtils.success(new HashMap<String, Object>(){{put("result",false);put("resultMsg","自定义简历为空");}});
            }
            JSONObject profileOtherJson = new JSONObject();
            List<JSONObject> appCvConfigJson = new ArrayList<>();
            try {
                profileOtherJson = JSONObject.parseObject(org.apache.commons.lang.StringUtils.defaultIfBlank(profileOther.getOther(), "{}"));
                appCvConfigJson = JSONArray.parseArray(hrAppCvConfDO.getFieldValue()).stream().flatMap(fm -> JSONObject.parseObject(String.valueOf(fm)).getJSONArray("fields").stream()).
                        map(m -> JSONObject.parseObject(String.valueOf(m))).filter(f -> f.getIntValue("required") == 0 && f.getIntValue("parent_id") == 0).collect(Collectors.toList());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                logger.error("profileOther: {}; hrAppCvConf: {}", profileOther, hrAppCvConfDO);
                return ResponseUtils.success(new HashMap<String, Object>(){{put("result",false);put("resultMsg","自定义简历解析错误");}});
            }
            Object customResult = "";
            for (JSONObject appCvConfig : appCvConfigJson) {
                if (appCvConfig.containsKey("map") && StringUtils.isNotNullOrEmpty(appCvConfig.getString("map"))) {
                    // 复合字段校验
                    String mappingFiled = appCvConfig.getString("map");
                    if (mappingFiled.contains("&")) {
                        String[] mapStr = mappingFiled.split("&", 2);
                        if (mapStr[0].contains(".") && mapStr[1].contains(".")) {
                            String[] mapLeft = mapStr[0].split("\\.", 2);
                            String[] mapRight = mapStr[1].split("\\.", 2);
                            customResult = profileOtherDao.customSelect(mapLeft[0], mapLeft[1], "profile_id", profileProfile.getId());
                            customResult = profileOtherDao.customSelect(mapRight[0], mapRight[1], mapStr[0].replace(".", "_"), NumberUtils.toInt(String.valueOf(customResult), 0));
                        }
                    } else if (mappingFiled.contains(".")) {
                        String[] mappingStr = mappingFiled.split("\\.", 2);
                        customResult = mappingStr[0].startsWith("user") ? (userDao.customSelect(mappingStr[0], mappingStr[1], profileProfile.getUserId())) : (profileOtherDao.customSelect(mappingStr[0], mappingStr[1], "profile_id", profileProfile.getId()));
                    } else {
                        return ResponseUtils.success(new HashMap<String, Object>(){{put("result",false);put("resultMsg","自定义字段#"+appCvConfig.getString("field_name") + "#" + appCvConfig.getString("field_title") + "为空");}});
                    }
                } else {
                    // 普通字段校验
                    customResult = profileOtherJson.get(appCvConfig.getString("field_name"));
                    if (!StringUtils.isJsonNullOrEmpty(customResult)) {
                        customResult = profileOtherJson.get(appCvConfig.getString("field_name"));
                    } else {
                        return ResponseUtils.success(new HashMap<String, Object>(){{put("result",false);put("resultMsg","自定义字段#"+appCvConfig.getString("field_name") + "#" + appCvConfig.getString("field_title") + "为空");}});
                    }
                }
                if (!Pattern.matches(org.apache.commons.lang.StringUtils.defaultIfEmpty(appCvConfig.getString("validate_re"), ""), String.valueOf(customResult))) {
                    return ResponseUtils.success(new HashMap<String, Object>(){{put("result",false);put("resultMsg","自定义字段#"+appCvConfig.getString("field_name") + "#" + appCvConfig.getString("field_title") + "校验失败");}});
                }
            }
        }
        return ResponseUtils.success(new HashMap<String, Object>(){{put("result",true);put("resultMsg","");}});
    }


    /**
     * 获取自定义简历数据
     * @param params
     * @return
     */
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
                hrAppCvConfDOList.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getFieldValue()));
        paramsStream.stream().forEach((JSONObject e) -> {
            int positionId = e.getIntValue("positionId");
            int profileId = e.getIntValue("profileId");
            e.put("other", "");
            try {
                if (positionCustomConfigMap.containsKey(positionId)) {
                    JSONObject profileOtherJson = JSONObject.parseObject(profileOtherMap.get(profileId));
                    if (profileOtherJson != null) {
                        Map<String, Object> parentValue = JSONArray.parseArray(positionOtherMap.get(positionCustomConfigMap.get(positionId)))
                                .stream()
                                .flatMap(fm -> JSONObject.parseObject(String.valueOf(fm)).getJSONArray("fields").stream()).
                                map(m -> JSONObject.parseObject(String.valueOf(m)))
                                .filter(f -> f.getIntValue("parent_id") == 0)
                                .collect(Collectors.toMap(k -> k.getString("field_name"), v -> {
                                    return org.apache.commons.lang.StringUtils
                                            .defaultIfBlank(profileOtherJson.getString(v.getString("field_name")), "");
                                }, (oldKey, newKey) -> newKey));

                        e.put("other", parentValue);
                    }
                }
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
        });
        return ResponseUtils.success(paramsStream);
    }

    /**
     * 校验other指定字段
     * @param fields
     * @return
     */
    public Response otherFieldsCheck(int profielId, String fields) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("id", profielId);
        ProfileProfileDO profileProfile = dao.getData(queryBuilder.buildQuery());
        if (profileProfile == null || profileProfile.getId() == 0) {
            return ResponseUtils.fail(1,"获取简历失败");
        }
        JSONObject fieldJson = JSONObject.parseObject(fields);
        queryBuilder.clear();
        queryBuilder.where(new Condition("field_name", fieldJson.keySet(), ValueOp.IN));
        Map<String, ConfigSysCvTplDO> configSysCvTplMap = new HashMap<>();
        configSysCvTplMap.putAll(configSysCvTplDao.getDatas(queryBuilder.buildQuery()).stream().collect(Collectors.toMap(k -> k.getFieldName(), v -> v)));
        Map<String, Object> result = new HashMap<>();
        fieldJson.keySet().forEach(fieldName -> {
            if (!configSysCvTplMap.containsKey(fieldName)){
                result.put(fieldName, new HashMap<String, Object>(){{put("result",false);put("msg","自定义字段："+fieldName+",不存在");}});
                return;
            }
            Object customResult = "";
            ConfigSysCvTplDO configSysCvTplDO = configSysCvTplMap.get(fieldName);
            if (StringUtils.isNotNullOrEmpty(configSysCvTplDO.getMapping())) {
                // 复合字段校验
                String mappingFiled = configSysCvTplDO.getMapping();
                if (mappingFiled.contains("&")) {
                    String[] mapStr = mappingFiled.split("&", 2);
                    if (mapStr[0].contains(".") && mapStr[1].contains(".")) {
                        String[] mapLeft = mapStr[0].split("\\.", 2);
                        String[] mapRight = mapStr[1].split("\\.", 2);
                        customResult = profileOtherDao.customSelect(mapLeft[0], mapLeft[1], "profile_id", profileProfile.getId());
                        customResult = profileOtherDao.customSelect(mapRight[0], mapRight[1], mapStr[0].replace(".", "_"), NumberUtils.toInt(String.valueOf(customResult), 0));
                    }
                } else if (mappingFiled.contains(".")) {
                    String[] mappingStr = mappingFiled.split("\\.", 2);
                    customResult = mappingStr[0].startsWith("user") ? (userDao.customSelect(mappingStr[0], mappingStr[1], profileProfile.getUserId())) : (profileOtherDao.customSelect(mappingStr[0], mappingStr[1], "profile_id", profileProfile.getId()));
                } else {
                    result.put(fieldName, new HashMap<String, Object>(){{put("result",false);put("msg","自定义字段:"+fieldName+",配置异常");}});
                    return;
                }
            } else if (fieldJson.getString(fieldName).startsWith("[{") && fieldJson.getString(fieldName).endsWith("}]")) {
                result.put(fieldName, new ArrayList<>());
                JSONArray.parseArray(fieldJson.getString(fieldName)).stream().forEach(e -> {
                    Response childRes = otherFieldsCheck(profielId, e.toString());
                    ((List)result.get(fieldName)).add(JSONObject.parseObject(childRes.getData()));
                });
                return;
            } else {
                // 普通字段校验
                customResult = fieldJson.get(fieldName);
            }
            if (Pattern.matches(org.apache.commons.lang.StringUtils.defaultIfEmpty(configSysCvTplDO.getValidateRe(), ""), String.valueOf(customResult))) {
                result.put(fieldName, new HashMap<String, Object>(){{put("result",true);put("msg","success");}});
            } else {
                result.put(fieldName, new HashMap<String, Object>(){{put("result",false);put("msg", org.apache.commons.lang.StringUtils.defaultIfEmpty(configSysCvTplDO.getErrorMsg(),"自定义字段"+fieldName+"为空"));}});
                logger.error("自定义字段校验失败! field_name:{}, value:{}, error_msg:{}", fieldName, customResult, configSysCvTplDO.getErrorMsg());
            }
        });
        return ResponseUtils.success(result);
    }
    /*
     人才库简历上传
     */
    public Response talentpoolUploadParse(String fileName,String fileData,int companyId) throws TException, IOException {
        Map<String,Object> result=new HashMap<>();
        ResumeObj resumeObj = profileEntity.profileParser(fileName, fileData);
        result.put("resumeObj",resumeObj);
        if(resumeObj.getStatus().getCode() == 200){
            String phone=resumeObj.getResult().getPhone();
            int userId=0;
            if(StringUtils.isNotNullOrEmpty(phone)){
                UserUserRecord userRecord=talentPoolEntity.getTalentUploadUser(phone,companyId);
                if(userRecord!=null){
                    userId=userRecord.getId();
                }

            }
            ProfileObj profileObj=handlerParseData(resumeObj,userId,fileName);
            logger.info("==============**********************");
            logger.info(JSON.toJSONString(profileObj));
            logger.info("==============**********************");
            result.put("profile",profileObj);
        }else{
            ResponseUtils.fail(1,"解析失败");
        }
        return ResponseUtils.success(result);
    }
}
