package com.moseeker.baseorm.service;

import java.util.List;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDo;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

public interface UserEmployeeService {
	public Response getUserEmployeeByWeChats(Integer companyId,List<Integer> weChatIds);
	public Response postUserEmployeePointsRecords(List<UserEmployeePointStruct> records);
	public Response putUserEmployeePointsRecords(List<UserEmployeePointStruct> records);
	public Response getSumPoint(List<Long> records);
	public Response putUserEmployees(List<UserEmployeeStruct> records);

	public List<UserEmployeePointsRecordDo> getUserEmployeePoints(int employeeId);
}
