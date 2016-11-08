package com.moseeker.application.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.application.service.impl.JobApplicataionService;
import com.moseeker.thrift.gen.application.service.JobApplicationServices.Iface;
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
	
	@Autowired
	private JobApplicataionService service;

    /**
     * 创建申请
     * @param jobApplication 申请参数
     * @return 新创建的申请记录ID
     * @throws TException
     */
    @Override
    public Response postApplication(JobApplication jobApplication) throws TException {
        return service.postApplication(jobApplication);
    }


    /**
     * 更新申请数据
     *
     * @param jobApplication 用户实体
     *
     * */
    @Override
    public Response putApplication(JobApplication jobApplication) throws TException {
       return service.putApplication(jobApplication);
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
    		return service.deleteApplication(applicationId);
    }

    /**
     * 创建申请
     * @param jobResumeOther 申请参数
     * @return 新创建的申请记录ID
     * @throws TException
     */
    @Override
    public Response postJobResumeOther(JobResumeOther jobResumeOther) throws TException {
    		return service.postJobResumeOther(jobResumeOther);
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
    		return service.getApplicationByUserIdAndPositionId(userId, positionId, companyId);
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验
     *      超出申请次数限制, 每月每家公司一个人只能申请10次
     * <p>
     *
     * @param userId 用户id
     * @param companyId 公司id
     * @return
     */
    public Response validateUserApplicationCheckCountAtCompany(long userId, long companyId){
    		return service.validateUserApplicationCheckCountAtCompany(userId, companyId);
    }

    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     *
     * @param userId 用户id
     * @param companyId 公司id
     */
    public Response deleteRedisKeyApplicationCheckCount(long userId, long companyId) throws TException {
    		return service.deleteRedisKeyApplicationCheckCount(userId, companyId);
    }
}
