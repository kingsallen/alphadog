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
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
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
 * 
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
public class UseraccountsServiceImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected BaseDao<UserWxUserRecord> wxuserdao = new WxuserDaoImpl();
	protected BaseDao<UserUserRecord> userdao = new UserDaoImpl();
	protected BaseDao<LogUserloginRecordRecord> loguserlogindao = new LogUserLoginDaoImpl();

	public static void main(String[] args) {
		userloginreq userlogin = new userloginreq();
		userlogin.setMobile("13818252514");
		// userlogin.setPassword("123456");

		// System.out.println(MD5Util.md5("1234"));

		try {
			// new UseraccountsServiceImpl().postsendsignupcode("13818252514");
			Response r = new UseraccountsServiceImpl().postuserlogin(userlogin);
			System.out.print(r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 用户登陆， 返回用户登陆后的信息。
	 */
	@Override
	public Response postuserlogin(userloginreq userloginreq) throws TException {
		// TODO to add login log
		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		if (userloginreq.getUnionid() != null) {
			filters.put("unionid", userloginreq.getUnionid());
		} else {
			filters.put("username", userloginreq.getMobile());
			filters.put("password", MD5Util.md5(userloginreq.getPassword()));
		}

		query.setEqualFilter(filters);
		try {
			UserUserRecord user = userdao.getResource(query);

			if (user != null) {
				// login success

				if (user.getParentid() != null) {
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
	 * 记录用户登出时的信息。可能会移到 service-manager 处理。
	 * 
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

	/**
	 * 发送手机注册的验证码
	 */
	@Override
	public Response postsendsignupcode(String mobile) throws TException {
		// TODO 未注册用户才能发送。

		if (SmsSender.sendSMS_signup(mobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail("failed");
		}
	}

	/**
	 * 注册手机号用户。 password 为空时， 需要把密码直接发送给用户。
	 */
	@Override
	public Response postusermobilesignup(String mobile, String code, String password) throws TException {
		// TODO validate code.
		if (validateCode(mobile, code, 1)) {
			;
		} else {
			return ResponseUtils.fail(10011, "mobile signup validation code failed");
		}

		boolean hasPassword = true;
		if (password == null) {
			hasPassword = false;
			password = StringUtils.getRandomString(6);

		}

		UserUserRecord user = new UserUserRecord();
		user.setUsername(mobile);
		user.setMobile(Long.parseLong(mobile));
		user.setPassword(MD5Util.md5(password));

		try {
			int newuserid = userdao.postResource(user);
			if (newuserid > 0) {
				Map hashmap = new HashMap();
				hashmap.put("user_id", newuserid);
				if (!hasPassword) {
					// 未设置密码， 主动发送给用户。
					SmsSender.sendSMS_signupRandomPassword(mobile, password);
				}
				return ResponseUtils.success(hashmap); // 返回 user id

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postusermobilesignup error: ", e);
		}
		return ResponseUtils.fail("register failed");
	}

	/**
	 * 返回手机验证码的正确性
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            验证码
	 * @param type
	 *            1:注册 2:忘记密码
	 */
	private boolean validateCode(String mobile, String code, int type) {
		String codeinRedis = null;
		switch (type) {
		case 1:
			codeinRedis = RedisClientFactory.getCacheClient().get(0, "SMS_SIGNUP", mobile);
			if (code.equals(codeinRedis)) {
				RedisClientFactory.getCacheClient().del(0, "SMS_SIGNUP", mobile);
				return true;
			}
			break;
		case 2:
			codeinRedis = RedisClientFactory.getCacheClient().get(0, "SMS_PWD_FORGOT", mobile);
			if (code.equals(codeinRedis)) {
				RedisClientFactory.getCacheClient().del(0, "SMS_PWD_FORGOT", mobile);
				return true;
			}
			break;
		}

		return false;
	}

	/**
	 * 绑定用户的手机号和unionid， 如果unionid或者手机号均没有， 则post新增， 如果在一条记录里都有，提示已经绑定成功，
	 * 如果在一条记录里有部分，unionid 或者 mobile， 补全。 否则unionid和mobile分别存在2条记录里面， 需要做合并。
	 */
	@Override
	public Response postuserwxbindmobile(int appid, String unionid, String code, String mobile) throws TException {
		// TODO validate code.
		if (validateCode(mobile, code, 1)) {
			;
		} else {
			return ResponseUtils.fail(10011, "mobile validation code failed");
		}

		try {
			CommonQuery query1 = new CommonQuery();
			Map filters1 = new HashMap();
			filters1.put("unionid", unionid);
			query1.setEqualFilter(filters1);
			UserUserRecord user1 = userdao.getResource(query1);

			CommonQuery query2 = new CommonQuery();
			Map filters2 = new HashMap();
			filters2.put("mobile", mobile);
			query1.setEqualFilter(filters2);
			UserUserRecord user2 = userdao.getResource(query2);

			if ((user1 == null) && (user2 == null)) {
				// post
			} else if ((user1 != null) && (user2 != null) && (user1.getId().intValue() == user2.getId().intValue())) {
				// already bound
			} else if ((user1 != null) && (user2 == null)) {
				// only unionid
			} else if ((user1 == null) && (user2 != null)) {
				// only mobile
			} else {
				// 2 accounts, one unoinid, one mobile, need to merge.
				;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseUtils.fail("register failed");

	}

	/**
	 * 
	 * @param user_id
	 * @param old_password
	 * @param password
	 * @return
	 * @throws TException
	 */
	@Override
	public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {
		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		filters.put("id", user_id);
		filters.put("password", MD5Util.md5(old_password));
		query.setEqualFilter(filters);

		int result = 0;
		try {
			UserUserRecord user = userdao.getResource(query);

			if (user != null) {
				// login success
				if (user.getParentid() != null) {
					// 当前帐号已经被合并到 parentid.
					int parentid = user.getParentid().intValue();
					query = new CommonQuery();
					filters = new HashMap();
					filters.put("id", parentid);
					query.setEqualFilter(filters);
					UserUserRecord userParent = userdao.getResource(query);
					userParent.setPassword(MD5Util.md5(password));
					result = userdao.putResource(userParent);
				}
				user.setPassword(MD5Util.md5(password));
				result = userdao.putResource(user);
				if (result > 0) {
					return ResponseUtils.success(null);
				}
			} else {
				ResponseUtils.fail(10012, "failed to change password: old password doesn't match!");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserchangepassword error: ", e);

		}
		return ResponseUtils.fail(10013, "update password failed");

	}

	/**
	 * 发送忘记密码的验证码
	 */
	@Override
	public Response postusersendpasswordforgotcode(String mobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		if (SmsSender.sendSMS_signup(mobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail("failed");
		}
	}

	/**
	 * 忘记密码后重置密码
	 */
	@Override
	public Response postuserresetpassword(String mobile, String code, String password) throws TException {

		if (validateCode(mobile, code, 2)) {
			;
		} else {
			return ResponseUtils.fail(10011, "mobile validation code failed");
		}

		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		filters.put("mobile", mobile);
		query.setEqualFilter(filters);

		int result = 0;
		try {
			UserUserRecord user = userdao.getResource(query);

			if (user != null) {
				// login success
				if (user.getParentid() != null) {
					// 当前帐号已经被合并到 parentid.
					int parentid = user.getParentid().intValue();
					query = new CommonQuery();
					filters = new HashMap();
					filters.put("id", parentid);
					query.setEqualFilter(filters);
					UserUserRecord userParent = userdao.getResource(query);
					userParent.setPassword(MD5Util.md5(password));
					result = userdao.putResource(userParent);
				}
				user.setPassword(MD5Util.md5(password));
				result = userdao.putResource(user);
				if (result > 0) {
					return ResponseUtils.success(null);
				}
			} else {
				ResponseUtils.fail(10014, "mobile doesn't exist.");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserresetpassword error: ", e);

		}
		return ResponseUtils.fail(10013, "update password failed");

	}

	@Override
	public Response postusermergebymobile(int appid, String mobile) throws TException {
		// TODO Auto-generated method stub
		return null;
	}
/**
 * 检查手机号是否已经注册。 exist: true 已经存在， exist：false 不存在。
 * @param mobile
 * @return
 * @throws TException
 */
	@Override
	public Response getismobileregisted(String mobile) throws TException {
		CommonQuery query = new CommonQuery();
		Map filters = new HashMap();
		if (mobile.length()>0){
			filters.put("mobile", mobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				Map hashmap = new HashMap();
				if (user == null) {
					hashmap.put("exist", false);
				}else{
					hashmap.put("exist", true);
				}
				return ResponseUtils.success(hashmap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("getismobileregisted error: ", e);

			}			
		}
		
		return ResponseUtils.fail(-1 ,"系统繁忙，此时稍候再试");
		

	}
}
