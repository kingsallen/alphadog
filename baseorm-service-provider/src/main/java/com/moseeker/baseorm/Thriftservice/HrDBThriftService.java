package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.service.HrDBService;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.HrDBDao.Iface;
import com.moseeker.thrift.gen.hr.struct.HrOperationrecordStruct;
@Service
public class HrDBThriftService implements Iface {
	@Autowired
	private HrDBService hrDBService;
	@Override
	public Response postHrOperationrecords(List<HrOperationrecordStruct> record) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.postHrOperations(record);
	}

	@Override
	public Response postHrOperationrecord(HrOperationrecordStruct record) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.postHrOperation(record);
	}

	@Override
	public Response getHrHistoryOperations(List<ProcessValidationStruct> record) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.getHrHistoryOpertation(record);
	}

}
