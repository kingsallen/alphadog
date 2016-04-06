package com.moseeker.common.redis.cache;

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
 * 缓存客户端帮助类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Mar 30, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version Beta
 */
public class CacheClient extends RedisClient {
	
	private static volatile CacheClient instance = null;

	public CacheClient() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		redisConfigKeyName = propertiesUtils.get("redis.cache.config_key_name",
				String.class);
		redisConfigTimeOut = propertiesUtils.get("redis.cache.config_timeout",
				Integer.class);
		redisConfigType = Constant.cacheConfigType;
		redisCluster = initRedisCluster();
		reloadRedisKey();
	}

	public static CacheClient getInstance() {
		if (instance == null) {
			synchronized (CacheClient.class) {
				if (instance == null) {
					instance = new CacheClient();
				}
			}
		}
		return instance;
	}

	protected JedisCluster initRedisCluster() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		if (redisCluster == null) {
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			// Jedis Cluster will attempt to discover cluster nodes
			String host = propertiesUtils.get("redis.cache.host", String.class);
			String port = propertiesUtils.get("redis.cache.port", String.class);
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
