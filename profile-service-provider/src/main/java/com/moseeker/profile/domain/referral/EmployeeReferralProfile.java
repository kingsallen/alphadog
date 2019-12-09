package com.moseeker.profile.domain.referral;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.entity.exception.EmployeeException;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.domain.ProfileAttementVO;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.MobotReferralResultVO;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.moseeker.common.constants.Constant.RETRY_UPPER_LIMIT;

/**
 * Created by moseeker on 2018/11/22.
 */
@Service
public abstract class EmployeeReferralProfile {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeReferralProfile.class);

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private ReferralEntity referralEntity;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private HrOperationRecordDao operationRecordDao;

    @Autowired
    private JobApplicationDao applicationDao;

    JobApplicationServices.Iface applicationService = ServiceManager.SERVICE_MANAGER
            .getService(JobApplicationServices.Iface.class);

    ThreadPool tp = ThreadPool.Instance;

    ScheduledThread tp1= ScheduledThread.Instance;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private ScheduledThread scheduledThread=ScheduledThread.Instance;


    protected abstract void validateReferralInfo(EmployeeReferralProfileNotice profileNotice);

    protected abstract ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice);

    private boolean checkIsRepeatedRecommend(List<ReferralLog> referraledList, int positionId, int userid, Future<Boolean> future){
        // 检查仟寻人才库
        Optional<ReferralLog> referralLogOptional = referraledList.stream()
                .filter(referralLog -> referralLog.getPositionId() == positionId
                        && referralLog.getOldReferenceId() == userid)
                .findAny();
        boolean result = referralLogOptional.isPresent() ;
        if(referralLogOptional.isPresent()){
            logger.info("moseeker人才库已存在的重复推荐 positionId:{} userid:{}",positionId,userid);
        }

        // 百济workday查重(根据手机号或邮箱)
        if(future != null){
            // 如果仟寻人才库已存在，不需要查客户人才库
            if(result){
                future.cancel(true);
            }else{
                try {
                    result = future.get(10, TimeUnit.SECONDS);
                } catch (InterruptedException|ExecutionException e) {
                    logger.error("百济workday查重错误",e);
                } catch (TimeoutException e) {
                    logger.error("百济查重超时错误",e);
                }
            }
        }
        return result ;
    }



    /**
     * 推荐查重加分布式锁
     * @param profileNotice
     * @param execute
     */
    private void lock(int companyId,EmployeeReferralProfileNotice profileNotice,Runnable execute){
        String uuid = UUID.randomUUID().toString();
        String pattern = companyId + "-" + profileNotice.getMobile(); // 编译器自动转换为StringBuild方式实现，不用担心性能
        int times = 0 ;
        while (times++ <= RETRY_UPPER_LIMIT) {
            try {
                boolean getLock = redisClient.tryGetLock(Constant.APPID_ALPHADOG, KeyIdentifier.REFERRAL_CHECK_REPEAT.toString(), pattern, uuid);
                if (getLock) {
                    // 执行特定步骤
                    execute.run();
                    return ;
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                        Thread.currentThread().interrupt();
                    }
                }
            } finally {
                redisClient.releaseLock(Constant.APPID_ALPHADOG, KeyIdentifier.REFERRAL_CHECK_REPEAT.toString(), pattern, uuid);
            }
        }
        throw CommonException.PROGRAM_UPDATE_FIALED;
    }


    /**
     * 存储
     * @param userRecord
     * @param profileNotice
     * @param profilePojo
     * @param employeeDO
     * @param attementVO
     * @return
     */
    protected abstract void storeReferralUser(UserUserRecord userRecord, EmployeeReferralProfileNotice profileNotice,
                                              ProfilePojo profilePojo, UserEmployeeDO employeeDO, ProfileAttementVO attementVO);

    private void addTime(String title,List<Map<String,Object>> timeRecords,long startTime){
        Map<String,Object> map = new HashMap<>();
        map.put("time",System.currentTimeMillis()-startTime);
        map.put("title",title);
        timeRecords.add(map);
    }

    public List<MobotReferralResultVO> employeeReferralProfileAdaptor(EmployeeReferralProfileNotice profileNotice){
        //logger.info("employeeReferralProfileAdaptor {}",JSONObject.toJSONString(profileNotice));
        long startTime = System.currentTimeMillis();
        List<MobotReferralResultVO> resultVOS = new ArrayList<>();
        List<Map<String,Object>> timeRecords = new LinkedList();
        ProfileAttementVO attementVO = new ProfileAttementVO();
        try{
            validateReferralInfo(profileNotice);
            addTime("employeeReferralProfileAdaptor.validateReferralInfo",timeRecords,startTime);

            UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(profileNotice.getEmployeeId());
            addTime("employeeReferralProfileAdaptor.employeeEntity_getEmployeeByID",timeRecords,startTime);
            if (employeeDO == null || employeeDO.getId() <= 0) {
                throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
            }

            ProfilePojo profilePojo = getProfilePojo(profileNotice);
            addTime("employeeReferralProfileAdaptor.getProfilePojo",timeRecords,startTime);
            UserUserRecord userRecord = userAccountEntity.getReferralUser(
                    profileNotice.getMobile(), employeeDO.getCompanyId(), profileNotice.getReferralScene());
            addTime("employeeReferralProfileAdaptor.userAccountEntity_getReferralUser",timeRecords,startTime);

            storeReferralUser(userRecord, profileNotice, profilePojo, employeeDO, attementVO);
            addTime("employeeReferralProfileAdaptor.storeReferralUser",timeRecords,startTime);

            List<JobPositionDO> positions = getJobPositions(profileNotice.getPositionIds(), employeeDO.getCompanyId());
            addTime("employeeReferralProfileAdaptor.getJobPositions",timeRecords,startTime);


            String email = profileNotice.getEmail() != null ?  profileNotice.getEmail() :
                    profileNotice.getOtherFields() != null ? profileNotice.getOtherFields().get("email") : null ;
            if (positions != null && positions.size() > 0) {
                List<ReferralLog> referraledList = referralEntity.fetchByPositionIdAndOldReferenceId(profileNotice.getPositionIds(), attementVO.getUserId());
                addTime("employeeReferralProfileAdaptor.referralEntity_fetchByPositionIdAndOldReferenceId",timeRecords,startTime);
                // 推荐查重加分布式锁,根据被推荐人手机号和companyId加锁
                // 一旦某一被推荐人被推荐，redis写入一条记录，推荐成功或失败后删除记录。如果在测过程中发生出现重复推荐请求，发生所等待，
                // 要么等待锁超时，页面提示用户重试，要么等待若干时间获得锁，接下来进入人才库查重，抛出重复推荐错误提示，
                lock(employeeDO.getCompanyId(),profileNotice,()-> {
                    addTime("employeeReferralProfileAdaptor.getlock",timeRecords,startTime);
                    for (int i = 0; i < profileNotice.getPositionIds().size(); i++) {
                        int index = i;
                        Optional<JobPositionDO> positionDOOptional = positions
                                .stream()
                                .filter(jobPositionDO -> jobPositionDO.getId() == profileNotice.getPositionIds().get(index))
                                .findAny();
                        if (positionDOOptional.isPresent()) {

                            if (checkIsRepeatedRecommend(referraledList, positionDOOptional.get().getId(), attementVO.getUserId(),
                                    profileNotice.getCheckRepeateFuture())) {
                                MobotReferralResultVO mobotReferralResultVO = new MobotReferralResultVO();
                                mobotReferralResultVO.setPosition_id(positionDOOptional.get().getId());
                                mobotReferralResultVO.setTitle(positionDOOptional.get().getTitle());
                                mobotReferralResultVO.setSuccess(false);
                                mobotReferralResultVO.setErrorCode(ProfileException.REFERRAL_REPEATE_REFERRAL.getCode());
                                mobotReferralResultVO.setReason(ProfileException.REFERRAL_REPEATE_REFERRAL.getMessage());
                                resultVOS.add(mobotReferralResultVO);
                            } else {
                                logger.debug("EmployeeReferralProfile employeeReferralProfileAdaptor attementVO:{}", JSONObject.toJSONString(attementVO));
                                int origin = profileNotice.getReferralScene().getScene() == ReferralScene.Referral.getScene() ? ApplicationSource.EMPLOYEE_REFERRAL.getValue() :
                                        ApplicationSource.EMPLOYEE_CHATBOT.getValue();
                                addTime(String.format("employeeReferralProfileAdaptor.lock[%d].handleRecommend:before",i),timeRecords,startTime);
                                try {
                                    handleRecommend(profileNotice, employeeDO, attementVO.getUserId(), positionDOOptional.get(), origin,
                                            resultVOS, attementVO.getAttachmentId(),timeRecords,startTime);
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e);
                                }
                                addTime(String.format("employeeReferralProfileAdaptor.lock[%d].handleRecommend:after",i),timeRecords,startTime);
                                try {
                                    tp1.startTast(() -> {
                                        logger.info("============三秒后执行=============================");
                                        updateApplicationEsIndex(attementVO.getUserId());
                                    }, 10000);
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e);
                                    throw ProfileException.PROGRAM_EXCEPTION;
                                }
                            }

                        } else {
                            resultVOS.add(generateNotExistInfo(i));
                        }
                    }
                });
                addTime("employeeReferralProfileAdaptor.lock:after",timeRecords,startTime);
            } else {
                for (int i=0; i<profileNotice.getPositionIds().size(); i++) {
                    resultVOS.add(generateNotExistInfo(i));
                }
            }

            /**
             * 如果 storeReferralUser 方法更新了userRecord的属性（用户信息），
             * 那么如果有一次推荐成功就将userRecord的信息持久化到数据库
             */
            if (userRecord != null) {
                boolean flag = false;
                UserUserRecord userUserRecord = new UserUserRecord();
                userUserRecord.setId(userRecord.getId());
                if (StringUtils.isBlank(userRecord.getName()) || !userRecord.getName().equals(profileNotice.getName())) {
                    userRecord.setName(profileNotice.getName());
                    userUserRecord.setName(profileNotice.getName());
                    flag = true;
                }
                if (StringUtils.isNotBlank(profileNotice.getEmail()) && !Objects.equals(userRecord.getEmail(),profileNotice.getEmail())) {
                    userRecord.setEmail(profileNotice.getEmail());
                    userUserRecord.setEmail(profileNotice.getEmail());
                    flag = true;
                }
                if (userRecord.getMobile() == null || userRecord.getMobile() == 0) {
                    userRecord.setMobile(Long.valueOf(profileNotice.getMobile()));
                    userUserRecord.setMobile(Long.valueOf(profileNotice.getMobile()));
                    flag = true;
                }
                addTime("employeeReferralProfileAdaptor.mobotReferralResultVOOptional:before",timeRecords,startTime);
                Optional<MobotReferralResultVO> mobotReferralResultVOOptional = resultVOS
                        .stream()
                        .filter(mobotReferralResultVO -> mobotReferralResultVO.getSuccess() != null && mobotReferralResultVO.getSuccess())
                        .findAny();
                if (flag && mobotReferralResultVOOptional.isPresent()) {
                    userAccountEntity.updateUserRecord(userUserRecord);
                    addTime("employeeReferralProfileAdaptor.userAccountEntity_updateUserRecord",timeRecords,startTime);
                    Optional<MobotReferralResultVO> successReferralOption = resultVOS
                            .stream()
                            .filter(MobotReferralResultVO::getSuccess)
                            .findAny();

                    successReferralOption.ifPresent(mobotReferralResultVO -> {
                        userAccountEntity.updateUserRecord(userUserRecord);
                        addTime("employeeReferralProfileAdaptor.userAccountEntity_updateUserRecord",timeRecords,startTime);
                    });
                }
            }
        }catch (RuntimeException e){
            logger.error(String.format("employeeReferralProfileAdaptor(%s)",JSONObject.toJSONString(profileNotice)),e);
        }finally {
            long time = System.currentTimeMillis() - startTime;
            logger.info("employeeReferralProfileAdaptor{} employeeId:{},总耗时：{}ms ,params：({}) ,详细情况：{}  ",
                        time>4000?"耗时过长":"",profileNotice.getEmployeeId(),time,JSONObject.toJSONString(profileNotice),timeRecords);

        }
        logger.info("employeeReferralProfileAdaptor {} result: ",JSONObject.toJSONString(profileNotice),JSONObject.toJSONString(resultVOS));
        return resultVOS;
    }

    /**
     * 组装职位信息不存在的校验结果信息
     * @param i 职位下标
     * @return 校验信息
     */
    private MobotReferralResultVO generateNotExistInfo(int i) {
        MobotReferralResultVO mobotReferralResultVO = new MobotReferralResultVO();
        mobotReferralResultVO.setPosition_id(0);
        mobotReferralResultVO.setTitle("第"+i+"个职位");
        mobotReferralResultVO.setSuccess(false);
        mobotReferralResultVO.setReason("第"+i+"个职位信息不存在");
        return mobotReferralResultVO;
    }

    private void updateApplicationEsIndex(int userId){

        logger.info("************************更新data/application索引=================================");
        logger.info("==========更新data/application===========ES_CRON_UPDATE_INDEX_APPLICATION_USER_IDS===");
        redisClient.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_APPLICATION_USER_IDS",String.valueOf(userId));
        logger.info("************************更新data/profile索引=================================");
        logger.info("==========更新data/profile===========ES_CRON_UPDATE_INDEX_PROFILE_COMPANY_USER_IDS===");
        redisClient.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_PROFILE_COMPANY_USER_IDS",String.valueOf(userId));
        logger.info("====================redis==============application更新=============");
        logger.info("================userid={}=================",userId);
        Map<String,Object> result=new HashMap<>();
        result.put("tableName","application_recom");
        result.put("user_id",userId);
        logger.info("==========更新data/application===========ES_CRON_UPDATE_INDEX_APPLICATION_USER_IDS===");
        redisClient.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_APPLICATION_ID_RENLING", JSON.toJSONString(result));
        logger.info("ES_CRON_UPDATE_INDEX_APPLICATION_ID_RENLING====={}",JSON.toJSONString(result));

    }


    /**
     * 该方法的目的是给线程中的业务加上事务
     * @author  cjm
     * @date  2018/11/4
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handleRecommend(EmployeeReferralProfileNotice profileNotice, UserEmployeeDO employeeDO, int userId, JobPositionDO jobPositionDO,
                                   int origin, List<MobotReferralResultVO> resultVOS, int attachmentId,List<Map<String,Object>> timeRecords,long startTime)
            throws TException,EmployeeException {
        MobotReferralResultVO referralResultVO = new MobotReferralResultVO();
        referralResultVO.setPosition_id(jobPositionDO.getId());
        referralResultVO.setTitle(jobPositionDO.getTitle());
        resultVOS.add(referralResultVO);
        try {
            addTime("handleRecommend:before",timeRecords,startTime);
            int referralId = referralEntity.logReferralOperation(employeeDO.getId(), userId, attachmentId, jobPositionDO.getId(),
                    profileNotice.getReferralType());
            addTime("handleRecommend.referralEntity_logReferralOperation",timeRecords,startTime);
            int applicationId = createJobApplication(userId, jobPositionDO.getCompanyId(), jobPositionDO.getId(),
                    profileNotice.getName(), origin, employeeDO.getSysuserId(), referralResultVO);
            addTime("handleRecommend.createJobApplication",timeRecords,startTime);
            referralEntity.logReferralOperation(jobPositionDO.getId(), applicationId,  profileNotice.getReferralReasons(),
                    profileNotice.getMobile(), employeeDO.getSysuserId(), userId, profileNotice.getRelationship(), profileNotice.getReferralText());
            addTime("handleRecommend.referralEntity_logReferralOperation",timeRecords,startTime);
            addRecommandReward(employeeDO, userId, applicationId, jobPositionDO.getId(), profileNotice.getReferralType());
            addTime("handleRecommend.addRecommandReward",timeRecords,startTime);
            referralResultVO.setId(referralId);
        } catch (EmployeeException e){
            logger.error(e.getMessage(), e);
            referralResultVO.setReason(e.getMessage());
            referralResultVO.setSuccess(false);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            referralResultVO.setReason(e.getMessage());
            referralResultVO.setSuccess(false);
            throw e;
        }
    }

    private int createJobApplication(int userId, int companyId, int positionId, String name, int origin,
                                                int employeeSysUserId, MobotReferralResultVO referralResultVO) throws TException {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApp_tpl_id(userId);
        jobApplication.setCompany_id(companyId);
        jobApplication.setAppid(0);
        jobApplication.setApplier_id(userId);
        jobApplication.setPosition_id(positionId);
        jobApplication.setApplier_name(name);
        jobApplication.setOrigin(origin);
        jobApplication.setRecommender_user_id(employeeSysUserId);
        jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
        Response response = applicationService.postApplication(jobApplication);
        int applicationId = 0;
        if (response.getStatus() == 0) {
            referralResultVO.setSuccess(true);
            JSONObject jsonObject1 = JSONObject.parseObject(response.getData());
            applicationId = jsonObject1.getInteger("jobApplicationId");
        }else {
            referralResultVO.setSuccess(false);
            referralResultVO.setErrorCode(response.getStatus());
            referralResultVO.setReason(response.getMessage());
            throw new EmployeeException(response.getStatus(),response.getMessage());
        }
        return applicationId;
    }

    private void addRecommandReward(UserEmployeeDO employeeDO, int userId, int applicationId,
                                    int positionId, ReferralType referralType) throws ApplicationException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeeId", employeeDO.getId());
            jsonObject.put("companyId", employeeDO.getCompanyId());
            jsonObject.put("positionId", positionId);
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
//            operationRecordDao.addRecord(application.getId(), application.getSubmitTime().getTime(),
//                    Constant.RECRUIT_STATUS_UPLOAD_PROFILE, employeeDO.getCompanyId(), 0);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.APPLICATION_REFERRAL_REWARD_CREATE_FAILED;
        }

    }

    /**
     * 获取申请的职位信息
     * @param  positionIds  职位ids
     * @param  companyId   公司id
     * @author  cjm
     * @date  2018/11/2
     * @return   职位信息list
     */
    private List<JobPositionDO> getJobPositions(List<Integer> positionIds, int companyId) {
        List<JobPositionDO> jobPositions = jobPositionDao.getPositionList(positionIds);
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        for(JobPositionDO jobPosition : jobPositions){
            if (jobPosition == null || jobPosition.getStatus() != PositionStatus.ACTIVED.getValue()) {
                throw ApplicationException.APPLICATION_POSITION_NOTEXIST;
            }
            if (!companyIdList.contains(jobPosition.getCompanyId())) {
                throw ApplicationException.NO_PERMISSION_EXCEPTION;
            }
        }
        return jobPositions;
    }


}
