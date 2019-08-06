package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.jobdb.tables.daos.JobApplicationAtsProcessDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplicationAtsProcess;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.jobdb.tables.JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS;
import static org.jooq.impl.DSL.using;

/**
 * @Author: mdc
 * @Date: 2019-07-25 19:27
 */
@Repository
public class JobApplicationAtsProcessJOOQDaoImpl extends JobApplicationAtsProcessDao {
    public JobApplicationAtsProcessJOOQDaoImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public JobApplicationAtsProcess fetchOneByAppId(Integer appId) {
        JobApplicationAtsProcess jobApplicationAtsProcess = using(configuration())
                .selectFrom(JOB_APPLICATION_ATS_PROCESS)
                .where(JOB_APPLICATION_ATS_PROCESS.APP_ID.eq(appId))
                .fetchOneInto(JobApplicationAtsProcess.class);
        return jobApplicationAtsProcess != null ? jobApplicationAtsProcess : null;
    }
}
