package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.common.exception.TCodeMessageException;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;
import com.moseeker.thrift.gen.useraccounts.struct.userloginresp;
import com.moseeker.useraccounts.dao.impl.UserDaoImpl;
import com.moseeker.useraccounts.dao.impl.WxuserDaoImpl;

@Service
public class UseraccountsServiceImpl implements Iface {

	
	protected BaseDao<UserWxUserRecord> wxuserdao = new WxuserDaoImpl();
	protected BaseDao<UserUserRecord> userdao = new UserDaoImpl();

	
	@Override
	public userloginresp postuserlogin(userloginreq userloginreq) throws TException {
		// TODO Auto-generated method stub
		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		if (userloginreq.getUnionid() != null ){
			filters.put("unionid", userloginreq.getUnionid());
		}else{
			filters.put("username", userloginreq.getMobile());
		//	filters.put("password", md5(userloginreq.getPassword()));
		}
		filters.put("parentid", null); // to exclude merged accounts.
		query.setLimit(1);
		query.setEqualFilter(filters);
//		UserUserRecord user;
//		try {
//			user = userdao.getResource(query);
//			if (user != null){
//				// login success
//				userloginresp resp = new userloginresp();
//				resp.setUnionid(user.getUnionid());
//				resp.setUser_id(user.getId().intValue());
//				resp.setMobile(user.getMobile().toString());
//				resp.setLast_login_time(user.getLastLoginTime().toString());
//				return resp;		
//			}			
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			throw new TCodeMessageException(2001, "帐号信息不一致");
//			//throw new TException("帐号信息不一致");
//		}
		throw new TCodeMessageException(2001, "帐号信息不一致");
		// throw new TException("帐号信息不一致");
		//userloginresp resp = new userloginresp();
		//return resp;
	}
	
	public static void main(String[] args){
		userloginreq userlogin = new userloginreq();
		userlogin.setMobile("13818252514");
		userlogin.setPassword("1234");
		
			try {
				new UseraccountsServiceImpl().postuserlogin(userlogin);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
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
