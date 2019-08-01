package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.jobdb.tables.daos.JobPositionAtsProcessDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionAtsProcess;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.jobdb.tables.JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS;
import static org.jooq.impl.DSL.using;

/**
 * @Author: mdc
 * @Date: 2019-07-25 19:51
 */
@Repository
public class JobPositionAtsProcessJOOQDaoImpl extends JobPositionAtsProcessDao {
    public JobPositionAtsProcessJOOQDaoImpl(Configuration configuration) {
        super(configuration);
    }

    public JobPositionAtsProcess fetchByPid(Integer positionId) {
        JobPositionAtsProcess jobPositionAtsProcess = using(configuration())
                .selectFrom(JOB_POSITION_ATS_PROCESS)
                .where(JOB_POSITION_ATS_PROCESS.PROCESS_ID.eq(positionId))
                .fetchOneInto(JobPositionAtsProcess.class);
        return jobPositionAtsProcess != null ? jobPositionAtsProcess : null;
    }
}
