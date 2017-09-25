package com.moseeker.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.application.service.application.StatusChangeUtil;
import com.moseeker.application.service.application.alipay_campus.AlipaycampusStatus;
import com.moseeker.application.service.application.qianxun.Status;
import com.moseeker.baseorm.dao.historydb.HistoryJobApplicationDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobResumeOtherDao;
import com.moseeker.baseorm.dao.userdb.UserAliUserDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryJobApplicationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.application.struct.ApplicationResponse;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserAliUserDO;

import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * @author ltf 申请服务 2016年11月3日
 */
@Service
@Transactional
public class JobApplicataionService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    // 申请次数限制 3次
    private static final int APPLICATION_COUNT_LIMIT = 3;
    // ats投递
    private static final int IS_MOSEEKER_APPLICATION = 0;
    private static final int IS_ATS_APPLICATION = 1;
    // 申请次数redis key
    private static final String REDIS_KEY_APPLICATION_COUNT_CHECK = "APPLICATION_COUNT_CHECK";
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
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private UserUserDao userUserDao;
    @Autowired
    private UserAliUserDao userAliUserDao;

    @Autowired
    private UserEmployeeDao userEmployeedao;

    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;

    @Autowired
    private HistoryJobApplicationDao historyJobApplicationDao;

    @Autowired
    EmployeeEntity employeeEntity;

    /**
     * 创建申请
     *
     * @param jobApplication 申请参数
     * @return 新创建的申请记录ID
     */
    @SuppressWarnings("serial")
    @CounterIface
    public Response postApplication(JobApplication jobApplication) throws TException {
        logger.info("JobApplicataionService postApplication jobApplication:{}", jobApplication);
        try {
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
            long userId=(long)jobApplication.getApplier_id();
            int companyId=jobPositionRecord.getCompanyId();
            Query queryEmployee=new Query.QueryBuilder().where("sys_user_id",userId)
                    .and("company_id",companyId)
                    .and("disable",0)
                    .and("activation",0)
                    .buildQuery();
            UserEmployeeDO userEmployeeDO=userEmployeedao.getEmployee(queryEmployee);
            if(userEmployeeDO!=null){
                return ResponseUtils.fail(1,"申请人已经是该公司的员工，所以无法申请该职位");
            }
            if (checkApplicationCountAtCompany(jobApplication.getApplier_id(), jobPositionRecord.getCompanyId())) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_VALIDATE_COUNT_CHECK);
            }
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
            int jobApplicationId = this.saveJobApplication(jobApplicationRecord, jobPositionRecord);
            if (jobApplicationId > 0) {
                // proxy 0: 正常投递, 1: 代理投递, null:默认为0
                // 代理投递不能增加用户的申请限制次数
                if (jobApplicationRecord.getProxy() == null || jobApplicationRecord.getProxy() == 0) {
                    // 添加该人该公司的申请次数
                    addApplicationCountAtCompany(jobApplication);
                }
                // 返回 jobApplicationId
                return ResponseUtils.success(new HashMap<String, Object>() {
                                                 {
                                                     put("jobApplicationId", jobApplicationId);
                                                 }
                                             }
                );
            }
        } catch (Exception e) {
            logger.error("postResources JobApplication error: ", e);
            throw new TException();
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
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

    @SuppressWarnings("serial")
    @CounterIface
    public Response postApplicationIfNotApply(JobApplication jobApplication) throws TException {
        try {
            appIDToSource(jobApplication);
            // 获取该申请的职位
            Query query = new QueryBuilder().where("id", jobApplication.position_id).buildQuery();
            JobPositionRecord jobPositionRecord = jobPositionDao.getRecord(query);
            //校验申请来源的有效性
            if (jobApplication.getOrigin() == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_SOURCE_NOTEXIST);
            }
            // 职位有效性验证
            Response responseJob = validateJobPosition(jobPositionRecord);
            if (responseJob.status > 0) {
                return responseJob;
            }
            // 初始化参数
            initJobApplication(jobApplication, jobPositionRecord);
            // 添加申请
            JobApplicationRecord jobApplicationRecord = (JobApplicationRecord) BeanUtils.structToDB(jobApplication, JobApplicationRecord.class);
            if (jobApplicationRecord.getWechatId() == null) {
                jobApplicationRecord.setWechatId((int) (0));
            }
            int jobApplicationId = this.saveApplicationIfNotExist(jobApplicationRecord, jobPositionRecord);
            if (jobApplicationId > 0) {
                // proxy 0: 正常投递, 1: 代理投递, null:默认为0
                // 代理投递不能增加用户的申请限制次数
                if (jobApplicationRecord.getProxy() == null || jobApplicationRecord.getProxy() == 0) {
                    // 添加该人该公司的申请次数
                    addApplicationCountAtCompany(jobApplication);
                }
                // 返回 jobApplicationId
                return ResponseUtils.success(new HashMap<String, Object>() {
                                                 {
                                                     put("jobApplicationId", jobApplicationId);
                                                 }
                                             }
                );
            }
        } catch (Exception e) {
            logger.error("postResources JobApplication error: ", e);
            throw new TException();
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 更新申请数据
     *
     * @param jobApplication 用户实体
     */
    @SuppressWarnings("serial")
    @CounterIface
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
        if (jobApplicationDO != null) {
            ApplicationSource applicationSource = ApplicationSource.instaceFromInteger(jobApplication.getOrigin());
            if (applicationSource == null) {
                jobApplication.setOrigin(jobApplication.getOrigin() | jobApplication.getOrigin());
            } else {
                jobApplication.setOrigin(applicationSource.andSource(jobApplicationDO.getOrigin()));
            }
            // 更新申请
            JobApplicationRecord jobApplicationRecord = BeanUtils.structToDB(jobApplication, JobApplicationRecord.class);
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            jobApplicationRecord.setUpdateTime(updateTime);
            updateStatus = jobApplicationDao.updateRecord(jobApplicationRecord);
        }
        return updateStatus;
    }

    /**
     * 删除申请记录
     *
     * @param applicationId 申请Id
     */
    @CounterIface
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
     * 添加该人该公司的申请次数
     */
    private void addApplicationCountAtCompany(JobApplication jobApplication) {

        String applicationCountCheck = null;
        try {
            applicationCountCheck = redisClient.get(Constant.APPID_ALPHADOG,
                    REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(jobApplication.applier_id),
                    String.valueOf(jobApplication.company_id));
            // 获取当前申请次数 +1
            if (applicationCountCheck != null) {
                redisClient.incr(Constant.APPID_ALPHADOG,
                        REDIS_KEY_APPLICATION_COUNT_CHECK,
                        String.valueOf(jobApplication.applier_id),
                        String.valueOf(jobApplication.company_id));
                // 本月第一次申请
            } else {
                redisClient.set(Constant.APPID_ALPHADOG,
                        REDIS_KEY_APPLICATION_COUNT_CHECK,
                        String.valueOf(jobApplication.applier_id),
                        String.valueOf(jobApplication.company_id),
                        "1",
                        (int) DateUtils.calcCurrMonthSurplusSeconds()
                );
            }
        } catch (RedisException e) {
            WarnService.notify(e);
        }
    }

    /**
     * 该人该公司的申请次数 -1
     */
    private void subApplicationCountAtCompany(JobApplicationRecord jobApplication) {

        try {
            String applicationCountCheck = redisClient.get(
                    Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(jobApplication.getApplierId()),
                    String.valueOf(jobApplication.getCompanyId()));
            // 获取当前申请次数 -1
            if (applicationCountCheck != null
                    && Integer.parseInt(applicationCountCheck) > 0
                    && Integer.parseInt(applicationCountCheck) <= this
                    .getApplicationCountLimit(jobApplication
                            .getCompanyId())) {

                redisClient.decr(Constant.APPID_ALPHADOG,
                        REDIS_KEY_APPLICATION_COUNT_CHECK,
                        String.valueOf(jobApplication.getApplierId()),
                        String.valueOf(jobApplication.getCompanyId()));
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
    public Response validateUserApplicationCheckCountAtCompany(long userId, long companyId) {
        try {
            return ResponseUtils.success(this.checkApplicationCountAtCompany(userId, companyId));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("validateUserApplicationCheckCountAtCompany error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
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
     * 申请必填项校验
     */
    private Response validateJobApplication(JobApplication jobApplication) throws Exception {

        // 必填项校验
        Response response = validateGetApplicationByUserIdAndPositionId(jobApplication.applier_id,
                jobApplication.position_id, jobApplication.company_id);

        if (response.status == Constant.OK) {

            // 一个用户在一家公司的每月的申请次数校验
            boolean checkApplicationCount = this.checkApplicationCountAtCompany(jobApplication.applier_id,
                    jobApplication.company_id);
            if (checkApplicationCount) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_VALIDATE_COUNT_CHECK);
            }

            // 判断是否申请过该职位
            boolean isApplied = this.isAppliedPosition(jobApplication.applier_id, jobApplication.position_id);
            if (isApplied) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_POSITION_DUPLICATE);
            }

            // 申请人的有效性验证
            response = validateUserApplicationInfo(jobApplication.applier_id);
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
        if (jobPositionRecord != null && jobPositionRecord.getSourceId() > 0) {
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
     * 一个用户在一家公司的每月的申请次数校验 超出申请次数限制, 每月每家公司一个人只能申请10次 <p>
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    private boolean checkApplicationCountAtCompany(long userId, long companyId) {

        try {
            String applicationCountCheck = redisClient.get(
                    Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(userId), String.valueOf(companyId));
            // 超出申请次数限制, 每月每家公司一个人只能申请3次
            if (applicationCountCheck != null
                    && Integer.parseInt(applicationCountCheck) >= this
                    .getApplicationCountLimit((int) companyId)) {
                return true;
            }
        } catch (RedisException e) {
            WarnService.notify(e);
        }
        return false;
    }

    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    @CounterIface
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
     * 获取申请限制次数 默认3次 企业有自己的配置,使用企业的配置
     *
     * @param companyId 公司ID
     */
    private int getApplicationCountLimit(int companyId) {
        int applicaitonCountLimit = APPLICATION_COUNT_LIMIT;
        Query query = new QueryBuilder().where("company_id", companyId).buildQuery();
        HrCompanyConfRecord hrCompanyConfRecord = hrCompanyConfDao.getRecord(query);
        if (hrCompanyConfRecord != null && hrCompanyConfRecord.getApplicationCountLimit().shortValue() > 0) {
            applicaitonCountLimit = hrCompanyConfRecord.getApplicationCountLimit().shortValue();
        }
        logger.info("JobApplicataionService getApplicationCountLimit applicaitonCountLimit:{}", applicaitonCountLimit);
        return applicaitonCountLimit;
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

    private int saveJobApplication(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord) throws TException {
        // TODO Auto-generated method stub
        int appId = 0;
        try {
            if (jobApplicationRecord.getRecommenderUserId() != null && jobApplicationRecord.getRecommenderUserId().intValue() > 0) {
                boolean existUserEmployee = employeeEntity.isEmployee(jobApplicationRecord.getRecommenderUserId(), jobApplicationRecord.getCompanyId());
                logger.info("JobApplicataionService saveJobApplication existUserEmployee:{}", existUserEmployee);
                if (!existUserEmployee) {
                    logger.info("JobApplicataionService saveJobApplication not employee");
                    jobApplicationRecord.setRecommenderUserId(0);
                }
                if (jobApplicationRecord.getApplierId() != null && jobApplicationRecord.getApplierId().intValue() == jobApplicationRecord.getRecommenderUserId().intValue()) {
                    logger.info("JobApplicataionService saveJobApplication applier_id equals recommender_user_id");
                    jobApplicationRecord.setRecommenderUserId(0);
                }

            }
            logger.info("JobApplicataionService saveJobApplication jobApplicationRecord:{}", jobApplicationRecord);
            jobApplicationDao.addRecord(jobApplicationRecord);
            appId = jobApplicationRecord.getId();
            if (appId > 0) {
                HrOperationRecordRecord hrOperationRecord = getHrOperationRecordRecord(appId, jobApplicationRecord, jobPositionRecord);
                hrOperationRecordDao.addRecord(hrOperationRecord);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
        return appId;
    }

    //构建hr操作记录record
    private HrOperationRecordRecord getHrOperationRecordRecord(long appId,
                                                               JobApplicationRecord jobApplicationRecord,
                                                               JobPositionRecord JobPositonrecord) {
        HrOperationRecordRecord hrOperationRecordRecord = new HrOperationRecordRecord();
        hrOperationRecordRecord.setAdminId(JobPositonrecord.getPublisher().longValue());
        hrOperationRecordRecord.setCompanyId(jobApplicationRecord.getCompanyId().longValue());
        hrOperationRecordRecord.setAppId(appId);
        hrOperationRecordRecord.setOperateTplId(jobApplicationRecord.getAppTplId().intValue());
        return hrOperationRecordRecord;
    }

    private int saveApplicationIfNotExist(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord) throws TException {
        // TODO Auto-generated method stub
        int appId = 0;
        try {
            HrOperationRecordRecord hrOperationRecord = null;
            jobApplicationDao.addRecord(jobApplicationRecord);
            appId = jobApplicationRecord.getId().intValue();
            if (appId > 0) {
                hrOperationRecord = getHrOperationRecordRecord(appId, jobApplicationRecord, jobPositionRecord);
                hrOperationRecordDao.addRecord(hrOperationRecord);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
        return appId;
    }


}
