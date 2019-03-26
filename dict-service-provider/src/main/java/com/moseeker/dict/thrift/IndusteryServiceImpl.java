package com.moseeker.dict.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.dict.service.impl.IndusteryService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.IndustryService.Iface;

@Service
public class IndusteryServiceImpl implements Iface {
	
	Logger logger = LoggerFactory.getLogger(IndusteryServiceImpl.class);

	@Autowired
	private IndusteryService service;
	
	@Override
	public Response getIndustriesByCode(String code) throws TException {
		return service.getIndustriesByCode(code);
	}

	@Override
	public Response getMarsIndustriesByCode(String code) throws TException {
		return service.getMarsIndustriesByCode(code);
	}

}
