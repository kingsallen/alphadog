package com.moseeker.baseorm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.service.UserEmployeeService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
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

	@Override
	public List<UserEmployeePointsRecordDO> getUserEmployeePoints(int employeeId) {
		List<UserEmployeePointsRecordDO> result = new ArrayList<>();
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("employee_id", String.valueOf(employeeId));

			List<UserEmployeePointsRecordRecord> records =
					dao1.getResources(qu);
			result = BeanUtils.DBToStruct(UserEmployeePointsRecordDO.class, records);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Response putUserEmployee(UserEmployeePointsRecordDO employeeDo) {
		try {
			UserEmployeeRecord record = (UserEmployeeRecord) BeanUtils.structToDB(employeeDo, UserEmployeeRecord.class);
			int result = dao.putResource(record);
			return ResponseUtils.success(result);
		}
		catch (Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	@Override
	public List<UserEmployeeDO> getEmployeesDO(CommonQuery query) {
		List<UserEmployeeDO> result = new ArrayList<>();
		try {
			List<UserEmployeeRecord> records =
					dao.getResources(query);
			result = BeanUtils.DBToStruct(UserEmployeeDO.class, records);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Response putEmployeesDO(List<UserEmployeeDO> employeeDOs) {
		try{
			List<UserEmployeeRecord> list = new ArrayList<UserEmployeeRecord>();
			for(UserEmployeeDO employeeDo: employeeDOs){
				list.add((UserEmployeeRecord) BeanUtils.structToDB(employeeDo, UserEmployeeRecord.class));
			}
			int result=dao.putResources(list);
			return ResponseUtils.success(result);
		}
		catch(Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
}
