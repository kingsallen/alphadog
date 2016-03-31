package com.moseeker.common.redis;

import java.util.List;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.redis.cache.db.DbManager;
import com.moseeker.common.util.StringUtils;

public abstract class RedisClient {

	protected JedisCluster redisCluster;
	protected String redisConfigKeyName;
	protected int redisConfigTimeOut;
	protected byte redisConfigType;
	
	protected abstract JedisCluster initRedisCluster();
	
	/**
	 * 一次性加载配置信息到缓存中，并返回配置信息集合
	 * 
	 * @return List<CacheConfigRedisKey> {@see
	 *         com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	protected List<RedisConfigRedisKey> reloadRedisKey() {
		List<RedisConfigRedisKey> redisKeys = DbManager.readAllConfigFromDB(redisConfigType);
		if (redisKeys != null && redisKeys.size() > 0) {
			for (RedisConfigRedisKey redisKey : redisKeys) {
				String keyword = redisConfigKeyName + "_" + redisKey.getAppId()
						+ redisKey.getKeyIdentifier();
				if (redisCluster.exists(keyword)) {
					redisCluster.setex(keyword, redisConfigTimeOut,
							JSON.toJSONString(redisKey));
				}
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
	protected RedisConfigRedisKey readRedisKey(int appId, String keyIdentifier)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = null;
		String appIdKeyIdentifier = redisConfigKeyName+"_"+appId + keyIdentifier;
		String redisValue = redisCluster.get(appIdKeyIdentifier);
		if (StringUtils.isNullOrEmpty(redisValue)) {
			redisKey = DbManager.readFromDB(appId, keyIdentifier, redisConfigType);
			if (!StringUtils.isNullOrEmpty(redisValue)) {
				redisCluster.setex(appIdKeyIdentifier, redisConfigTimeOut,
						JSON.toJSONString(redisKey));
			} else {
				throw new CacheConfigNotExistException();
			}
		} else {
			redisKey = JSON.parseObject(redisValue, RedisConfigRedisKey.class);
		}
		return redisKey;
	}

	public String set(int appId, String key_identifier, String str, String value)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
	}

	public String get(int appId, String key_identifier, String str)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		return redisCluster.get(cacheKey);
	}

	public String get(int appId, String key_identifier, String str,
			RedisCallback callback) throws CacheConfigNotExistException {
		String result = null;
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		if(redisCluster.exists(cacheKey)) {
			result =  redisCluster.get(cacheKey);
		} else {
			result = callback.call();
			redisCluster.setex(cacheKey, redisKey.getTtl(), result);
		}
		return result;
	}

	public String set(int appId, String key_identifier, String str1,
			String str2, String value) throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
	}

	public String get(int appId, String key_identifier, String str1, String str2)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		return redisCluster.get(cacheKey);
	}

	public String get(int appId, String key_identifier, String str1,
			String str2, RedisCallback callback) throws CacheConfigNotExistException {
		String result = null;
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		if(redisCluster.exists(cacheKey)) {
			result = redisCluster.get(cacheKey);
		} else {
			result = callback.call();
			redisCluster.setex(cacheKey, redisKey.getTtl(), result);
		}
		return result;
	}

	public Long lpush(int appId, String key_identifier, String newvalue)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		return redisCluster.lpush(cacheKey, newvalue);
	}

	public String rpoplpush(int appId, String key_identifier_pop,
			String key_identifier_push) throws CacheConfigNotExistException {
		RedisConfigRedisKey cfg_rpop = readRedisKey(appId, key_identifier_pop);
		RedisConfigRedisKey cfg_lpush = readRedisKey(appId, key_identifier_push);
		String cacheKey_rpop = cfg_rpop.getPattern();
		String cacheKey_lpush = cfg_lpush.getPattern();
		return redisCluster.rpoplpush(cacheKey_rpop, cacheKey_lpush);
	}

	public String rpop(int appId, String key_identifier) throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		return redisCluster.rpop(cacheKey);
	}
}
