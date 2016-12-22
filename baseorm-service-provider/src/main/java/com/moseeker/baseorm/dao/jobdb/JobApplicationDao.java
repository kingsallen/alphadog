package com.moseeker.baseorm.dao.jobdb;

import java.util.List;

import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;

public class JobApplicationDao extends BaseDaoImpl<JobApplicationRecord, JobApplication>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=JobApplication.JOB_APPLICATION;
	}
	public List<ProcessValidationStruct> getAuth(){
		
		return null;
	}
}
