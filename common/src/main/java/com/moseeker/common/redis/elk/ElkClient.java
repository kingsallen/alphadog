package com.moseeker.common.redis.elk;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.log.LogClient;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Notification;
import com.moseeker.common.util.StringUtils;

/**
 * @author ltf
 * log-elk 客户端
 * 2016年12月20日
 */
public class ElkClient extends RedisClient {
	
	private static volatile ElkClient instance = null;

	private ElkClient() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
		redisConfigKeyName = propertiesUtils.get("redis.elk.config_key_name", String.class);
		redisConfigTimeOut = propertiesUtils.get("redis.elk.config_timeout", Integer.class);
		redisConfigType = Constant.logConfigType;
		redisCluster = initRedisCluster();
		reloadRedisKey();
	}

	public static ElkClient getInstance() {
		if (instance == null) {
			synchronized (LogClient.class) {
				if (instance == null) {
					instance = new ElkClient();
				}
			}
		}
		return instance;
	}

	@Override
	protected JedisCluster initRedisCluster() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
		if (redisCluster == null) {
			try {
				Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
				// Jedis Cluster will attempt to discover cluster nodes
				String host = propertiesUtils.get("redis.elk.host", String.class);
				String port = propertiesUtils.get("redis.elk.port", String.class);
				if (!StringUtils.isNullOrEmpty(host) && !StringUtils.isNullOrEmpty(port)) {
					String[] hostArray = host.split(",");
					String[] portArray = port.split(",");
					if (hostArray.length == portArray.length) {
						for (int i = 0; i < hostArray.length; i++) {
							jedisClusterNodes.add(new HostAndPort(hostArray[i], Integer.parseInt(portArray[i])));
						}
					}
				} else {
					Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID,
							Constant.REDIS_CONNECT_ERROR_EVENTKEY, "Redis集群redis.log.host,redis.log.port尚未配置");
				}
				redisCluster = new JedisCluster(jedisClusterNodes);
			} catch (Exception e) {
				e.printStackTrace();
				Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY,
						e.getMessage());

			}
		}
		return redisCluster;
	}
}
