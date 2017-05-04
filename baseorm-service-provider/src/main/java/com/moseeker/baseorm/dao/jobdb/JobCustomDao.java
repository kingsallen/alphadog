package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.records.JobCustomRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobCustomDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;
@Service
public class JobCustomDao extends JooqCrudImpl<JobCustomDO, JobCustomRecord> {

	public JobCustomDao(TableImpl<JobCustomRecord> table, Class<JobCustomDO> jobCustomDOClass) {
		super(table, jobCustomDOClass);
	}
}
