package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.common.struct.Response;
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
	public Response postuserlogin(userloginreq userloginreq) throws TException {
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
		query.setLimit(1);
		query.setEqualFilter(filters);
		UserUserRecord user = userdao.getResource(query);
		
		Response jsonresp = new Response();
		if (user != null){
			// login success
			userloginresp resp = new userloginresp();
			Map resp2 = new HashMap();

			resp.setUser_id(user.getId().intValue());
			resp2.put("user_id", user.getId().intValue());
			resp2.put("union_id", user.getUnionid());
			resp2.put("mobile", user.getMobile());
			resp2.put("last_login_time", user.getLastLoginTime());
			
			if ( user.getUnionid() != null ){
				resp.setUnionid(user.getUnionid());
			}
			if ( user.getMobile() != null ){
				resp.setMobile(user.getMobile().toString());
			}			
			if(user.getLastLoginTime() != null) {
				resp.setLast_login_time(user.getLastLoginTime().toString());
			}
			
			jsonresp.setStatus(0);
			jsonresp.setMessage("ok");
			jsonresp.setData(JSON.toJSONString(resp2));
			return jsonresp;		
		}			
		
		jsonresp.setStatus(1);
		jsonresp.setMessage("username and password do");
		return jsonresp;
	}

	@Override
	public Response postuserlogout(int userid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response postsendsignupcode(String mobile) throws TException {
		// TODO Auto-generated method stub
		return null;
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
