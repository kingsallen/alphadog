package com.moseeker.common.redis.log;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.Notification;
import com.moseeker.common.util.StringUtils;

/**
 * 
 * log帮助类 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 31, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class LogClient extends RedisClient {

	private static volatile LogClient instance = null;
	
	private LogClient() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		redisConfigKeyName = propertiesUtils.get("redis.log.config_key_name",
				String.class);
		redisConfigTimeOut = propertiesUtils.get("redis.log.config_timeout",
				Integer.class);
		redisConfigType = Constant.logConfigType;
		redisCluster = initRedisCluster();
		reloadRedisKey();
	}

	public static LogClient getInstance() {
		if (instance == null) {
			synchronized (LogClient.class) {
				if (instance == null) {
					instance = new LogClient();
				}
			}
		}
		return instance;
	}

	@Override
	protected JedisCluster initRedisCluster() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		if (redisCluster == null) {
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			// Jedis Cluster will attempt to discover cluster nodes
			String host = propertiesUtils.get("redis.log.host", String.class);
			String port = propertiesUtils.get("redis.log.port", String.class);
			if (!StringUtils.isNullOrEmpty(host)
					&& !StringUtils.isNullOrEmpty(port)) {
				String[] hostArray = host.split(",");
				String[] portArray = port.split(",");
				if (hostArray.length == portArray.length) {
					for (int i = 0; i < hostArray.length; i++) {
						jedisClusterNodes.add(new HostAndPort(hostArray[i],
								Integer.parseInt(portArray[i])));
					}
				}
			} else {
				Notification.sendLostRedisWarning(host, port);		//报警
			}
			redisCluster = new JedisCluster(jedisClusterNodes);
		}
		return redisCluster;
	}
}
