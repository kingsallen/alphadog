package com.moseeker.profile.service.impl.retriveprofile.flow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.executor.Coupler;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.RetrievalFlow;
import com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow.*;
import com.moseeker.profile.service.impl.retriveprofile.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付宝简历回收流程
 * Created by jack on 10/07/2017.
 */
@Component("alipay_retrieval_flow")
public class AliPayRetrievalFlow extends RetrievalFlow {

    @Autowired
    UserTaskAliPaySource userTask;

    @Autowired
    UserAliTask userAliTask;

    @Autowired
    CreatePasswordTask passwordTask;

    @Autowired
    ProfileTask profileTask;

    @Autowired
    ApplicationTask applicationTask;

    @Autowired
    JobResumeTask jobResumeTask;

    @Autowired
    UserTaskParamUtil userTaskParamUtil;

    @Autowired
    CreatePasswordTaskParamUtil passwordParamUtil;

    @Autowired
    UserAliTaskParamUtil userAliTaskParamUtil;

    @Autowired
    ProfileTaskParamUtil profileTaskParamUtil;

    @Autowired
    ApplicationTaskParamUtil applicationTaskParamUtil;

    @Autowired
    JobResumeTaskParamUtil jobResumeTaskParamUtil;

    @Override
    protected ExecutorParam initParam() {
        return new AliPayRetrievalParam();
    }

    @Override
    protected Coupler customExcutor() throws CommonException {

        Coupler firstExecutor = new Coupler(userTask, userTaskParamUtil);
        Coupler secondExecutor = new Coupler(passwordTask, passwordParamUtil);
        Coupler thirdExecutor = new Coupler(userAliTask, userAliTaskParamUtil);
        Coupler fourthExecutor = new Coupler(profileTask, profileTaskParamUtil);
        Coupler fifthExecutor = new Coupler(applicationTask, applicationTaskParamUtil);
        Coupler sixthExecutor = new Coupler(jobResumeTask, jobResumeTaskParamUtil);

        fifthExecutor.setNextExecutor(sixthExecutor);
        fourthExecutor.setNextExecutor(fifthExecutor);
        thirdExecutor.setNextExecutor(fourthExecutor);
        secondExecutor.setNextExecutor(thirdExecutor);
        firstExecutor.setNextExecutor(secondExecutor);
        return firstExecutor;
    }
}
