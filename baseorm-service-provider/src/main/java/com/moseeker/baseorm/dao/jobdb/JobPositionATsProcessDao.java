package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionAtsProcess;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionAtsProcessRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class JobPositionATsProcessDao extends JooqCrudImpl<JobPositionAtsProcess, JobPositionAtsProcessRecord> {
    public JobPositionATsProcessDao() {
        super(com.moseeker.baseorm.db.jobdb.tables.JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS,JobPositionAtsProcess.class);
    }
    public JobPositionATsProcessDao(TableImpl<JobPositionAtsProcessRecord> table, Class<JobPositionAtsProcess> jobPositionAtsProcessClass) {
        super(table, jobPositionAtsProcessClass);
    }

    public JobPositionAtsProcess getJobPositionAtsProcessSingle(int positionId){
        JobPositionAtsProcess result=create.selectFrom(com.moseeker.baseorm.db.jobdb.tables.JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS)
                .where(com.moseeker.baseorm.db.jobdb.tables.JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.PID.eq(positionId)).limit(1)
                .fetchOneInto(JobPositionAtsProcess.class);
        return result;
    }
}
