package com.moseeker.profile.service.impl.retriveprofile.excutors;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Executor;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.tasks.ApplicationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 申请执行者
 * Created by jack on 10/07/2017.
 */
@Component("application_executor")
public class ApplicationExecutor extends Executor {

    @Autowired
    ApplicationTask applicationTask;

    @Override
    public boolean checkParam(RetrieveParam param) throws CommonException {
        if (param.getUserUserRecord() == null || param.getUserUserRecord().getId() == null) {
            ExceptionFactory.buildException(Category.PROFILE_ALLREADY_EXIST);
        }
        if (param.getPositionRecord() == null || param.getPositionRecord().getId() == 0
                || param.getPositionRecord().getCompanyId() == 0) {
            ExceptionFactory.buildException(Category.VALIDATION_POSITION_NOT_EXIST);
        }
        return true;
    }

    @Override
    public Task initTask() throws CommonException {
        return applicationTask;
    }
}
