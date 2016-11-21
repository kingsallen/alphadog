package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.jobdb.tables.JobOccupation;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
@Service
public class JobOccupationDao extends BaseDaoImpl<JobOccupationRecord, JobOccupation> {

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=JobOccupation.JOB_OCCUPATION;
	}

}
