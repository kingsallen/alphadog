package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.parameters.JobResumeTaskParam;
import org.springframework.stereotype.Component;

/**
 * job_resume任务参数处理工具
 * Created by jack on 20/07/2017.
 */
@Component
public class JobResumeTaskParamUtil implements CouplerParamUtil<JobResumeTaskParam, Integer, Integer> {

    @Override
    public JobResumeTaskParam parseExecutorParam(Integer applicationId, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;
        JobResumeTaskParam jobResumeTaskParam = new JobResumeTaskParam();
        jobResumeTaskParam.setAppId(applicationId);
        jobResumeTaskParam.setJobResume(aliPayRetrievalParam.getJobResume());
        return jobResumeTaskParam;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
