package com.moseeker.warn.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.warn.service.WarnSetService.Iface;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.service.ValidationService;

@Service
public class WarnThriftService implements Iface{
	
	@Autowired
	private ValidationService validService;
	
	//验证预警信息，并且置于redis队列
	@Override
	public void sendOperator(WarnBean bean) throws TException {
		validService.valid(bean);
	}

}
