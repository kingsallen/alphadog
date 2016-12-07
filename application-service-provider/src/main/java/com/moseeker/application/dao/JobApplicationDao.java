package com.moseeker.application.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;

/**
 * 申请服务接口
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
public interface JobApplicationDao extends BaseDao<JobApplicationRecord>{

    public JobApplicationRecord getApplicationById(long applicationId) throws Exception;

    public int getApplicationByUserIdAndPositionId(long userId, long positionId) throws Exception;

	public int saveApplication(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord) throws Exception;

    public int archiveApplicationRecord(JobApplicationRecord jobApplicationRecord) throws Exception;

	public int saveApplicationIfNotExist(JobApplicationRecord jobApplicationRecord,
			JobPositionRecord jobPositionRecord) throws Exception;

}
