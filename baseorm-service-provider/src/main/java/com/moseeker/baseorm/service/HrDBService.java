package com.moseeker.baseorm.service;

import java.util.List;

import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.hr.struct.HrOperationrecordStruct;

public interface HrDBService {
	public Response postHrOperation(HrOperationrecordStruct record);
	public Response postHrOperations(List<HrOperationrecordStruct> record);
	public Response getHrHistoryOpertation(List<ProcessValidationStruct> records);
	// 获取团队信息
	public Response getHrTeam(CommonQuery query);
}
