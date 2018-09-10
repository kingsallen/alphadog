package com.moseeker.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.application.domain.ApplicationBatchEntity;
import com.moseeker.application.domain.HREntity;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.application.service.application.StatusChangeUtil;
import com.moseeker.application.service.application.alipay_campus.AlipaycampusStatus;
import com.moseeker.application.service.application.qianxun.Status;
import com.moseeker.baseorm.dao.historydb.HistoryJobApplicationDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobResumeOtherDao;
import com.moseeker.baseorm.dao.userdb.UserAliUserDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryJobApplicationRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.pojo.ApplicationSaveResultVO;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.ApplicationEntity;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.PositionEntity;
import com.moseeker.entity.application.UserApplyCount;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.ApplicationResponse;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.chat.service.ChatService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserAliUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageEmailStruct;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


/**
 * @author ltf 申请服务 2016年11月3日
 */
@Service
public class JobApplicataionService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    // 申请次数限制 3次
    private static final int APPLICATION_COUNT_LIMIT = 3;
    // ats投递
    private static final int IS_MOSEEKER_APPLICATION = 0;
    private static final int IS_ATS_APPLICATION = 1;
    // 申请次数redis key
    private static final String REDIS_KEY_APPLICATION_COUNT_CHECK = "APPLICATION_COUNT_CHECK";

    private ThreadPool tp = ThreadPool.Instance;
    //redis的客户端
    @Resource(name = "cacheClient")
    private RedisClient redisClient;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private JobResumeOtherDao jobResumeOtherDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private UserUserDao userUserDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    private UserAliUserDao userAliUserDao;

    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;

    @Autowired
    private HistoryJobApplicationDao historyJobApplicationDao;

    @Autowired
    private  JobApplicationFilterService filterService;

    @Autowired
    EmployeeEntity employeeEntity;
    MqService.Iface mqServer = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    ChatService.Iface chatService = ServiceManager.SERVICEMANAGER.getService(ChatService.Iface.class);

    ProfileOtherThriftService.Iface profileOtherService = ServiceManager.SERVICEMANAGER.getService(ProfileOtherThriftService.Iface.class);

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    PositionEntity positionEntity;

    @Autowired
    ApplicationEntity applicationEntity;


    /**
     * 创建申请
     *
     * @param jobApplication 申请参数
     * @return 新创建的申请记录ID
     */
    @SuppressWarnings("serial")
    @CounterIface
    public Response postApplication(JobApplication jobApplication) throws  TException {
        logger.info("JobApplicataionService postApplication jobApplication:{}", jobApplication);

        appIDToSource(jobApplication);
        // 获取该申请的职位
        Query query = new QueryBuilder().where("id", jobApplication.getPosition_id()).buildQuery();
        JobPositionRecord jobPositionRecord = jobPositionDao.getRecord(query);
        //JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById((int) jobApplication.position_id);
        //校验申请来源的有效性
        if (jobApplication.getOrigin() == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_SOURCE_NOTEXIST);
        }
        // 职位有效性验证
        Response responseJob = validateJobPosition(jobPositionRecord);
        if (responseJob.status > 0) {
            return responseJob;
        }
        Response responseCheck = checkApplicationCountAtCompany(jobApplication.getApplier_id(),
                jobPositionRecord.getCompanyId(), jobPositionRecord.getCandidateSource());
        if (responseCheck != null && responseCheck.getStatus() != 0) {
            updateOrigin(jobApplication);
            return responseCheck;
        }
        int jobApplicationId = postApplication(jobApplication, jobPositionRecord);
        if (jobApplicationId > 0) {
            sendMessageAndEmailThread(jobApplicationId, (int) jobApplication.getPosition_id(),
                    jobApplication.getApply_type(), jobApplication.getEmail_status(),
                    (int) jobApplication.getRecommender_user_id(), (int) jobApplication.getApplier_id(),
                    jobApplication.getOrigin());
            // 返回 jobApplicationId
            return ResponseUtils.success(new HashMap<String, Object>() {
                                             {
                                                 put("jobApplicationId", jobApplicationId);
                                             }
                                         }
            );
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    @Transactional
    public int postApplication(JobApplication jobApplication, JobPositionRecord jobPositionRecord) throws TException {
        try {
            // 初始化参数
            initJobApplication(jobApplication, jobPositionRecord);
            // 添加申请
            logger.info("JobApplicataionService postApplication ");
            JobApplicationRecord jobApplicationRecord = BeanUtils.structToDB(jobApplication,
                    JobApplicationRecord.class);
            logger.info("JobApplicataionService postApplication jobApplicationRecord:{}", jobApplicationRecord);
            if (jobApplicationRecord.getWechatId() == null) {
                jobApplicationRecord.setWechatId(0);
            }
            ApplicationSaveResultVO jobApplicationVO = this.saveJobApplication(jobApplicationRecord, jobPositionRecord);
            if (jobApplicationVO.isCreate()) {
                // proxy 0: 正常投递, 1: 代理投递, null:默认为0
                // 代理投递不能增加用户的申请限制次数
                if (jobApplicationRecord.getProxy() == null || jobApplicationRecord.getProxy() == 0) {
                    // 添加该人该公司的申请次数
                    addApplicationCountAtCompany(jobApplication,jobPositionRecord.getCandidateSource());
                }
            }

            return jobApplicationVO.getApplicationId();
        }  catch (Exception e) {
            logger.error("postResources JobApplication error: ", e);
            throw new TException();
        } finally {
            //do nothing
        }
    }

    /**
     * 校验申请的有效性，并发送 消息通知
     * @param jobApplicationId 申请编号
     * @param positionId 职位编号
     * @param applyType 申请类型（prifile or email）
     * @param emailStatus email投递的状态
     * @param recommenderUserId 推荐人
     * @param applierId 申请人
     * @param origin 来源
     */
    private void sendMessageAndEmailThread (int jobApplicationId, int positionId, int applyType, int emailStatus,
                                            int recommenderUserId, int applierId, int origin) {
        MessageEmailStruct messageEmailStruct = new MessageEmailStruct();
        messageEmailStruct.setApplication_id(jobApplicationId);
        messageEmailStruct.setPosition_id(positionId);
        messageEmailStruct.setApply_type(applyType);
        messageEmailStruct.setEmail_status(emailStatus);
        messageEmailStruct.setRecommender_user_id(recommenderUserId);
        messageEmailStruct.setApplier_id(applierId);
        messageEmailStruct.setOrigin(origin);
        logger.info("sendMessageAndEmailThread messageEmailStruct{}", messageEmailStruct);
        tp.startTast(() -> {
            filterService.handerApplicationFilter(messageEmailStruct);
            return 0;
        });
        //发送模板消息，短信，邮件
        tp.startTast(() -> {
            try {
                Response resppnse = mqServer.sendMessageAndEmailToDelivery(messageEmailStruct);
                logger.info("JobApplicataionService response {}", JSON.toJSONString(resppnse));
                return resppnse;
            }catch (TException e){
                return ResponseUtils.fail(e.getMessage());
            }
        });
    }
    /**
     * 合并申请来源
     * @param jobApplication
     */
    private void updateOrigin(JobApplication jobApplication) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.DISABLE.getName(), 0)
                .and(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.APPLIER_ID.getName(), jobApplication.getApplier_id())
                .and(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.POSITION_ID.getName(), jobApplication.getPosition_id());
        JobApplicationRecord record = jobApplicationDao.getRecord(queryBuilder.buildQuery());

        if (record != null && jobApplication.getOrigin() > 0 && jobApplication.getOrigin() != record.getOrigin()) {
            record.setOrigin(jobApplication.getOrigin() | record.getOrigin());
            record.setUpdateTime(new Timestamp(new Date().getTime()));
            jobApplicationDao.updateRecord(record);
        }
    }

    private void appIDToSource(JobApplication jobApplication) {
        if (jobApplication.getOrigin() == 0) {
            switch (jobApplication.getAppid()) {
                case 1:
                    jobApplication.setOrigin(1);
                    break;
                case 5:
                case 2:
                    jobApplication.setOrigin(4);
                    break;
                case 6:
                case 3:
                    jobApplication.setOrigin(2);
                    break;
                default:
            }
        }
    }

    /**
     * 更新申请数据
     *
     * @param jobApplication 用户实体
     */
    @SuppressWarnings("serial")
    @CounterIface
    @Transactional
    public Response putApplication(JobApplication jobApplication) throws TException {
        try {
            // 必填项校验
            if (jobApplication == null || jobApplication.getId() == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "id"));
            }
            // 更新申请
            int updateStatus = updateApplication(jobApplication);
            //int updateStatus = jobApplicationDao.putResource(jobApplicationRecord);
            if (updateStatus > 0) {
                // 返回 userFavoritePositionId
                return ResponseUtils.success(new HashMap<String, Object>() {
                    {
                        put("updateStatus", updateStatus);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("putApplication JobApplicationRecord error: ", e);
            throw new TException();
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }



    /**
     * 更新申请
     *
     * @param jobApplication
     * @return
     */
    private int updateApplication(JobApplication jobApplication) {
        int updateStatus = 0;
        QueryBuilder queryBuilder = new QueryBuilder();
        JobApplicationDO jobApplicationDO = jobApplicationDao.getData(
                queryBuilder.where(
                        com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.ID.getName(), jobApplication.getId())
                        .buildQuery());
        boolean bool = false;
        if(jobApplication != null && jobApplicationDO != null && jobApplication.getEmail_status() == 0 && jobApplicationDO.getEmailStatus()!=0){
            bool = true;
        }
        if (jobApplicationDO != null) {
            if(jobApplication.isSetOrigin()) {
                ApplicationSource applicationSource = ApplicationSource.instaceFromInteger(jobApplication.getOrigin());
                if (applicationSource == null) {
                    jobApplication.setOrigin(jobApplication.getOrigin());
                } else {
                    jobApplication.setOrigin(applicationSource.andSource(jobApplicationDO.getOrigin()));
                }
            }
            // 更新申请
            JobApplicationRecord jobApplicationRecord = BeanUtils.structToDB(jobApplication, JobApplicationRecord.class);
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            jobApplicationRecord.setUpdateTime(updateTime);
            updateStatus = jobApplicationDao.updateRecord(jobApplicationRecord);
            if(updateStatus>0 && bool){
                sendMessageAndEmailThread((int)jobApplication.getId(), jobApplicationDO.getPositionId(),
                        jobApplicationDO.getApplyType(), jobApplication.getEmail_status(),
                        jobApplicationDO.getRecommenderUserId(), jobApplicationDO.getApplierId(),
                        jobApplicationDO.getOrigin());
            }
        }
        return updateStatus;
    }

    /**
     * 删除申请记录
     *
     * @param applicationId 申请Id
     */
    @CounterIface
    @Transactional
    public Response deleteApplication(long applicationId) throws TException {
        try {
            // 必填项校验
            if (applicationId == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "id"));
            }
            Query query = new QueryBuilder().where("id", applicationId).buildQuery();
            JobApplicationRecord jobApplicationRecord = jobApplicationDao.getRecord(query);
            if (jobApplicationRecord == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            // 删除的数据归档
            int status = this.archiveApplicationRecord(jobApplicationRecord);
            // 归档成功后, 用户在该公司下的申请限制次数 -1
            if (status > 0) {
                // 用户在该公司下的申请限制次数 -1 TODO: throw RedisException, 提示相关信息
                this.subApplicationCountAtCompany(jobApplicationRecord);

                return ResponseUtils.success(status);

            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_ARCHIVE_FAILED);
            }
        } catch (Exception e) {
            logger.error("deleteApplication JobApplicationRecord error: ", e);
            throw new TException();
        } finally {
            //do nothing
        }
    }

    /**
     * 该人该公司的申请次数 -1
     */
    private void subApplicationCountAtCompany(JobApplicationRecord jobApplication) {

        try {

            Query query = new QueryBuilder().where("id", jobApplication.getPositionId()).buildQuery();
            JobPositionRecord position = jobPositionDao.getRecord(query);

            if (position != null) {
                String applicationCountCheck = redisClient.get(
                        Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                        String.valueOf(jobApplication.getApplierId()),
                        String.valueOf(jobApplication.getCompanyId()));
                UserApplyCount userApplyCount = UserApplyCount.initFromRedis(applicationCountCheck);

                if (!userApplyCount.isInit()) {
                    if (position.getCandidateSource() == 0) {
                        if (userApplyCount.getSocialApplyCount() > 1) {
                            userApplyCount.setSocialApplyCount(userApplyCount.getSocialApplyCount() - 1);
                        }
                    } else {
                        if (userApplyCount.getSchoolApplyCount() > 1) {
                            userApplyCount.setSchoolApplyCount(userApplyCount.getSchoolApplyCount() - 1);
                        }
                    }

                    redisClient.set(Constant.APPID_ALPHADOG,
                            REDIS_KEY_APPLICATION_COUNT_CHECK,
                            String.valueOf(jobApplication.getApplierId()),
                            String.valueOf(jobApplication.getCompanyId()),
                            JSON.toJSONString(userApplyCount),
                            (int) DateUtils.calcCurrMonthSurplusSeconds());
                }
            }
        } catch (RedisException e) {
            WarnService.notify(e);
        }
    }

    /**
     * 创建申请
     *
     * @param jobResumeOther 申请参数
     * @return 新创建的申请记录ID
     */
    @CounterIface
    @Transactional
    public Response postJobResumeOther(JobResumeOther jobResumeOther) throws TException {
//        try {
        // 必填项验证
        Response response = validateJobResumeOther(jobResumeOther);
        if (response.status > 0) {
            return response;
        }
        // 自定义简历 - 申请副本 添加
        JobResumeOtherRecord jobResumeOtherRecord = (JobResumeOtherRecord) BeanUtils.structToDB(jobResumeOther,
                JobResumeOtherRecord.class);
        jobResumeOtherDao.addRecord(jobResumeOtherRecord);
        int jobResumeOtherStatus = jobResumeOtherRecord.getAppId();
        //int jobResumeOtherStatus = jobResumeOtherDao.postResource(jobResumeOtherRecord);
        if (jobResumeOtherStatus > 0) {
            Map<String, Object> hashmap = new HashMap<>();
            hashmap.put("jobResumeOtherStatus", jobResumeOtherStatus);
            return ResponseUtils.success(hashmap); // 返回 jobResumeOtherStatus
        }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("postJobResumeOther JobResumeOther error: ", e);
//        } finally {
//            //do nothing
//        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 判断当前用户是否申请了该职位
     *
     * @param userId     用户ID
     * @param positionId 职位ID
     * @return true : 申请, false: 没申请过
     */
    @CounterIface
    @Transactional
    public Response getApplicationByUserIdAndPositionId(long userId, long positionId, long companyId) throws TException {
        try {
            Response response = validateGetApplicationByUserIdAndPositionId(userId, positionId, companyId);
            if (response.status == 0) {
                return ResponseUtils.success(this.isAppliedPosition(userId, positionId));
            } else {
                response.setData(JSON.toJSONString(Boolean.FALSE));
                return response;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postJobResumeOther JobResumeOther error: ", e);
            throw new TException();
        } finally {
            //do nothing
        }
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验 超出申请次数限制, 每月每家公司一个人只能申请10次 <p>
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    @CounterIface
    public Response validateUserApplicationCheckCountAtCompany(long userId, long companyId, long positionId) {
        try {
            Query query = new QueryBuilder().where("id", positionId).buildQuery();
            JobPositionRecord jobPositionRecord = jobPositionDao.getRecord(query);
            Response response = this.checkApplicationCountAtCompany(userId, jobPositionRecord.getCompanyId(),
                    jobPositionRecord.getCandidateSource());
            if(response!= null && response.getStatus() == 0){
                return ResponseUtils.success(false);
            }else{
                return ResponseUtils.success(true);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("validateUserApplicationCheckCountAtCompany error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }



    /**
     * 一个用户在一家公司的每月的申请次数校验 超出申请次数限制, 每月每家公司一个人只能申请10次 <p>
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    @CounterIface
    public Response validateUserApplicationTypeCheckCountAtCompany(long userId, long companyId) {
        Map<String, Boolean> params = new HashMap<>();
        try {
            String applicationCountCheck = redisClient.get(
                    Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(userId), String.valueOf(companyId));

            UserApplyCount userApplyCount = UserApplyCount.initFromRedis(applicationCountCheck);
            UserApplyCount conf = applicationEntity.getApplicationCountLimit((int) companyId);
            if (userApplyCount.getSocialApplyCount() >= conf.getSocialApplyCount()) {
                params.put("socialApply", false);
            }else{
                params.put("socialApply", true);
            }
            if (userApplyCount.getSchoolApplyCount() >= conf.getSchoolApplyCount()) {
                params.put("schoolApply", false);
            }else{
                params.put("schoolApply", true);
            }
        } catch (RedisException e) {
            WarnService.notify(e);
        }
        return ResponseUtils.success(params);
    }

    /**
     * 必填项校验 - 判断当前用户是否申请了该职位 <p>
     *
     * @param userId     用户ID
     * @param positionId 职位ID
     * @param companyId  公司ID
     */
    private Response validateGetApplicationByUserIdAndPositionId(long userId, long positionId, long companyId) {

        Response response = new Response(0, "ok");

        if (userId == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "user_id"));
        }
        if (companyId == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "company_id"));
        }
        if (positionId == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }

        return response;
    }

    /**
     * 自定义简历 - 申请副本 必填项校验
     */
    private Response validateJobResumeOther(JobResumeOther jobResumeOther) {

        Response response = new Response(0, "ok");

        // 参数校验
        if (jobResumeOther == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if (jobResumeOther.app_id == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "app_id"));
        }
        return response;
    }

    /**
     * 创建申请时初始化申请变量 <p>
     *
     * @param jobApplication    申请参数
     * @param jobPositionRecord 职位记录
     */
    private void initJobApplication(JobApplication jobApplication, JobPositionRecord jobPositionRecord) {

        // 初始化申请状态
        if (jobApplication.getApp_tpl_id() == 0) {
            jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_APPLY);
        }
        if (jobApplication.getCompany_id() == 0) {
            jobApplication.setCompany_id(jobPositionRecord.getCompanyId().intValue());
        }
        // ats_status初始化 ats_status初始化:1 是ats职位申请  ats_status初始化:0 仟寻职位申请
        // TODO 职位表的source_id > 0 只能识别出是ats职位/ 不能识别出该ats是否可用
        if (jobPositionRecord != null && jobPositionRecord.getSourceId() > 0 && jobApplication.getOrigin() != 64) {
            jobApplication.setAts_status(IS_ATS_APPLICATION);
        } else {
            // 默认仟寻投递
            jobApplication.setAts_status(IS_MOSEEKER_APPLICATION);
        }

    }

    /**
     * 是否申请过该职位 <p>
     *
     * @param userId     用户id
     * @param positionId 职位id
     */
    private boolean isAppliedPosition(long userId, long positionId) throws Exception {
        Query query = new QueryBuilder().where("applier_id", userId).and("position_id", positionId).buildQuery();
        Integer count = jobApplicationDao.getCount(query);
        return count > 0 ? true : false;
    }

    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    @CounterIface
    @Transactional
    public Response deleteRedisKeyApplicationCheckCount(long userId, long companyId) throws TException, RedisException {
        try {
            redisClient.del(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(userId), String.valueOf(companyId));
            return new Response(0, "ok");
        } catch (RedisException e) {
            WarnService.notify(e);
            throw new TException();
        } catch (Exception e) {
            logger.error("deleteRedisKeyApplicationCheckCount error:", e);
            throw new TException();
        }
    }

    /**
     * 校验职位的有效性
     *
     * @param JobPositonrecord 职位记录
     */
    private Response validateJobPosition(JobPositionRecord JobPositonrecord) {

        Response response = new Response(0, "ok");

        // 职位是否存在
        if (JobPositonrecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_POSITION_NOT_EXIST);
        }

        // 职位是否下线, 0:有效的职位
        if (JobPositonrecord.getStatus() > 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_POSITION_STATUS_STOP);
        }
        return response;
    }

    /**
     * 校验申请用户信息
     *
     * @param userId 用户ID
     */
    private Response validateUserApplicationInfo(long userId) throws Exception {

        Response response = new Response(0, "ok");
        Query query = new QueryBuilder().where("id", userId).buildQuery();
        UserUserRecord userUserRecord = userUserDao.getRecord(query);
        // 申请人是否存在
        if (userUserRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_USER_INVALID);
        }
        // TODO 用户信息 申请必填逻辑, 联调的时候, 将下面的注释打开
//        // 用户姓名必填项校验
//        if(userUserRecord.getName() == null || "".equals(userUserRecord.getName())){
//            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "姓名"));
//        }
//        // 用户邮箱必填项校验
//        if(userUserRecord.getEmail() == null || "".equals(userUserRecord.getEmail())){
//            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "邮箱"));
//        }
//        // 用户手机号必填项校验
//        if(userUserRecord.getMobile() == null || userUserRecord.getMobile() == 0){
//            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "手机号"));
//        }
        return response;
    }


    /**
     * 通过ApplicationId 获取company_id 和account_id
     */
    @Transactional
    public ApplicationResponse getAccountIdAndCompanyId(long applicationId) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        try {
            Query query = new QueryBuilder().where("id", applicationId).buildQuery();
            JobApplicationRecord jobApplicationRecord = jobApplicationDao.getRecord(query);
            if (jobApplicationRecord != null) {
                Query query1 = new QueryBuilder().where("id", jobApplicationRecord.getPositionId().intValue()).buildQuery();
                JobPositionRecord jobPositionRecord = jobPositionDao.getRecord(query1);
                applicationResponse.setAccount_id(jobPositionRecord.getPublisher());
                applicationResponse.setCompany_id(jobApplicationRecord.getCompanyId().intValue());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return applicationResponse;
    }

    private int archiveApplicationRecord(JobApplicationRecord jobApplicationRecord) throws TException {
        // TODO Auto-generated method stub
        int status = 0;
        try {
            HistoryJobApplicationRecord historyJobApplicationRecord = setHistoryJobApplicationRecord(jobApplicationRecord);
            if (historyJobApplicationRecord != null) {
                historyJobApplicationDao.addRecord(historyJobApplicationRecord);
                status = historyJobApplicationRecord.getId();
            }
            if (status > 0) {
                status = jobApplicationDao.deleteRecord(jobApplicationRecord);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
        return status;
    }


    /**
     * 根据指定渠道 channel=5（支付宝），指定时间段（"2017-05-10 14:57:14"）， 返回给第三方渠道同步的申请状态。
     *
     * @param channel
     * @param start_time
     * @param end_time
     * @return
     */

    @Transactional
    public Response getApplicationListForThirdParty(int channel, String start_time, String end_time) {
        Query query = new Query.QueryBuilder().select("id").select("position_id").select("applier_id").select("update_time").select("not_suitable").select("app_tpl_id")
                .where(new Condition("update_time", start_time, ValueOp.GE))
                .and(new Condition("update_time", end_time, ValueOp.LT)).buildQuery();

        List<JobApplicationDO> jobApplicationlist = jobApplicationDao.getApplications(query);
        if (jobApplicationlist == null) {
            return ResponseUtils.success(new ArrayList<>());
        }

        List<Integer> applier_ids = jobApplicationlist.stream().map(JobApplicationDO::getApplierId).collect(Collectors.toList());
        query = new Query.QueryBuilder().where(new Condition("user_id", applier_ids.toArray(), ValueOp.IN)).buildQuery();
        List<UserAliUserDO> aliUserList = userAliUserDao.getDatas(query);
        if (aliUserList == null) {
            return ResponseUtils.success(new ArrayList<>());
        }
        HashMap<Integer, String> userToAlidMap = new HashMap<Integer, String>(aliUserList.size());
        for (UserAliUserDO aliUser : aliUserList) {
            userToAlidMap.put(aliUser.getUserId(), aliUser.getUid());
        }

        List<HashMap> syncApplications = new ArrayList<HashMap>();
        for (JobApplicationDO applicationDO : jobApplicationlist) {
            String thirdparty_uid = userToAlidMap.get(applicationDO.getApplierId());
            if (thirdparty_uid != null) {
                HashMap thirdPartyApplication = new HashMap();
                thirdPartyApplication.put("source_id", String.valueOf(applicationDO.getPositionId()));
                thirdPartyApplication.put("alipay_user_id", thirdparty_uid);

                Status status = Status.instanceFromCode(String.valueOf(applicationDO.getAppTplId()));
                AlipaycampusStatus alipaycampus = StatusChangeUtil.getAlipaycampusStatus(status);
                if (alipaycampus == null) {
                    continue; // 忽略不需要要同步的
                }
                thirdPartyApplication.put("status", alipaycampus.getValue());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    java.util.Date update_time = sdf.parse(applicationDO.getUpdateTime());
                    thirdPartyApplication.put("update_time", String.valueOf(update_time.getTime()));
                } catch (Exception e) {
                    continue;
                }

                syncApplications.add(thirdPartyApplication);
            }
        }

        return ResponseUtils.success(syncApplications);
    }


    /**
     * 转换归档申请记录
     *
     * @param jobApplicationRecord
     * @return
     */
    private HistoryJobApplicationRecord setHistoryJobApplicationRecord(JobApplicationRecord jobApplicationRecord) {

        HistoryJobApplicationRecord historyJobApplicationRecord = null;

        if (jobApplicationRecord != null) {
            historyJobApplicationRecord = new HistoryJobApplicationRecord();

            historyJobApplicationRecord.setId(jobApplicationRecord.getId());
            historyJobApplicationRecord.setPositionId(jobApplicationRecord.getPositionId());
            historyJobApplicationRecord.setRecommenderId(jobApplicationRecord.getRecommenderId());
            historyJobApplicationRecord.setLApplicationId(jobApplicationRecord.getLApplicationId());
            historyJobApplicationRecord.setUserId(jobApplicationRecord.getApplierId());
            historyJobApplicationRecord.setAtsStatus((jobApplicationRecord.getAtsStatus()));
            historyJobApplicationRecord.setDisable((int) (jobApplicationRecord.getDisable()));
            historyJobApplicationRecord.setRoutine((int) (jobApplicationRecord.getRoutine()));
            historyJobApplicationRecord.setIsViewed((byte) (jobApplicationRecord.getIsViewed()));
            historyJobApplicationRecord.setViewCount((int) (jobApplicationRecord.getViewCount()));
            historyJobApplicationRecord.setNotSuitable((byte) (jobApplicationRecord.getNotSuitable()));
            historyJobApplicationRecord.setCompanyId(jobApplicationRecord.getCompanyId());
            historyJobApplicationRecord.setAppTplId(jobApplicationRecord.getAppTplId());
            historyJobApplicationRecord.setProxy((byte) (jobApplicationRecord.getProxy()));
            historyJobApplicationRecord.setApplyType((int) (jobApplicationRecord.getApplyType()));
            historyJobApplicationRecord.setEmailStatus((int) (jobApplicationRecord.getEmailStatus()));
            historyJobApplicationRecord.setSubmitTime(jobApplicationRecord.getSubmitTime());
            historyJobApplicationRecord.setCreateTime(jobApplicationRecord.get_CreateTime());
            historyJobApplicationRecord.setUpdateTime(jobApplicationRecord.getUpdateTime());
        }
        return historyJobApplicationRecord;
    }

    private ApplicationSaveResultVO saveJobApplication(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord) throws CommonException {
        // TODO Auto-generated method stub
        if (jobApplicationRecord.getRecommenderUserId() != null && jobApplicationRecord.getRecommenderUserId().intValue() > 0) {
            boolean existUserEmployee = employeeEntity.isEmployee(jobApplicationRecord.getRecommenderUserId(), jobApplicationRecord.getCompanyId());
            logger.info("JobApplicataionService saveJobApplication existUserEmployee:{}", existUserEmployee);
            if (!existUserEmployee) {
                logger.info("JobApplicataionService saveJobAp plication not employee");
                jobApplicationRecord.setRecommenderUserId(0);
            }
            if (jobApplicationRecord.getApplierId() != null && jobApplicationRecord.getApplierId().intValue() == jobApplicationRecord.getRecommenderUserId().intValue()) {
                logger.info("JobApplicataionService saveJobApplication applier_id equals recommender_user_id");
                jobApplicationRecord.setRecommenderUserId(0);
            }

        }
        logger.info("JobApplicataionService saveJobApplication jobApplicationRecord:{}", jobApplicationRecord);

        ApplicationSaveResultVO resultVO = jobApplicationDao.addIfNotExists(jobApplicationRecord);
        if (!resultVO.isCreate()) {
            HrOperationRecordRecord hrOperationRecord = applicationEntity.getHrOperationRecordRecord(resultVO.getApplicationId(), jobApplicationRecord, jobPositionRecord);
            hrOperationRecordDao.addRecord(hrOperationRecord);
        }
        try {
            ThreadPool.Instance.startTast(() -> {
                logger.info("saveJobApplication updateApplyStatus applier_id:{}, position_id:{}", jobApplicationRecord.getApplierId(), jobApplicationRecord.getPositionId());
                chatService.updateApplyStatus(jobApplicationRecord.getApplierId(), jobApplicationRecord.getPositionId());
                return 0;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultVO;
    }

    public Response getHrApplicationNum(int user_id) {
        int num = 0;
        Query accountQuery = new Query.QueryBuilder().where(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), user_id).buildQuery();
        UserHrAccountDO accountDO = userHrAccountDao.getData(accountQuery);
        if (accountDO == null)
            return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
        Query companyQuery = null;
        Query positionQuery = null;
        List<JobApplicationDO> isViewCountList = null;
        List<Integer> accountIdList = new ArrayList<>();
       if(accountDO.getAccountType() == 0 ){
            Query companyAccountQuery = new Query.QueryBuilder().where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.getName(), accountDO.getId()).buildQuery();
            HrCompanyAccountDO companyAccountDO = hrCompanyAccountDao.getData(companyAccountQuery);
            if(companyAccountDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            companyQuery = new Query.QueryBuilder().where(HrCompany.HR_COMPANY.PARENT_ID.getName(), companyAccountDO.getCompanyId()).or("id", companyAccountDO.getCompanyId()).buildQuery();
            List<HrCompanyDO> companyDOList = hrCompanyDao.getDatas(companyQuery);
            if (companyDOList != null && companyDOList.size() > 0) {
                List<Integer> companyIdList = companyDOList.stream().map(m -> m.getId()).collect(Collectors.toList());
                Query companyAccountQuery1 = new Query.QueryBuilder().where(new Condition(HrCompanyAccount.HR_COMPANY_ACCOUNT.COMPANY_ID.getName(), companyIdList.toArray(), ValueOp.IN)).buildQuery();
                List<HrCompanyAccountDO> companyAccountList = hrCompanyAccountDao.getDatas(companyAccountQuery1);
                accountIdList = companyAccountList.stream().map(m -> m.getAccountId()).collect(Collectors.toList());
            }else {
                accountIdList.add(accountDO.getId());
            }

        }else{
            accountIdList.add(accountDO.getId());
        }

        positionQuery =  new Query.QueryBuilder().where(new Condition(JobPosition.JOB_POSITION.PUBLISHER.getName(), accountIdList.toArray(),ValueOp.IN)).buildQuery();
        List<JobPositionDO> positionDOList = jobPositionDao.getDatas(positionQuery);
        if(positionDOList != null && positionDOList.size()>0){
            List<Integer> positionIdList = positionDOList.stream().map(m -> m.getId()).collect(Collectors.toList());
            Query applicationQuery = new Query.QueryBuilder().select(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.APPLIER_ID.getName())
                    .where(new Condition(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.POSITION_ID.getName(), positionIdList.toArray(),ValueOp.IN))
                    .and(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.IS_VIEWED.getName(),1).andInnerCondition(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.APPLY_TYPE.getName(),0)
                    .orInnerCondition(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.APPLY_TYPE.getName(),1).and(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.EMAIL_STATUS.getName(), 0)
                    .groupBy(com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION.APPLIER_ID.getName()).buildQuery();
            logger.info("applicationQuery SQL :{}", applicationQuery);
            isViewCountList = jobApplicationDao.getDatas(applicationQuery);

        }
        if (isViewCountList != null)
            num = isViewCountList.size();
        logger.info("HR账号未读申请数量：{}", num);
        return ResponseUtils.success(num);
    }

    /**
     * HR查看申请
     * 查看申请的是为了处理HR查看，导出，下载某一个申请人信息时，将申请人对应的“是否查看申请”（是否查看的标记是在申请记录上，
     * 但是业务逻辑做过改版，现有的业务逻辑应该在申请人上比较合适）标记为“已经查看”状态。
     * 查看申请时需要权限的，主账号可以查看公司下的任何简历，子账号只能查看自己发布职位收到的申请人的简历。
     * 查看简历还会触发发送模板消息和积分添加。如果申请有推荐人，并且该公司存在公司积分配置，
     * 那么会对推荐人添加对应积分。HR查看申请 对推荐人（申请是员工转发的候选人投递的，
     * 那么员工就是该申请的推荐人）添加对应积分（需要公司配置了该操作的积分项）。
     * @param hrId HR编号
     * @param applicationIdList  申请集合
     * @throws CommonException 业务异常 (41012,  "请提交正确的HR信息!" )(41013,  "HR账号类型异常!" )(41014,  "申请信息不正确!" )(41010,  "没有权限查看申请信息!" )
     */
    @Transactional
    @CounterIface
    public void viewApplications(int hrId, List<Integer> applicationIdList) throws CommonException {

        HREntity hrEntity = applicationRepository.fetchHREntity(hrId);
        ApplicationBatchEntity applicationBatchEntity = applicationRepository.fetchApplicationEntity(applicationIdList);
        hrEntity.viewApplication(applicationBatchEntity);
    }

    /**
     * 员工代理投递
     * @param referenceId 推荐者
     * @param applierId 申请者
     * @param positionIdList 职位列表
     * @return 申请编号
     */
    @CounterIface
    public List<Integer> employeeProxyApply(int referenceId, int applierId, List<Integer> positionIdList) throws ApplicationException {

        //校验员工
        UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(referenceId);
        if (employeeDO == null) {
            throw ApplicationException.APPLICATION_EMPLOYEE_NOT_EXIST;
        }
        if (!positionEntity.validatePositions(employeeDO.getCompanyId(), positionIdList)) {
            throw ApplicationException.APPLICATION_POSITIONS_NOT_LEGAL;
        }

        //校验职位
        for (Integer positionId: positionIdList) {
            try {
                Future<Response> future = tp.startTast(() -> profileOtherService.checkProfileOther(applierId, positionId));
                JSONObject jsonObject = JSON.parseObject(future.get().getData());
                if (!(Boolean)jsonObject.get("result")) {
                    throw ApplicationException.APPLICATION_CUSTOM_POSITION_VALIDATE_FAILED;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw ApplicationException.APPLICATION_CUSTOM_POSITION_VALIDATE_FAILED;
            }
        }

        List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition> positionList =
                positionEntity.getPositionInfoByIdList(positionIdList);
        if (positionList.size() != positionIdList.size()) {
            throw ApplicationException.APPLICATION_POSITIONS_NOT_LEGAL;
        }

        //校验投递限制
        UserApplyCount userApplyCount = applicationEntity.getApplyCount(applierId, employeeDO.getCompanyId());
        for (com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition position : positionList) {
            if (position.getCandidateSource() == 0) {
                userApplyCount.setSocialApplyCount(userApplyCount.getSocialApplyCount() + 1);
            } else {
                userApplyCount.setSchoolApplyCount(userApplyCount.getSchoolApplyCount() + 1);
            }
        }
        checkApplicationCountAtCompany(employeeDO.getCompanyId(), userApplyCount);


        List<ApplicationSaveResultVO> applyIdList = applicationEntity.storeEmployeeProxyApply(referenceId, applierId,
                employeeDO.getCompanyId(), positionIdList);

        if (applyIdList != null && applyIdList.size() > 0) {

            List<ApplicationSaveResultVO> createList = applyIdList.stream().filter(ApplicationSaveResultVO::isCreate)
                    .collect(Collectors.toList());
            if (createList == null || createList.size() == 0) {
                throw ApplicationException.APPLICATION_CREATE_FAILED;
            }
            addApplicationCountAtCompany(applierId, employeeDO.getCompanyId(), userApplyCount);

            for (ApplicationSaveResultVO resultVO : applyIdList) {
                tp.startTast(() -> {
                    logger.info("saveJobApplication updateApplyStatus applier_id:{}, position_id:{}",
                            resultVO.getApplierId(), resultVO.getPositionId());
                    try {
                        chatService.updateApplyStatus(resultVO.getApplierId(), resultVO.getPositionId());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                    if (resultVO.isCreate()) {
                        sendMessageAndEmailThread(resultVO.getApplicationId(), resultVO.getPositionId(), 0,
                                0, referenceId, resultVO.getApplierId(),
                                ApplicationSource.EMPLOYEE_CHATBOT.getValue());
                    }
                    return 0;
                });
            }
            return applyIdList.stream().map(ApplicationSaveResultVO::getApplicationId).collect(Collectors.toList());
        }

        //todo 如果投递失败，则需要将投递次数减回去

        return new ArrayList<>();
    }

    /**
     * 添加该人该公司的申请次数
     */
    private void addApplicationCountAtCompany(JobApplication jobApplication, byte candidateSource) {

        try {
            applicationEntity.addApplicationCountAtCompany(jobApplication.getAppid(), (int)jobApplication.getCompany_id(), candidateSource);

        } catch (RedisException e) {
            WarnService.notify(e);
        }
    }

    /**
     * 修改用户申请数据
     * @param userId 用户编号
     * @param companyId 公司编号
     * @param applyCount 申请数据
     */
    private void addApplicationCountAtCompany(int userId, int companyId, UserApplyCount applyCount) {

        try {
            redisClient.set(Constant.APPID_ALPHADOG,
                    REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(userId),
                    String.valueOf(companyId),
                    JSON.toJSONString(applyCount),
                    (int) DateUtils.calcCurrMonthSurplusSeconds());

        } catch (RedisException e) {
            WarnService.notify(e);
        }
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验 超出申请次数限制, 每月每家公司一个人只能申请10次 <p>
     *  @param userId    用户id
     * @param companyId 公司id
     * @param candidateSource
     */
    private Response checkApplicationCountAtCompany(long userId, long companyId, byte candidateSource) {

        try {

            applicationEntity.checkApplicationCountAtCompany(userId, companyId, candidateSource);
        } catch (com.moseeker.entity.exception.ApplicationException e) {
            return ResponseUtils.fail(e.getCode(), e.getMessage());
        } catch (RedisException e) {
            WarnService.notify(e);
        }
        return ResponseUtils.success("SUCCESS");
    }

    /**
     * 校验是否超过投递次数上线
     * @param companyId 公司编号
     * @param userApplyCount 投递数据
     * @throws ApplicationException 业务异常
     */
    private void checkApplicationCountAtCompany(long companyId, UserApplyCount userApplyCount) throws ApplicationException {

        logger.info("userApplyCount使用校招参数：{};社招参数:{}", userApplyCount.getSchoolApplyCount(), userApplyCount.getSocialApplyCount());
        UserApplyCount conf = applicationEntity.getApplicationCountLimit((int) companyId);
        logger.info("userApplyCount参数校招参数：{};社招参数:{}", userApplyCount.getSchoolApplyCount(), userApplyCount.getSocialApplyCount());
        if (userApplyCount.getSocialApplyCount() >= conf.getSocialApplyCount()) {
            throw ApplicationException.APPLICATION_VALIDATE_SOCIAL_COUNT_CHECK;
        }
        if (userApplyCount.getSchoolApplyCount() >= conf.getSchoolApplyCount()) {
            throw ApplicationException.APPLICATION_VALIDATE_SCHOOL_COUNT_CHECK;
        }
    }
}

