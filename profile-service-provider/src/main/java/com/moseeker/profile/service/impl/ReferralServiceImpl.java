package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.Constant.GenderType;
import com.moseeker.entity.*;
import com.moseeker.entity.application.UserApplyCount;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.domain.ResumeEntity;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.ReferralService;
import com.moseeker.profile.service.impl.resumefileupload.ReferralProfileParser;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.*;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 内推服务
 * @Author: jack
 * @Date: 2018/9/4
 */
@Service
@CounterIface
@PropertySource("classpath:setting.properties")
public class ReferralServiceImpl implements ReferralService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "cacheClient")
    private RedisClient client;

    private ThreadPool tp = ThreadPool.Instance;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private HrOperationRecordDao operationRecordDao;

    @Autowired
    private ProfileCompanyTagService companyTagService;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private ApplicationEntity applicationEntity;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Resource(type=ReferralProfileParser.class)
    private AbstractResumeFileParser abstractResumeFileParser;


    @Autowired
    public ReferralServiceImpl(EmployeeEntity employeeEntity, ProfileEntity profileEntity, ResumeEntity resumeEntity,
                               UserAccountEntity userAccountEntity, ProfileParseUtil profileParseUtil,
                               PositionEntity positionEntity, ReferralEntity referralEntity,

                               Environment env ) {
        this.employeeEntity = employeeEntity;
        this.profileEntity = profileEntity;
        this.resumeEntity = resumeEntity;
        this.userAccountEntity = userAccountEntity;
        this.profileParseUtil = profileParseUtil;
        this.positionEntity = positionEntity;
        this.referralEntity = referralEntity;
        this.env = env;
    }

    /**
     * 员工简历解析
     * @param employeeId 员工编号
     * @param fileName 文件名称
     * @param fileData 文件二进制刘
     * @return 解析结果
     * @throws ProfileException 业务异常
     */
    public ProfileDocParseResult parseFileProfile(int employeeId, String fileName, ByteBuffer fileData) throws ProfileException {
        return abstractResumeFileParser.parseResume(employeeId,fileName,fileData);
//        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
//
//        if (!ProfileDocCheckTool.checkFileName(fileName)) {
//            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
//        }
//
//        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(employeeId);
//        if (employeeDO == null || employeeDO.getId() <= 0) {
//            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
//        }
//
//        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
//        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
//        FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
//        profileDocParseResult.setFile(fileNameData.getFileName());
//        fileNameData.setOriginName(fileName);
//
//        return parseResult(employeeId, fileName, StreamUtils.byteArrayToBase64String(dataArray), fileNameData);

        /*// 调用SDK得到结果
        ResumeObj resumeObj;
        try {
            resumeObj = profileEntity.profileParserAdaptor(fileName, StreamUtils.byteArrayToBase64String(dataArray));
        } catch (TException | IOException e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }
        ProfileObj profileObj = resumeEntity.handlerParseData(resumeObj,0,fileName);
        profileDocParseResult.setMobile(profileObj.getUser().getMobile());
        profileDocParseResult.setName(profileObj.getUser().getName());
        profileObj.setResumeObj(null);
        JSONObject jsonObject = ProfileExtUtils.convertToReferralProfileJson(profileObj);
        ProfileExtUtils.createAttachment(jsonObject, fileNameData, Constant.EMPLOYEE_PARSE_PROFILE_DOCUMENT);
        ProfileExtUtils.createReferralUser(jsonObject, profileDocParseResult.getName(), profileDocParseResult.getMobile());

        ProfilePojo profilePojo = profileEntity.parseProfile(jsonObject.toJSONString());

        client.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId),
                "", profilePojo.toJson(), 24*60*60);
        return profileDocParseResult;*/
    }

    @Override
    public ProfileDocParseResult parseFileStreamProfile(int employeeId, String fileOriginName, String fileName,
                                                        String fileAbsoluteName, String fileData)
            throws ProfileException {

        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        byte[] fileDataArray = fileData.getBytes(Consts.ASCII);
        FileNameData fileNameData = StreamUtils.persistFile(fileDataArray, env.getProperty("profile.persist.url"), suffix);
        fileNameData.setOriginName(fileName);

        return parseResult(employeeId, fileAbsoluteName, StreamUtils.byteArrayToBase64String(fileDataArray), fileNameData);
    }

    @Override
    public void employeeDeleteReferralProfile(int employeeId) throws ProfileException {
        client.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(),
                String.valueOf(employeeId),
                "");
    }


    @Override
    public Map<String, String> saveMobotReferralProfile(int employeeId, List<Integer> ids) throws BIZException, InterruptedException {
        Map<String, String> applyLimit = checkCompanyApply(employeeId, ids);
        Map<String, String> referralResultMap = new HashMap<>(1 >> 4);
        if(applyLimit.get("state") != null){
            return applyLimit;
        }
        List<MobotReferralResultVO> resultVOS = new ArrayList<>();
        String dataStr = client.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_INFO_CACHE.toString(), String.valueOf(employeeId));
        if(StringUtils.isBlank(dataStr)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DATA_OVERTIME);
        }
        List<JobPositionDO> jobPositionDOS = jobPositionDao.getPositionList(ids);
        ReferralInfoCacheDTO referralInfoCacheDTO = JSONObject.parseObject(dataStr, ReferralInfoCacheDTO.class);
        try{
            CountDownLatch countDownLatch = new CountDownLatch(jobPositionDOS.size());
            for(JobPositionDO position : jobPositionDOS){
                tp.startTast(() -> {
                    MobotReferralResultVO referralResultVO = new MobotReferralResultVO();
                    try {
                        int referralId = employeeReferralProfileIfMobot(employeeId, referralInfoCacheDTO.getName(), referralInfoCacheDTO.getMobile(),
                                referralInfoCacheDTO.getReferralReasons(), position.getId(), referralInfoCacheDTO.getReferralType(), ReferralScene.ChatBot);
                        referralResultVO.setId(referralId);
                        referralResultVO.setSuccess(true);
                    }catch (Exception e){
                        referralResultVO.setSuccess(false);
                        referralResultVO.setReason(e.getMessage());
                    }
                    referralResultVO.setPositionId(position.getId());
                    referralResultVO.setTitle(position.getTitle());
                    resultVOS.add(referralResultVO);
                    countDownLatch.countDown();
                    return 0;
                });
            }
            countDownLatch.await(60, TimeUnit.SECONDS);
            referralResultMap.put("list", JSON.toJSONString(resultVOS));
            client.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
            client.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_INFO_CACHE.toString(), String.valueOf(employeeId));
        }catch (Exception e){
            throw e;
        }
        return referralResultMap;
    }

    @Override
    public int saveMobotReferralProfileCache(int employeeId, String name, String mobile, List<String> referralReasons, byte referralType) throws BIZException {
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getUserEmployeeForUpdate(employeeId);
        if(userEmployeeDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.USEREMPLOYEE_NONEXIST);
        }
        String profilePojoStr = client.get(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
        JSONObject jsonObject = JSONObject.parseObject(profilePojoStr);
        if (StringUtils.isBlank(profilePojoStr)) {
            throw ProfileException.REFERRAL_PROFILE_NOT_EXIST;
        }
        ProfilePojo profilePojo = ProfilePojo.parseProfile(jsonObject, profileParseUtil.initParseProfileParam());
        profilePojo.getUserRecord().setName(name);
        profilePojo.getUserRecord().setMobile(Long.parseLong(mobile));
        // todo 生成虚拟用户
        ReferralInfoCacheDTO referralInfoCacheDTO = new ReferralInfoCacheDTO(employeeId, userEmployeeDO.getCompanyId(), name, mobile, referralReasons, referralType);
        UserUserRecord userRecord = userAccountEntity.getCompanyUser(mobile, userEmployeeDO.getCompanyId());
        if(userRecord == null){
            referralInfoCacheDTO.setEmployee(false);
            userRecord = profileEntity.storeChatBotUser(profilePojo, userEmployeeDO.getSysuserId(), userEmployeeDO.getCompanyId(), UserSource.EMPLOYEE_REFERRAL_CHATBOT, 0);
        }else {
            referralInfoCacheDTO.setEmployee(true);
        }
        referralInfoCacheDTO.setUserId(userRecord.getId());
        String dataStr = JSON.toJSONString(referralInfoCacheDTO);
        client.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_INFO_CACHE.toString(), String.valueOf(employeeId), dataStr);
        return userRecord.getId();
    }

    /**
     * 检验投递次数
     * @param employeeId 员工id
     * @param ids 职位ids
     * @author  cjm
     * @date  2018/10/29
     */
    private Map<String, String> checkCompanyApply(int employeeId, List<Integer> ids) throws BIZException {
        String str = client.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_INFO_CACHE.toString(), employeeId + "");
        ReferralInfoCacheDTO referralInfoCacheDTO = JSONObject.parseObject(str, ReferralInfoCacheDTO.class);
        int companyId = referralInfoCacheDTO.getCompanyId();
        UserApplyCount userApplyCount = applicationEntity.getApplyCount(referralInfoCacheDTO.getUserId(), companyId);
        UserApplyCount conf = applicationEntity.getApplicationCountLimit(companyId);
        List<JobPosition> jobPositions = jobPositionDao.getJobPositionByIdList(ids);
        List<Integer> companyIdList = jobPositions.stream().map(JobPosition::getCompanyId).collect(Collectors.toList());
        for(int id : companyIdList){
           if(id != companyId){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_APPLY_FAILED);
           }
        }
        return getApplyLimitMap(userApplyCount, conf, jobPositions);
    }

    private Map<String,String> getApplyLimitMap(UserApplyCount userApplyCount, UserApplyCount conf, List<JobPosition> jobPositions) {
        Map<String, String> applyLimitMap = new HashMap<>(1 >> 4);
        int schoolApply = 0;
        int socialApply = 0;
        for(JobPosition jobPosition : jobPositions){
            if(jobPosition.getCandidateSource() == 0){
                socialApply ++;
            }else {
                schoolApply ++;
            }
        }
        if ((userApplyCount.getSocialApplyCount() + socialApply) > conf.getSocialApplyCount()) {
            applyLimitMap.put("social_apply_limit", (conf.getSocialApplyCount() - userApplyCount.getSocialApplyCount()) + "");
            applyLimitMap.put("state", "-1");
        }
        if ((userApplyCount.getSchoolApplyCount() + schoolApply) > conf.getSchoolApplyCount()) {
            applyLimitMap.put("school_apply_limit", (conf.getSchoolApplyCount() - userApplyCount.getSchoolApplyCount()) + "");
            applyLimitMap.put("state", "-1");
        }
        return applyLimitMap;
    }

    /**
     * 控制是否验证推荐原因
     * @param employeeId 员工编号
     * @param name 推荐者名称
     * @param mobile 手机号码
     * @param referralReasons 推荐理由
     * @param position 职位编号
     * @param referralType 推荐方式
     * @param isMobot 是否为mobot推荐朋友
     * @author  cjm
     * @date  2018/10/30
     * @return 推荐记录编号
     */
    @Transactional(rollbackFor = Exception.class)
    public int employeeReferralProfileIfMobot(int employeeId, String name, String mobile, List<String> referralReasons,
                                                        int position, byte referralType, ReferralScene referralScene){
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredOneValidate("推荐理由", referralReasons);
        if (referralReasons != null) {
            String reasons = referralReasons.stream().collect(Collectors.joining(","));
            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
        }
        validateUtil.addRequiredStringValidate("候选人姓名", name);
        validateUtil.addStringLengthValidate("候选人姓名", name, null, 100);
        validateUtil.addRequiredStringValidate("手机号码", mobile);
        validateUtil.addRegExpressValidate("手机号码", mobile, FormCheck.getMobileExp());
        validateUtil.addIntTypeValidate("推荐方式", (int)referralType, 1, 4);
        String validateResult = validateUtil.validate();
        if (StringUtils.isNotBlank(validateResult)) {
            throw ProfileException.validateFailed(validateResult);
        }

        ReferralType type = ReferralType.instanceFromValue(referralType);
        if (type == null) {
            throw ApplicationException.APPLICATION_REFERRAL_TYPE_NOT_EXIST;
        }

        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(employeeId);
        if (employeeDO == null || employeeDO.getId() <= 0) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }

        JobPositionRecord positionRecord = positionEntity.getPositionByID(position);
        if (positionRecord == null || positionRecord.getStatus() != PositionStatus.ACTIVED.getValue()) {
            throw ApplicationException.APPLICATION_POSITION_NOTEXIST;
        }

        List<Integer> companyIdList = employeeEntity.getCompanyIds(employeeDO.getCompanyId());
        if (!companyIdList.contains(positionRecord.getCompanyId())) {
            throw ApplicationException.NO_PERMISSION_EXCEPTION;
        }

        String profilePojoStr = client.get(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));

        if (StringUtils.isBlank(profilePojoStr)) {
            throw ProfileException.REFERRAL_PROFILE_NOT_EXIST;
        }

        JSONObject jsonObject = JSONObject.parseObject(profilePojoStr);

        ProfilePojo profilePojo = ProfilePojo.parseProfile(jsonObject, profileParseUtil.initParseProfileParam());
        profilePojo.getUserRecord().setName(name);
        profilePojo.getUserRecord().setMobile(Long.parseLong(mobile));

        GenderType genderType = GenderType.Secret;
        if (profilePojo.getBasicRecord() != null && profilePojo.getBasicRecord().getGender() != null) {
            if (GenderType.instanceFromValue(profilePojo.getBasicRecord().getGender().intValue()) != null) {
                genderType = GenderType.instanceFromValue(profilePojo.getBasicRecord().getGender().intValue());
            }
        }
        String email = StringUtils.defaultIfBlank(profilePojo.getUserRecord().getEmail(), "");

        return recommend(profilePojo, employeeDO, positionRecord, name, mobile, referralReasons,
                genderType, email, type, referralScene);
    }

    /**
     * 员工推荐
     * 产生虚拟用户、简历、申请记录
     * @param employeeId 员工编号
     * @param name 推荐者名称
     * @param mobile 手机号码
     * @param referralReasons 推荐理由
     * @param position 职位编号
     * @param referralType 推荐方式
     * @return 推荐记录编号
     * @throws ProfileException
     */
    @Override
    public int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons,
                                       int position, byte referralType) throws ProfileException {
        int referralId = employeeReferralProfileIfMobot(employeeId, name, mobile, referralReasons, position, referralType, ReferralScene.Referral);
        client.del(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
        return referralId;
    }

    /**
     * 员工提交候选人关键信息
     * @param employeeId 员工编号
     * @param candidate 候选人信息
     * @return 推荐记录编号
     * @throws ProfileException 业务异常
     */
    @Override
    public int postCandidateInfo(int employeeId, CandidateInfo candidate) throws ProfileException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredStringValidate("姓名", candidate.getName());
        validateUtil.addStringLengthValidate("姓名", candidate.getName(), 0, 100);
        validateUtil.addRequiredStringValidate("手机号码", candidate.getMobile());
        validateUtil.addRegExpressValidate("手机号码", candidate.getMobile(), FormCheck.getMobileExp());
        validateUtil.addRequiredStringValidate("邮箱", candidate.getEmail());
        validateUtil.addRegExpressValidate("邮箱", candidate.getEmail(), FormCheck.getEmailExp());
        validateUtil.addStringLengthValidate("邮箱", candidate.getEmail(), null, 50);
        validateUtil.addRequiredStringValidate("就职公司", candidate.getCompany());
        validateUtil.addStringLengthValidate("就职公司", candidate.getCompany(), null, 200);
        validateUtil.addIntTypeValidate("职位信息", candidate.getPosition(), 1, null);
        validateUtil.addRequiredStringValidate("就职职位", candidate.getJob());
        validateUtil.addStringLengthValidate("就职职位", candidate.getJob(), null, 200);
        validateUtil.addRequiredOneValidate("推荐理由", candidate.getReasons());
        if (candidate.getReasons() != null) {
            String reasons = candidate.getReasons().stream().collect(Collectors.joining(","));
            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
        }

        String result = validateUtil.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ProfileException.validateFailed(result);
        }

        GenderType genderType = GenderType.instanceFromValue(candidate.getGender());
        if (genderType == null) {
            genderType = GenderType.Secret;
        }

        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(employeeId);
        if (employeeDO == null || employeeDO.getId() <= 0) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }

        JobPositionRecord positionRecord = positionEntity.getPositionByID(candidate.getPosition());
        if (positionRecord == null || positionRecord.getStatus() != PositionStatus.ACTIVED.getValue()) {
            throw ApplicationException.APPLICATION_POSITION_NOTEXIST;
        }

        List<Integer> companyIdList = employeeEntity.getCompanyIds(employeeDO.getCompanyId());
        if (!companyIdList.contains(positionRecord.getCompanyId())) {
            throw ApplicationException.NO_PERMISSION_EXCEPTION;
        }

        ProfilePojo profilePojo = new ProfilePojo();
        ProfileExtUtils.createReferralProfileData(profilePojo);
        ProfileExtUtils.createProfileBasic(profilePojo, genderType);
        ProfileExtUtils.createReferralUser(profilePojo, candidate.getName(), candidate.getMobile(), candidate.getEmail());

        return recommend(profilePojo, employeeDO, positionRecord, candidate.getName(), candidate.getMobile(),
                candidate.getReasons(), genderType, candidate.getEmail(), ReferralType.PostInfo, ReferralScene.Referral);

    }

    /**
     * 推荐执行的业务
     * @param profilePojo 简历数据
     * @param employeeDO 员工数据
     * @param positionRecord 职位数据
     * @param name 用户姓名
     * @param mobile 用户手机号码
     * @param referralReasons 推荐理由
     * @param gender
     * @param email @return 推荐记录编号
     * @param referralType 推荐方式
     * @throws ProfileException 业务异常
     */
    private int recommend(ProfilePojo profilePojo, UserEmployeeDO employeeDO, JobPositionRecord positionRecord,
                          String name, String mobile, List<String> referralReasons, GenderType gender, String email,
                          ReferralType referralType, ReferralScene referralScene)
            throws ProfileException {

        UserUserRecord userRecord = userAccountEntity.getReferralUser(
                profilePojo.getUserRecord().getMobile().toString(), employeeDO.getCompanyId(), referralScene);
        int userId;
        if (userRecord != null) {
            logger.info("recommend userRecord.id:{}", userRecord.getId());
            UserUserRecord userUserRecord = new UserUserRecord();
            userUserRecord.setId(userRecord.getId());
            boolean flag = false;
            if (StringUtils.isBlank(userRecord.getName()) || !userRecord.getName().equals(name)) {
                userRecord.setName(name);
                userUserRecord.setName(name);
                flag = true;
            }
            if (userRecord.getMobile() == null || userRecord.getMobile() == 0) {
                userRecord.setMobile(Long.valueOf(mobile));
                userUserRecord.setMobile(Long.valueOf(mobile));
                flag = true;
            }
            if (flag) {
                userAccountEntity.updateUserRecord(userUserRecord);
            }
            userId = userRecord.getId();
            profilePojo.setUserRecord(userRecord);
            if (StringUtils.isBlank(userRecord.getUsername())) {
                if (profilePojo.getProfileRecord() != null) {
                    profilePojo.getProfileRecord().setUserId(userRecord.getId());
                }
                profileEntity.mergeProfile(profilePojo, userRecord.getId());
                tp.startTast(() -> {
                    companyTagService.handlerCompanyTagByUserId(userId);
                    return true;
                });
            }
        } else {
            userRecord = profileEntity.storeReferralUser(profilePojo, employeeDO.getId(), employeeDO.getCompanyId(), referralScene);
            profilePojo.getProfileRecord().setUserId(userRecord.getId());
            userId = userRecord.getId();
            tp.startTast(() -> {
                companyTagService.handlerCompanyTagByUserId(userId);
                return true;
            });
        }

        int referralId = referralEntity.logReferralOperation(employeeDO.getId(), userId, positionRecord.getId(),
                referralType);
        Future<Response> responseFeature = tp.startTast(() -> {
            try {
                JobApplication jobApplication = new JobApplication();
                jobApplication.setApp_tpl_id(userId);
                jobApplication.setCompany_id(positionRecord.getCompanyId());
                jobApplication.setAppid(0);
                jobApplication.setApplier_id(userId);
                jobApplication.setPosition_id(positionRecord.getId());
                jobApplication.setApplier_name(name);
                jobApplication.setOrigin(ApplicationSource.EMPLOYEE_REFERRAL.getValue());
                jobApplication.setRecommender_user_id(employeeDO.getSysuserId());
                jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
                Response response = applicationService.postApplication(jobApplication);

                int applicationId = 0;
                if (response.getStatus() == 0) {
                    JSONObject jsonObject1 = JSONObject.parseObject(response.getData());
                    applicationId = jsonObject1.getInteger("jobApplicationId");
                }
                referralEntity.logReferralOperation(positionRecord.getId(), applicationId, 1, referralReasons,
                        mobile, employeeDO, userId, (byte) gender.getValue(), email);

                addRecommandReward(employeeDO, userId, applicationId, positionRecord, referralType);

                return response;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return new Response(ProfileException.PROGRAM_EXCEPTION.getCode(), ProfileException.PROGRAM_EXCEPTION.getMessage());
            }
        });

        try {
            Response response = responseFeature.get();
            if (response.status == 0) {
                return referralId;
            } else {
                throw new CommonException(response.getStatus(), response.getMessage());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROGRAM_EXCEPTION;
        }
    }

    private void addRecommandReward(UserEmployeeDO employeeDO, int userId, int applicationId,
                                    JobPositionRecord positionRecord, ReferralType referralType) throws ApplicationException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeeId", employeeDO.getId());
            jsonObject.put("companyId", employeeDO.getCompanyId());
            jsonObject.put("positionId", positionRecord.getId());
            jsonObject.put("berecomUserId", userId);
            jsonObject.put("applicationId", applicationId);
            jsonObject.put("appid", AppId.APPID_ALPHADOG.getValue());
            MessageProperties mp = new MessageProperties();
            mp.setAppId(String.valueOf(AppId.APPID_ALPHADOG.getValue()));
            mp.setReceivedExchange("user_action_topic_exchange");
            logger.info("");
            if (!referralType.equals(ReferralType.PostInfo)) {
                jsonObject.put("templateId", Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
                amqpTemplate.send("user_action_topic_exchange", "sharejd.jd_clicked",
                        MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).andProperties(mp).build());
            } else {
                jsonObject.put("templateId", Constant.RECRUIT_STATUS_FULL_RECOM_INFO);
                amqpTemplate.send("user_action_topic_exchange", "sharejd.jd_clicked",
                        MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).andProperties(mp).build());
            }

            com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication application = applicationDao.fetchOneById(applicationId);
            operationRecordDao.addRecord(application.getId(), application.getSubmitTime().getTime(),
                    Constant.RECRUIT_STATUS_UPLOAD_PROFILE, employeeDO.getCompanyId(), 0);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.APPLICATION_REFERRAL_REWARD_CREATE_FAILED;
        }

    }

    private ProfileDocParseResult parseResult(int employeeId, String fileName, String fileData,
                                              FileNameData fileNameData) throws ProfileException {
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        profileDocParseResult.setFile(fileNameData.getFileName());
        // 调用SDK得到结果
        ResumeObj resumeObj;
        try {
            resumeObj = profileEntity.profileParserAdaptor(fileName, fileData);
        } catch (TException | IOException e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }
        ProfileObj profileObj = resumeEntity.handlerParseData(resumeObj,0,fileName);
        profileDocParseResult.setMobile(profileObj.getUser().getMobile());
        profileDocParseResult.setName(profileObj.getUser().getName());
        profileObj.setResumeObj(null);
        JSONObject jsonObject = ProfileExtUtils.convertToReferralProfileJson(profileObj);
        ProfileExtUtils.createAttachment(jsonObject, fileNameData, Constant.EMPLOYEE_PARSE_PROFILE_DOCUMENT);
        ProfileExtUtils.createReferralUser(jsonObject, profileDocParseResult.getName(), profileDocParseResult.getMobile());

        ProfilePojo profilePojo = profileEntity.parseProfile(jsonObject.toJSONString());

        client.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId),
                "", profilePojo.toJson(), 24*60*60);
        return profileDocParseResult;
    }

    JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
            .getService(JobApplicationServices.Iface.class);

    private EmployeeEntity employeeEntity;
    private ProfileEntity profileEntity;
    private ResumeEntity resumeEntity;
    private UserAccountEntity userAccountEntity;
    private ProfileParseUtil profileParseUtil;
    private PositionEntity positionEntity;
    private ReferralEntity referralEntity;

    private Environment env;

}
