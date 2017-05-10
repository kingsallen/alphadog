package com.moseeker.dict.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.dict.service.impl.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.PositionService.Iface;

@Service("positionServiceDao")
public class PositionServiceImpl implements Iface {

	@Autowired
	private PositionService service; 
	
	@Override
	public Response getPositionsByCode(String code) throws TException {
		return service.getPositionsByCode(code);
	}

}
