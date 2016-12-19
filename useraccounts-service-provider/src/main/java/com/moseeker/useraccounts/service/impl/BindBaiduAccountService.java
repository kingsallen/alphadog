package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.asm.Type;
import com.google.common.base.FinalizablePhantomReference;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.userdb.tables.records.UserBdUserRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.useraccounts.struct.BindType;
import com.moseeker.useraccounts.dao.impl.UserBdUserDaoImpl;
import com.moseeker.useraccounts.service.BindOnAccountService;

/**
 * 百度账号绑定
 * @author ltf
 *
 * 2016年12月14日
 */
@Service("baidu")
public class BindBaiduAccountService extends BindOnAccountService{
	
	private static final Logger logger = LoggerFactory.getLogger(BindBaiduAccountService.class);
	
	@Autowired
	private UserBdUserDaoImpl bduserDao;
	
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
	protected boolean volidationBind(UserUserRecord user) {
		QueryUtil queryUtil = new QueryUtil();
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", String.valueOf(user.getId()));
		UserBdUserRecord resource = null;
		try {
			resource = bduserDao.getResource(queryUtil);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resource != null || StringUtils.isNotNullOrEmpty(user.getUnionid());
	}
}
