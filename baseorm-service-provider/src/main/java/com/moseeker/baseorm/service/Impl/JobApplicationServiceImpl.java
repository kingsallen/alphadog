package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.service.JobApplicationService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
@Service
public class JobApplicationServiceImpl implements JobApplicationService {
	@Autowired
	private JobApplicationDao dao;
	@Override
	public Response processvalidation(List<Integer> params, Integer companyId,
			Integer progressStatus) {
		// TODO Auto-generated method stub
		try{
			List<ProcessValidationStruct> result=dao.getAuth(params, companyId, progressStatus);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	@Override
	public Response postJobApplication(JobApplication application) {
		// TODO Auto-generated method stub
		try{
			JobApplicationRecord record=(JobApplicationRecord) BeanUtils.structToDB(application, JobApplicationRecord.class);
			int result=dao.postResource(record);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response putJobApplication(JobApplication application) {
		// TODO Auto-generated method stub
		try{
			JobApplicationRecord record=(JobApplicationRecord) BeanUtils.structToDB(application, JobApplicationRecord.class);
			int result=dao.putResource(record);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response postJobApplications(List<JobApplication> applications) {
		// TODO Auto-generated method stub
		try{
			int result=dao.postResources(this.convertDB(applications));
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response putJobApplications(List<JobApplication> applications) {
		// TODO Auto-generated method stub
		try{
			int result=dao.putResources(this.convertDB(applications));
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	private List<JobApplicationRecord>convertDB(List<JobApplication> applications){
		List<JobApplicationRecord> list=new ArrayList<JobApplicationRecord>();
		for(JobApplication application:applications){
			list.add((JobApplicationRecord) BeanUtils.structToDB(application, JobApplicationRecord.class));
		}
		return list;
	}
	@Override
	public Response getApplicationByAts(List<Integer> list) {
		// TODO Auto-generated method stub
		try{
			List<Integer> params=new ArrayList<Integer>();
			for(Integer num:list){
				params.add((int)(num));
			}
			List<ApplicationAts> result=dao.getApplicationByLApId(params);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
		
	}
}
