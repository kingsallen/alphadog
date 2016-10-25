package com.moseeker.warn.service;

import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Constant;

public class ManageService {
	
	/**
	 * 从redis发送队列中获取信息
	 * @return
	 */
	private String fetchConstantlyMessage() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		return redisClient.brpop(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_WARNING).get(1);
	}
	
	/**
	 * 发送信息
	 */
	public void sendMessage() {
		String redisMsg = fetchConstantlyMessage();
		
	}
	
}
