package com.moseeker.warn.service;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
=======
>>>>>>> f3d910d733004ccf89ae29a23631cd0a3fb2f2df
import com.moseeker.warn.dto.Event;
import com.moseeker.warn.dto.WarnMsg;
import com.moseeker.warn.utils.SendChannel;

@Service
public class ManageService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
<<<<<<< HEAD
	/**
	 * 从redis发送队列中获取信息
	 * @return
	 */
	private String fetchConstantlyMessage() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		return redisClient.brpop(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.NEW_WARNING_REDIS_KEY.toString()).get(1);
=======
	public String environment;
	
	@PostConstruct
	public void init(){
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("warn.properties"));
			environment = properties.getProperty("environment");
		} catch (IOException e) {
			log.error("load warn.properties error:", e);
		}
>>>>>>> f3d910d733004ccf89ae29a23631cd0a3fb2f2df
	}
	
	/**
	 * 发送信息
	 */
	public void sendMessage(Event event, WarnMsg msg) {
		msg.setEnvironment(environment);
		// 匹配通知渠道
		event.getNotifyChannels().forEach(channel -> {
			SendChannel sendChannel;
			try {
				sendChannel = SendChannel.valueOf(channel.toUpperCase());
				sendChannel.send(event.getMembers(), msg.toString());
			} catch (IllegalArgumentException e) {
				log.error("not found the sendChannel:{0}", channel);
			}
		});
	}

}
