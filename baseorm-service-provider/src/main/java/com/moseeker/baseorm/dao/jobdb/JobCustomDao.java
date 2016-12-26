package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.jobdb.tables.JobCustom;
import com.moseeker.baseorm.db.jobdb.tables.records.JobCustomRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
@Service
public class JobCustomDao extends BaseDaoImpl<JobCustomRecord, JobCustom> {

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=JobCustom.JOB_CUSTOM;
	}

}
