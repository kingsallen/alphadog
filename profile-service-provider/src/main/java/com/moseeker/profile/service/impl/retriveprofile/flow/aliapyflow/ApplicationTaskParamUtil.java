package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

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
public class ApplicationTaskParamUtil implements CouplerParamUtil<ApplicationTaskParam, Integer, Integer> {
    @Override
    public ApplicationTaskParam parseExecutorParam(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam) globalParam;
        ApplicationTaskParam applicationTaskParam = new ApplicationTaskParam();
        applicationTaskParam.setPositionId(aliPayRetrievalParam.getPositionId());
        applicationTaskParam.setOrigin(aliPayRetrievalParam.getOrigin());
        int id = 0;
        if (aliPayRetrievalParam.getUser().get("id") != null) {
            id = (Integer) aliPayRetrievalParam.getUser().get("id");
        }
        applicationTaskParam.setUserId(id);
        return applicationTaskParam;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
