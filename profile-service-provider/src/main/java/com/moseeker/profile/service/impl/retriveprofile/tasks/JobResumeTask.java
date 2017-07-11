package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.jobdb.JobResumeOtherDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.retriveprofile.RetriveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 10/07/2017.
 */
@Component
public class JobResumeTask extends Task {

    @Autowired
    JobResumeOtherDao jobResumeOtherDao;

    @Override
    protected void handler(RetriveParam param) throws CommonException {
        if (StringUtils.isNotNullOrEmpty(param.getJobResume()) && param.getApplicationId() > 0) {
            JobResumeOtherRecord jobResumeOtherRecord = new JobResumeOtherRecord();
            jobResumeOtherRecord.setAppId(param.getApplicationId());
            jobResumeOtherRecord.setOther(param.getJobResume());
            jobResumeOtherDao.addRecord(jobResumeOtherRecord);

        }
    }
}
