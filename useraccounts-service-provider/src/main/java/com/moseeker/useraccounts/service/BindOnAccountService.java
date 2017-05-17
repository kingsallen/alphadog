package com.moseeker.useraccounts.service;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.WxUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ltf
 * 账号绑定--模板方法
 * 2016年12月14日
 */
@Service
public abstract class BindOnAccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(BindOnAccountService.class);
	
	protected ExecutorService taskPool = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	
	@Autowired
	protected UserUserDao userdao;

	@Autowired
	protected ProfileProfileDao profileDao;

	@Autowired
	protected UserSettingsDao userSettingDao;
	
	@Autowired
	protected WxUserDao wxUserDao;
	
	/**
	 * 账号绑定操作
	 * @param appid
	 * @param unionid
	 * @param mobile
	 */
	public Response handler(int appid, String unionid, String mobile) {
		try {
			
			UserUserRecord userUnionid = getUserByUnionId(unionid);

            Query.QueryBuilder query = new Query.QueryBuilder();
			Map<String, String> filters = new HashMap<>();
			query.where("username", mobile);
			UserUserRecord userMobile = userdao.getRecord(query.buildQuery());

			if (userUnionid == null && userMobile == null) {
				// post, 都为空的情况, 需要事先调用 user_
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
			} else if (userUnionid != null && userMobile != null
					&& userUnionid.getId().intValue() == userMobile.getId().intValue()) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_NONEED);
			} else if (userUnionid != null && userMobile == null) {
				userUnionid.setMobile(Long.valueOf(mobile));
				userUnionid.setUsername(mobile);
				if (userdao.updateRecord(userUnionid) > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					resultFull(userUnionid, map);
					return ResponseUtils.success(map);
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
				}
			} else if (userUnionid == null && userMobile != null) {
				if(volidationBind(userMobile, userUnionid)) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_REPEATBIND);
				}
				completeUserMobile(userMobile, unionid);
				Map<String, Object> map = new HashMap<String, Object>();
				resultFull(userMobile, map);
				return ResponseUtils.success(map);
			} else if (userUnionid != null && userMobile != null
					&& userUnionid.getId().intValue() != userMobile.getId().intValue()) {
				// 2 accounts, one unoinid, one mobile, need to merge.
				if (volidationBind(userMobile, userUnionid)) {
					return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_BIND_REPEATBIND);
				}
				combineAccount(appid, userMobile, userUnionid);
				// 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录, 7:PC(正常添加) 8:PC(我要投递) 9:
				// PC(我感兴趣)
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dest_id", userUnionid.getId().intValue());
				map.put("origin_id", userMobile.getId().intValue());
				resultFull(userMobile, map);
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

	private void resultFull(UserUserRecord userUnionid, Map<String, Object> map) {
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
	}
	
	
	/**
	 * 账号合并
	 * @param appid
	 * @param userMobile
	 * @param userUnionid
	 */
	protected abstract void combineAccount(int appid, UserUserRecord userMobile, UserUserRecord userUnionid);
	
	/**
	 * 账号合并完善账号信息
	 * 
	 * @param userMobile
	 *            需要完善的账号
	 * @param userUnionid
	 *            信息来源
	 */
	protected void consummateUserAccount(UserUserRecord userMobile, UserUserRecord userUnionid) {
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
			userdao.updateRecord(userMobile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据第三方标示获取账号信息
	 * @param unionId 
	 * @return
	 */
	protected abstract UserUserRecord getUserByUnionId(String unionId);
	
	/**
	 * 数据迁移操作
	 * @param orig
	 * @param dest
	 */
	protected abstract void doSomthing(int orig, int dest);
	
	/**
	 * 判断是否绑定过第三方账号
	 */
	protected abstract boolean volidationBind(UserUserRecord mobileUser, UserUserRecord idUser) throws Exception;
	
	protected abstract void completeUserMobile(UserUserRecord userMobile, String unionid);
}
