package com.moseeker.apps.thrift.service;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.apps.service.UserBs;
import com.moseeker.thrift.gen.apps.userbs.service.UserBS.Iface;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.BindType;

@Service
public class UserBSThriftService implements Iface {
	
	@Autowired
	private UserBs userbs;

	@Override
	public Response bindOnAccount(int appid, String unionid, String code,
			String mobile, BindType bindType) throws TException {
		return userbs.bindOnAccount(appid, unionid, code, mobile, bindType);
	}
}
