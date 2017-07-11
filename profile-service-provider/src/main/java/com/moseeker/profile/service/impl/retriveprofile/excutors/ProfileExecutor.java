package com.moseeker.profile.service.impl.retriveprofile.excutors;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Executor;
import com.moseeker.profile.service.impl.retriveprofile.RetriveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.tasks.ProfileTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 10/07/2017.
 */
@Component("profile_executor")
public class ProfileExecutor extends Executor {

    @Autowired
    ProfileTask profileTask;

    @Override
    public boolean checkParam(RetriveParam param) throws CommonException {
        if (param.getProfilePojo() == null || param.getProfilePojo().getProfileRecord() == null) {
            ExceptionFactory.buildException(Category.PROFILE_ILLEGAL);
        }
        return true;
    }

    @Override
    public Task initTask() throws CommonException {
        return profileTask;
    }
}
