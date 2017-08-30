package com.moseeker.profile.service.impl.retriveprofile.flow.common;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.ExceptionFactory;
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
public class CommonUserTaskParamUtil implements CouplerParamUtil<UserTaskParam, Integer, Object> {

    @Override
    public UserTaskParam parseExecutorParam(Object tmpParam, ExecutorParam globalParam) throws CommonException {
        Map<String, Object> user = globalParam.getUser();
        UserTaskParam userTaskParam = new UserTaskParam();
        userTaskParam.parse(user);
        return userTaskParam;
    }

    @Override
    public void storeTaskResult(Integer userId, ExecutorParam globalParam) throws CommonException {
        try {
            globalParam.getUser().put("id", userId);
            Map<String, Object> user = (Map<String, Object>) globalParam.getProfile().get("user");
            user.put("id", userId);
            Map<String, Object> profile = (Map<String, Object>) globalParam.getProfile().get("profile");
            profile.put("userId", userId);
        } catch (Exception e) {
            throw ExceptionFactory.buildException(com.moseeker.common.exception.Category.PROGRAM_PARAM_NOTEXIST);
        }
    }
}
