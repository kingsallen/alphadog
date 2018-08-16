package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysCvTplDao;
import com.moseeker.baseorm.dao.hrdb.HrAppCvConfDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.FileUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.*;
import com.moseeker.entity.biz.CommonUtils;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.domain.ResumeEntity;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.utils.ProfileMailUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysCvTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppCvConfDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import com.moseeker.thrift.gen.profile.struct.UserProfile;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.Consts;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@CounterIface
public class ProfileService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    protected UserHrAccountDao userHrAccountDao;

    @Autowired
    protected ProfileProfileDao dao;

    @Autowired
    protected UserUserDao userDao;

    @Autowired
    protected ProfileCompletenessDao completenessDao;

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private UserSettingsDao settingDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ProfileOtherEntity profileOtherEntity;

    @Autowired
    private HrAppCvConfDao hrAppCvConfDao;

    @Autowired
    private PositionEntity positionEntity;

    @Autowired
    private ProfileOtherDao profileOtherDao;

    @Autowired
    private ConfigSysCvTplDao configSysCvTplDao;

    @Autowired
    private TalentPoolEntity talentPoolEntity;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    @Autowired
    private ProfileMailUtil profileMailUtil;

    @Autowired
    private ResumeEntity resumeEntity;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private EmployeeEntity employeeEntity;


    JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
            .getService(JobApplicationServices.Iface.class);

    ThreadPool pool = ThreadPool.Instance;

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

        profileCompanyTagService.handlerCompanyTag(record.getId(),struct.getUser_id());

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

    public Response getProfileTokenDecrypt(String token) throws TException {
        try {
            String info = CommonUtils.stringDecrypt(token);
            Map<String, String> params = new HashMap<>();
            String[] tokenInfo = info.split("&");
            for (int i =0; i<tokenInfo.length ; i++){
                String str = tokenInfo[i];
                String[] strSplit = str.split("=");
                if(strSplit!=null && strSplit.length==2){
                    params.put(strSplit[0],strSplit[1]);
                }
            }
            return  ResponseUtils.success(params);
        } catch (Exception e) {
            logger.error("简历详情token解析失败：token：{}；e.getMessage:{}", token, e.getMessage());
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
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
        for(Profile profile:structs){
            profileCompanyTagService.handlerCompanyTag(profile.getId(),profile.getUser_id());
        }
        return ResponseUtils.success("1");
    }

    @Transactional
    public Response putResources(List<Profile> structs) throws TException {
        int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileProfileRecord.class));
        if (ArrayUtils.contains(result, 1)) {
            for(Profile profile:structs){
                profileCompanyTagService.handlerCompanyTag(profile.getId(),profile.getUser_id());
            }
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResources(List<Profile> structs) throws TException {
        int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileProfileRecord.class));
        if (ArrayUtils.contains(result, 1)) {
            for(Profile profile:structs){
                profileCompanyTagService.handlerCompanyTag(profile.getId(),profile.getUser_id());
            }
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    @Transactional
    public Response putResource(Profile struct) throws TException {
        int result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileProfileRecord.class));
        if (result > 0) {
            profileCompanyTagService.handlerCompanyTag(struct.getId(),struct.getUser_id());
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResource(Profile struct) throws TException {
        int result = dao.deleteRecord(BeanUtils.structToDB(struct, ProfileProfileRecord.class));
        if (result > 0) {
            profileCompanyTagService.handlerCompanyTag(struct.getId(),struct.getUser_id());
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
        String uuid = UUID.randomUUID().toString();
        ProfileObj profileObj = parseByResumeSDK(fileName, file, uuid, uid);
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
        logger.info("profileParser:{}", JSON.toJSONString(profileObj));
        return ResponseUtils.success(profileObj);
    }

    /**
     * 解析简历
     *
     * @param fileName
     * @param file
     * @return
     * @throws TException
     */
    public Response profileParser(String fileName, String file) throws TException {
        String uuid = UUID.randomUUID().toString();
        ProfileObj profileObj = parseByResumeSDK(fileName, file, uuid, 0);
        logger.info("profileParser:{}", JSON.toJSONString(profileObj));
        return ResponseUtils.success(profileObj);
    }
    /**
     * 通过resumeSDK服务商解析文本文件成一个我们可识别的简历数据
     * @param fileName 文件名称
     * @param file 文件
     * @param uuid profile uuid
     * @param uid 用户编号
     * @return 简历数据
     */
    private ProfileObj parseByResumeSDK(String fileName, String file, String uuid, int uid) {
        ProfileObj profileObj = new ProfileObj();
        try {
            // 调用SDK得到结果
            ResumeObj resumeObj = profileEntity.profileParserAdaptor(fileName, file);
            // 验证ResumeSDK解析剩余调用量是否大于1000，如果小于1000则发送预警邮件
            validateParseLimit(resumeObj);
            logger.info("profileParser resumeObj:{}", JSON.toJSONString(resumeObj));
            // 调用成功,开始转换对象,我把它单独独立出来
            profileObj=resumeEntity.handlerParseData(resumeObj,uid,fileName);
            resumeEntity.fillProfileObj(profileObj, resumeObj, uid, fileName, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("profileParser:{}", JSON.toJSONString(profileObj));
        return profileObj;
    }

    /**
     * 验证ResumeSDK解析剩余调用量是否大于1000，如果小于1000则发送预警邮件
     * @param
     * @author  cjm
     * @date  2018/6/26
     * @return
     */
    private void validateParseLimit(ResumeObj resumeObj) {
        if(resumeObj != null && resumeObj.getAccount() != null && resumeObj.getAccount().getUsage_remaining() < 1000){
            profileMailUtil.sendProfileParseWarnMail(resumeObj.getAccount());
        }
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
     * 根据申请者的简历编号和申请的有效职位获取申请者自定义简历的自定义数据结构
     *
     * @param positionIds   申请的有效职位
     * @param profileId     申请者的简历编号
     * @return
     * @throws CommonException
     */
    public Map<String, Object> getProfileOther(List<Integer> positionIds, int profileId) throws CommonException{
        long start = System.currentTimeMillis();
        Map<String, Object> otherMap = new HashMap<>();
        Map<String, Object> parentValues = new HashMap<>();
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(ProfileOther.PROFILE_OTHER.PROFILE_ID.getName(), profileId);
        ProfileOtherDO profileOtherDO = profileOtherDao.getData(queryBuilder.buildQuery());
        if (profileOtherDO == null) {
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        Map<String, Object> otherDatas = JSON.parseObject(profileOtherDO.getOther(), Map.class);
        if(positionIds != null && positionIds.size()>0 && profileId > 0) {
            logger.info("positionIdList:{}", positionIds);
            long profileTime = System.currentTimeMillis();
            logger.info("getProfileOther others profile time:{}", profileTime - start);
            Map<Integer, Integer> positionCustomConfigMap = positionEntity.getAppCvConfigIdByPositions(positionIds);
            queryBuilder.clear();
            long configTime = System.currentTimeMillis();
            logger.info("getProfileOther others config time:{}", configTime - profileTime);
            queryBuilder.where(new Condition("id", new ArrayList<>(positionCustomConfigMap.values()), ValueOp.IN));
            List<HrAppCvConfDO> hrAppCvConfDOList = hrAppCvConfDao.getDatas(queryBuilder.buildQuery());
            //获取申请职位的自定义简历字段
            long cvConfTime = System.currentTimeMillis();
            logger.info("getProfileOther others cvConfTime time:{}", cvConfTime - configTime);
            Map<Integer, String> positionOtherMap = (hrAppCvConfDOList == null || hrAppCvConfDOList.isEmpty()) ? new HashMap<>() :
                    hrAppCvConfDOList.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getFieldValue()));
            //遍历职位获取职位与人自定义字段交集
            long infoTime = System.currentTimeMillis();
            logger.info("getProfileOther others info time:{}", profileTime - infoTime);
            logger.info("getProfileOther others info time:{}", infoTime - start);
            for(Integer positionId : positionIds){
                if(positionCustomConfigMap.containsKey(positionId)){
                    JSONArray otherCvTplMap = JSONArray.parseArray(positionOtherMap.get(positionCustomConfigMap.get(positionId)));
                    if(otherCvTplMap != null && otherCvTplMap.size()>0){
                        for(int i=0;i<otherCvTplMap.size();i++){
                            JSONObject apJson = otherCvTplMap.getJSONObject(i);
                            List<Map<String, Object>> fieldList = (List<Map<String, Object>>)apJson.get("fields");
                            if(!StringUtils.isEmptyList(fieldList)){
                                for(Map<String, Object> obj : fieldList){
                                    if(obj.get("parent_id") != null && ((int)obj.get("parent_id")) == 0){
                                        if(obj.get("field_name") != null ) {
                                            String field_name = (String)obj.get("field_name");
                                            parentValues.put(field_name, otherDatas.get(field_name));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            long positionTime = System.currentTimeMillis();
            logger.info("getProfileOther others position time:{}", positionTime - infoTime);
        }else{
            parentValues.putAll(otherDatas);
        }
        otherMap = profileOtherEntity.handerOtherInfo(parentValues);
        long end = System.currentTimeMillis();
        logger.info("getProfileOther others time:{}", end-start);
//        }
//        profileParseUtil.handerSortprofileOtherMap(otherMap);
        return otherMap;
    }

    public Map<String, Object> getApplicationOtherCommon(int userId, int accountId, int positionId) {
        ProfileProfileRecord profileRecord = dao.getProfileByUserId(userId);
        if (profileRecord == null)
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        Integer profileId = profileRecord.getId();
        List<JobApplicationDO> applicationDOS = new ArrayList<>();
        List<Integer> updateList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        if (positionId <= 0 && accountId > 0) {
            profileOtherEntity.getApplicationByHrAndApplier(accountId, userId, applicationDOS, updateList);
        } else if(positionId > 0) {
            Query applicationQuery = new Query.QueryBuilder().where(JobApplication.JOB_APPLICATION.POSITION_ID.getName(), positionId)
                    .and(JobApplication.JOB_APPLICATION.APPLIER_ID.getName(), userId).buildQuery();
            JobApplicationDO application = jobApplicationDao.getData(applicationQuery);
            if (application != null) {
                applicationDOS.add(application);
                if (((int) application.getIsViewed()) == 1) {
                    updateList.add(application.getId());
                }
            }
        }
        List<Integer> positionList = new ArrayList<>();
        logger.info("有效申请职位：{}；数量：{}", applicationDOS, applicationDOS.size());
        if(applicationDOS != null && applicationDOS.size()>0){
            positionList = applicationDOS.stream().map(m -> m.getPositionId()).collect(Collectors.toList());
        }
        params.put("profile_id", profileId);
        params.put("updateList", updateList);
        params.put("positionList", positionList);
        return params;
    }

    public void viewApplicationsByApplication(int accountId, List<Integer> updateList){
        if (updateList != null && updateList.size() > 0) {
            pool.startTast(() -> viewApplications(accountId, updateList));
        }
    }

    public String viewApplications(int accountId, List<Integer> updateList){
        try {
            logger.info("查看简历 viewApplications accountId:{}; updateList:{}", accountId, updateList);
            applicationService.viewApplications(accountId, updateList);
        } catch (Exception e) {
            logger.info("申请查看状态更新以及发送模板消息出错");
        }
        return null;
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
                logger.warn("自定义字段校验失败! field_name:{}, value:{}, error_msg:{}", fieldName, customResult, configSysCvTplDO.getErrorMsg());
            }
        });
        return ResponseUtils.success(result);
    }
    /*
     人才库简历上传
     */
    public Response talentpoolUploadParse(String fileName,String fileData,int companyId) throws TException, IOException {
        Map<String, Object> result = new HashMap<>();
        ResumeObj resumeObj = profileEntity.profileParserAdaptor(fileName, fileData);
        // 验证ResumeSDK解析剩余调用量是否大于1000，如果小于1000则发送预警邮件
        validateParseLimit(resumeObj);
        logger.info(JSON.toJSONString(resumeObj));
        result.put("resumeObj", resumeObj);
        if (resumeObj.getStatus().getCode() == 200) {
            String phone = resumeObj.getResult().getPhone();
            int userId = 0;
            if (StringUtils.isNotNullOrEmpty(phone)) {
                UserUserRecord userRecord = userAccountEntity.getCompanyUser(phone, companyId);
                if (userRecord != null) {
                    userId = userRecord.getId();
                }

            }
            ProfileObj profileObj = resumeEntity.handlerParseData(resumeObj, userId, fileName);
            resumeEntity.fillProfileObj(profileObj, resumeObj, 0, fileName, null);
            logger.info(JSON.toJSONString(profileObj));
            result.put("profile", profileObj);
        } else {
            ResponseUtils.fail(1, "解析失败");
        }
        return ResponseUtils.success(result);
    }

    public List<UserProfile> fetchUserProfile(List<Integer> userIdList) {
        return profileEntity.fetchUserProfile(userIdList);
    }

    /**
     * 解析简历数据并产生公司下的虚拟用户以及简历数据
     * @param profile 简历内容
     * @param referenceId 推荐者的编号
     * @return 用户编号
     * @throws ProfileException
     */
    public int parseText(String profile, int referenceId) throws ProfileException {

        UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(referenceId);
        if (employeeDO == null) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        logger.info("parseText ");
        File file;
        try {
            file = FileUtil.createFile("employee_proxy_apply.txt", profile);
        } catch (IOException e) {
            logger.error(this.getClass().getName()+" parseText error", e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }
        byte[] fileBytes;
        try {
            fileBytes = FileUtil.convertToBytes(file);
        } catch (IOException e) {
            logger.error(this.getClass().getName()+" parseText error", e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }
        String data = new String(org.apache.commons.codec.binary.Base64.encodeBase64(fileBytes), Consts.UTF_8);

        // 调用SDK得到结果
        ResumeObj resumeObj;
        try {
            resumeObj = profileEntity.profileParserAdaptor(file.getName(), data);
        } catch (TException | IOException e) {
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }
        // 验证ResumeSDK解析剩余调用量是否大于1000，如果小于1000则发送预警邮件
        validateParseLimit(resumeObj);
        logger.info("profileParser resumeObj:{}", JSON.toJSONString(resumeObj));

        ProfileObj profileObj=resumeEntity.handlerParseData(resumeObj,0,"文本解析，不存在附件");

        if (profileObj.getUser() == null || org.apache.commons.lang.StringUtils.isBlank(profileObj.getUser().getMobile())) {
            throw ProfileException.PROFILE_USER_NOTEXIST;
        }

        resumeEntity.fillProfileObj(profileObj, resumeObj, 0, file.getName(), profile);

        profileObj.setResumeObj(null);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(profileObj);
        JSONObject profileProfile = createProfileData();
        jsonObject.put("profile", profileProfile);

        ProfilePojo profilePojo = profileEntity.parseProfile(jsonObject.toJSONString());

        UserUserRecord userRecord = userAccountEntity.getCompanyUser(
                profilePojo.getUserRecord().getMobile().toString(), employeeDO.getCompanyId());
        int userId;
        if (userRecord != null) {
            userId = userRecord.getId();
            profilePojo.setUserRecord(userRecord);
            profileEntity.mergeProfile(profilePojo, userRecord.getId());
        } else {
            userRecord = profileEntity.storeUser(profilePojo, referenceId, employeeDO.getCompanyId(), UserSource.EMPLOYEE_REFERRAL);
            profilePojo.getProfileRecord().setUserId(userRecord.getId());
            userId = userRecord.getId();
        }

        return userId;
    }

    /**
     * 生成简历来源的数据
     * @return
     */
    private JSONObject createProfileData() {
        JSONObject profileProfile = new JSONObject();
        profileProfile.put("source", 221);                                      //员工主动上传
        profileProfile.put("origin", "10000000000000000000000000000");          //员工主动上传
        profileProfile.put("uuid", UUID.randomUUID().toString());               //员工主动上传
        profileProfile.put("user_id", 0);
        return profileProfile;
    }


}
