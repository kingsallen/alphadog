package com.moseeker.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.application.dao.JobApplicationDao;
import com.moseeker.application.dao.JobResumeBasicDao;
import com.moseeker.application.dao.JobResumeOtherDao;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.jobdb.tables.records.JobResumeBasicRecord;
import com.moseeker.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.thrift.gen.application.service.JobApplicationServices.Iface;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeBasic;
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

    // 申请次数redis key
    private static final String REDIS_KEY_APPLICATION_COUNT_CHECK = "APPLICATION_COUNT_CHECK";

    private RedisClient redisClient = RedisClientFactory.getCacheClient();

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private JobResumeOtherDao jobResumeOtherDao;
    
    @Autowired
    private JobResumeBasicDao jobResumeBasicDao;
    
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

            // 初始化参数
            initJobApplication(jobApplication);

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
     * 添加该人该公司的申请次数
     *
     * @param jobApplication
     *
     * */
    private void addApplicationCountAtCompany(JobApplication jobApplication){

        Integer count = 1;

        String applicationCountCheck = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(jobApplication.applier_id), String.valueOf(jobApplication.company_id));

        // 获取当前申请次数 +1

        if(applicationCountCheck != null && Integer.valueOf(applicationCountCheck) <= APPLICATION_COUNT_LIMIT){
            count = Integer.valueOf(applicationCountCheck) + 1;
        }

        // 设置申请次数
        redisClient.set(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(jobApplication.applier_id), String.valueOf(jobApplication.company_id),
                String.valueOf(count));
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
     */
    private void initJobApplication(JobApplication jobApplication){

        // 初始化申请状态
        if(jobApplication.getApp_tpl_id() == 0){
            jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_APPLY);
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
}
