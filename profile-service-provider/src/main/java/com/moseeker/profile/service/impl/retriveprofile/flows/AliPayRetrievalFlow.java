package com.moseeker.profile.service.impl.retriveprofile.flows;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.Executor;
import com.moseeker.profile.service.impl.retriveprofile.RetrievalFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 支付宝简历回收流程
 * Created by jack on 10/07/2017.
 */
@Component("alipay_retrieval_flow")
public class AliPayRetrievalFlow extends RetrievalFlow {

    @Autowired
    protected Map<String, Executor> flowMap;

    @Override
    protected Executor customExcutor() throws CommonException {
        Executor firstExecutor = flowMap.get("ali_pay_user_executor");
        Executor secondExecutor = flowMap.get("createPassword_executor");
        Executor thirdExecutor = flowMap.get("profile_executor");
        Executor fourthExecutor = flowMap.get("application_executor");
        Executor fifthExecutor = flowMap.get("job_resume_executor");
        fourthExecutor.setNextExcutor(fifthExecutor);
        thirdExecutor.setNextExcutor(fourthExecutor);
        secondExecutor.setNextExcutor(thirdExecutor);
        firstExecutor.setNextExcutor(secondExecutor);
        return firstExecutor;
    }
}
