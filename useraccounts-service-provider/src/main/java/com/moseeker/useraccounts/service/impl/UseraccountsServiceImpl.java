package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;
import com.moseeker.useraccounts.dao.impl.UserDaoImpl;
import com.moseeker.useraccounts.dao.impl.WxuserDaoImpl;

@Service
public class UseraccountsServiceImpl implements Iface {

	
	protected BaseDao<UserWxUserRecord> wxuserdao = new WxuserDaoImpl();
	protected BaseDao<UserUserRecord> userdao = new UserDaoImpl();


	public static void main(String[] args){
		userloginreq userlogin = new userloginreq();
		userlogin.setMobile("13818252514");
		userlogin.setPassword("1234");
		
			try {
				new UseraccountsServiceImpl().postuserlogin(userlogin);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
	}

	@Override
	public Response postuserlogin(userloginreq userloginreq)  throws TException  {
		// TODO Auto-generated method stub
		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		if (userloginreq.getUnionid() != null ){
			filters.put("unionid", userloginreq.getUnionid());
		}else{
			filters.put("username", userloginreq.getMobile());
		//	filters.put("password", md5(userloginreq.getPassword()));
		}
	//	filters.put("parentid", null); // to exclude merged accounts.
		//query.setLimit(1);
		query.setEqualFilter(filters);
		Response jsonresp;
		try {
			UserUserRecord user = userdao.getResource(query);
			
			jsonresp = new Response();
			if (user != null){
				// login success
				Map resp = new HashMap();

				resp.put("user_id", user.getId().intValue());
				resp.put("union_id", user.getUnionid());
				resp.put("mobile", user.getMobile());
				resp.put("last_login_time", user.getLastLoginTime());
				
				//user.setLastLoginTime(new Timestamp());
				userdao.postResource(user);

				return ResponseUtils.success(resp);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}			
		return ResponseUtils.fail(10010, "username and password do not match!");
		
	}

	@Override
	public Response postuserlogout(int userid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postsendsignupcode(String mobile) throws TException {
		// TODO Auto-generated method stub
		Response jsonresp = new Response();

		if ( SmsSender.sendSMS_signup(mobile) ){
			return ResponseUtils.success(null);	
		}else{
			return ResponseUtils.fail("failed");
		}
	}

	@Override
	public Response postusermobilesignup(String mobile, String code, String password) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postuserwxsignup(String unionid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postuserwxbindmobile(String unionid, String code, String mobile) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postusermobilebindwx(String mobile, String code, String unionid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postusersendpasswordforgotcode(String mobile) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postuserresetpassword(String mobile, String code, String password) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postusermergebymobile(String mobile) throws TException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
