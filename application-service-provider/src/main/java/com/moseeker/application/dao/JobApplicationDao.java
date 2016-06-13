package com.moseeker.application.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;

/**
 * 申请服务接口
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
public interface JobApplicationDao extends BaseDao<JobApplicationRecord>{

    public int getApplicationByUserIdAndPositionId(long userId, long positionId) throws Exception;
    
    ProfileBasicRecord getBasicByUserId(int userId) throws Exception;
    
    public int getViewNumber(long positionId, long userId) throws Exception;

	public int saveApplication(JobApplicationRecord jobApplicationRecord) throws Exception;

}
