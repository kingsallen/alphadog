package com.moseeker.common.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.moseeker.common.cache.lru.CacheConfigRedisKey;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;

/**
 * 
 * Redis客户端帮助类
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
 * @version
 */
public class RedisClient {
	private JedisCluster redisCluster;
	private static volatile RedisClient instance = null;

	private RedisClient() {
		redisCluster = initRedisCluster();
		reloadRedisKey();
	}

	public static RedisClient getInstance() {
		if (instance == null) {
			synchronized (RedisClient.class) {
				if (instance == null) {
					instance = new RedisClient();
				}
			}
		}
		return instance;
	}

	private JedisCluster initRedisCluster() {
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		if (redisCluster == null) {
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			// Jedis Cluster will attempt to discover cluster nodes
			String host = propertiesUtils.get("cashe_host", String.class);
			String port = propertiesUtils.get("cashe_port", String.class);
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

	/**
	 * 一次性加载配置信息到缓存中，并返回配置信息集合
	 * 
	 * @return List<CacheConfigRedisKey> {@see
	 *         com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	private List<CacheConfigRedisKey> reloadRedisKey() {
		List<CacheConfigRedisKey> redisKeys = DbManager.readAllConfigFromDB();
		if (redisKeys != null && redisKeys.size() > 0) {
			for (CacheConfigRedisKey redisKey : redisKeys) {

				redisCluster.set(
						redisKey.getAppId() + redisKey.getKeyIdentifier(),
						JSON.toJSONString(redisKey));
			}
		}
		return redisKeys;
	}

	/**
	 * 根据项目编号和标识符查找缓存配置。先查找缓存中是否存在，如果不存在则查找数据库，并更新缓存
	 * 
	 * @param appId
	 *            项目编号
	 * @param keyIdentifier
	 *            标识符
	 * @return CacheConfigRedisKey {@see
	 *         com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	private CacheConfigRedisKey readRedisKey(int appId, String keyIdentifier) {
		CacheConfigRedisKey redisKey = null;
		String appIdKeyIdentifier = appId + keyIdentifier;
		String redisValue = redisCluster.get(appIdKeyIdentifier);
		if (StringUtils.isNullOrEmpty(redisValue)) {
			redisKey = DbManager.readFromDB(appId, keyIdentifier);
			if (!StringUtils.isNullOrEmpty(redisValue)) {
				redisCluster.setex(appIdKeyIdentifier, 60 * 60,
						JSON.toJSONString(redisKey));
			}
		} else {
			redisKey = JSON.parseObject(redisValue, CacheConfigRedisKey.class);
		}
		return redisKey;
	}

	public String set(int appId, String key_identifier, String str, String value) {
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
	}

	public String get(int appId, String key_identifier, String str)
			throws Exception {
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		return redisCluster.get(cacheKey);
	}

	public String getWith(int appId, String key_identifier, String str,
			RedisCallback callback) throws Exception {
		String result = "";
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		if (!StringUtils.isNullOrEmpty(cacheKey)) {
			result = redisCluster.get(cacheKey);
		}
		if (!StringUtils.isNullOrEmpty(result)) {
			result = callback.call();
			if (!StringUtils.isNullOrEmpty(result)) {
				redisCluster.setex(cacheKey, redisKey.getTtl(), result);
			}
		}
		return result;
	}

	public String set(int appId, String key_identifier, String str1,
			String str2, String value) throws Exception {
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
	}

	public String get(int appId, String key_identifier, String str1, String str2)
			throws Exception {
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		return redisCluster.get(cacheKey);
	}

	public String getWith(int appId, String key_identifier, String str1,
			String str2, RedisCallback callback) throws Exception {
		String result = "";
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		if (!StringUtils.isNullOrEmpty(cacheKey)) {
			result = redisCluster.get(cacheKey);
		}
		if (!StringUtils.isNullOrEmpty(result)) {
			result = callback.call();
			if (!StringUtils.isNullOrEmpty(result)) {
				redisCluster.setex(cacheKey, redisKey.getTtl(), result);
			}
		}
		return result;
	}

	public Long lpush(int appId, String key_identifier, String newvalue)
			throws Exception {
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		return redisCluster.lpush(cacheKey, newvalue);
	}

	public String rpoplpush(int appId, String key_identifier_pop,
			String key_identifier_push) throws Exception {
		CacheConfigRedisKey cfg_rpop = readRedisKey(appId, key_identifier_pop);
		CacheConfigRedisKey cfg_lpush = readRedisKey(appId, key_identifier_push);
		String cacheKey_rpop = cfg_rpop.getPattern();
		String cacheKey_lpush = cfg_lpush.getPattern();
		return redisCluster.rpoplpush(cacheKey_rpop, cacheKey_lpush);
	}

	public String rpop(int appId, String key_identifier) throws Exception {
		CacheConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		return redisCluster.rpop(cacheKey);
	}
}
