package com.moseeker.common.redis;

import java.util.List;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisConnectionException;
import com.alibaba.fastjson.JSON;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.exception.RedisClientException;
import com.moseeker.common.redis.cache.db.DbManager;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.warn.service.WarnSetService;

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
	protected List<RedisConfigRedisKey> reloadRedisKey() throws RedisClientException {
		List<RedisConfigRedisKey> redisKeys = DbManager.readAllConfigFromDB(redisConfigType);
		try {
			if (redisKeys != null && redisKeys.size() > 0) {
				for (RedisConfigRedisKey redisKey : redisKeys) {
					String keyword = redisConfigKeyName + "_" + redisKey.getAppId()
							+ redisKey.getKeyIdentifier();
					if (!redisCluster.exists(keyword)) {
						redisCluster.setex(keyword, redisConfigTimeOut,
								JSON.toJSONString(redisKey));
					}
				}
			}
		} catch (JedisConnectionException e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
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
			throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisval = null;
		try {
			String appIdKeyIdentifier = redisConfigKeyName+"_"+appId + keyIdentifier;
			String redisValue = redisCluster.get(appIdKeyIdentifier);
			if (StringUtils.isNullOrEmpty(redisValue)) {
				redisval = DbManager.readFromDB(appId, keyIdentifier, redisConfigType);
				if (redisval != null) {
					redisCluster.setex(appIdKeyIdentifier, redisConfigTimeOut,
							JSON.toJSONString(redisval));
				} else {
					//Notification.sendNotification(appId, "", "未能找到关键词数据库配置信息");
					throw new CacheConfigNotExistException();
				}
			} else {
				redisval = JSON.parseObject(redisValue, RedisConfigRedisKey.class);
			}
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
		return redisval;
	}

	/**
	 * 
	 * redisClient.setex 用来设置关键词对应的字符串，并设置在给定的时间之后超时
	 * SET mykey value
	 * EXPIRE mykey seconds
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词
	 * @param value 关键词对应的字符串
	 * @return
	 * @throws CacheConfigNotExistException
	 */
	public String set(int appId, String key_identifier, String str, String value)
			throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}

	/**
	 * 查询关键词对应的字符串
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置提示异常
	 */
	public String get(int appId, String key_identifier, String str)
			throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			return redisCluster.get(cacheKey);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}

	/**
	 * 查询关键词对应的字符串。
	 * 如果关键词不存在，则执行RedisCallback.call方法拿到字符串，存储到redis中
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词
	 * @param callback 回调方法。如果关键词不存在，则利用回调方法获取字符串，存储到redis中。
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置提示异常
	 */
	public String get(int appId, String key_identifier, String str,
			RedisCallback callback) throws CacheConfigNotExistException,RedisClientException {
		String result = null;
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			if(redisCluster.exists(cacheKey)) {
				result =  redisCluster.get(cacheKey);
			} else {
				result = callback.call();
				redisCluster.setex(cacheKey, redisKey.getTtl(), result);
			}
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
		return result;
	}

	/**
	 * 设置关键词对应的字符串
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 关键词通配符匹配的第一部分
	 * @param str2 关键词通配符匹配的第二部分
	 * @param value 字符串
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public String set(int appId, String key_identifier, String str1,
			String str2, String value) throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		try {
			return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}

	/**
	 * 设置关键词对应的字符串，并且设置超时时间
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 关键词通配符匹配的第一部分
	 * @param str2 关键词通配符匹配的第二部分
	 * @param value 字符串
	 * @param ttl 超时时间（s）
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public String set(int appId, String key_identifier, String str1,
					  String str2, String value, int ttl) throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		return redisCluster.setex(cacheKey, ttl, value);
	}

	/**
	 * 查询关键词对应的字符串
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 关键词通配符匹配的第一部分
	 * @param str2 关键词通配符匹配的第二部分
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public String get(int appId, String key_identifier, String str1, String str2)
			throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		try {
			return redisCluster.get(cacheKey);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}

	/**
	 * 查询关键词对应的字符串
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 关键词通配符匹配的第一部分
	 * @param str2 关键词通配符匹配的第二部分
	 * @param callback 关键词不存在时的回调函数，用于获取关键词对应的字符串
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public String get(int appId, String key_identifier, String str1,
			String str2, RedisCallback callback) throws CacheConfigNotExistException,RedisClientException {
		String result = null;
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		try {
			if(redisCluster.exists(cacheKey)) {
				result = redisCluster.get(cacheKey);
			} else {
				result = callback.call();
				redisCluster.setex(cacheKey, redisKey.getTtl(), result);
			}
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
		return result;
	}

	/**
	 * 往列表中添加一条字符串
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param newvalue 字符串
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public Long lpush(int appId, String key_identifier, String newvalue)
			throws CacheConfigNotExistException,RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		if(cacheKey == null) {
			throw new CacheConfigNotExistException();
		}
		try {
			return redisCluster.lpush(cacheKey, newvalue);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}
	
	/**
	 * BRPOP 是一个阻塞的列表弹出原语。 
	 * 它是 RPOP 的阻塞版本，因为这个命令会在给定list无法弹出任何元素的时候阻塞连接。 
	 * 该命令会按照给出的 key 顺序查看 list，并在找到的第一个非空 list 的尾部弹出一个元素。
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @return 当没有元素可以被弹出时返回一个 nil 的多批量值，并且 timeout 过期。 当有元素弹出时会返回一个双元素的多批量值，其中第一个元素是弹出元素的 key，第二个元素是 value。
	 */
	public List<String> brpop(int appId, String key_identifier) throws RedisClientException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		if(cacheKey == null) {
			throw new CacheConfigNotExistException();
		}
		try {
			return redisCluster.brpop(redisKey.getTtl(), cacheKey);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}
	
	/**
	 * 原子性地返回并移除存储在 source 的列表的最后一个元素（列表尾部元素）， 
	 * 并把该元素放入存储在 destination 的列表的第一个元素位置（列表头部）。
	 * 当 source 是空的时候，Redis将会阻塞这个连接，直到另一个客户端 push 元素进入或者达到 timeout 时限
	 * @param appId 调用方项目编号
	 * @param key_identifier_pop config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param key_identifier_push config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @return
	 */
	public String brpoplpush(int appId, String key_identifier_pop,
			String key_identifier_push) {
		RedisConfigRedisKey cfg_rpop = readRedisKey(appId, key_identifier_pop);
		RedisConfigRedisKey cfg_lpush = readRedisKey(appId, key_identifier_push);
		String cacheKey_rpop = cfg_rpop.getPattern();
		String cacheKey_lpush = cfg_lpush.getPattern();
		try {
			return redisCluster.brpoplpush(cacheKey_rpop, cacheKey_lpush, cfg_rpop.getTtl());
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}

	/**
	 * 原子性地返回并移除存储在 key_identifier_pop 的列表的最后一个元素（列表尾部元素）， 
	 * 并把该元素放入存储在 key_identifier_push 的列表的第一个元素位置（列表头部）。
	 * @param appId 调用方项目编号
	 * @param key_identifier_pop config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param key_identifier_push config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public String rpoplpush(int appId, String key_identifier_pop,
			String key_identifier_push) throws CacheConfigNotExistException {
		RedisConfigRedisKey cfg_rpop = readRedisKey(appId, key_identifier_pop);
		RedisConfigRedisKey cfg_lpush = readRedisKey(appId, key_identifier_push);
		String cacheKey_rpop = cfg_rpop.getPattern();
		String cacheKey_lpush = cfg_lpush.getPattern();
		try {
			return redisCluster.rpoplpush(cacheKey_rpop, cacheKey_lpush);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}
	
	/**
	 * 移除并返回存于 key 的 list 的最后一个元素。
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public String rpop(int appId, String key_identifier) throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		try {
			return redisCluster.rpop(cacheKey);
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
	}
	
	/**
	 * 如果删除的key不存在，则直接忽略。
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 替代通配符的字符串
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public Long  del(int appId, String key_identifier, String str)
			throws CacheConfigNotExistException {
		Long result = (long) 0;
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			if(redisCluster.exists(cacheKey)) {
				result =  redisCluster.del(cacheKey);
			}
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
		return result;
	}

	/**
	 * 如果删除的key不存在，则直接忽略。
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 替代第一个通配符的字符串
	 * @param str2 替代第二个通配符的字符串
	 * @return
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public Long  del(int appId, String key_identifier, String str1, String str2)
			throws CacheConfigNotExistException {
		Long result = (long) 0;
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		try {
			if(redisCluster.exists(cacheKey)) {
				result =  redisCluster.del(cacheKey);
			}
		} catch (Exception e) {
			throw new RedisClientException(e.getMessage(), Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		}
		return result;
	}

	/**
	 * 对存储在指定key的数值执行原子的加1操作。
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 替代第一个通配符的字符串
	 * @param str2 替代第二个通配符的字符串
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public void incr(int appId, String key_identifier, String str1, String str2)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		if(redisCluster.exists(cacheKey)) {
			redisCluster.incr(cacheKey);
		}
	}

	/**
	 * 对key对应的数字做减1操作 
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str1 替代第一个通配符的字符串
	 * @param str2 替代第二个通配符的字符串
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public void decr(int appId, String key_identifier, String str1, String str2)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		if(redisCluster.exists(cacheKey)) {
			redisCluster.decr(cacheKey);
		}
	}
}
