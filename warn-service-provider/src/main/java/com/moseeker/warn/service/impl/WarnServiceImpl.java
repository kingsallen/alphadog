package com.moseeker.warn.service.impl;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.warn.service.WarnSetService.Iface;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.service.validate.ValidationException;
@Service
public class WarnServiceImpl implements Iface{
@Autowired
private ValidationException validException;
	@Override
	public void sendOperator(WarnBean bean) throws TException {
		// TODO Auto-generated method stub
		validException.valid(bean);
	}

}
