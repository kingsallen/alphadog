package com.moseeker.common.redis.log;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.cache.CacheClient;
import com.moseeker.common.util.ConfigPropertiesUtil;
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
	
	public LogClient() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		redisConfigKeyName = propertiesUtils.get("logConfigKeyName",
				String.class);
		redisConfigTimeOut = propertiesUtils.get("logConfigTimeOut",
				Integer.class);
		redisConfigType = propertiesUtils.get("logConfigType", Byte.class);
		redisCluster = initRedisCluster();
		reloadRedisKey();
	}

	public static LogClient getInstance() {
		if (instance == null) {
			synchronized (CacheClient.class) {
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
			String host = propertiesUtils.get("log_redis_host", String.class);
			String port = propertiesUtils.get("log_redis_port", String.class);
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
			}
			redisCluster = new JedisCluster(jedisClusterNodes);
		}
		return redisCluster;
	}
}
