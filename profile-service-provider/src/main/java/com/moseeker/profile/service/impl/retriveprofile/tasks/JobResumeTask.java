package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.jobdb.JobResumeOtherDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.parameters.JobResumeTaskParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * job_resume_other表处理业务
 * Created by jack on 10/07/2017.
 */
@Component
public class JobResumeTask implements Task<JobResumeTaskParam, Integer> {

    @Autowired
    JobResumeOtherDao jobResumeOtherDao;

    @Override
    public Integer handler(JobResumeTaskParam param) throws CommonException {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("app_id", param.getAppId());
        JobResumeOtherRecord jobResumeOtherRecord = jobResumeOtherDao.getRecord(queryBuilder.buildQuery());
        if (jobResumeOtherRecord == null) {
            jobResumeOtherRecord = new JobResumeOtherRecord();
            jobResumeOtherRecord.setAppId(param.getAppId());
            jobResumeOtherRecord.setOther(param.getJobResume());
            jobResumeOtherDao.addRecord(jobResumeOtherRecord);
        } else {
            jobResumeOtherRecord.setOther(param.getJobResume());
            jobResumeOtherDao.updateRecord(jobResumeOtherRecord);
        }
        return jobResumeOtherRecord.getAppId();
    }
}
