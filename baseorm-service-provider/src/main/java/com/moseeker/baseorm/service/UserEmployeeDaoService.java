package com.moseeker.baseorm.service;

import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

import java.util.List;

public interface UserEmployeeDaoService {
	public Response getUserEmployeeByWeChats(Integer companyId,List<Integer> weChatIds);
	public Response postUserEmployeePointsRecords(List<UserEmployeePointStruct> records);
	public Response putUserEmployeePointsRecords(List<UserEmployeePointStruct> records);
	public Response getSumPoint(List<Long> records);
	public Response putUserEmployees(List<UserEmployeeStruct> records);
	public List<UserEmployeePointsRecordDO> getUserEmployeePoints(int employeeId);
	public Response putUserEmployee(UserEmployeePointsRecordDO employeeDO);
	public List<UserEmployeeDO> getEmployeesDO(Query query);
	public Response putEmployeesDO(List<UserEmployeeDO> employeeDOs);
}
