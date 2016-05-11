package com.moseeker.useraccounts.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.MD5Util;
import com.moseeker.db.logdb.tables.records.LogUserloginRecordRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;
import com.moseeker.useraccounts.dao.impl.LogUserLoginDaoImpl;
import com.moseeker.useraccounts.dao.impl.UserDaoImpl;
import com.moseeker.useraccounts.dao.impl.WxuserDaoImpl;

/**
 * 用户登陆， 注册，合并等api的实现
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
public class UseraccountsServiceImpl implements Iface {

	 Logger logger = LoggerFactory.getLogger(this.getClass());

	
	protected BaseDao<UserWxUserRecord> wxuserdao = new WxuserDaoImpl();
	protected BaseDao<UserUserRecord> userdao = new UserDaoImpl();
	protected BaseDao<LogUserloginRecordRecord> loguserlogindao = new LogUserLoginDaoImpl();


	public static void main(String[] args){
		userloginreq userlogin = new userloginreq();
		userlogin.setMobile("13818252514");
		userlogin.setPassword("123456");
		
		// System.out.println(MD5Util.md5("1234"));
		
			try {
				new UseraccountsServiceImpl().postsendsignupcode("13818252514");
				// new UseraccountsServiceImpl().postuserlogin(userlogin);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
	}
	
/**
 * 用户登陆， 返回用户登陆后的信息。	
 */
	@Override
	public Response postuserlogin(userloginreq userloginreq)  throws TException  {
		// TODO to add login log
		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		if (userloginreq.getUnionid() != null ){
			filters.put("unionid", userloginreq.getUnionid());
		}else{
			filters.put("username", userloginreq.getMobile());
			filters.put("password", MD5Util.md5(userloginreq.getPassword()));
		}

		query.setEqualFilter(filters);
		Response jsonresp;
		try {
			UserUserRecord user = userdao.getResource(query);
			
			jsonresp = new Response();
			if (user != null){
				// login success
				
				if (user.getParentid() != null){
					// 当前帐号已经被合并到 parentid.
					int parentid = user.getParentid().intValue();
					query = new CommonQuery();
					filters = new HashMap();
					filters.put("id", parentid);
					query.setEqualFilter(filters);
					user = userdao.getResource(query);
				}
				
				
				Map resp = new HashMap();

				resp.put("user_id", user.getId().intValue());
				resp.put("union_id", user.getUnionid());
				resp.put("mobile", user.getMobile());
				resp.put("last_login_time", user.getLastLoginTime());
				
				user.setLastLoginTime(new Timestamp(new Date().getTime()));
				userdao.putResource(user);

				System.out.println(resp);
				return ResponseUtils.success(resp);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserlogin error: ", e);

		}			
		return ResponseUtils.fail(10010, "username and password do not match!");
		
	}

	/**
	 * 记录用户登出时的信息。
	 * @param userid 
	 * @return
	 * @throws TException
	 */
	@Override
	public Response postuserlogout(int userid) throws TException {
		LogUserloginRecordRecord logout = new LogUserloginRecordRecord();
		logout.setUserId(userid);
		logout.setActiontype(2);
		try {
			loguserlogindao.postResource(logout);
			return ResponseUtils.success(null);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserlogout error: ", e);
		}
		return ResponseUtils.fail("failed to record logout record");	
	}

	@Override
	public Response postsendsignupcode(String mobile) throws TException {
		// TODO ip limit
		Response jsonresp = new Response();

		if ( SmsSender.sendSMS_signup(mobile) ){
			return ResponseUtils.success(null);	
		}else{
			return ResponseUtils.fail("failed");
		}
	}

	@Override
	public Response postusermobilesignup(String mobile, String code, String password) throws TException {
		// TODO validate code.
		UserUserRecord user = new UserUserRecord();
		user.setUsername(mobile);
		user.setMobile(Long.parseLong(mobile));
		user.setPassword(MD5Util.md5(password));
		
		try {
			int newuserid = userdao.postResource(user);
			if ( newuserid > 0 ){
				// ResponseUtils.success(null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseUtils.fail("register failed");
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
		// TODO ip limit
		Response jsonresp = new Response();

		if ( SmsSender.sendSMS_signup(mobile) ){
			return ResponseUtils.success(null);	
		}else{
			return ResponseUtils.fail("failed");
		}
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
