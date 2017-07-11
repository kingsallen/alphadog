package com.moseeker.profile.service.impl.retriveprofile.excutors;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.tasks.AliPayUserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 10/07/2017.
 */
@Component("ali_pay_user_executor")
public class AliPayUserExecutor extends UserExecutor {

    @Autowired
    AliPayUserTask aliPayUserTask;

    @Override
    public Task initTask() throws CommonException {
        return aliPayUserTask;
    }
}
