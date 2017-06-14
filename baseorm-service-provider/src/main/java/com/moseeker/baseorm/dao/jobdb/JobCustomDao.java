package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobCustom;
import com.moseeker.baseorm.db.jobdb.tables.records.JobCustomRecord;
import com.moseeker.baseorm.tool.OrmTools;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobCustomDO;
import com.moseeker.thrift.gen.position.struct.dao.JobOccupationCustom;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;
@Service
public class JobCustomDao extends JooqCrudImpl<JobCustomDO, JobCustomRecord> {

    public JobCustomDao() {
        super(JobCustom.JOB_CUSTOM, JobCustomDO.class);
    }

	public JobCustomDao(TableImpl<JobCustomRecord> table, Class<JobCustomDO> jobCustomDOClass) {
		super(table, jobCustomDOClass);
	}
	/*
	 * 获取job_custom
	 */
    public Response getJobCustoms(Query query) {
        return OrmTools.getList(this, query, new JobOccupationCustom());
    }
}
