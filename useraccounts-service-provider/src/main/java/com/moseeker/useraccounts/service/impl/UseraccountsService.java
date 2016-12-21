package com.moseeker.useraccounts.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RespnoseUtil;
import com.moseeker.common.constants.TemplateId;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.constants.UserType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.weixin.AccountMng;
import com.moseeker.common.weixin.QrcodeType;
import com.moseeker.common.weixin.WeixinTicketBean;
import com.moseeker.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.logdb.tables.records.LogUserloginRecordRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserFavoritePosition;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.useraccounts.dao.ProfileDao;
import com.moseeker.useraccounts.dao.UserDao;
import com.moseeker.useraccounts.dao.UserFavoritePositionDao;
import com.moseeker.useraccounts.dao.UsersettingDao;
import com.moseeker.useraccounts.dao.WechatDao;
import com.moseeker.useraccounts.pojo.MessageTemplate;

/**
 * 用户登陆， 注册，合并等api的实现
 * 
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
@CounterIface
public class UseraccountsService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
	
	com.moseeker.thrift.gen.dao.service.UserDao.Iface userDao = ServiceManager.SERVICEMANAGER
			.getService(com.moseeker.thrift.gen.dao.service.UserDao.Iface.class);

	@Autowired
	protected BaseDao<UserWxUserRecord> wxuserdao;

	@Autowired
	protected UserDao userdao;

	@Autowired
	protected BaseDao<LogUserloginRecordRecord> loguserlogindao;

	@Autowired
	protected ProfileDao profileDao;

	@Autowired
	protected UsersettingDao userSettingDao;

	@Autowired
	protected UserFavoritePositionDao userFavoritePositionDao;
	
	@Autowired
	protected SmsSender smsSender;
	
	@Autowired
	protected WechatDao wechatDao;
	
	/**
	 * 用户登陆， 返回用户登陆后的信息。
	 */
	public Response postuserlogin(Userloginreq userloginreq) throws TException {
		// TODO to add login log
		CommonQuery query = new CommonQuery();
		int parentid = 0;
		Map<String, String> filters = new HashMap<>();

		String code = userloginreq.getCode();
		if (code != null) {
			// 存在验证码,就是手机号+验证码登陆.
			String mobile = userloginreq.getMobile();
			if (validateCode(mobile, code, 1)) {
				filters.put("username", mobile);
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
			}
		} else if (userloginreq.getUnionid() != null) {
			filters.put("unionid", userloginreq.getUnionid());
		} else {
			filters.put("username", userloginreq.getMobile());
			filters.put("password", MD5Util.encryptSHA(userloginreq.getPassword()));
		}

		query.setEqualFilter(filters);
		try {
			UserUserRecord user = userdao.getResource(query);
			if (user != null) {
				// login success
				parentid = user.getParentid().intValue();
				if (parentid > 0) {
					// 当前帐号已经被合并到 parentid.
					query = new CommonQuery();
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
					query.setEqualFilter(filters);
					user = userdao.getResource(query);
				}

				if (user != null) {
					Map<String, Object> resp = new HashMap<>();

					resp.put("user_id", user.getId().intValue());
					resp.put("unionid", user.getUnionid());

					if (user.getUsername().length() < 12) {
						resp.put("mobile", user.getUsername());
					} else {
						resp.put("mobile", "");
					}

					resp.put("last_login_time", user.getLastLoginTime());
					resp.put("name", user.getName());
					resp.put("headimg", user.getHeadimg());

					user.setLastLoginTime(new Timestamp(new Date().getTime()));
					user.setLoginCount(user.getLoginCount() + 1);

					userdao.putResource(user);

					return ResponseUtils.success(resp);
				} else {
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
	public Response postsendsignupcode(String mobile) throws TException {
		// TODO 未注册用户才能发送。
		/*
		 * CommonQuery query = new CommonQuery(); Map<String, String> filters =
		 * new HashMap<>();
		 */

		/*
		 * 以下代码限制未注册用户才能发送。 由于存在 mobile+code的登陆方式, 老用户也可以发送验证码. if
		 * (mobile.length()>0){ filters.put("mobile", mobile);
		 * query.setEqualFilter(filters); try { UserUserRecord user =
		 * userdao.getResource(query); if (user != null) { return
		 * ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST); } }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * logger.error("getismobileregisted error: ", e); return
		 * ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		 * 
		 * } }
		 */

		if (mobile.length() < 10) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		}

		if (smsSender.sendSMS_signup(mobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
		}
	}

	/**
	 * 注册手机号用户。 password 为空时， 需要把密码直接发送给用户。
	 * <p>
	 *
	 * @param user
	 *            用户实体
	 * @param code
	 *            验证码 , 可选, 有的时候必须验证.
	 * @return 新添加用户ID
	 *
	 * @exception TException
	 *
	 */
	public Response postusermobilesignup(User user, String code) throws TException {

		boolean hasPassword = true; // 判断是否需要生成密码
		String plainPassword = "892304"; // 没有密码用户的初始密码, 随机数替换

		// 空指针校验
		if (user == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		}

		if (!StringUtils.isNullOrEmpty(code) && !validateCode(String.valueOf(user.mobile), code, 1)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}

		// 没有密码生成6位随机密码
		if (user.password == null) {
			hasPassword = false;
			plainPassword = StringUtils.getRandomString(6);
			user.password = MD5Util.encryptSHA(plainPassword);
		} else {
			user.password = MD5Util.encryptSHA(user.password);
		}

		try {
			// 添加用户
			int newCreateUserId = userdao.createUser(user);
			if (newCreateUserId > 0) {

				// 未设置密码, 主动短信通知用户
				if (!hasPassword) {
					smsSender.sendSMS_signupRandomPassword(String.valueOf(user.mobile), plainPassword);
				}

				// // 初始化 user_setting 表.
				// UserSettingsRecord userSettingsRecord = new
				// UserSettingsRecord();
				// userSettingsRecord.setUserId(UInteger.valueOf(newCreateUserId));
				// userSettingsRecord.setPrivacyPolicy(UByte.valueOf(0));
				// userSettingDao.postResource(userSettingsRecord);

				return ResponseUtils.success(new HashMap<String, Object>() {

					private static final long serialVersionUID = -5518436764754085050L;

					{
						put("user_id", newCreateUserId);
					}
				}); // 返回 user id
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postusermobilesignup error: ", e);

		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_EXIST);
	}

	/**
	 * 绑定用户的手机号和unionid， 如果在一条记录里都有，提示已经绑定成功， 如果在一条记录里有部分，unionid 或者 mobile， 补全。
	 * 否则unionid和mobile分别存在2条记录里面， 需要做合并。 如果unionid或者手机号均没有， 应该在之前先注册.
	 * code验证码可选.
	 */
	public Response postuserwxbindmobile(int appid, String unionid, String code, String mobile) throws TException {
		// TODO validate code.
		/*
		 * if (!StringUtils.isNullOrEmpty(code) && !validateCode(mobile, code,
		 * 1)) { return
		 * ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE); }
		 */
		try {
			CommonQuery query1 = new CommonQuery();
			Map<String, String> filters1 = new HashMap<>();
			filters1.put("unionid", unionid);
			filters1.put("parentid", "0");
			query1.setEqualFilter(filters1);
			UserUserRecord userUnionid = userdao.getResource(query1);

			CommonQuery query2 = new CommonQuery();
			Map<String, String> filters2 = new HashMap<>();
			filters2.put("username", mobile);
			query2.setEqualFilter(filters2);
			UserUserRecord userMobile = userdao.getResource(query2);

			if (userUnionid == null && userMobile == null) {
				// post, 都为空的情况, 需要事先调用 user_
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
			} else if (userUnionid != null && userMobile != null
					&& userUnionid.getId().intValue() == userMobile.getId().intValue()) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
			} else if (userUnionid != null && userMobile == null) {
				userUnionid.setMobile(Long.valueOf(mobile));
				userUnionid.setUsername(mobile);
				if (userdao.putResource(userUnionid) > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", userUnionid.getId().intValue());
					map.put("username", userUnionid.getUsername());
					if (userUnionid.getIsDisable() != null) {
						map.put("is_disable", userUnionid.getIsDisable().intValue());
					}
					if (userUnionid.getRank() != null) {
						map.put("rank", userUnionid.getRank());
					}
					if (userUnionid.getRegisterTime() != null) {
						map.put("register_time", DateUtils.dateToShortTime(userUnionid.getRegisterTime()));
					}
					map.put("register_ip", userUnionid.getRegisterIp());
					if (userUnionid.getLastLoginTime() != null) {
						map.put("last_login_time", DateUtils.dateToShortTime(userUnionid.getLastLoginTime()));
					}
					map.put("last_login_ip", userUnionid.getLastLoginIp());
					if (userUnionid.getLoginCount() != null) {
						map.put("login_count", userUnionid.getLoginCount().intValue());
					}
					if (userUnionid.getMobile() != null) {
						map.put("mobile", userUnionid.getMobile().longValue());
					}
					map.put("email", userUnionid.getEmail());
					if (userUnionid.getActivation() != null) {
						map.put("activation", userUnionid.getActivation().intValue());
					}
					map.put("activation_code", userUnionid.getActivationCode());
					map.put("token", userUnionid.getToken());
					map.put("name", userUnionid.getName());
					map.put("headimg", userUnionid.getHeadimg());
					if (userUnionid.getNationalCodeId() != null) {
						map.put("national_code_id", userUnionid.getNationalCodeId().intValue());
					}
					if (userUnionid.getWechatId() != null) {
						map.put("wechat_id", userUnionid.getWechatId().intValue());
					}
					map.put("unionid", userUnionid.getUnionid());
					if (userUnionid.getSource() != null) {
						map.put("source", userUnionid.getSource().intValue());
					}
					map.put("company", userUnionid.getCompany());
					map.put("position", userUnionid.getPosition());
					map.put("parentid", userUnionid.getParentid().intValue());
					return ResponseUtils.success(map);
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
				}
			} else if (userUnionid == null && userMobile != null) {
				if(StringUtils.isNotNullOrEmpty(userMobile.getUnionid())) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_REPEATBIND);
				}
				userMobile.setUnionid(unionid);
				if (userdao.putResource(userMobile) > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", userMobile.getId().intValue());
					map.put("username", userMobile.getUsername());
					if (userMobile.getIsDisable() != null) {
						map.put("is_disable", userMobile.getIsDisable().intValue());
					}
					if (userMobile.getRank() != null) {
						map.put("rank", userMobile.getRank());
					}
					if (userMobile.getRegisterTime() != null) {
						map.put("register_time", DateUtils.dateToShortTime(userMobile.getRegisterTime()));
					}
					map.put("register_ip", userMobile.getRegisterIp());
					if (userMobile.getLastLoginTime() != null) {
						map.put("last_login_time", DateUtils.dateToShortTime(userMobile.getLastLoginTime()));
					}
					map.put("last_login_ip", userMobile.getLastLoginIp());
					if (userMobile.getLoginCount() != null) {
						map.put("login_count", userMobile.getLoginCount().intValue());
					}
					if (userMobile.getMobile() != null) {
						map.put("mobile", userMobile.getMobile().longValue());
					}
					map.put("email", userMobile.getEmail());
					if (userMobile.getActivation() != null) {
						map.put("activation", userMobile.getActivation().intValue());
					}
					map.put("activation_code", userMobile.getActivationCode());
					map.put("token", userMobile.getToken());
					map.put("name", userMobile.getName());
					map.put("headimg", userMobile.getHeadimg());
					if (userMobile.getNationalCodeId() != null) {
						map.put("national_code_id", userMobile.getNationalCodeId().intValue());
					}
					if (userMobile.getWechatId() != null) {
						map.put("wechat_id", userMobile.getWechatId().intValue());
					}
					map.put("unionid", userMobile.getUnionid());
					if (userMobile.getSource() != null) {
						map.put("source", userMobile.getSource().intValue());
					}
					map.put("company", userMobile.getCompany());
					map.put("position", userMobile.getPosition());
					map.put("parentid", userMobile.getParentid().intValue());
					return ResponseUtils.success(map);
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
				}
			} else if (userUnionid != null && userMobile != null
					&& userUnionid.getId().intValue() != userMobile.getId().intValue()) {
				// 2 accounts, one unoinid, one mobile, need to merge.
				if (StringUtils.isNotNullOrEmpty(userMobile.getUnionid())) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_REPEATBIND);
				}
				combineAccount(appid, userMobile, userUnionid);
				// 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录, 7:PC(正常添加) 8:PC(我要投递) 9:
				// PC(我感兴趣)
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", userMobile.getId().intValue());
				map.put("username", userMobile.getUsername());
				if (userMobile.getIsDisable() != null) {
					map.put("is_disable", userMobile.getIsDisable().intValue());
				}
				if (userMobile.getRank() != null) {
					map.put("rank", userMobile.getRank());
				}
				if (userMobile.getRegisterTime() != null) {
					map.put("register_time", DateUtils.dateToShortTime(userMobile.getRegisterTime()));
				}
				map.put("register_ip", userMobile.getRegisterIp());
				if (userMobile.getLastLoginTime() != null) {
					map.put("last_login_time", DateUtils.dateToShortTime(userMobile.getLastLoginTime()));
				}
				map.put("last_login_ip", userMobile.getLastLoginIp());
				if (userMobile.getLoginCount() != null) {
					map.put("login_count", userMobile.getLoginCount().intValue());
				}
				if (userMobile.getMobile() != null) {
					map.put("mobile", userMobile.getMobile().longValue());
				}
				map.put("email", userMobile.getEmail());
				if (userMobile.getActivation() != null) {
					map.put("activation", userMobile.getActivation().intValue());
				}
				map.put("activation_code", userMobile.getActivationCode());
				map.put("token", userMobile.getToken());
				map.put("name", userMobile.getName());
				map.put("headimg", userMobile.getHeadimg());
				if (userMobile.getNationalCodeId() != null) {
					map.put("national_code_id", userMobile.getNationalCodeId().intValue());
				}
				if (userMobile.getWechatId() != null) {
					map.put("wechat_id", userMobile.getWechatId().intValue());
				}
				map.put("unionid", userMobile.getUnionid());
				if (userMobile.getSource() != null) {
					map.put("source", userMobile.getSource().intValue());
				}
				map.put("company", userMobile.getCompany());
				map.put("position", userMobile.getPosition());
				map.put("parentid", userMobile.getParentid().intValue());
				return ResponseUtils.success(map);
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

	}

	private void combineAccount(int appid, UserUserRecord userMobile, UserUserRecord userUnionid) {
		try {
			// unnionid置为子账号
			userUnionid.setParentid(userMobile.getId());
			/* 完善unionid */
			if (StringUtils.isNullOrEmpty(userMobile.getUnionid())
					&& StringUtils.isNotNullOrEmpty(userUnionid.getUnionid())) {
				userMobile.setUnionid(userUnionid.getUnionid());
			}
			userUnionid.setUnionid("");
			if (userdao.putResource(userUnionid) > 0) {
				consummateUserAccount(userMobile, userUnionid);
				// profile合并成功
			} else {
				// 合并失败, log.
			}

			// weixin端(聚合号),weixin端（企业号） 发起, 保留微信端 profile; 否则保留pc端(无需处理).
			switch (appid) {
			case Constant.APPID_QX:
			case Constant.APPID_PLATFORM:
				ProfileProfileRecord userMobileProfileRecord = profileDao
						.getProfileByUserId(userMobile.getId().intValue());
				// 微信端profile转移到pc用户下.
				ProfileProfileRecord userUnionProfileRecord = profileDao
						.getProfileByUserId(userUnionid.getId().intValue());
				if (userUnionProfileRecord != null) {
					// pc 端profile 设置为无效
					if (userMobileProfileRecord != null) {
						profileDao.delResource(userMobileProfileRecord);
					}
					userUnionProfileRecord.setUserId(userMobile.getId());
					profileDao.putResource(userUnionProfileRecord);
				}

				break;
			case Constant.APPID_C:
				ProfileProfileRecord userMobileProfileRecord1 = profileDao
					.getProfileByUserId(userMobile.getId().intValue());
				if(userMobileProfileRecord1 == null) {
					// 微信端profile转移到pc用户下.
					ProfileProfileRecord userUnionProfileRecord1 = profileDao
							.getProfileByUserId(userUnionid.getId().intValue());
					if(userUnionProfileRecord1 != null) {
						userUnionProfileRecord1.setUserId(userMobile.getId());
						profileDao.putResource(userUnionProfileRecord1);
					}
				}
			default:
				break;
			}
			// 合并业务代码
			// 最后通过消息队列交给独立的服务处理
			new Thread(() -> {
				try {
					userdao.combineAccount(userMobile.getId().intValue(), userUnionid.getId().intValue());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}).start();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 账号合并完善账号信息
	 * 
	 * @param userMobile
	 *            需要完善的账号
	 * @param userUnionid
	 *            信息来源
	 */
	private void consummateUserAccount(UserUserRecord userMobile, UserUserRecord userUnionid) {
		/* 完善用户名称 */
		if (StringUtils.isNullOrEmpty(userMobile.getName()) && StringUtils.isNotNullOrEmpty(userUnionid.getName())) {
			userMobile.setName(userUnionid.getName());
		}
		/* 完善用户昵称 */
		if (StringUtils.isNullOrEmpty(userMobile.getNickname())
				&& StringUtils.isNotNullOrEmpty(userUnionid.getNickname())) {
			userMobile.setNickname(userUnionid.getNickname());
		}
		/* 完善用户级别，预计rank越高，表示用户等级越高。 */
		if ((userUnionid.getRank() != null && userMobile.getRank() == null) || (userUnionid.getRank() != null
				&& userMobile.getRank() != null && userUnionid.getRank() > userMobile.getRank())) {
			userMobile.setRank(userUnionid.getRank());
		}
		/* 完善用户未验证的手机号码 */
		if (userUnionid.getMobile() != null && userUnionid.getMobile() > 0
				&& (userMobile.getMobile() == null || userMobile.getMobile() == 0)) {
			userMobile.setMobile(userUnionid.getMobile());
		}
		/* 完善用户邮箱 */
		if (StringUtils.isNullOrEmpty(userMobile.getEmail()) && StringUtils.isNotNullOrEmpty(userUnionid.getEmail())) {
			userMobile.setEmail(userUnionid.getEmail());
		}
		/* 完善用户头像 */
		if (StringUtils.isNullOrEmpty(userMobile.getHeadimg())
				&& StringUtils.isNotNullOrEmpty(userUnionid.getHeadimg())) {
			userMobile.setHeadimg(userUnionid.getHeadimg());
		}
		/* 完善国家代码 */
		if (userUnionid.getNationalCodeId() != null && userUnionid.getNationalCodeId() != 1
				&& (userMobile.getNationalCodeId() == null || userMobile.getNationalCodeId() == 1)) {
			userMobile.setNationalCodeId(userUnionid.getNationalCodeId());
		}
		/* 完善感兴趣的公司 */
		if (StringUtils.isNullOrEmpty(userMobile.getCompany())
				&& StringUtils.isNotNullOrEmpty(userUnionid.getCompany())) {
			userMobile.setCompany(userUnionid.getCompany());
		}
		/* 完善感兴趣的职位 */
		if (StringUtils.isNullOrEmpty(userMobile.getPosition())
				&& StringUtils.isNotNullOrEmpty(userUnionid.getPosition())) {
			userMobile.setPosition(userUnionid.getPosition());
		}
		try {
			userdao.putResource(userMobile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 修改现有密码
	 * 
	 * @param user_id
	 * @param old_password
	 * @param password
	 * @return
	 * @throws TException
	 */
	public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {
		
		
		if(StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(old_password)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
		}
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		filters.put("id", String.valueOf(user_id));
		filters.put("password", MD5Util.encryptSHA(old_password));
		query.setEqualFilter(filters);

		int result = 0;
		try {
			UserUserRecord user = userdao.getResource(query);

			if (user != null) {
				// login success
				int parentid = user.getParentid().intValue();
				if (parentid > 0) {
					// 当前帐号已经被合并到 parentid.
					query = new CommonQuery();
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
					query.setEqualFilter(filters);
					UserUserRecord userParent = userdao.getResource(query);
					userParent.setPassword(MD5Util.encryptSHA(password));
					result = userdao.putResource(userParent);
				}
				user.setPassword(MD5Util.encryptSHA(password));
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
	public Response postusersendpasswordforgotcode(String mobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();

		if (mobile.length() > 0) {
			filters.put("username", mobile);
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

		if (smsSender.sendSMS_passwordforgot(mobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
		}
	}

	/**
	 * 忘记密码后重置密码,
	 * 
	 * @param code
	 *            验证码，可选， 填写时必须判断。不填时， 请先调用postvalidatepasswordforgotcode 进行验证。
	 */
	public Response postuserresetpassword(String mobile, String password, String code) throws TException {

		if (code != null && !validateCode(mobile, code, 2)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		}
		

		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		filters.put("username", mobile);
		query.setEqualFilter(filters);

		int result = 0;
		try {
			UserUserRecord user = userdao.getResource(query);

			if (user != null) {
				// login success
				int parentid = user.getParentid().intValue();
				String newPassword = MD5Util.encryptSHA(password);
				if (parentid > 0) {
					// 当前帐号已经被合并到 parentid.
					query = new CommonQuery();
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
					query.setEqualFilter(filters);
					UserUserRecord userParent = userdao.getResource(query);
					if(newPassword.equals(userParent.getPassword())) {
						return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_PASSWORD_REPEATPASSWORD);
					}
					userParent.setPassword(newPassword);
					result = userdao.putResource(userParent);
				}
				if(newPassword.equals(user.getPassword())) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_PASSWORD_REPEATPASSWORD);
				}
				user.setPassword(newPassword);
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

	public Response postusermergebymobile(int appid, String mobile) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取用户数据
	 *
	 * @param userId
	 *            用户ID
	 *
	 */
	public Response getUserById(long userId) throws TException {
		try {
			if (userId > 0) {
				return ResponseUtils.success(userdao.getUserById(userId));
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("getUserById error: ", e);
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}
	
	public Response getUsers(CommonQuery query) throws TException {
		try {
			List<User> users = new ArrayList<>();
			List<UserUserRecord> records = userdao.getResources(query);
			if(records != null) {
				records.forEach(record -> {
					users.add(record.into(User.class));
				});
			}
			//record.in
			return ResponseUtils.success(users);
		} catch (Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	/**
	 * 更新用户数据
	 *
	 * @param user
	 *            用户实体
	 *
	 */
	public Response updateUser(User user) throws TException {
		try {
			if (user != null && user.getId() > 0) {
				if(StringUtils.isNotNullOrEmpty(user.getPassword())) {
					user.setPassword(MD5Util.encryptSHA(user.getPassword()));
				}
				// 用户记录转换
				UserUserRecord userUserRecord = (UserUserRecord) BeanUtils.structToDB(user, UserUserRecord.class);
				if (userdao.putResource(userUserRecord) > 0) {
					if (user.isSetUsername() || user.isSetMobile() || user.isSetEmail() || user.isSetName()
							|| user.isSetHeadimg()) {
						profileDao.updateUpdateTimeByUserId((int) user.getId());
					}
					return ResponseUtils.success(null);
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
				}
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} catch (Exception e) {
			logger.error("updateUser error: ", e);

		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}

	/**
	 * 检查手机号是否已经注册。 exist: true 已经存在， exist：false 不存在。
	 * 
	 * @param mobile
	 * @return
	 * @throws TException
	 */
	public Response getismobileregisted(String mobile) throws TException {
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		if (mobile.length() > 0) {
			filters.put("username", mobile);
			query.setEqualFilter(filters);
			try {
				UserUserRecord user = userdao.getResource(query);
				Map<String, Boolean> hashmap = new HashMap<>();
				if (user == null) {
					hashmap.put("exist", false);
				} else {
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
	 *
	 */
	public Response postsendchangemobilecode(String oldmobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();

		if (oldmobile.length() > 0) {
			filters.put("username", oldmobile);
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

		if (smsSender.sendSMS_changemobilecode(oldmobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
		}
	}

	/**
	 * 修改手机号时， 验证现有手机号的验证码。
	 *
	 */
	public Response postvalidatechangemobilecode(String oldmobile, String code) throws TException {
		if (!validateCode(oldmobile, code, 3)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		} else {
			return ResponseUtils.success(null);
		}
	}

	/**
	 * 修改手机号时， 向新手机号发送验证码。
	 *
	 */
	public Response postsendresetmobilecode(String newmobile) throws TException {
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();

		if (newmobile.length() > 0) {
			filters.put("username", newmobile);
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

		if (smsSender.sendSMS_resetmobilecode(newmobile)) {
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
		}
	}

	/**
	 * 修改当前用户手机号。
	 * 
	 * @param user_id
	 * @param newmobile
	 *            新手机号
	 * @param code
	 *            新手机号的验证码
	 */
	public Response postresetmobile(int user_id, String newmobile, String code) throws TException {
		if (code != null && !validateCode(newmobile, code, 2)) {
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
				int parentid = user.getParentid().intValue();
				if (parentid > 0) {
					// 当前帐号已经被合并到 parentid.
					query = new CommonQuery();
					filters = new HashMap<>();
					filters.put("id", String.valueOf(parentid));
					query.setEqualFilter(filters);
					UserUserRecord userParent = userdao.getResource(query);
					userParent.setMobile(Long.parseLong(newmobile));
					userParent.setUsername(newmobile);
					result = userdao.putResource(userParent);
				}
				user.setMobile(Long.parseLong(newmobile));
				user.setUsername(newmobile);

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
	 * 获取我感兴趣
	 * <p>
	 *
	 * @param userId
	 *            用户ID
	 * @param positionId
	 *            职位ID //@param favorite 0:收藏, 1:取消收藏, 2:感兴趣
	 *
	 * @return data : {true //感兴趣} {false //不感兴趣}
	 *
	 *         TODO : 不知道以后职位收藏啥逻辑, 赞不支持
	 */
	public Response getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId) throws TException {
		try {
			byte favorite = 2;
			Integer count = userFavoritePositionDao.getUserFavPositionCountByUserIdAndPositionId(userId, positionId,
					favorite);
			return ResponseUtils.success(count > 0 ? true : false);
		} catch (Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.USER_FAV_POSITION_FAILED);
		}
	}

	/**
	 * 标记我感兴趣/职位收藏/取消职位收藏
	 * <p>
	 *
	 * @param userFavoritePosition
	 *            用户职位关系实体
	 * @return 关系表主键Id
	 * @exception TException
	 */
	public Response postUserFavoritePosition(UserFavoritePosition userFavoritePosition) throws TException {
		try {
			// 必填项验证
			Response response = validateUserFavoritePosition(userFavoritePosition);
			if (response.status > 0) {
				return response;
			}

			// 添加用户感兴趣的职位
			UserFavPositionRecord userFavPositionRecord = (UserFavPositionRecord) BeanUtils
					.structToDB(userFavoritePosition, UserFavPositionRecord.class);

			int userFavoritePositionId = userFavoritePositionDao.postResource(userFavPositionRecord);
			if (userFavoritePositionId > 0) {
				Map<String, Object> hashmap = new HashMap<>();
				hashmap.put("userFavoritePositionId", userFavoritePositionId);
//				Thread t = new Thread(() -> {
//					try {
//						MessageTemplate messageTemplate = fetchMessageTemplate(userFavoritePosition.getPosition_id(), userFavoritePosition.getSysuser_id());
//						MessageTemplateNoticeStruct mtns = createMessageTemplate(messageTemplate);
//						
//						mqService.messageTemplateNotice(mtns);
//					} catch (Exception e) {
//						logger.error(e.getMessage(), e);
//					}
//				});
//				t.start();
//				MessageTemplateNoticeStruct messageTemplateNoticeStruct = new MessageTemplateNoticeStruct();
				
				return ResponseUtils.success(hashmap); // 返回
														// userFavoritePositionId
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	private MessageTemplateNoticeStruct createMessageTemplate(MessageTemplate messageTemplate) {
		if(messageTemplate != null) {
			MessageTemplateNoticeStruct message = new MessageTemplateNoticeStruct();
			StringBuffer content = new StringBuffer();
			content.append("您发布的“");
			content.append(messageTemplate.getPositionTitle()+"”");
			content.append("职位有了一位新候选人，请及时与TA联系。");
			message.setUser_id(messageTemplate.getHrAccountId());
			message.setSys_template_id(TemplateId.TEMPLATE_MESSAGE_FAV_HR.getValue());
			message.setType(UserType.PC.getValueToByte());
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			//message.setData(data);
			
		}
		return null;
	}

	private MessageTemplate fetchMessageTemplate(int positionId, int userId) {
		MessageTemplate messageTemplate = null;
		try {
			JobPositionRecord position = userFavoritePositionDao.getUserFavPositiond(positionId);
			com.moseeker.useraccounts.pojo.User user = userdao.getUserById(userId);
			if(position != null && user != null) {
				messageTemplate = new MessageTemplate();
				messageTemplate.setPositionTitle(position.getTitle());
				messageTemplate.setHrAccountId(position.getPublisher());
				if(StringUtils.isNotNullOrEmpty(user.name)) {
					messageTemplate.setName(user.name);
				} else if(StringUtils.isNotNullOrEmpty(user.nickname)) {
					messageTemplate.setName(user.nickname);
				} else {
					messageTemplate.setName(user.username);
				}
				messageTemplate.setContact(String.valueOf(user.mobile));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			
		}
		return messageTemplate;
	}

	/**
	 * 验证我感兴趣
	 * <p>
	 *
	 */
	private Response validateUserFavoritePosition(UserFavoritePosition userFavoritePosition) {

		Response response = new Response(0, "ok");
		if (userFavoritePosition.sysuser_id == 0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "user_id"));
		}

		if (userFavoritePosition.position_id == 0) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
		}
		return response;
	}

	/**
	 * 验证忘记密码的验证码是否正确
	 */
	public Response postvalidatepasswordforgotcode(String mobile, String code) throws TException {
		if (!validateCode(mobile, code, 2)) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
		} else {
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
			default :
		}
		

		return false;
	}

	public Response validateVerifyCode(String mobile, String code, int type) throws TException {
		ValidateUtil vu = new ValidateUtil();
		vu.addRequiredStringValidate("手机号码", mobile, null, null);
		vu.addRequiredStringValidate("验证码", code, null, null);
		String message = vu.validate();
		if(StringUtils.isNullOrEmpty(message)) {
			boolean flag = validateCode(mobile, code, type);
			if(flag) {
				return ResponseUtils.success(1);
			} else {
				return ResponseUtils.success(0);
			}
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", message));
		}
	}

	public Response sendVerifyCode(String mobile, int type) throws TException {
		boolean result = smsSender.sendSMS(mobile, type);
		if(result) {
			return ResponseUtils.success("success");
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
		}
	}

	public Response checkEmail(String email) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("email", email);
		qu.addEqualFilter("email_verified", "1");
		try {
			UserUserRecord record = userdao.getResource(qu);
			if(record == null) {
				return ResponseUtils.success(1);
			} else {
				return ResponseUtils.success(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		} finally {
			
		}
	}

	/**
	 * 创建微信二维码
	 */
	public Response cerateQrcode(int wechatId, long sceneId, int expireSeconds, int action_name) throws TException {
		
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(wechatId));
			HrWxWechatRecord record = wechatDao.getResource(qu);
			if(record == null) {
				return RespnoseUtil.USERACCOUNT_WECHAT_NOTEXISTS.toResponse();
			} else {
				String accessToken = record.getAccessToken();
				if(StringUtils.isNotNullOrEmpty(accessToken)) {
					WeixinTicketBean bean = AccountMng.createTicket(accessToken, expireSeconds, QrcodeType.fromInt(action_name), sceneId, null);
					if(bean != null) {
						return RespnoseUtil.SUCCESS.toResponse(bean);
					} else {
						return RespnoseUtil.USERACCOUNT_WECHAT_GETQRCODE_FAILED.toResponse();
					}
				} else {
					return RespnoseUtil.USERACCOUNT_WECHAT_ACCESSTOKEN_NOTEXISTS.toResponse();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return RespnoseUtil.PROGRAM_EXCEPTION.setMessage(e.getMessage()).toResponse();
		} finally {
			//do nothing
		}
	}

	/**
	 * 利用票据获取二维码数据
	 */
	public Response getQrcode(String ticket) throws TException {
		if(StringUtils.isNullOrEmpty(ticket)) {
			return RespnoseUtil.USERACCOUNT_WECHAT_TICKET_NOTEXISTS.toResponse();
		} else {
			String qrcode = AccountMng.getQrcode(ticket);
			return RespnoseUtil.SUCCESS.toResponse(qrcode);
		}
	}

	/**
	 * 获取扫码信息
	 * @param wechatId 公众号信息
	 * @param sceneId 场景编号
	 * @return
	 * @throws TException
	 */
	public Response getScanResult(int wechatId, long sceneId) throws TException {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		String result = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.WEIXIN_SCANRESULT.toString(), String.valueOf(wechatId), String.valueOf(sceneId));
		if(StringUtils.isNotNullOrEmpty(result)) {
			redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.WEIXIN_SCANRESULT.toString(), String.valueOf(wechatId), String.valueOf(sceneId));
			JSONObject json = JSON.parseObject(result);
			if(json.get("status") != null && (Integer)json.get("status") ==0) {
				return RespnoseUtil.SUCCESS.toResponse(json.get("data"));
			} else {
				return RespnoseUtil.USERACCOUNT_WECHAT_SCAN_ERROR.setMessage((String)json.get("message")).toResponse(json.get("data"));
			}
		} else {
			return RespnoseUtil.SUCCESS.toResponse(2);
		}
	}

	/**
	 * 设置用户扫码信息
	 * @param wechatId 公众号编号
	 * @param sceneId 场景编号
	 * @param value 扫码结果
	 * @return
	 * @throws TException
	 */
	public Response setScanResult(int wechatId, long sceneId, String value) throws TException {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.WEIXIN_SCANRESULT.toString(), String.valueOf(wechatId), String.valueOf(sceneId), value);
		return RespnoseUtil.SUCCESS.toResponse();
	}
	
	public User ifExistUser(String mobile) {
		User user = new User();
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("mobile", mobile);
		qu.addEqualFilter("source", String.valueOf(UserSource.RETRIEVE_PROFILE.getValue()));
		try {
			user = userDao.getUser(qu);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return user;
	}

	public int createRetrieveProfileUser(User user) {
		if(user.getMobile() == 0) {
			return 0;
		}
		user.setSource((byte)UserSource.RETRIEVE_PROFILE.getValue());
		try {
			user = userDao.saveUser(user);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return (int)user.getId();
	}

	public boolean ifExistProfile(String mobile) {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("username", mobile);
		try {
			User user = userDao.getUser(qu);
			if(user == null || user.getId() == 0) {
				QueryUtil autoCreate = new QueryUtil();
				autoCreate.addEqualFilter("mobile", mobile);
				autoCreate.addEqualFilter("source", String.valueOf(UserSource.RETRIEVE_PROFILE.getValue()));
				user = userDao.getUser(autoCreate);
			}
			if(user != null && user.getId() > 0) {
				ProfileProfileRecord record = profileDao.getProfileByUserId((int)user.getId());
				if(record != null) {
					return true;
				}
			}
		} catch (TException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return false;
	}

	public BaseDao<UserWxUserRecord> getWxuserdao() {
		return wxuserdao;
	}

	public void setWxuserdao(BaseDao<UserWxUserRecord> wxuserdao) {
		this.wxuserdao = wxuserdao;
	}

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	public UsersettingDao getUserSettingDao() {
		return userSettingDao;
	}

	public void setUserSettingDao(UsersettingDao userSettingDao) {
		this.userSettingDao = userSettingDao;
	}

	public UserFavoritePositionDao getUserFavoritePositionDao() {
		return userFavoritePositionDao;
	}

	public void setUserFavoritePositionDao(UserFavoritePositionDao userFavoritePositionDao) {
		this.userFavoritePositionDao = userFavoritePositionDao;
	}

	public WechatDao getWechatDao() {
		return wechatDao;
	}

	public void setWechatDao(WechatDao wechatDao) {
		this.wechatDao = wechatDao;
	}
}