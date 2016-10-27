package com.moseeker.warn.service;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Constant;
import com.moseeker.warn.dto.Event;

public class ManageService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 从redis发送队列中获取信息
	 * @return
	 */
	private String fetchConstantlyMessage() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		return redisClient.brpop(Constant.APPID_ALPHADOG, "NEW_WARNING_REDIS_KEY").get(1);
	}
	
	/**
	 * 发送信息
	 */
	public void sendMessage() {
		String redisMsg = fetchConstantlyMessage();
		JSONObject jsonObject = JSONObject.parseObject(redisMsg);
		Event event = JSONObject.toJavaObject(jsonObject.getJSONObject("config"), Event.class);
		String location = jsonObject.getString("location");
		// 匹配通知渠道
		event.getNotifyChannels().forEach(channel -> {
			SendChannel sendChannel;
			try {
				sendChannel = SendChannel.valueOf(channel.toUpperCase());
				sendChannel.send(event.getMembers(), MessageFormat.format("出错位置:{0}, 错误描述:{1}", location, event.getEventDesc()));
			} catch (IllegalArgumentException e) {
				log.error("not found the sendChannel:{0}", channel);
			}
		});
	}

}
