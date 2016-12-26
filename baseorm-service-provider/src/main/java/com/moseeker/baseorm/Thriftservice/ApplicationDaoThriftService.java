package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import com.moseeker.baseorm.service.JobApplicationService;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ApplicationDao.Iface;

public class ApplicationDaoThriftService implements Iface{

	@Autowired
	private JobApplicationService applicationService;
	@Override
	public Response getProcessAuth(List<Integer> appIds, int companyId, int progressStatus) throws TException {
		// TODO Auto-generated method stub
		return applicationService.processvalidation(appIds, companyId, progressStatus);
	}
	@Override
	public Response putApplication(JobApplication application) throws TException {
		// TODO Auto-generated method stub
		return applicationService.putJobApplication(application);
	}
	@Override
	public Response postApplication(JobApplication application) throws TException {
		// TODO Auto-generated method stub
		return applicationService.postJobApplication(application);
	}
	@Override
	public Response putApplications(List<JobApplication> application) throws TException {
		// TODO Auto-generated method stub
		return applicationService.putJobApplications(application);
	}
	@Override
	public Response postApplications(List<JobApplication> application) throws TException {
		// TODO Auto-generated method stub
		return applicationService.postJobApplications(application);
	}

}
