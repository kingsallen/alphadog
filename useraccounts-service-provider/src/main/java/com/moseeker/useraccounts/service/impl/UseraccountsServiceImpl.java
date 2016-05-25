package com.moseeker.useraccounts.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.jooq.types.UByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.logdb.tables.records.LogUserloginRecordRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.thrift.gen.useraccounts.struct.Usersetting;
import com.moseeker.useraccounts.dao.UserDao;
import com.moseeker.useraccounts.dao.impl.LogUserLoginDaoImpl;
import com.moseeker.useraccounts.dao.impl.ProfileDaoImpl;
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
	protected UserDao userdao = new UserDaoImpl();
	protected BaseDao<LogUserloginRecordRecord> loguserlogindao = new LogUserLoginDaoImpl();
	protected ProfileDaoImpl profileDao = new ProfileDaoImpl();

	public static void main(String[] args) {
		Userloginreq userlogin = new Userloginreq();
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
	public Response postuserlogin(Userloginreq userloginreq) throws TException {
		// TODO to add login log
		CommonQuery query = new CommonQuery();
		int parentid = -1;
		Map<String, String> filters = new HashMap<>();
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
					parentid = user.getParentid().intValue();
					query = new CommonQuery();
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
					query.setEqualFilter(filters);
					user = userdao.getResource(query);
				}
				
				if (user != null){
					Map<String, Object> resp = new HashMap<>();

					resp.put("user_id", user.getId().intValue());
					resp.put("union_id", user.getUnionid());
					resp.put("mobile", user.getMobile());
					resp.put("last_login_time", user.getLastLoginTime());

					user.setLastLoginTime(new Timestamp(new Date().getTime()));
					userdao.putResource(user);

					return ResponseUtils.success(resp);					
				}else{
					// 主 user_id 不存在， 数据异常。
					logger.error("postuserlogin error: ", parentid);
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);					
				}
				

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserlogin error: ", e);

		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_ACCOUNT_ILLEAGUE);

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
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}

	/**
	 * 发送手机注册的验证码
	 */
	@Override
	public Response postsendsignupcode(String mobile) throws TException {
		// TODO 未注册用户才能发送。
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		
		if (mobile.length()>0){
			filters.put("mobile", mobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				if (user != null) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("getismobileregisted error: ", e);
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

			}			
		}
		
		if (SmsSender.sendSMS_signup(mobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
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
			return ResponseUtils.success(ConstantErrorCodeMessage.INVALID_SMS_CODE);
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
				Map<String, Object> hashmap = new HashMap<>();
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
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}


	/**
	 * 绑定用户的手机号和unionid， 如果unionid或者手机号均没有， 则post新增， 如果在一条记录里都有，提示已经绑定成功，
	 * 如果在一条记录里有部分，unionid 或者 mobile， 补全。 否则unionid和mobile分别存在2条记录里面， 需要做合并。
	 */
	@Override
	public Response postuserwxbindmobile(int appid, String unionid, String code, String mobile) throws TException {
		// TODO validate code.
		if (!StringUtils.isNullOrEmpty(code) && !validateCode(mobile, code, 1)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}
		try {
			CommonQuery query1 = new CommonQuery();
			Map<String, String> filters1 = new HashMap<>();
			filters1.put("unionid", unionid);
			query1.setEqualFilter(filters1);
			UserUserRecord userUnnionid = userdao.getResource(query1);

			CommonQuery query2 = new CommonQuery();
			Map<String, String> filters2 = new HashMap<>();
			filters2.put("mobile", mobile);
			query1.setEqualFilter(filters2);
			UserUserRecord userMobile = userdao.getResource(query2);

			if (userUnnionid == null && userMobile == null) {
				// post
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
			} else if (userUnnionid != null && userMobile != null
					&& userUnnionid.getId().intValue() == userMobile.getId().intValue()) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
			} else if (userUnnionid != null && userMobile == null) {
				userUnnionid.setMobile(Long.valueOf(mobile));
				userdao.putResource(userUnnionid);
			} else if (userUnnionid == null && userMobile != null) {
				userMobile.setUnionid(unionid);
				userdao.putResource(userMobile);
			} else if (userUnnionid != null && userMobile != null
					&& userUnnionid.getId().intValue() != userMobile.getId().intValue()) {
				// 2 accounts, one unoinid, one mobile, need to merge.
				new Thread(() -> {
					combineAccount(userMobile, userUnnionid);
				}).start();
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.success(null);

	}

	private void combineAccount(UserUserRecord userMobile, UserUserRecord userUnionid) {
		try {
			// unnionid置为子账号
			userUnionid.setParentid(userMobile.getId().intValue());
			userdao.putResource(userMobile);

			// 被合并账号的个人profile置为无效
			ProfileProfileRecord profileRecord = profileDao.getProfileByUserId(userUnionid.getId().intValue());
			if (profileRecord != null) {
				profileRecord.setDisable(UByte.valueOf(Constant.DISABLE));
				profileDao.putResource(profileRecord);
			}

			// 合并业务代码
			// 合并sys_user_id表数据
			List<String> sysUserIds = getSystemUserIdTable();
			userdao.combineAccount(sysUserIds, "sys_user_id", userMobile.getId().intValue(),
					userUnionid.getId().intValue());

			// 合并user_id表数据
			List<String> userIds = getUserIdTable();
			userdao.combineAccount(userIds, "user_id", userMobile.getId().intValue(), userUnionid.getId().intValue());

			// 合并sysuser_id数据
			List<String> syssUserIds = getSysUserIdTable();
			userdao.combineAccount(syssUserIds, "sysuser_id", userMobile.getId().intValue(),
					userUnionid.getId().intValue());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private List<String> getSysUserIdTable() {
		List<String> tables = Lists.newArrayList("candidate_position_share_record", "hr_wx_hr_chat_list",
				"user_fav_position", "user_intention", "user_wx_user", "user_wx_viewer");
		return tables;
	}

	private List<String> getUserIdTable() {
		List<String> tables = Lists.newArrayList("profile_profile");
		return tables;
	}

	private List<String> getSystemUserIdTable() {
		List<String> tables = Lists.newArrayList("candidate_company");
		return tables;
	}

	/**
	 * 修改现有密码 
	 * @param user_id
	 * @param old_password 
	 * @param password
	 * @return
	 * @throws TException
	 */
	@Override
	public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		filters.put("id", String.valueOf(user_id));
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
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
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
				return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_PASSWORD_UNLEGAL);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserchangepassword error: ", e);

		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_UPDATE_PASSWORD_FAILED);

	}

	/**
	 * 发送忘记密码的验证码
	 */
	@Override
	public Response postusersendpasswordforgotcode(String mobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		
		if (mobile.length()>0){
			filters.put("mobile", mobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				if (user == null) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("getismobileregisted error: ", e);
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

			}			
		}
		
		if (SmsSender.sendSMS_passwordforgot(mobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	/**
	 * 忘记密码后重置密码,
	 * @param code 验证码，可选， 填写时必须判断。不填时， 请先调用postvalidatepasswordforgotcode 进行验证。
	 */
	@Override
	public Response postuserresetpassword(String mobile, String password,  String code) throws TException {

		if (code!=null && !validateCode(mobile, code, 2)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}

		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
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
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
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
				return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserresetpassword error: ", e);

		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_UPDATE_PASSWORD_FAILED);

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
		Map<String, String> filters = new HashMap<>();
		if (mobile.length()>0){
			filters.put("mobile", mobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				Map<String, Boolean> hashmap = new HashMap<>();
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
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
	}

/**
 * 修改手机号时， 先要向当前手机号发送验证码。
 */
	@Override
	public Response postsendchangemobilecode(String oldmobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		
		if (oldmobile.length()>0){
			filters.put("mobile", oldmobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				if (user == null) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.LOGIN_MOBILE_NOTEXIST);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("postsendchangemobilecode error: ", e);
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

			}			
		}
		
		if (SmsSender.sendSMS_changemobilecode(oldmobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

/**
 * 修改手机号时， 验证现有手机号的验证码。
 */
	@Override
	public Response postvalidatechangemobilecode(String oldmobile, String code) throws TException {
		if ( !validateCode(oldmobile, code, 3)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}else{
			return ResponseUtils.success(null);
		}
	}

	/**
	 * 修改手机号时，  向新手机号发送验证码。
	 */
	@Override
	public Response postsendresetmobilecode(String newmobile) throws TException {
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		
		if (newmobile.length()>0){
			filters.put("mobile", newmobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				if (user != null) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("postsendresetmobilecode error: ", e);
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

			}			
		}
		
		if (SmsSender.sendSMS_resetmobilecode(newmobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	/**
	 * 修改当前用户手机号。
	 * @param user_id  
	 * @param newmobile 新手机号
	 * @param code  新手机号的验证码
	 */
	@Override
	public Response postresetmobile(int user_id, String newmobile, String code) throws TException {
		if (code!=null && !validateCode(newmobile, code, 2)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}

		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		filters.put("id", String.valueOf(user_id));
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
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
					query.setEqualFilter(filters);
					UserUserRecord userParent = userdao.getResource(query);
					userParent.setMobile(Long.parseLong(newmobile));
					result = userdao.putResource(userParent);
				}
				user.setMobile(Long.parseLong(newmobile));
				result = userdao.putResource(user);
				if (result > 0) {
					return ResponseUtils.success(null);
				}
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postuserresetpassword error: ", e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);

		
		
	}
	
	/**
	 * 验证忘记密码的验证码是否正确
	 */
	@Override
	public Response postvalidatepasswordforgotcode(String mobile, String code) throws TException {
		if ( !validateCode(mobile, code, 2)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}else{
			return ResponseUtils.success(null);
		}	
	}
	
	/**
	 * 返回手机验证码的正确性, true 验证码正确。
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
		RedisClient redisclient = RedisClientFactory.getCacheClient();
		switch (type) {
		case 1:
			codeinRedis = redisclient.get(0, "SMS_SIGNUP", mobile);
			if (code.equals(codeinRedis)) {
				redisclient.del(0, "SMS_SIGNUP", mobile);
				return true;
			}
			break;
		case 2:
			codeinRedis = redisclient.get(0, "SMS_PWD_FORGOT", mobile);
			if (code.equals(codeinRedis)) {
				redisclient.del(0, "SMS_PWD_FORGOT", mobile);
				return true;
			}
		case 3:
			codeinRedis = redisclient.get(0, "SMS_CHANGEMOBILE_CODE", mobile);
			if (code.equals(codeinRedis)) {
				redisclient.del(0, "SMS_CHANGEMOBILE_CODE", mobile);
				return true;
			}
		case 4:
			codeinRedis = redisclient.get(0, "SMS_RESETMOBILE_CODE", mobile);
			if (code.equals(codeinRedis)) {
				redisclient.del(0, "SMS_RESETMOBILE_CODE", mobile);
				return true;
			}
			break;
		}

		return false;
	}	
	
}