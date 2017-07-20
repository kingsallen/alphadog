package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * profile任务参数生成工具。
 * Created by jack on 20/07/2017.
 */
@Component
public class ProfileTaskParamUtil implements CouplerParamUtil<ProfilePojo, Integer, Boolean> {

    @Override
    public ProfilePojo parseExecutorParam(Boolean tmpParam, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;
        ProfilePojo profilePojo = ProfilePojo.parseProfile(aliPayRetrievalParam.getProfile());
        return profilePojo;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
