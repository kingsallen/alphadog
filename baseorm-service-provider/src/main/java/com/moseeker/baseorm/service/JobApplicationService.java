package com.moseeker.baseorm.service;

import java.util.List;

import org.apache.thrift.TException;

import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;

public interface JobApplicationService {
    public Response processvalidation(List<Integer> appIds, Integer companyId, Integer progressStatus);

    public Response postJobApplication(JobApplication application);

    public Response putJobApplication(JobApplication application);

    public Response postJobApplications(List<JobApplication> applications);

    public Response putJobApplications(List<JobApplication> applications);

    public Response getApplicationByAts(List<Integer> list);
    
    public int saveJobApplication(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord)throws TException;
    
    public int saveApplicationIfNotExist(JobApplicationRecord jobApplicationRecord,
			JobPositionRecord jobPositionRecord) throws TException;
    /**
     * 归档申请记录
     *
     * @param jobApplicationRecord
     * @return
     * @throws Exception
     */
    public int archiveApplicationRecord(JobApplicationRecord jobApplicationRecord) throws Exception;
}
