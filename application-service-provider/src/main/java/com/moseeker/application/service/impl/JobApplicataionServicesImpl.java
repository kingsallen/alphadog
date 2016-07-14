package com.moseeker.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.application.dao.JobApplicationDao;
import com.moseeker.application.dao.JobPositionDao;
import com.moseeker.application.dao.JobResumeOtherDao;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.thrift.gen.application.service.JobApplicationServices.Iface;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 申请服务实现类
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Service
public class JobApplicataionServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // 申请次数限制 3次
    private static final int APPLICATION_COUNT_LIMIT = 3;

    // ats投递
    private static final int IS_MOSEEKER_APPLICATION = 0;
    private static final int IS_ATS_APPLICATION = 1;

    // 申请次数redis key
    private static final String REDIS_KEY_APPLICATION_COUNT_CHECK = "APPLICATION_COUNT_CHECK";

    private RedisClient redisClient = RedisClientFactory.getCacheClient();

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private JobResumeOtherDao jobResumeOtherDao;

    @Autowired
    private JobPositionDao jobPositionDao;
    
    /**
     * 创建申请
     * @param jobApplication 申请参数
     * @return 新创建的申请记录ID
     * @throws TException
     */
    @Override
    public Response postApplication(JobApplication jobApplication) throws TException {
        try {
            // 申请验证
            Response response = validateJobApplication(jobApplication);
            if (response.status > 0){
                return response;
            }

            // 获取该申请的职位
            JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById((int)jobApplication.position_id);

            // TODO 职位校验 1.是否存在 2.是否过期

            // 初始化参数
            initJobApplication(jobApplication, jobPositionRecord);

            // 添加申请
            JobApplicationRecord jobApplicationRecord = (JobApplicationRecord)BeanUtils.structToDB(jobApplication,
                    JobApplicationRecord.class);

            if(jobApplicationRecord.getWechatId() == null) {
                jobApplicationRecord.setWechatId(UInteger.valueOf(0));
            }

            int jobApplicationId = jobApplicationDao.saveApplication(jobApplicationRecord);
            if (jobApplicationId > 0) {
                // 添加该人该公司的申请次数
                addApplicationCountAtCompany(jobApplication);

                return ResponseUtils.success(new HashMap<String, Object>(){
                        {
                            put("jobApplicationId", jobApplicationId);
                        }
                    }
                ); // 返回 jobApplicationId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postResources JobApplication error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }


    /**
     * 更新申请数据
     *
     * @param jobApplication 用户实体
     *
     * */
    @Override
    public Response putApplication(JobApplication jobApplication) throws TException {
        try {

            // 必填项校验
            if(jobApplication == null || jobApplication.getId() == 0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "id"));
            }

            // 更新申请
            JobApplicationRecord jobApplicationRecord = (JobApplicationRecord) BeanUtils.structToDB(jobApplication,
                    JobApplicationRecord.class);

            int updateStatus = jobApplicationDao.putResource(jobApplicationRecord);
            if (updateStatus > 0) {
                return ResponseUtils.success(new HashMap<String, Object>(){
                    {
                        put("updateStatus", updateStatus);
                    }
                }); // 返回 userFavoritePositionId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("putApplication JobApplicationRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 删除申请记录
     *
     * @param applicationId 申请Id
     * @return
     * @throws TException
     */
    @Override
    public Response deleteApplication(long applicationId) throws TException {
        try {
            // 必填项校验
            if(applicationId == 0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "id"));
            }

            JobApplicationRecord jobApplicationRecord = jobApplicationDao.getApplicationById(applicationId);
            if(jobApplicationRecord == null){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            // 删除的数据归档
            int status = jobApplicationDao.archiveApplicationRecord(jobApplicationRecord);

            // 归档成功后, 用户在该公司下的申请限制次数 -1
            if (status > 0){

                // 用户在该公司下的申请限制次数 -1 TODO: throw RedisException, 提示相关信息
                this.subApplicationCountAtCompany(jobApplicationRecord);

                return ResponseUtils.success(status);

            }else{
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_ARCHIVE_FAILED);
            }
        }catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("deleteApplication JobApplicationRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 添加该人该公司的申请次数
     *
     * @param jobApplication
     *
     * */
    private void addApplicationCountAtCompany(JobApplication jobApplication) {

        String applicationCountCheck = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(jobApplication.applier_id), String.valueOf(jobApplication.company_id));

        // 获取当前申请次数 +1
        if(applicationCountCheck != null){
            redisClient.incr(Constant.APPID_ALPHADOG,
                    REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(jobApplication.applier_id),
                    String.valueOf(jobApplication.company_id));
        // 本月第一次申请
        }else{
            redisClient.set(Constant.APPID_ALPHADOG,
                    REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(jobApplication.applier_id),
                    String.valueOf(jobApplication.company_id),
                    "1",
                    (int)DateUtils.calcCurrMonthSurplusSeconds()
            );
        }
    }

    /**
     * 该人该公司的申请次数 -1
     *
     * @param jobApplication
     *
     * */
    private void subApplicationCountAtCompany(JobApplicationRecord jobApplication){

        String applicationCountCheck = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(jobApplication.getApplierId()), String.valueOf(jobApplication.getCompanyId()));

        // 获取当前申请次数 -1
        if (applicationCountCheck != null
                && Integer.valueOf(applicationCountCheck) > 0
                && Integer.valueOf(applicationCountCheck) <= APPLICATION_COUNT_LIMIT) {

            redisClient.decr(Constant.APPID_ALPHADOG,
                    REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(jobApplication.getApplierId()),
                    String.valueOf(jobApplication.getCompanyId()));
        }
    }

    /**
     * 创建申请
     * @param jobResumeOther 申请参数
     * @return 新创建的申请记录ID
     * @throws TException
     */
    @Override
    public Response postJobResumeOther(JobResumeOther jobResumeOther) throws TException {
        try {
            // 必填项验证
            Response response = validateJobResumeOther(jobResumeOther);
            if (response.status > 0){
                return response;
            }

            // 自定义简历 - 申请副本 添加
            JobResumeOtherRecord jobResumeOtherRecord = (JobResumeOtherRecord)BeanUtils.structToDB(jobResumeOther,
                    JobResumeOtherRecord.class);
            int jobResumeOtherStatus = jobResumeOtherDao.postResource(jobResumeOtherRecord);
            if (jobResumeOtherStatus > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("jobResumeOtherStatus", jobResumeOtherStatus);
                return ResponseUtils.success(hashmap); // 返回 jobResumeOtherStatus
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postJobResumeOther JobResumeOther error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 判断当前用户是否申请了该职位
     *
     * @param userId 用户ID
     * @param positionId 职位ID
     *
     * @return true : 申请, false: 没申请过
     *
     * */
    @Override
    public Response getApplicationByUserIdAndPositionId(long userId, long positionId, long companyId) throws TException {
        try {
            Response response = validateGetApplicationByUserIdAndPositionId(userId, positionId, companyId);
            if(response.status == 0){
                return ResponseUtils.success(this.isAppliedPosition(userId, positionId));
            }else{
                response.setData(JSON.toJSONString(false));
                return response;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postJobResumeOther JobResumeOther error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验
     *      超出申请次数限制, 每月每家公司一个人只能申请3次
     * <p>
     *
     * @param userId 用户id
     * @param companyId 公司id
     * @return
     */
    public Response validateUserApplicationCheckCountAtCompany(long userId, long companyId){
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
     * 必填项校验 - 判断当前用户是否申请了该职位
     * <p>
     *
     * @param userId 用户ID
     * @param positionId 职位ID
     * @param companyId 公司ID
     *
     * @return
     */
    private Response validateGetApplicationByUserIdAndPositionId(long userId, long positionId, long companyId){

        Response response = new Response(0, "ok");

        if(userId == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "user_id"));
        }
        if(companyId == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "company_id"));
        }
        if(positionId == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }

        return response;
    }

    /**
     * 申请必填项校验
     * @param jobApplication
     * @return
     */
    private Response validateJobApplication(JobApplication jobApplication) throws Exception{

        // 必填项校验
        Response response = validateGetApplicationByUserIdAndPositionId(jobApplication.applier_id,
                jobApplication.position_id, jobApplication.company_id);

        // 一个用户在一家公司的每月的申请次数校验
        if(response.status == Constant.OK){
            boolean checkApplicationCount = this.checkApplicationCountAtCompany(jobApplication.applier_id,
                    jobApplication.company_id);
            if(checkApplicationCount){
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_VALIDATE_COUNT_CHECK);
            }
        }

        // 判断是否申请过该职位
        if(response.status == Constant.OK){
            boolean isApplied = this.isAppliedPosition(jobApplication.applier_id, jobApplication.position_id);
            if(isApplied){
                return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_POSITION_DUPLICATE);
            }
        }

        return response;
    }

    /**
     * 自定义简历 - 申请副本 必填项校验
     * @param jobResumeOther
     * @return
     */
    private Response validateJobResumeOther(JobResumeOther jobResumeOther){

        Response response = new Response(0, "ok");

        // 参数校验
        if(jobResumeOther == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if(jobResumeOther.app_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "app_id"));
        }
        return response;
    }

    /**
     * 创建申请时初始化申请变量
     * <p>
     *
     * @param jobApplication 申请参数
     * @param jobPositionRecord 职位记录
     */
    private void initJobApplication(JobApplication jobApplication, JobPositionRecord jobPositionRecord){

        // 初始化申请状态
        if(jobApplication.getApp_tpl_id() == 0){
            jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_APPLY);
        }

        // ats_status初始化 ats_status初始化:1 是ats职位申请  ats_status初始化:0 仟寻职位申请
        // TODO 职位表的source_id > 0 只能识别出是ats职位/ 不能识别出该ats是否可用
        if(jobPositionRecord != null && jobPositionRecord.getSourceId() > 0){
            jobApplication.setAts_status(IS_ATS_APPLICATION);
        }else{
            // 默认仟寻投递
            jobApplication.setAts_status(IS_MOSEEKER_APPLICATION);
        }

    }

    /**
     * 是否申请过该职位
     * <p>
     *
     * @param userId 用户id
     * @param positionId 职位id
     * @return
     * @throws Exception
     */
    private boolean isAppliedPosition(long userId, long positionId) throws Exception{
        Integer count = jobApplicationDao.getApplicationByUserIdAndPositionId(userId, positionId);
        return count > 0?true:false;
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验
     *      超出申请次数限制, 每月每家公司一个人只能申请3次
     * <p>
     *
     * @param userId 用户id
     * @param companyId 公司id
     * @return
     */
    private boolean checkApplicationCountAtCompany(long userId, long companyId){

        String applicationCountCheck = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(userId), String.valueOf(companyId));

        // 超出申请次数限制, 每月每家公司一个人只能申请3次
        if(applicationCountCheck != null && Integer.valueOf(applicationCountCheck) >= APPLICATION_COUNT_LIMIT){
            return true;
        }
        return false;
    }

    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     *
     * @param userId 用户id
     * @param companyId 公司id
     */
    public Response deleteRedisKeyApplicationCheckCount(long userId, long companyId) throws TException {
        try {
            redisClient.del(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(userId), String.valueOf(companyId));
            return new Response(0, "ok");
        }catch (Exception e){
            return new Response(1, "failed");
        }
    }
}
