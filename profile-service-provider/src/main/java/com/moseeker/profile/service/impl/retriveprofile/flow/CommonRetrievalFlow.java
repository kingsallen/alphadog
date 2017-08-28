package com.moseeker.profile.service.impl.retriveprofile.flow;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.RetrievalFlow;
import com.moseeker.profile.service.impl.retriveprofile.executor.Coupler;
import com.moseeker.profile.service.impl.retriveprofile.flow.common.CommonApplicationTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.common.CommonProfileTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.flow.common.CommonUserTaskParamUtil;
import com.moseeker.profile.service.impl.retriveprofile.tasks.ApplicationTask;
import com.moseeker.profile.service.impl.retriveprofile.tasks.ProfileTask;
import com.moseeker.profile.service.impl.retriveprofile.tasks.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付宝简历回收流程
 * Created by jack on 10/07/2017.
 */
@Component("common_retrieval_flow")
public class CommonRetrievalFlow extends RetrievalFlow {

    @Autowired
    UserTask userTask;

    @Autowired
    ProfileTask profileTask;

    @Autowired
    ApplicationTask applicationTask;

    @Autowired
    CommonUserTaskParamUtil userTaskParamUtil;

    @Autowired
    CommonProfileTaskParamUtil profileTaskParamUtil;

    @Autowired
    CommonApplicationTaskParamUtil applicationTaskParamUtil;

    @Override
    protected ExecutorParam initParam() {
        return new ExecutorParam();
    }

    @Override
    protected Coupler customExcutor() throws CommonException {

        Coupler firstExecutor = new Coupler(userTask, userTaskParamUtil);
        Coupler secondExecutor = new Coupler(profileTask, profileTaskParamUtil);
        Coupler thirdExecutor = new Coupler(applicationTask, applicationTaskParamUtil);

        secondExecutor.setNextExecutor(thirdExecutor);
        firstExecutor.setNextExecutor(secondExecutor);
        return firstExecutor;
    }
}
