package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.service.HrDBService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.HrOperationrecordStruct;
@Service
public class HrDBServiceImpl implements HrDBService{
	@Autowired
	private HrOperationRecordDao hrOperationRecordDao;
	@Override
	public Response postHrOperation(HrOperationrecordStruct record) {
		// TODO Auto-generated method stub
		try{
			HrOperationRecordRecord data=(HrOperationRecordRecord) BeanUtils.structToDB(record, HrOperationRecordRecord.class);
			int result=hrOperationRecordDao.postResource(data);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	@Override
	public Response postHrOperations(List<HrOperationrecordStruct> record) {
		// TODO Auto-generated method stub
		try{
			List<HrOperationRecordRecord> list=new ArrayList<HrOperationRecordRecord>();
			for(HrOperationrecordStruct data:record){
				list.add((HrOperationRecordRecord) BeanUtils.structToDB(data, HrOperationRecordRecord.class));
			}
			int result=hrOperationRecordDao.postResources(list);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}

	}
	
	public Response getHrHistoryOpertation(List<ProcessValidationStruct> records){
		if(records!=null&&records.size()>0){
			StringBuffer sb=new StringBuffer();
			sb.append("(");
			for(ProcessValidationStruct record:records ){
				sb.append(""+record.getId()+",");	
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			String param=sb.toString();
			try{
				List<HistoryOperate> list=hrOperationRecordDao.getHistoryOperate(param);
				return ResponseUtils.success(list);
			}catch(Exception e){
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
			}
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		
	}
	
}
