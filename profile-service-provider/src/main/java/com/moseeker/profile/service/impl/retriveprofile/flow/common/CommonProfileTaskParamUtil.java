package com.moseeker.profile.service.impl.retriveprofile.flow.common;

import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import org.springframework.stereotype.Component;

/**
 * profile任务参数生成工具。
 * Created by jack on 20/07/2017.
 */
@Component
public class CommonProfileTaskParamUtil implements CouplerParamUtil<ProfilePojo, Integer, Integer> {

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
