package com.moseeker.profile.service.impl.retriveprofile.flow.job51;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.parameters.ApplicationTaskParam;
import org.springframework.stereotype.Component;

/**
 * 申请任务 参数处理生成工具
 * Created by jack on 20/07/2017.
 */
@Component
public class Job51ApplicationTaskParamUtil implements CouplerParamUtil<ApplicationTaskParam, Integer, Integer> {
    @Override
    public ApplicationTaskParam parseExecutorParam(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        ApplicationTaskParam applicationTaskParam = new ApplicationTaskParam();
        applicationTaskParam.setPositionId(globalParam.getPositionId());
        int id = 0;
        if (globalParam.getUser().get("id") != null) {
            id = (Integer) globalParam.getUser().get("id");
        }
        applicationTaskParam.setUserId(id);
        return applicationTaskParam;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
