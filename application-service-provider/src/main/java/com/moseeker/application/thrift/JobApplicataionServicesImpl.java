package com.moseeker.application.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.application.service.impl.JobApplicataionService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.application.service.JobApplicationServices.Iface;
import com.moseeker.thrift.gen.application.struct.ApplicationResponse;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;

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
    private JobApplicataionService service;

    /**
     * 创建申请
     *
     * @param jobApplication 申请参数
     * @return 新创建的申请记录ID
     */
    @Override
    public Response postApplication(JobApplication jobApplication){
    	try{
    		return service.postApplication(jobApplication);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    	}
    }


    /**
     * 更新申请数据
     *
     * @param jobApplication 用户实体
     */
    @Override
    public Response putApplication(JobApplication jobApplication){
    	try{
    		return service.putApplication(jobApplication);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    	}
    }

    /**
     * 删除申请记录
     *
     * @param applicationId 申请Id
     */
    @Override
    public Response deleteApplication(long applicationId){
    	try{
    		return service.deleteApplication(applicationId);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    		return ResponseUtils.fail(ConstantErrorCodeMessage.APPLICATION_ARCHIVE_FAILED);
    	}
    }

    /**
     * 创建申请
     *
     * @param jobResumeOther 申请参数
     * @return 新创建的申请记录ID
     */
    @Override
    public Response postJobResumeOther(JobResumeOther jobResumeOther) throws TException {
    	try{
    		return service.postJobResumeOther(jobResumeOther);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    	}
    }

    /**
     * 判断当前用户是否申请了该职位
     *
     * @param userId     用户ID
     * @param positionId 职位ID
     * @return true : 申请, false: 没申请过
     */
    @Override
    public Response getApplicationByUserIdAndPositionId(long userId, long positionId, long companyId) throws TException {
        return service.getApplicationByUserIdAndPositionId(userId, positionId, companyId);
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验
     * 超出申请次数限制, 每月每家公司一个人只能申请10次
     * <p>
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    public Response validateUserApplicationCheckCountAtCompany(long userId, long companyId) {
        return service.validateUserApplicationCheckCountAtCompany(userId, companyId);
    }

    @Override
    public ApplicationResponse getAccountIdAndCompanyId(long jobApplicationId) throws TException {
        return service.getAccountIdAndCompanyId(jobApplicationId);
    }

    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     *
     * @param userId    用户id
     * @param companyId 公司id
     */
    public Response deleteRedisKeyApplicationCheckCount(long userId, long companyId) throws TException {
        return service.deleteRedisKeyApplicationCheckCount(userId, companyId);
    }


    @Override
    public Response postApplicationIfNotApply(JobApplication application) throws TException {
    	try{
    		return service.postApplicationIfNotApply(application);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    	}
    }
}
