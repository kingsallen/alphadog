package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.parameters.CratePasswordTaskParam;
import org.springframework.stereotype.Component;

/**
 * 创建用户密码的任务参数解析工具
 * Created by jack on 20/07/2017.
 */
@Component
public class CreatePasswordTaskParamUtil implements CouplerParamUtil<CratePasswordTaskParam, Boolean, Integer> {

    @Override
    public CratePasswordTaskParam parseExecutorParam(Integer userId, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;
        String mobile = (String)aliPayRetrievalParam.getUser().get("mobile");
        CratePasswordTaskParam passwordTaskParam = new CratePasswordTaskParam();
        passwordTaskParam.setMobile(mobile);
        passwordTaskParam.setUserId(userId);
        return passwordTaskParam;
    }

    @Override
    public void storeTaskResult(Boolean tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
