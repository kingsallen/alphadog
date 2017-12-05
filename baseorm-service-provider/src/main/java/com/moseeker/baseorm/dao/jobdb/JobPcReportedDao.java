package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPcReported;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcReportedRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcReportedDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/9/20.
 */
@Service
public class JobPcReportedDao extends JooqCrudImpl<JobPcReportedDO, JobPcReportedRecord> {
    public JobPcReportedDao() {
        super(JobPcReported.JOB_PC_REPORTED,JobPcReportedDO.class);
    }
    public JobPcReportedDao(TableImpl<JobPcReportedRecord> table, Class<JobPcReportedDO> JobPcReportedDOClass) {
        super(table,JobPcReportedDOClass);
    }
}
