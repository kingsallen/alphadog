package com.moseeker.baseorm.service;

import java.util.List;

import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;

public interface JobApplicationService {
    public Response processvalidation(List<Integer> appIds, Integer companyId, Integer progressStatus);

    public Response postJobApplication(JobApplication application);

    public Response putJobApplication(JobApplication application);

    public Response postJobApplications(List<JobApplication> applications);

    public Response putJobApplications(List<JobApplication> applications);

    public Response getApplicationByAts(List<Integer> list);

}
