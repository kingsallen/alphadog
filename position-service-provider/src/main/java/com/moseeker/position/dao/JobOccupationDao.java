package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobOccupationRecord;

/**
 * 自定义职位职能持久层
 * @author wjf
 *
 */
public interface JobOccupationDao extends BaseDao<JobOccupationRecord>{

    JobOccupationRecord getJobOccupationRecord(int id);
}
