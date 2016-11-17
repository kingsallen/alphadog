package com.moseeker.foundations.passport.thrift.service;

import org.apache.thrift.TException;

import com.moseeker.thrift.gen.foundation.passport.service.UserBS.Iface;

public class UserThriftBS implements Iface {

	@Override
	public boolean isHaveProfile(int userId) throws TException {
		return false;
	}

}
