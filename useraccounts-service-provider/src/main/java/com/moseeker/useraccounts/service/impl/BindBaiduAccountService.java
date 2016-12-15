package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.useraccounts.service.BindOnAccountService;

/**
 * 百度账号绑定
 * @author ltf
 *
 * 2016年12月14日
 */
@Service("bindBaiduAccount")
public class BindBaiduAccountService extends BindOnAccountService{
	
	private static final Logger logger = LoggerFactory.getLogger(BindBaiduAccountService.class);
	
	@Override
	protected UserUserRecord getUserByUnionId(String id) {
		CommonQuery query = new CommonQuery();
		Map<String, String> filters = new HashMap<>();
		filters.put("id", id);
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
		// 合并业务代码
		// 最后通过消息队列交给独立的服务处理
		new Thread(() -> {
			try {
				userdao.combineAccountBd(orig, dest);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}).start();
	}
}
