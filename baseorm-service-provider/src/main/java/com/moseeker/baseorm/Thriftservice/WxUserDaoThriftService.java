package com.moseeker.baseorm.Thriftservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.userdb.WxUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.WxUserDao.Iface;

@Service
public class WxUserDaoThriftService implements Iface {
	
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxUserDao dao;
	
	@Override
	public Response getResource(CommonQuery query) throws TException {
		try {
			UserWxUserRecord record = dao.getResource(query);
			if (record != null) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("id", record.getId().longValue());
				result.put("wechat_id", record.getWechatId().intValue());
				result.put("group_id", record.getGroupId().intValue());
				result.put("sysuser_id", record.getSysuserId().intValue());
				result.put("is_subscribe", record.getIsSubscribe().byteValue());
				result.put("openid", record.getOpenid());
				result.put("nickname", record.getNickname());
				result.put("sex", record.getSex().intValue());
				result.put("city", record.getCity());
				result.put("country", record.getCountry());
				result.put("province", record.getProvince());
				result.put("language", record.getLanguage());
				result.put("headimgurl", record.getHeadimgurl());
				result.put("subscribe_time", record.getSubscribeTime());
				result.put("unsubscibe_time", record.getUnsubscibeTime());
				result.put("unionid", record.getUnionid());
				result.put("reward", record.getReward().intValue());
				result.put("auto_sync_info", record.getAutoSyncInfo().byteValue());
				result.put("create_time", record.getCreateTime());
				result.put("update_time", record.getUpdateTime());
				result.put("source", record.getSource().byteValue());
				return ResponseUtils.success(result);
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
}
