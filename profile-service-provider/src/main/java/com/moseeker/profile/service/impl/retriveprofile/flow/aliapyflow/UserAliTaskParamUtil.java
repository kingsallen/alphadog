package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.parameters.UserAliTaskParam;
import org.springframework.stereotype.Component;

/**
 * 阿里用户任务之心参数的生成工具。根据上一个任务的执行结果和全局参数生成当前任务的参数.
 * Created by jack on 20/07/2017.
 */
@Component
public class UserAliTaskParamUtil implements CouplerParamUtil<UserAliTaskParam, Boolean, Boolean> {

    @Override
    public UserAliTaskParam parseExecutorParam(Boolean tmpParam, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;

        String uid = (String)aliPayRetrievalParam.getUser().get("uid");
        int id = 0;
        if (aliPayRetrievalParam.getUser().get("id") != null) {
            id = (Integer)aliPayRetrievalParam.getUser().get("id");
        }

        UserAliTaskParam userAliTaskParam = new UserAliTaskParam();
        userAliTaskParam.setUserId(id);
        userAliTaskParam.setUid(uid);

        return userAliTaskParam;
    }

    @Override
    public void storeTaskResult(Boolean tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
