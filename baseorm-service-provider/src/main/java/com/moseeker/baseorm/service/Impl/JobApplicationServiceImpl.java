package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.historydb.HistoryJobApplicationDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryJobApplicationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.service.JobApplicationService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
@Service
public class JobApplicationServiceImpl implements JobApplicationService {
	Logger logger = LoggerFactory.getLogger(JobApplicationServiceImpl.class);
	@Autowired
	private JobApplicationDao dao;
	@Autowired
	private UserUserDao userUserDao;
	@Autowired
	private UserEmployeeDao userEmployeedao;
	@Autowired
	private HrOperationRecordDao hrOperationRecordDao;
	@Autowired
	private HistoryJobApplicationDao historyJobApplicationDao;
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
			dao.addRecord(record);
			return ResponseUtils.success(1);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response putJobApplication(JobApplication application) {
		// TODO Auto-generated method stub
		try{
			JobApplicationRecord record = BeanUtils.structToDB(application, JobApplicationRecord.class);
			int result=dao.updateRecord(record);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response postJobApplications(List<JobApplication> applications) {
		// TODO Auto-generated method stub
		try{
			int result=dao.addAllRecord(this.convertDB(applications)).size();
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response putJobApplications(List<JobApplication> applications) {
		// TODO Auto-generated method stub
		try{
			int result=dao.updateRecords(this.convertDB(applications)).length;
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	private List<JobApplicationRecord>convertDB(List<JobApplication> applications){
		List<JobApplicationRecord> list=new ArrayList<JobApplicationRecord>();
		for(JobApplication application:applications){
			list.add(BeanUtils.structToDB(application, JobApplicationRecord.class));
		}
		return list;
	}
	@Override
	public Response getApplicationByAts(List<Integer> list) {
		// TODO Auto-generated method stub
		try{
			List<Integer> params=new ArrayList<Integer>();
			for(Integer num:list){
				params.add(num);
			}
			List<ApplicationAts> result=dao.getApplicationByLApId(params);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
		
	}
	//
	@Override
	public int saveJobApplication(JobApplicationRecord jobApplicationRecord,JobPositionRecord jobPositionRecord) throws TException {
		// TODO Auto-generated method stub
		int appId=0;
		try{
        	Query query=new QueryBuilder().where("id", jobApplicationRecord.getRecommenderUserId()).buildQuery();
			UserUserRecord userUserRecord=userUserDao.getRecord(query);
			if(jobApplicationRecord.getRecommenderUserId() != null && jobApplicationRecord.getRecommenderUserId().intValue() > 0) {
				boolean existUserEmployee = false;
				Query query1=new QueryBuilder().where("sysuser_id",userUserRecord.getId().intValue())
						.where("disable", 0).where("activation",0).where("status", 0).buildQuery();
				UserEmployeeRecord userEmployeeRecord=userEmployeedao.getRecord(query1);
				if(userEmployeeRecord==null){
					existUserEmployee = true;
				}
				if(!existUserEmployee) {
	                jobApplicationRecord.setRecommenderUserId(0);
	            }
				if(jobApplicationRecord.getApplierId() != null && jobApplicationRecord.getApplierId().intValue() == jobApplicationRecord.getRecommenderUserId().intValue()) {
	                jobApplicationRecord.setRecommenderUserId(0);
	            }
				
			}
			dao.addRecord(jobApplicationRecord);
			appId=jobApplicationRecord.getId();
			if(appId > 0){
				HrOperationRecordRecord  hrOperationRecord = getHrOperationRecordRecord(appId, jobApplicationRecord, jobPositionRecord);
				hrOperationRecordDao.addRecord(hrOperationRecord);
	        }
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new TException(e);
		}
		return appId;
	}
	//构建hr操作记录record
	private HrOperationRecordRecord getHrOperationRecordRecord(long appId,
            JobApplicationRecord jobApplicationRecord,
            JobPositionRecord JobPositonrecord){
		HrOperationRecordRecord hrOperationRecordRecord = new HrOperationRecordRecord();
		hrOperationRecordRecord.setAdminId(JobPositonrecord.getPublisher().longValue());
		hrOperationRecordRecord.setCompanyId(jobApplicationRecord.getCompanyId().longValue());
		hrOperationRecordRecord.setAppId(appId);
		hrOperationRecordRecord.setOperateTplId(jobApplicationRecord.getAppTplId().intValue());
		return hrOperationRecordRecord;
}

	@Override
	public int saveApplicationIfNotExist(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord)throws TException{
		// TODO Auto-generated method stub
		 int appId = 0;
		 try{
			 HrOperationRecordRecord hrOperationRecord = null;
			 dao.addRecord(jobApplicationRecord);		 
			 appId = jobApplicationRecord.getId().intValue();
			 if(appId > 0){
	             hrOperationRecord = getHrOperationRecordRecord(appId, jobApplicationRecord, jobPositionRecord);
	             hrOperationRecordDao.addRecord(hrOperationRecord);
	         }
		 }catch(Exception e){
			 logger.error(e.getMessage(),e);
			 throw new TException(e);
		 }
		 return appId;
	}
	 /**
     * 转换归档申请记录
     *
     * @param jobApplicationRecord
     * @return
     */
    private HistoryJobApplicationRecord setHistoryJobApplicationRecord(JobApplicationRecord jobApplicationRecord){

        HistoryJobApplicationRecord historyJobApplicationRecord = null;

        if (jobApplicationRecord != null) {
            historyJobApplicationRecord = new HistoryJobApplicationRecord();

            historyJobApplicationRecord.setId(jobApplicationRecord.getId());
            historyJobApplicationRecord.setPositionId(jobApplicationRecord.getPositionId());
            historyJobApplicationRecord.setRecommenderId(jobApplicationRecord.getRecommenderId());
            historyJobApplicationRecord.setLApplicationId(jobApplicationRecord.getLApplicationId());
            historyJobApplicationRecord.setUserId(jobApplicationRecord.getApplierId());
            historyJobApplicationRecord.setAtsStatus((jobApplicationRecord.getAtsStatus()));
            historyJobApplicationRecord.setDisable((int)(jobApplicationRecord.getDisable()));
            historyJobApplicationRecord.setRoutine((int)(jobApplicationRecord.getRoutine()));
            historyJobApplicationRecord.setIsViewed((byte)(jobApplicationRecord.getIsViewed()));
            historyJobApplicationRecord.setViewCount((int)(jobApplicationRecord.getViewCount()));
            historyJobApplicationRecord.setNotSuitable((byte)(jobApplicationRecord.getNotSuitable()));
            historyJobApplicationRecord.setCompanyId(jobApplicationRecord.getCompanyId());
            historyJobApplicationRecord.setAppTplId(jobApplicationRecord.getAppTplId());
            historyJobApplicationRecord.setProxy((byte)(jobApplicationRecord.getProxy()));
            historyJobApplicationRecord.setApplyType((int)(jobApplicationRecord.getApplyType()));
            historyJobApplicationRecord.setEmailStatus((int)(jobApplicationRecord.getEmailStatus()));
            historyJobApplicationRecord.setSubmitTime(jobApplicationRecord.getSubmitTime());
            historyJobApplicationRecord.setCreateTime(jobApplicationRecord.get_CreateTime());
            historyJobApplicationRecord.setUpdateTime(jobApplicationRecord.getUpdateTime());
        }
        return historyJobApplicationRecord;
    }
    /**
     * 归档申请记录
     *
     * @param jobApplicationRecord
     * @return
     * @throws Exception
     */
	@Override
	public int archiveApplicationRecord(JobApplicationRecord jobApplicationRecord) throws TException {
		// TODO Auto-generated method stub
		int status = 0;
		try{
			HistoryJobApplicationRecord historyJobApplicationRecord = setHistoryJobApplicationRecord(jobApplicationRecord);
	        if (historyJobApplicationRecord != null){
	        	historyJobApplicationDao.addRecord(historyJobApplicationRecord);
	        	status=historyJobApplicationRecord.getId();
	        }
	        if (status > 0){
	            status =dao.deleteRecord(jobApplicationRecord);
	        }
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new TException(e);
		}
		return status;
	}

}
