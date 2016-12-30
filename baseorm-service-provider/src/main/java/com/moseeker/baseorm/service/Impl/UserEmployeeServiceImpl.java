package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.user.UserEmployeeDao;
import com.moseeker.baseorm.dao.user.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.service.UserEmployeeService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
@Service
public class UserEmployeeServiceImpl implements UserEmployeeService {
	@Autowired
	private UserEmployeeDao dao;
	@Autowired
	private UserEmployeePointsRecordDao dao1;
	@Override
	public Response getUserEmployeeByWeChats(Integer companyId, List<Integer> weChatIds) {
		// TODO Auto-generated method stub
		try{
			List<UserEmployeeRecord> result=dao.getEmployeeByWeChat(companyId, weChatIds);
			List<UserEmployeeStruct> data=this.convertStruct(result);
			return ResponseUtils.success(data);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	private List<UserEmployeeStruct> convertStruct(List<UserEmployeeRecord> list){
		List<UserEmployeeStruct> datas=new ArrayList<UserEmployeeStruct>();
		if(list!=null&&list.size()>0){
			for(UserEmployeeRecord record:list){
				UserEmployeeStruct data=(UserEmployeeStruct) BeanUtils.DBToStruct(UserEmployeeStruct.class, record);
				datas.add(data);
			}
		}
		return datas;
	}
	public Response postUserEmployeePointsRecords(List<UserEmployeePointStruct> records){
		try{
			List<UserEmployeePointsRecordRecord> list=new ArrayList<UserEmployeePointsRecordRecord>();
			for(UserEmployeePointStruct record:records){
				list.add((UserEmployeePointsRecordRecord) BeanUtils.structToDB(record, UserEmployeePointsRecordRecord.class));
			}
			int result=dao1.postResources(list);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	public Response getSumPoint(List<Long> records){
		try{
			List<UserEmployeePointSum> result=dao1.getSumRecord(records);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response putUserEmployees(List<UserEmployeeStruct> records) {
		// TODO Auto-generated method stub
		try{
			List<UserEmployeeRecord> list=new ArrayList<UserEmployeeRecord>();
			for(UserEmployeeStruct bean:records){
				list.add((UserEmployeeRecord) BeanUtils.structToDB(bean, UserEmployeeRecord.class));
			}
			int result=dao.putResources(list);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	@Override
	public Response putUserEmployeePointsRecords(List<UserEmployeePointStruct> records) {
		// TODO Auto-generated method stub
		try{
			List<UserEmployeePointsRecordRecord> list=new ArrayList<UserEmployeePointsRecordRecord>();
			for(UserEmployeePointStruct record:records){
				list.add((UserEmployeePointsRecordRecord) BeanUtils.structToDB(record, UserEmployeePointsRecordRecord.class));
			}
			int result=dao1.putResources(list);
			return ResponseUtils.success(result);
		}catch(Exception e){
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	
}
