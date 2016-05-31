package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;

public interface JobPositionDao extends BaseDao<JobPositionRecord> {
	
	JobPositionRecord getPositionById(int positionId);
	
}
