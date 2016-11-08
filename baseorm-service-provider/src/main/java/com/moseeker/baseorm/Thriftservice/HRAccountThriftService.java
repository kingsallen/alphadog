package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.orm.service.UserHrAccountDao.Iface;;

@Service
public class HRAccountThriftService implements Iface {

	@Override
	public Response getAccount(CommonQuery query) throws TException {
		return null;
	}

}
