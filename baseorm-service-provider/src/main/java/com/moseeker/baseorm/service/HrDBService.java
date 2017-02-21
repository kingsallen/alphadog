package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.HrOperationRecordDO;

import java.util.List;

public interface HrDBService {
	public Response postHrOperation(HrOperationRecordDO record);
	public Response postHrOperations(List<HrOperationRecordDO> record);
	public Response getHrHistoryOpertation(List<ProcessValidationStruct> records);
}
