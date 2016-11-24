package com.moseeker.baseorm.dao.position;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
@Service
public class JobPositionDao extends BaseDaoImpl<JobPositionRecord, JobPosition> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike=JobPosition.JOB_POSITION;
		
	}

}
