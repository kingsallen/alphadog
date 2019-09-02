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
import com.moseeker.common.constants.Position.PositionStatus;
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

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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


    protected abstract void validateReferralInfo(EmployeeReferralProfileNotice profileNotice);

    protected abstract ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice);

    protected abstract void storeReferralUser(UserUserRecord userRecord, EmployeeReferralProfileNotice profileNotice,
                                              ProfilePojo profilePojo, UserEmployeeDO employeeDO, ProfileAttementVO attementVO);

    public List<MobotReferralResultVO> employeeReferralProfileAdaptor(EmployeeReferralProfileNotice profileNotice){
        logger.info("=============employeeReferralProfileAdaptor==============");
        ProfileAttementVO attementVO = new ProfileAttementVO();
        validateReferralInfo(profileNotice);
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(profileNotice.getEmployeeId());
        if (employeeDO == null || employeeDO.getId() <= 0) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }

        ProfilePojo profilePojo = getProfilePojo(profileNotice);
        UserUserRecord userRecord = userAccountEntity.getReferralUser(
                profileNotice.getMobile(), employeeDO.getCompanyId(), profileNotice.getReferralScene());

        storeReferralUser(userRecord, profileNotice, profilePojo, employeeDO, attementVO);

        List<JobPositionDO> positions = getJobPositions(profileNotice.getPositionIds(), employeeDO.getCompanyId());

        List<MobotReferralResultVO> resultVOS = new ArrayList<>();

        if (positions != null && positions.size() > 0) {
            List<ReferralLog> referraledList = referralEntity.fetchByPositionIdAndOldReferenceId(profileNotice.getPositionIds(), attementVO.getUserId());
            for (int i=0; i<profileNotice.getPositionIds().size(); i++) {
                int index = i;
                Optional<JobPositionDO> positionDOOptional = positions
                        .stream()
                        .filter(jobPositionDO -> jobPositionDO.getId() == profileNotice.getPositionIds().get(index))
                        .findAny();
                if (positionDOOptional.isPresent()) {

                    Optional<ReferralLog> referralLogOptional = referraledList
                            .stream()
                            .filter(referralLog -> referralLog.getPositionId() == positionDOOptional.get().getId()
                                    && referralLog.getOldReferenceId() == attementVO.getUserId())
                            .findAny();
                    if (referralLogOptional.isPresent()) {
                        MobotReferralResultVO mobotReferralResultVO = new MobotReferralResultVO();
                        mobotReferralResultVO.setPosition_id(positionDOOptional.get().getId());
                        mobotReferralResultVO.setTitle(positionDOOptional.get().getTitle());
                        mobotReferralResultVO.setSuccess(false);
                        mobotReferralResultVO.setReason("重复推荐");
                        resultVOS.add(mobotReferralResultVO);
                    } else {
                        logger.info("EmployeeReferralProfile employeeReferralProfileAdaptor attementVO:{}",JSONObject.toJSONString(attementVO));
                        int origin = profileNotice.getReferralScene().getScene() == ReferralScene.Referral.getScene() ? ApplicationSource.EMPLOYEE_REFERRAL.getValue() :
                                ApplicationSource.EMPLOYEE_CHATBOT.getValue();
                        for(JobPositionDO jobPositionDO : positions){
                            try{
                                handleRecommend( profileNotice, employeeDO, attementVO.getUserId(), jobPositionDO,  origin,
                                        resultVOS, attementVO.getAttachmentId());
                            }catch(Exception e){
                                logger.error(e.getMessage(),e);
                            }
                        }
                        try {
                            tp1.startTast(()->{
                                logger.info("============三秒后执行=============================");
                                updateApplicationEsIndex(attementVO.getUserId());
                            },3000);
                            return resultVOS;
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            throw ProfileException.PROGRAM_EXCEPTION;
                        }
                    }

                } else {
                    resultVOS.add(generateNotExistInfo(i));
                }
            }
        } else {
            for (int i=0; i<profileNotice.getPositionIds().size(); i++) {
                resultVOS.add(generateNotExistInfo(i));
            }
        }
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
                                   int origin, List<MobotReferralResultVO> resultVOS, int attachmentId)
            throws TException,EmployeeException {
        MobotReferralResultVO referralResultVO = new MobotReferralResultVO();
        referralResultVO.setPosition_id(jobPositionDO.getId());
        referralResultVO.setTitle(jobPositionDO.getTitle());
        resultVOS.add(referralResultVO);
        try {
            int referralId = referralEntity.logReferralOperation(employeeDO.getId(), userId, attachmentId, jobPositionDO.getId(),
                    profileNotice.getReferralType());
            int applicationId = createJobApplication(userId, jobPositionDO.getCompanyId(), jobPositionDO.getId(),
                    profileNotice.getName(), origin, employeeDO.getSysuserId(), referralResultVO);
            referralEntity.logReferralOperation(jobPositionDO.getId(), applicationId,  profileNotice.getReferralReasons(),
                    profileNotice.getMobile(), employeeDO.getSysuserId(), userId, profileNotice.getRelationship(), profileNotice.getReferralText());
            addRecommandReward(employeeDO, userId, applicationId, jobPositionDO.getId(), profileNotice.getReferralType());
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
