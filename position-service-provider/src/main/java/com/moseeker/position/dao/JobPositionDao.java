package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.pojo.JobPositionPojo;

public interface JobPositionDao extends BaseDao<JobPositionRecord> {

	JobPositionPojo getPosition(int positionId);

	JobPositionRecord getPositionById(int positionId);

	HrCompanyAccountRecord getHrCompanyAccountByPublisher(int publisher);
	
}
