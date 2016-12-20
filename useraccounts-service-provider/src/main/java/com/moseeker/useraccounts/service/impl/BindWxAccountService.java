package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.StringUtils;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.useraccounts.service.BindOnAccountService;

/**
 * 微信账号绑定
 * @author ltf
 *
 * 2016年12月14日
 */
@Service("wechat")
public class BindWxAccountService extends BindOnAccountService{
	
	private static final Logger logger = LoggerFactory.getLogger(BindWxAccountService.class);
	
	@Override
	protected UserUserRecord getUserByUnionId(String unionId) {
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		filters.put("unionid", unionId);
		query.setEqualFilter(filters);
		UserUserRecord userUnionid = null;
		try {
			userUnionid = userdao.getResource(query);
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
}
