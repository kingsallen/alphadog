package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.dao.userdb.UserBdUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserBdUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.query.Query;
import com.moseeker.useraccounts.service.BindOnAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度账号绑定
 * @author ltf
 *
 * 2016年12月14日
 */
@Service("baidu")
@CounterIface
public class BindBaiduAccountService extends BindOnAccountService{
	
	private static final Logger logger = LoggerFactory.getLogger(BindBaiduAccountService.class);
	
	@Autowired
    private UserBdUserDao bduserDao;
	
	@Override
	protected UserUserRecord getUserByUnionId(String id) {
        Query.QueryBuilder query = new Query.QueryBuilder();
		query.where("id", id);
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
		// 合并业务代码  最后通过消息队列交给独立的服务处理
		taskPool.execute(() -> {
			try {
				userdao.combineAccountBd(orig, dest);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		});
	}
	
	@Override
	protected boolean volidationBind(UserUserRecord mobileUser, UserUserRecord idUser) throws Exception {
		Query.QueryBuilder queryUtil = new Query.QueryBuilder();
		Map<String, String> map = new HashMap<>();
		queryUtil.where("user_id", String.valueOf(mobileUser.getId()));
		UserBdUserRecord bdMbUser = bduserDao.getRecord(queryUtil.buildQuery());
		if (bdMbUser != null) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void combineAccount(int appid, UserUserRecord userMobile, UserUserRecord userUnionid) {
		try {
			// unnionid置为子账号
			userUnionid.setParentid(userMobile.getId());
			if (userdao.updateRecord(userUnionid) > 0) {
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
		
	}
}