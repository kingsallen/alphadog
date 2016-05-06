package com.moseeker.useraccounts.service.impl;

import org.apache.thrift.TException;

import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;
import com.moseeker.thrift.gen.useraccounts.struct.userloginresp;

public class UseraccountsServiceImpl implements Iface {

	@Override
	public userloginresp postuserlogin(userloginreq userloginreq) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int postuserlogout(int userid) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postsendsignupcode(String mobile) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postusermobilesignup(String mobile, String code, String password) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postuserwxsignup(String unionid) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postuserwxbindmobile(String unionid, String code, String mobile) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postusermobilebindwx(String mobile, String code, String unionid) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postuserchangepassword(int user_id, String old_password, String password) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postusersendpasswordforgotcode(String mobile) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postuserresetpassword(String mobile, String code, String password) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postusermergebymobile(String mobile) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}
}
