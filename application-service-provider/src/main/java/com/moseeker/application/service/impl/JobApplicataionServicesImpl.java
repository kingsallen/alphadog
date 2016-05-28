package com.moseeker.application.service.impl;

import com.moseeker.application.dao.JobApplicationDao;
import com.moseeker.application.dao.JobResumeOtherDao;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.thrift.gen.application.service.JobApplicationServices.Iface;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;

import org.apache.thrift.TException;
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

    @Autowired
    protected JobApplicationDao jobApplicationDao;

    @Autowired
    protected JobResumeOtherDao jobResumeOtherDao;

    /**
     * 创建申请
     * @param JobApplication 申请参数
     * @return 新创建的申请记录ID
     * @throws TException
     */
    @Override
    public Response postApplication(JobApplication JobApplication) throws TException {
        try {
            // 必填项验证
            Response response = validateJobApplication(JobApplication);
            if (response.status > 0){
                return response;
            }

            // 添加申请
            JobApplicationRecord jobApplicationRecord = (JobApplicationRecord)BeanUtils.structToDB(JobApplication,
                    JobApplicationRecord.class);

            int jobApplicationId = jobApplicationDao.postResource(jobApplicationRecord);
            if (jobApplicationId > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("jobApplicationId", jobApplicationId);
                return ResponseUtils.success(hashmap); // 返回 jobApplicationId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postResources JobApplication error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail("JobApplication failed");
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
    public Response getApplicationByUserIdAndPositionId(long userId, long positionId) throws TException {
        try {
            Integer count = jobApplicationDao.getApplicationByUserIdAndPositionId(userId, positionId);
            return ResponseUtils.success(count > 0?true:false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postJobResumeOther JobResumeOther error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 申请必填项校验
     * @param JobApplication
     * @return
     */
    private Response validateJobApplication(JobApplication JobApplication){

        Response response = new Response(0, "ok");

        // 参数校验
        if(JobApplication == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if(JobApplication.applier_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "applier_id"));
        }
        if(JobApplication.company_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "company_id"));
        }
        if(JobApplication.position_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
        }
        if(JobApplication.wechat_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "wechat_id"));
        }
        if(JobApplication.app_tpl_id == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "app_tpl_id"));
        }

        // 业务校验

        // TODO: 职位可申请校验, 把数据放进来, 当收集数据了, 申请记录显示时, 控制一下数据

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

}
