package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.service.JobApplicationService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
@Service
public class JobApplicationServiceImpl implements JobApplicationService {
	@Autowired
	private JobApplicationDao dao;
	@Override
	public Response processvalidation(String params, Integer companyId,
			Integer progressStatus) {
		// TODO Auto-generated method stub
		List<Integer> appIds=this.convertList(params);
		List<UInteger> list=this.convertUInteger(appIds);
		List<ProcessValidationStruct> result=null;
		try{
			result=dao.getAuth(list, companyId, progressStatus);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
    private List<Integer> convertList(String params){
   	 List<Integer> list=new ArrayList<Integer>();
   	 if(params.contains("[")){
   		 params=params.replace("[", "").replace("]", "");
   		 String [] array=params.split(",");
   		 for(String param:array){
   			 list.add(Integer.parseInt(param.trim()));
   		 }
   	 }
		return list;
   	 
    }
	private List<UInteger> convertUInteger(List<Integer> appIds){
		List<UInteger> list=new ArrayList<UInteger>();
		for(Integer param:appIds){
			list.add(UInteger.valueOf(param));
		}
		return list;
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
			int result=dao.postResources(this.convertDB(applications));
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
}
