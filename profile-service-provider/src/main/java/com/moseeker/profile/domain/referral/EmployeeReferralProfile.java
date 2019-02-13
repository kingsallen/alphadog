package com.moseeker.profile.domain.referral;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.Position.PositionStatus;
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
import java.util.ArrayList;
import java.util.List;
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

    JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
            .getService(JobApplicationServices.Iface.class);

    ThreadPool tp = ThreadPool.Instance;


    protected abstract void validateReferralInfo(EmployeeReferralProfileNotice profileNotice);

    protected abstract ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice);

    protected abstract void storeReferralUser(UserUserRecord userRecord, EmployeeReferralProfileNotice profileNotice,
                                              ProfilePojo profilePojo, UserEmployeeDO employeeDO, ProfileAttementVO attementVO);

    public List<MobotReferralResultVO> employeeReferralProfileAdaptor(EmployeeReferralProfileNotice profileNotice){
        ProfileAttementVO attementVO = new ProfileAttementVO();
        validateReferralInfo(profileNotice);
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(profileNotice.getEmployeeId());
        if (employeeDO == null || employeeDO.getId() <= 0) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        List<JobPositionDO> positions = getJobPositions(profileNotice.getPositionIds(), employeeDO.getCompanyId());
        ProfilePojo profilePojo = getProfilePojo(profileNotice);
        UserUserRecord userRecord = userAccountEntity.getReferralUser(
                profileNotice.getMobile(), employeeDO.getCompanyId(), profileNotice.getReferralScene());
        storeReferralUser(userRecord, profileNotice, profilePojo, employeeDO, attementVO);
        int origin = profileNotice.getReferralScene().getScene() == ReferralScene.Referral.getScene() ? ApplicationSource.EMPLOYEE_REFERRAL.getValue() :
                ApplicationSource.EMPLOYEE_CHATBOT.getValue();
        List<Integer> positionIds = positions.stream().map(JobPositionDO::getId).collect(Collectors.toList());
        List<MobotReferralResultVO> resultVOS = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(positionIds.size());
        for(JobPositionDO jobPositionDO : positions){
            tp.startTast(() -> {
                handleRecommend( profileNotice, employeeDO, attementVO.getUserId(), jobPositionDO,  origin,
                        resultVOS, countDownLatch, attementVO.getAttachmentId());
                return 0;
            });
        }
        try {
            countDownLatch.await(60, TimeUnit.SECONDS);
            return resultVOS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROGRAM_EXCEPTION;
        }
    }


    /**
     * 该方法的目的是给线程中的业务加上事务
     * @author  cjm
     * @date  2018/11/4
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handleRecommend(EmployeeReferralProfileNotice profileNotice, UserEmployeeDO employeeDO, int userId, JobPositionDO jobPositionDO,
                                   int origin, List<MobotReferralResultVO> resultVOS, CountDownLatch countDownLatch,
                                   int attachmentId)
            throws TException,EmployeeException {
        MobotReferralResultVO referralResultVO = new MobotReferralResultVO();
        referralResultVO.setPosition_id(jobPositionDO.getId());
        referralResultVO.setTitle(jobPositionDO.getTitle());
        try {
            int referralId = referralEntity.logReferralOperation(employeeDO.getId(), userId, attachmentId, jobPositionDO.getId(),
                    profileNotice.getReferralType());
            int applicationId = createJobApplication(userId, jobPositionDO.getCompanyId(), jobPositionDO.getId(),
                    profileNotice.getName(), origin, employeeDO.getSysuserId(), referralResultVO);
            referralEntity.logReferralOperation(jobPositionDO.getId(), applicationId,  profileNotice.getReferralReasons(),
                    profileNotice.getMobile(), employeeDO.getSysuserId(), userId, profileNotice.getRelationship(), profileNotice.getReferralText());
            addRecommandReward(employeeDO, userId, applicationId, jobPositionDO.getId(), profileNotice.getReferralType());
            referralResultVO.setId(referralId);
            resultVOS.add(referralResultVO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            referralResultVO.setReason(e.getMessage());
            referralResultVO.setSuccess(false);
            resultVOS.add(referralResultVO);
            throw e;
        }finally {
            countDownLatch.countDown();
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
            operationRecordDao.addRecord(application.getId(), application.getSubmitTime().getTime(),
                    Constant.RECRUIT_STATUS_UPLOAD_PROFILE, employeeDO.getCompanyId(), 0);

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
