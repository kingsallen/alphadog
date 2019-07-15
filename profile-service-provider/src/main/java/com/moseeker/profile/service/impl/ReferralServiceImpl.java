package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.*;
import com.moseeker.entity.application.UserApplyCount;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.domain.ResumeEntity;
import com.moseeker.profile.domain.referral.EmployeeReferralProfile;
import com.moseeker.profile.domain.referral.EmployeeReferralProfileFileUpload;
import com.moseeker.profile.domain.referral.EmployeeReferralProfileInformation;
import com.moseeker.profile.domain.referral.EmployeeReferralProfileMobot;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.ReferralService;
import com.moseeker.profile.service.impl.resumefileupload.ReferralProfileParser;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.*;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.profile.struct.MobotReferralResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Resource(type=EmployeeReferralProfileFileUpload.class)
    private EmployeeReferralProfile referralProfileFileUpload;

    @Resource(type=EmployeeReferralProfileInformation.class)
    private EmployeeReferralProfile referralProfileInformation;

    @Resource(type=EmployeeReferralProfileMobot.class)
    private EmployeeReferralProfile referralProfileMobot;


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
    @Override
    public ProfileDocParseResult parseFileProfile(int employeeId, String fileName, ByteBuffer fileData) throws ProfileException {
        LocalDateTime anylisisStart = LocalDateTime.now();
        logger.info("ReferralServiceImpl parseFileProfile 员工简历解析耗时：anylisisStart{}",anylisisStart);
        ProfileDocParseResult profileDocParseResult = abstractResumeFileParser.parseResume(employeeId,fileName,fileData);
        LocalDateTime anylisisEnd = LocalDateTime.now();
        logger.info("ReferralServiceImpl parseFileProfile 员工简历解析耗时：anylisisEnd{}",anylisisEnd);
        Duration duration = Duration.between(anylisisStart,anylisisEnd);
        long millis = duration.toMillis();//相差毫秒数
        logger.info("ReferralServiceImpl parseFileProfile 员工简历解析耗时：millis{}",millis);
        return profileDocParseResult;
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
    public ProfileDocParseResult parseFileProfileByFilePath(String filePath, int userId) throws ProfileException {
        UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(userId);
        if (employeeDO == null) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        LocalDateTime parseFileStart = LocalDateTime.now();
        logger.info("ReferralServiceImpl parseFileProfileByFilePath parseFileStart{}",parseFileStart);
        File file = new File(filePath);
        if (!file.exists()) {
            throw ProfileException.REFERRAL_FILE_NOT_EXIST;
        }
        byte[] fileData = new byte[(int)file.length()];
        try(FileInputStream in = new FileInputStream(file)) {
            in.read(fileData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        String data = new String(Base64.encodeBase64(fileData), Consts.UTF_8);
        FileNameData fileNameData = new FileNameData();
        fileNameData.setFileName(file.getName());
        fileNameData.setFileAbsoluteName(filePath);
        fileNameData.setOriginName(file.getName());

        LocalDateTime parseFileStartReadFile = LocalDateTime.now();
        logger.info("ReferralServiceImpl parseFileProfileByFilePath parseFileStartReadFile{}",parseFileStartReadFile);
        Duration duration = Duration.between(parseFileStart,parseFileStartReadFile);
        long millis = duration.toMillis();//相差毫秒数
        logger.info("ReferralServiceImpl parseFileProfileByFilePath 读取文件时间差:millis{}",millis);
        ProfileDocParseResult profileDocParseResult = parseResult(employeeDO.getId(), file.getName(), data, fileNameData);
        LocalDateTime parseFileEnd = LocalDateTime.now();
        Duration parseTimeDiffer = Duration.between(parseFileStart,parseFileEnd);
        long parseTimeDifferMills = parseTimeDiffer.toMillis();//相差毫秒数
        logger.info("ReferralServiceImpl parseFileProfileByFilePath parseFileEnd{}",parseFileEnd);
        logger.info("ReferralServiceImpl parseFileProfileByFilePath 解析简历时间差:parseTimeDifferMills{}",parseTimeDifferMills);
        return profileDocParseResult;
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

        // 获取缓存的推荐记录
        ReferralInfoCacheDTO referralInfoCacheDTO = JSONObject.parseObject(getReferralCache(employeeId), ReferralInfoCacheDTO.class);
        List<JobPositionDO> jobPositionDOS = jobPositionDao.getPositionListWithoutStatus(ids);
        // 检验是否达到申请上限
        Map<String, String> applyLimit = checkCompanyApply(referralInfoCacheDTO.getCompanyId(), referralInfoCacheDTO.getUserId(), jobPositionDOS);
        Map<String, String> referralResultMap = new HashMap<>(1 >> 4);
        if(applyLimit.get("state") != null){
            return applyLimit;
        }
        EmployeeReferralProfileNotice profileNotice =  new EmployeeReferralProfileNotice
                .EmployeeReferralProfileBuilder(employeeId, referralInfoCacheDTO.getName(), referralInfoCacheDTO.getMobile(),
                referralInfoCacheDTO.getReferralReasons(), ReferralScene.ChatBot)
                .buildPosition(ids)
                .buildRecomReason(referralInfoCacheDTO.getRelationship(), referralInfoCacheDTO.getReferralReasonText(),
                        referralInfoCacheDTO.getReferralType())
                .buildEmployeeReferralProfileNotice();
        List<MobotReferralResultVO> referralResultVOS = referralProfileMobot.employeeReferralProfileAdaptor(profileNotice);

        referralResultMap.put("list", JSON.toJSONString(referralResultVOS));
        return referralResultMap;
    }

    private String getReferralCache(int employeeId) throws BIZException {
        String referralCache = client.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_INFO_CACHE.toString(), String.valueOf(employeeId));
        if(StringUtils.isBlank(referralCache)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.REFERRAL_DATA_OVERTIME);
        }
        return referralCache;
    }

    @Override
    public int saveMobotReferralProfileCache(int employeeId, String name, String mobile, List<String> referralReasons,
                                             byte referralType, String fileName, int relationship, String referralReasonText) throws BIZException {
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
        // 生成虚拟用户
        ReferralInfoCacheDTO referralInfoCacheDTO = new ReferralInfoCacheDTO(employeeId, userEmployeeDO.getCompanyId(),
                name, mobile, referralReasons, referralType, fileName, (byte)relationship, referralReasonText);
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

    @Override
    public String getMobotReferralCache(int employeeId) throws BIZException {
        return getReferralCache(employeeId);
    }

//    /**
//     * 控制是否验证推荐原因
//     * @param employeeId 员工编号
//     * @param name 推荐者名称
//     * @param mobile 手机号码
//     * @param referralReasons 推荐理由
//     * @param positionIds 职位编号
//     * @param referralType 推荐方式
//     * @author  cjm
//     * @date  2018/10/30
//     * @return 推荐记录编号
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public List<MobotReferralResultVO> employeeReferralProfileAdaptor(int employeeId, String name, String mobile, List<String> referralReasons,
//                                                        List<Integer> positionIds, byte referralType, byte shipType,
//                                                                      String referralText, ReferralScene referralScene){
//        validateReferralInfo(name, mobile, referralType, referralReasons);
//
//        ReferralType type = getReferralType(referralType);
//
//        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(employeeId);
//        if (employeeDO == null || employeeDO.getId() <= 0) {
//            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
//        }
//
//        List<JobPositionDO> jobPositions = getJobPositions(positionIds, employeeDO.getCompanyId());
//        ProfilePojo profilePojo = getRedisProfilePojo(employeeId, name, mobile);
//        return recommend(profilePojo, employeeDO, jobPositions, name, mobile, referralReasons,
//                 type, shipType, referralText, referralScene);
//    }

//    /**
//     * 获取redis中存的简历信息
//     * @param  employeeId 员工id
//     * @param  name 被推荐人姓名
//     * @param  mobile 被推荐人手机号
//     * @author  cjm
//     * @date  2018/11/2
//     * @return ProfilePojo
//     */
//    private ProfilePojo getRedisProfilePojo(int employeeId, String name, String mobile) {
//        String profilePojoStr = client.get(AppId.APPID_ALPHADOG.getValue(),
//                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
//        if (StringUtils.isBlank(profilePojoStr)) {
//            throw ProfileException.REFERRAL_PROFILE_NOT_EXIST;
//        }
//        JSONObject jsonObject = JSONObject.parseObject(profilePojoStr);
//        ProfilePojo profilePojo = ProfilePojo.parseProfile(jsonObject, profileParseUtil.initParseProfileParam());
//        profilePojo.getUserRecord().setName(name);
//        profilePojo.getUserRecord().setMobile(Long.parseLong(mobile));
//        return profilePojo;
//    }

//    /**
//     * 获取申请的职位信息
//     * @param  positionIds  职位ids
//     * @param  companyId   公司id
//     * @author  cjm
//     * @date  2018/11/2
//     * @return   职位信息list
//     */
//    private List<JobPositionDO> getJobPositions(List<Integer> positionIds, int companyId) {
//        List<JobPositionDO> jobPositions = jobPositionDao.getPositionList(positionIds);
//        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
//        for(JobPositionDO jobPosition : jobPositions){
//            if (jobPosition == null || jobPosition.getStatus() != PositionStatus.ACTIVED.getValue()) {
//                throw ApplicationException.APPLICATION_POSITION_NOTEXIST;
//            }
//            if (!companyIdList.contains(jobPosition.getCompanyId())) {
//                throw ApplicationException.NO_PERMISSION_EXCEPTION;
//            }
//        }
//        return jobPositions;
//    }
//
//    private ReferralType getReferralType(byte referralType) {
//        ReferralType type = ReferralType.instanceFromValue(referralType);
//        if (type == null) {
//            throw ApplicationException.APPLICATION_REFERRAL_TYPE_NOT_EXIST;
//        }
//        return type;
//    }

//    private void validateReferralInfo(String name, String mobile, byte referralType, List<String> referralReasons) {
//        ValidateUtil validateUtil = new ValidateUtil();
//        validateUtil.addRequiredOneValidate("推荐理由", referralReasons);
//        if (referralReasons != null) {
//            String reasons = referralReasons.stream().collect(Collectors.joining(","));
//            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
//        }
//        validateUtil.addRequiredStringValidate("候选人姓名", name);
//        validateUtil.addStringLengthValidate("候选人姓名", name, null, 100);
//        validateUtil.addRequiredStringValidate("手机号码", mobile);
//        validateUtil.addRegExpressValidate("手机号码", mobile, FormCheck.getMobileExp());
//        validateUtil.addIntTypeValidate("推荐方式", (int)referralType, 1, 4);
//        String validateResult = validateUtil.validate();
//        if (StringUtils.isNotBlank(validateResult)) {
//            throw ProfileException.validateFailed(validateResult);
//        }
//    }

//    /**
//     * 员工推荐
//     * 产生虚拟用户、简历、申请记录
//     * @param employeeId 员工编号
//     * @param name 推荐者名称
//     * @param mobile 手机号码
//     * @param referralReasons 推荐理由
//     * @param position 职位编号
//     * @param referralType 推荐方式
//     * @return 推荐记录编号
//     * @throws ProfileException
//     */
//    @Override
//    public int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons,
//                                       int position, byte referralType) throws ProfileException {
//
//        ValidateUtil validateUtil = new ValidateUtil();
//        validateUtil.addRequiredOneValidate("推荐理由", referralReasons);
//        if (referralReasons != null) {
//            String reasons = referralReasons.stream().collect(Collectors.joining(","));
//            validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
//        }
//        validateUtil.addRequiredStringValidate("候选人姓名", name);
//        validateUtil.addStringLengthValidate("候选人姓名", name, null, 100);
//        validateUtil.addRequiredStringValidate("手机号码", mobile);
//        validateUtil.addRegExpressValidate("手机号码", mobile, FormCheck.getMobileExp());
//        validateUtil.addIntTypeValidate("推荐方式", (int)referralType, 1, 4);
//        String validateResult = validateUtil.validate();
//        if (StringUtils.isNotBlank(validateResult)) {
//            throw ProfileException.validateFailed(validateResult);
//        }
//
//        ReferralType type = ReferralType.instanceFromValue(referralType);
//        if (type == null) {
//            throw ApplicationException.APPLICATION_REFERRAL_TYPE_NOT_EXIST;
//        }
//
//        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(employeeId);
//        if (employeeDO == null || employeeDO.getId() <= 0) {
//            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
//        }
//
//        JobPositionRecord positionRecord = positionEntity.getPositionByID(position);
//        if (positionRecord == null || positionRecord.getStatus() != PositionStatus.ACTIVED.getValue()) {
//            throw ApplicationException.APPLICATION_POSITION_NOTEXIST;
//        }
//
//        List<Integer> companyIdList = employeeEntity.getCompanyIds(employeeDO.getCompanyId());
//        if (!companyIdList.contains(positionRecord.getCompanyId())) {
//            throw ApplicationException.NO_PERMISSION_EXCEPTION;
//        }
//
//        String profilePojoStr = client.get(AppId.APPID_ALPHADOG.getValue(),
//                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
//
//        if (StringUtils.isBlank(profilePojoStr)) {
//            throw ProfileException.REFERRAL_PROFILE_NOT_EXIST;
//        } else {
//            client.del(AppId.APPID_ALPHADOG.getValue(),
//                    KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
//        }
//
//        JSONObject jsonObject = JSONObject.parseObject(profilePojoStr);
//
//        ProfilePojo profilePojo = ProfilePojo.parseProfile(jsonObject, profileParseUtil.initParseProfileParam());
//        profilePojo.getUserRecord().setName(name);
//        profilePojo.getUserRecord().setMobile(Long.parseLong(mobile));
//
//        GenderType genderType = GenderType.Secret;
//        if (profilePojo.getBasicRecord() != null && profilePojo.getBasicRecord().getGender() != null) {
//            if (GenderType.instanceFromValue(profilePojo.getBasicRecord().getGender().intValue()) != null) {
//                genderType = GenderType.instanceFromValue(profilePojo.getBasicRecord().getGender().intValue());
//            }
//        }
//        String email = StringUtils.defaultIfBlank(profilePojo.getUserRecord().getEmail(), "");
//
//        return recommend(profilePojo, employeeDO, positionRecord, name, mobile, referralReasons,
//                genderType, email, type);
//    }

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
    @Transactional
    @CounterIface
    @Override
    public int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons,
                                       int position, byte relationship, String referralText,   byte referralType) throws ProfileException, BIZException {
        logger.info("=======================开始执行上传操作======employeeReferralProfile===");
        EmployeeReferralProfileNotice profileNotice =  new EmployeeReferralProfileNotice
                .EmployeeReferralProfileBuilder(employeeId, name, mobile, referralReasons, ReferralScene.Referral)
                .buildPosition(position)
                .buildRecomReason(relationship,referralText,referralType)
                .buildEmployeeReferralProfileNotice();
        List<MobotReferralResultVO> referralResultVOS = referralProfileFileUpload.employeeReferralProfileAdaptor(profileNotice);
        checkReferralResult(referralResultVOS);
        List<Integer> referralIds = referralResultVOS.stream().map(MobotReferralResultVO::getId).collect(Collectors.toList());
        client.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
        if(com.moseeker.common.util.StringUtils.isEmptyList(referralIds)){
            throw CommonException.PROGRAM_EXCEPTION;
        }
        return referralIds.get(0);
    }

    @Override
    public List<MobotReferralResultVO> employeeReferralProfile(int employeeId, String name, String mobile,
                                                               List<String> referralReasons, List<Integer> positions,
                                                               byte relationship, String referralText,
                                                               byte referralType) throws ProfileException, BIZException {
        logger.info("Multi positions recommendation start {}",positions);
        EmployeeReferralProfileNotice profileNotice =  new EmployeeReferralProfileNotice
                .EmployeeReferralProfileBuilder(employeeId, name, mobile, referralReasons, ReferralScene.Referral)
                .buildPosition(positions)
                .buildRecomReason(relationship,referralText,referralType)
                .buildEmployeeReferralProfileNotice();
        List<MobotReferralResultVO> referralResultVOS = referralProfileFileUpload.employeeReferralProfileAdaptor(profileNotice);
        checkReferralResult(referralResultVOS);
        List<Integer> referralIds = referralResultVOS.stream().map(MobotReferralResultVO::getId).collect(Collectors.toList());
        client.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(employeeId));
        if(com.moseeker.common.util.StringUtils.isEmptyList(referralIds)){
            throw CommonException.PROGRAM_EXCEPTION;
        }
        //获取积分
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(employeeId);
        HrPointsConfDO confDO = employeeEntity.fetchByCompanyId(employeeDO.getCompanyId());
        referralResultVOS = referralResultVOS.stream().map(resultVO -> {
            resultVO.setReward(confDO.getReward());
            return resultVO;
        }).collect(Collectors.toList());
        return referralResultVOS;
    }

    /**
     * 员工提交候选人关键信息
     * @param employeeId 员工编号
     * @param candidate 候选人信息
     * @return 推荐记录编号
     * @throws ProfileException 业务异常
     */
    @CounterIface
    @Override
    public int postCandidateInfo(int employeeId, CandidateInfo candidate) throws ProfileException {
        EmployeeReferralProfileNotice profileNotice =  new EmployeeReferralProfileNotice
                .EmployeeReferralProfileBuilder(employeeId, candidate.getName(), candidate.getMobile(),
                    candidate.getReasons(), ReferralScene.Referral)
                .buildPosition(candidate.getPosition())
                .buildRecomReason(candidate.getRelationship(),candidate.getRecomReasonText(), candidate.getReferralType())
                .buildKeyInformation(candidate.getEmail(), candidate.getDegree(), candidate.getCompany(), candidate.getJob(),
                    candidate.getGender(), candidate.getCity())
                .buildEmployeeReferralProfileNotice();
        List<MobotReferralResultVO> referralResultVOS = referralProfileInformation.employeeReferralProfileAdaptor(profileNotice);
        if(com.moseeker.common.util.StringUtils.isEmptyList(referralResultVOS)){
            throw CommonException.PROGRAM_EXCEPTION;
        }
        MobotReferralResultVO referralResultVO = referralResultVOS.get(0);
        if(referralResultVO.getSuccess()){
            return referralResultVO.getId();
        }else {
            throw new CommonException(99999, referralResultVO.getReason());
        }
    }

    /**
     *
     * @param referralResultVOS 推荐结果
     * @author  cjm
     * @date  2018/11/2
     *  验证推荐结果是否成功了，如果失败的话，目前除了mobot简历上传接口会，其他都会抛异常
     */
    private void checkReferralResult(List<MobotReferralResultVO> referralResultVOS) throws BIZException {
        for(MobotReferralResultVO referralResultVO : referralResultVOS){
            if(!referralResultVO.getSuccess()){
                throw new BIZException(referralResultVO.getErrorCode()>0?referralResultVO.getErrorCode():1, referralResultVO.getReason());
            }
        }
    }

//    /**
//     * 推荐执行的业务
//     * @param profilePojo 简历数据
//     * @param employeeDO 员工数据
//     * @param positionRecord 职位数据
//     * @param name 用户姓名
//     * @param mobile 用户手机号码
//     * @param referralReasons 推荐理由
//     * @param gender
//     * @param email @return 推荐记录编号
//     * @param referralType 推荐方式
//     * @throws ProfileException 业务异常
//     */
//    private int recommend(ProfilePojo profilePojo, UserEmployeeDO employeeDO, JobPositionRecord positionRecord,
//                          String name, String mobile, List<String> referralReasons, GenderType gender, String email,
//                          ReferralType referralType)
//            throws ProfileException {
//
//        UserUserRecord userRecord = userAccountEntity.getReferralUser(
//                profilePojo.getUserRecord().getMobile().toString(), employeeDO.getCompanyId());
//        int userId;
//        int attachmentId = 0;
//        if (userRecord != null) {
//            logger.info("recommend userRecord.id:{}", userRecord.getId());
//            UserUserRecord userUserRecord = new UserUserRecord();
//            userUserRecord.setId(userRecord.getId());
//            boolean flag = false;
//            if (StringUtils.isBlank(userRecord.getName()) || !userRecord.getName().equals(name)) {
//                userRecord.setName(name);
//                userUserRecord.setName(name);
//                flag = true;
//            }
//            if (userRecord.getMobile() == null || userRecord.getMobile() == 0) {
//                userRecord.setMobile(Long.valueOf(mobile));
//                userUserRecord.setMobile(Long.valueOf(mobile));
//                flag = true;
//            }
//            if (flag) {
//                userAccountEntity.updateUserRecord(userUserRecord);
//            }
//            userId = userRecord.getId();
//            profilePojo.setUserRecord(userRecord);
//            if (StringUtils.isBlank(userRecord.getUsername())) {
//                if (profilePojo.getProfileRecord() != null) {
//                    profilePojo.getProfileRecord().setUserId(userRecord.getId());
//                }
//                ReferralLog logRecord = referralEntity.fetchReferralLog(employeeDO.getId(), userId);
//                int id = 0;
//                if(logRecord != null){
//                    id = logRecord.getAttementId();
//                }
//                attachmentId = profileEntity.mergeProfileReferral(profilePojo, userRecord.getId(), id);
//                tp.startTast(() -> {
//                    companyTagService.handlerCompanyTagByUserId(userId);
//                    return true;
//                });
//            }
//        } else {
//            userRecord = profileEntity.storeReferralUser(profilePojo, employeeDO.getId(), employeeDO.getCompanyId());
//            profilePojo.getProfileRecord().setUserId(userRecord.getId());
//            userId = userRecord.getId();
//            ProfileProfileDO profileDO =profileEntity.getProfileByUserId(userId);
//            ProfileAttachmentDO attachmentRecord = profileEntity.getProfileAttachmentByProfileId(profileDO.getId());
//            attachmentId = attachmentRecord.getId();
//            tp.startTast(() -> {
//                companyTagService.handlerCompanyTagByUserId(userId);
//                return true;
//            });
//        }
//
//        int referralId = referralEntity.logReferralOperation(employeeDO.getId(), userId, attachmentId, positionRecord.getId(),
//                referralType);
//        Future<Response> responseFeature = tp.startTast(() -> {
//            try {
//                JobApplication jobApplication = new JobApplication();
//                jobApplication.setApp_tpl_id(userId);
//                jobApplication.setCompany_id(positionRecord.getCompanyId());
//                jobApplication.setAppid(0);
//                jobApplication.setApplier_id(userId);
//                jobApplication.setPosition_id(positionRecord.getId());
//                jobApplication.setApplier_name(name);
//                jobApplication.setOrigin(ApplicationSource.EMPLOYEE_REFERRAL.getValue());
//                jobApplication.setRecommender_user_id(employeeDO.getSysuserId());
//                jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
//                Response response = applicationService.postApplication(jobApplication);
//
//                int applicationId = 0;
//                if (response.getStatus() == 0) {
//                    JSONObject jsonObject1 = JSONObject.parseObject(response.getData());
//                    applicationId = jsonObject1.getInteger("jobApplicationId");
//                }
//                referralEntity.logReferralOperation(positionRecord.getId(), applicationId, 1, referralReasons,
//                        mobile, employeeDO, userId, (byte) gender.getValue(), email);
//
//                addRecommandReward(employeeDO, userId, applicationId, positionRecord, referralType);
//
//                return response;
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                return new Response(ProfileException.PROGRAM_EXCEPTION.getCode(), ProfileException.PROGRAM_EXCEPTION.getMessage());
//            }
//        });
//
//        try {
//            Response response = responseFeature.get();
//            if (response.status == 0) {
//                return referralId;
//            } else {
//                throw new CommonException(response.getStatus(), response.getMessage());
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw ProfileException.PROGRAM_EXCEPTION;
//        }
//    }
//
//    /**
//     * 推荐执行的业务
//     * @param employeeDO 员工数据
//     * @param positions 职位数据
//     * @param name 用户姓名
//     * @param mobile 用户手机号码
//     * @param referralReasons 推荐理由
//     * @param referralType 推荐方式
//     * @throws ProfileException 业务异常
//     */
//    private List<MobotReferralResultVO> recommend(ProfilePojo profilePojo, UserEmployeeDO employeeDO, List<JobPositionDO> positions,
//                          String name, String mobile, List<String> referralReasons,
//                          ReferralType referralType, byte shipType, String referralText, ReferralScene referralScene)
//            throws ProfileException {
//        UserUserRecord userRecord = userAccountEntity.getReferralUser(
//                profilePojo.getUserRecord().getMobile().toString(), employeeDO.getCompanyId(), referralScene);
//        int userId;
//        int attachmentId=0;
//        if (userRecord != null) {
//            logger.info("recommend userRecord.id:{}", userRecord.getId());
//            UserUserRecord userUserRecord = new UserUserRecord();
//            userUserRecord.setId(userRecord.getId());
//            boolean flag = false;
//            if (StringUtils.isBlank(userRecord.getName()) || !userRecord.getName().equals(name)) {
//                userRecord.setName(name);
//                userUserRecord.setName(name);
//                flag = true;
//            }
//            if (userRecord.getMobile() == null || userRecord.getMobile() == 0) {
//                userRecord.setMobile(Long.valueOf(mobile));
//                userUserRecord.setMobile(Long.valueOf(mobile));
//                flag = true;
//            }
//            if (flag) {
//                userAccountEntity.updateUserRecord(userUserRecord);
//            }
//            userId = userRecord.getId();
//            profilePojo.setUserRecord(userRecord);
//            if (StringUtils.isBlank(userRecord.getUsername())) {
//                if (profilePojo.getProfileRecord() != null) {
//                    profilePojo.getProfileRecord().setUserId(userRecord.getId());
//                }
//                ReferralLog logRecord = referralEntity.fetchReferralLog(employeeDO.getId(), userId);
//                int id = 0;
//                if(logRecord != null){
//                    id = logRecord.getAttementId();
//                }
//                attachmentId = getAttachmentId(profilePojo, userRecord.getId(),id, referralScene);
//
//                tp.startTast(() -> {
//                    companyTagService.handlerCompanyTagByUserId(userId);
//                    return true;
//                });
//            }
//        } else {
//            userRecord = profileEntity.storeReferralUser(profilePojo, employeeDO.getId(), employeeDO.getCompanyId(), referralScene);
//            profilePojo.getProfileRecord().setUserId(userRecord.getId());
//            userId = userRecord.getId();
//            ProfileProfileDO profileDO =profileEntity.getProfileByUserId(userId);
//            ProfileAttachmentDO attachmentRecord = profileEntity.getProfileAttachmentByProfileId(profileDO.getId());
//            attachmentId = attachmentRecord.getId();
//            tp.startTast(() -> {
//                companyTagService.handlerCompanyTagByUserId(userId);
//                return true;
//            });
//        }
//        int tempAttachmentId = attachmentId;
//        int origin = referralScene.getScene() == ReferralScene.Referral.getScene() ? ApplicationSource.EMPLOYEE_REFERRAL.getValue() :
//                ApplicationSource.EMPLOYEE_CHATBOT.getValue();
//        List<Integer> positionIds = positions.stream().map(JobPositionDO::getId).collect(Collectors.toList());
//        List<MobotReferralResultVO> resultVOS = new ArrayList<>();
//        CountDownLatch countDownLatch = new CountDownLatch(positionIds.size());
//        for(JobPositionDO jobPositionDO : positions){
//            tp.startTast(() -> {
//                handleRecommend(employeeDO, userId, jobPositionDO, name, origin, referralType,referralReasons, mobile,
//                        resultVOS, countDownLatch, tempAttachmentId, shipType,  referralText);
//                return 0;
//            });
//        }
//
//        try {
//            countDownLatch.await(60, TimeUnit.SECONDS);
//             return resultVOS;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw ProfileException.PROGRAM_EXCEPTION;
//        }
//    }

//    private int getAttachmentId(ProfilePojo profilePojo, Integer userId, int tempId, ReferralScene referralScene) {
//       int attachmentId;
//       // mobot 在缓存简历信息时已经创建虚拟用户，所以这里不再创建合并
//       if(referralScene.getScene() != ReferralScene.ChatBot.getScene()){
//           attachmentId = profileEntity.mergeProfileReferral(profilePojo, userId,tempId);
//       }else {
//           ProfileProfileDO profileDO =profileEntity.getProfileByUserId(userId);
//           ProfileAttachmentDO attachmentRecord = profileEntity.getProfileAttachmentByProfileId(profileDO.getId());
//           attachmentId = attachmentRecord.getId();
//       }
//       return attachmentId;
//    }

//    /**
//     * 该方法的目的是给线程中的业务加上事务
//     * @author  cjm
//     * @date  2018/11/4
//     */
//    @Transactional(rollbackFor = Exception.class)
//    protected void handleRecommend(UserEmployeeDO employeeDO, int userId, JobPositionDO jobPositionDO, String name,
//                                   int origin, ReferralType referralType, List<String> referralReasons, String mobile,
//                                   List<MobotReferralResultVO> resultVOS, CountDownLatch countDownLatch,
//                                   int attachmentId, byte shipType, String referralText)
//            throws TException,EmployeeException {
//        MobotReferralResultVO referralResultVO = new MobotReferralResultVO();
//        referralResultVO.setPosition_id(jobPositionDO.getId());
//        referralResultVO.setTitle(jobPositionDO.getTitle());
//        try {
//            int referralId = referralEntity.logReferralOperation(employeeDO.getId(), userId, attachmentId, jobPositionDO.getId(),
//                    referralType);
//            JobApplication jobApplication = createJobApplication(userId, jobPositionDO.getCompanyId(), jobPositionDO.getId(), name, origin, employeeDO.getSysuserId());
//            Response response = applicationService.postApplication(jobApplication);
//            int applicationId = 0;
//            if (response.getStatus() == 0) {
//                referralResultVO.setSuccess(true);
//                JSONObject jsonObject1 = JSONObject.parseObject(response.getData());
//                applicationId = jsonObject1.getInteger("jobApplicationId");
//            }else {
//                referralResultVO.setSuccess(false);
//            }
//            referralEntity.logReferralOperation(jobPositionDO.getId(), applicationId, referralReasons,
//                    mobile, employeeDO, userId, shipType, referralText);
//            addRecommandReward(employeeDO, userId, applicationId, jobPositionDO.getId(), referralType);
//            referralResultVO.setId(referralId);
//            resultVOS.add(referralResultVO);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            referralResultVO.setReason(e.getMessage());
//            referralResultVO.setSuccess(false);
//            resultVOS.add(referralResultVO);
//            throw e;
//        }finally {
//            countDownLatch.countDown();
//        }
//    }

//    private JobApplication createJobApplication(int userId, int companyId, int positionId, String name, int origin, int employeeSysUserId) {
//        JobApplication jobApplication = new JobApplication();
//        jobApplication.setApp_tpl_id(userId);
//        jobApplication.setCompany_id(companyId);
//        jobApplication.setAppid(0);
//        jobApplication.setApplier_id(userId);
//        jobApplication.setPosition_id(positionId);
//        jobApplication.setApplier_name(name);
//        jobApplication.setOrigin(origin);
//        jobApplication.setRecommender_user_id(employeeSysUserId);
//        jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
//        return jobApplication;
//    }
//
//    private void addRecommandReward(UserEmployeeDO employeeDO, int userId, int applicationId,
//                                    int positionId, ReferralType referralType) throws ApplicationException {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("employeeId", employeeDO.getId());
//            jsonObject.put("companyId", employeeDO.getCompanyId());
//            jsonObject.put("positionId", positionId);
//            jsonObject.put("berecomUserId", userId);
//            jsonObject.put("applicationId", applicationId);
//            jsonObject.put("appid", AppId.APPID_ALPHADOG.getValue());
//            MessageProperties mp = new MessageProperties();
//            mp.setAppId(String.valueOf(AppId.APPID_ALPHADOG.getValue()));
//            mp.setReceivedExchange("user_action_topic_exchange");
//            logger.info("");
//            if (!referralType.equals(ReferralType.PostInfo)) {
//                jsonObject.put("templateId", Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
//                amqpTemplate.send("user_action_topic_exchange", "sharejd.jd_clicked",
//                        MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).andProperties(mp).build());
//            } else {
//                jsonObject.put("templateId", Constant.RECRUIT_STATUS_FULL_RECOM_INFO);
//                amqpTemplate.send("user_action_topic_exchange", "sharejd.jd_clicked",
//                        MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).andProperties(mp).build());
//            }
//
//            com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication application = applicationDao.fetchOneById(applicationId);
//            operationRecordDao.addRecord(application.getId(), application.getSubmitTime().getTime(),
//                    Constant.RECRUIT_STATUS_UPLOAD_PROFILE, employeeDO.getCompanyId(), 0);
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw ApplicationException.APPLICATION_REFERRAL_REWARD_CREATE_FAILED;
//        }
//
//    }

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
        logger.info("ReferralServiceImpl parseResult profileDocParseResult:{}", JSONObject.toJSONString(profileDocParseResult));
        return profileDocParseResult;
    }


    /**
     * 检验投递次数
     * @param companyId 公司id
     * @param userId 虚拟用户id
     * @author  cjm
     * @date  2018/10/29
     */
    private Map<String, String> checkCompanyApply(int companyId, int userId, List<JobPositionDO> jobPositions) throws BIZException {
        UserApplyCount userApplyCount = applicationEntity.getApplyCount(userId, companyId);
        UserApplyCount conf = applicationEntity.getApplicationCountLimit(companyId);
        List<Integer> companyIdList = jobPositions.stream().map(JobPositionDO::getCompanyId).collect(Collectors.toList());
        for(int id : companyIdList){
            if(id != companyId){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_APPLY_FAILED);
            }
        }
        return getApplyLimitMap(userApplyCount, conf, jobPositions);
    }

    private Map<String,String> getApplyLimitMap(UserApplyCount userApplyCount, UserApplyCount conf, List<JobPositionDO> jobPositions) {
        Map<String, String> applyLimitMap = new HashMap<>(1 >> 4);
        int schoolApply = 0;
        int socialApply = 0;
        for(JobPositionDO jobPosition : jobPositions){
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


    JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
            .getService(JobApplicationServices.Iface.class);

    private EmployeeEntity employeeEntity;
    private ProfileEntity profileEntity;
    private ResumeEntity resumeEntity;
    private UserAccountEntity userAccountEntity;
    private ProfileParseUtil profileParseUtil;
    private PositionEntity positionEntity;
    private ReferralEntity referralEntity;
    private HrPointsConfDao hrPointsConfDao;

    private Environment env;

}
