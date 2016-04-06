package com.moseeker.common.redis.log;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;

/**
 * 
 * 用于让log4j支持rediscluster方式存储日志 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 31, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class RedisClusterAppender extends AppenderSkeleton {
	
	private int appid = 0;
	private String key = "LOG";

	// Redis connection and messages buffer
	private RedisClient redisCluster;
	private Map<String, String> messages;

	public void activateOptions() {
		super.activateOptions();

		if (redisCluster == null) {
			redisCluster = RedisClientFactory.getLogClient();
		}
		messages = new ConcurrentHashMap<String, String>();

		Entry<String, String> message;

		for (Iterator<Entry<String, String>> it = messages.entrySet()
				.iterator(); it.hasNext();) {
			message = it.next();
			it.remove();
			redisCluster.set(appid, key, message.getKey(), message.getValue());
		}
	}

	protected void append(LoggingEvent event) {
		try {
			messages.put(this.layout.format(event), event.getRenderedMessage());
		} catch (Exception e) {
			// what to do? ignore? send back error - from log???
		}
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return true;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
