package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupation;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRecord;
import com.moseeker.baseorm.tool.OrmTools;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationDO;
import com.moseeker.thrift.gen.position.struct.dao.JobOccupationCustom;

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
	
	  public Response getJobOccupations(Query query) {
	        // TODO Auto-generated method stub
	        return OrmTools.getList(this, query, new JobOccupationCustom());
	  }
}
