package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.useraccounts.service.BindOnAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 微信账号绑定
 * @author ltf
 *
 * 2016年12月14日
 */
@Service("wechat")
@CounterIface
public class BindWxAccountService extends BindOnAccountService{
	
	private static final Logger logger = LoggerFactory.getLogger(BindWxAccountService.class);
	
	@Override
	protected UserUserRecord getUserByUnionId(String unionId) {
        Query.QueryBuilder query = new Query.QueryBuilder();
		query.where("unionid", unionId);
		UserUserRecord userUnionid = null;
		try {
			userUnionid = userdao.getRecord(query.buildQuery());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return userUnionid;
	}

	@Override
	protected void doSomthing(int orig, int dest) {
		// 合并业务代码 最后通过消息队列交给独立的服务处理
		taskPool.execute(() -> {
			try {
				userdao.combineAccount(orig, dest);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		});
	}
	
	@Override
	protected boolean volidationBind(UserUserRecord mobileUser, UserUserRecord idUser) {
		return StringUtils.isNotNullOrEmpty(mobileUser.getUnionid());
	}

	@Override
	protected void combineAccount(int appid, UserUserRecord userMobile, UserUserRecord userUnionid) {
		try {
			logger.info("BindOnAccountService combineAccount appid:{}, userMobile:{}, userUnionid:{}", appid, userMobile, userUnionid);
			// unnionid置为子账号
			userUnionid.setParentid(userMobile.getId());
			/* 完善unionid */
			String unionId = userUnionid.getUnionid();
			userUnionid.setUnionid("");
			if (userdao.updateRecord(userUnionid) > 0) {
				wxUserDao.combineWxUser(userMobile.getId(), userUnionid.getId());
				logger.info("BindOnAccountService combineAccount change wxuserwx from userMobile.getId() to userUnionid.getId()");
				// 如果手机用户之前绑定过微信，需要把微信unionid对应的user_wx_user的sysuser_id都置为0
				if(StringUtils.isNotNullOrEmpty(userMobile.getUnionid())) {
					wxUserDao.invalidOldWxUser(userMobile.getUnionid());
					logger.info("BindOnAccountService combineAccount set wx_user_wx.sysuser_id = 0 where unionid={}", userMobile.getUnionid());
				}
				userMobile.setUnionid(unionId);
				consummateUserAccount(userMobile, userUnionid);
			}
			doSomthing(userMobile.getId().intValue(), userUnionid.getId().intValue());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	protected void completeUserMobile(UserUserRecord userMobile, String unionid) {
		// TODO Auto-generated method stub
		userMobile.setUnionid(unionid);
		try {
			invalidOldWxUser(userMobile.getUnionid());
			userdao.updateRecord(userMobile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 如果手机用户之前绑定过微信，需要把微信unionid对应的user_wx_user的sysuser_id都置为0
	 * @param unionid
	 */
	private void invalidOldWxUser(String unionid){
		if(StringUtils.isNotNullOrEmpty(unionid)) {
			wxUserDao.invalidOldWxUser(unionid);
		}
	}
}
