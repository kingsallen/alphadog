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
		return false;//StringUtils.isNotNullOrEmpty(mobileUser.getUnionid());
	}

	@Override
	protected void combineAccount(int appid, UserUserRecord userMobile, UserUserRecord userUnionid) {
		try {
			// unnionid置为子账号
			userUnionid.setParentid(userMobile.getId());
			/* 完善unionid */
			if (StringUtils.isNullOrEmpty(userMobile.getUnionid())
					&& StringUtils.isNotNullOrEmpty(userUnionid.getUnionid())) {
				userMobile.setUnionid(userUnionid.getUnionid());
			}
			userUnionid.setUnionid("");
			if (userdao.updateRecord(userUnionid) > 0) {
				wxUserDao.combineWxUser(userMobile.getId(), userUnionid.getId());
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
			userdao.updateRecord(userMobile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
