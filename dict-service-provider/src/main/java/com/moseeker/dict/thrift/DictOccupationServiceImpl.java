package com.moseeker.dict.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.dict.service.impl.DictOccupationService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictOccupationService.Iface;

@Service
public class DictOccupationServiceImpl implements Iface{
	@Autowired
	private DictOccupationService service;
	@Override
	public Response getDictOccupation(String param) throws TException {
		// TODO Auto-generated method stub
		return service.queryOccupation(param);
	}
	
}
