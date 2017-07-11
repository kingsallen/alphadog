package com.moseeker.profile.service.impl.retriveprofile.excutors;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Executor;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.tasks.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 10/07/2017.
 */
@Component("user_executor")
public class UserExecutor extends Executor {

    @Autowired
    UserTask userTask;

    @Override
    public boolean checkParam(RetrieveParam param) throws CommonException {
        if (param.getUserUserRecord() == null) {
            ExceptionFactory.buildException(Category.VALIDATION_USER_ILLEGAL_PARAM);
        }
        return true;
    }

    @Override
    public Task initTask() throws CommonException {
        return userTask;
    }
}
