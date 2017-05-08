package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupation;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;
@Service
public class JobOccupationDao extends JooqCrudImpl<JobOccupationDO, JobOccupationRecord> {

    public JobOccupationDao() {
        super(JobOccupation.JOB_OCCUPATION, JobOccupationDO.class);
    }

	public JobOccupationDao(TableImpl<JobOccupationRecord> table, Class<JobOccupationDO> jobOccupationDOClass) {
		super(table, jobOccupationDOClass);
	}
}
