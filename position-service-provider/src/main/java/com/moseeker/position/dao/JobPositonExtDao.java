package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionExtRecord;

/**
 * 职位副表
 *
 * Created by zzh on 16/8/11.
 */
public interface JobPositonExtDao extends BaseDao<JobPositionExtRecord>{

    JobPositionExtRecord getJobPositonExtRecordByPositionId(int positionId);
}
