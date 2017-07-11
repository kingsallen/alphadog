package com.moseeker.profile.service.impl.retriveprofile.excutors;

import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.retriveprofile.Executor;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.tasks.JobResumeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 11/07/2017.
 */
@Component("job_resume_executor")
public class JobResumeExecutor extends Executor {

    @Autowired
    JobResumeTask jobResumeTask;

    @Override
    public boolean checkParam(RetrieveParam param) throws CommonException {
        if (StringUtils.isNotNullOrEmpty(param.getJobResume()) && param.getApplicationId() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Task initTask() throws CommonException {
        return jobResumeTask;
    }
}
