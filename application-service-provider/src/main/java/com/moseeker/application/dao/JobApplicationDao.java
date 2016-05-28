package com.moseeker.application.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;

/**
 * 申请服务接口
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
public interface JobApplicationDao extends BaseDao<JobApplicationRecord>{

    public int getApplicationByUserIdAndPositionId(long userId, long positionId) throws Exception;

}
