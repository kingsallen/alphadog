package com.moseeker.profile.service.impl.retriveprofile.flow.job51;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;
import org.springframework.stereotype.Component;

/**
 * profile任务参数生成工具。
 * Created by jack on 20/07/2017.
 */
@Component
public class Job51ProfileTaskParamUtil implements CouplerParamUtil<ProfilePojo, Integer, Integer> {

    @Override
    public ProfilePojo parseExecutorParam(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        ProfilePojo profilePojo = ProfilePojo.parseProfile(globalParam.getProfile());
        return profilePojo;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
