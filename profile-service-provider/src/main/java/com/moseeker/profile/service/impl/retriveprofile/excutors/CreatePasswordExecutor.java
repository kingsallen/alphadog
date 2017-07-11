package com.moseeker.profile.service.impl.retriveprofile.excutors;

import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Executor;
import com.moseeker.profile.service.impl.retriveprofile.RetriveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.tasks.CreatePasswordTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 10/07/2017.
 */
@Component("createPassword_executor")
public class CreatePasswordExecutor extends Executor {

    @Autowired
    CreatePasswordTask createPasswordTask;

    @Override
    public boolean checkParam(RetriveParam param) throws CommonException {
        if (param.getUserUserRecord() == null || param.getUserUserRecord().getId() == null) {
            throw ExceptionFactory.buildException(Category.VALIDATION_USER_ILLEGAL_PARAM);
        }
        if (StringUtils.isNullOrEmpty(param.getUserUserRecord().getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Task initTask() throws CommonException {
        return createPasswordTask;
    }
}
