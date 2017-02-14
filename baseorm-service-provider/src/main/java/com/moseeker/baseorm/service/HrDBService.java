package com.moseeker.baseorm.service;

import java.util.List;

import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.HrOperationrecordDO;

public interface HrDBService {
	public Response postHrOperation(HrOperationrecordDO record);
	public Response postHrOperations(List<HrOperationrecordDO> record);
	public Response getHrHistoryOpertation(List<ProcessValidationStruct> records);
}
