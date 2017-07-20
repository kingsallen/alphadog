package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.parameters.UserTaskParam;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * UserTask参数处理工具
 * Created by jack on 20/07/2017.
 */
@Component
public class UserTaskParamUtil implements CouplerParamUtil<UserTaskParam, Integer, Object> {

    @Override
    public UserTaskParam parseExecutorParam(Object tmpParam, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;
        Map<String, Object> user = aliPayRetrievalParam.getUser();
        UserTaskParam userTaskParam = new UserTaskParam();
        userTaskParam.parse(user);
        return userTaskParam;
    }

    @Override
    public void storeTaskResult(Integer userId, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;
        aliPayRetrievalParam.getUser().put("id", userId);
    }
}
