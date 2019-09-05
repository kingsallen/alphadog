package com.moseeker.baseorm.redis;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.configdb.ConfigCacheconfigRediskeyDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.sortedset.ZAddParams;

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class RedisClient {

	protected Logger logger = LoggerFactory.getLogger(RedisClient.class);

	@Autowired
    protected ConfigCacheconfigRediskeyDao cacheconfigRediskeyDao;

	protected JedisCluster redisCluster;
	protected String redisConfigKeyName;
	protected int redisConfigTimeOut;
	protected byte redisConfigType;
	
	protected abstract JedisCluster initRedisCluster();
	
	private static final String className = RedisClient.class.getName();
	
	private static final Logger log = LoggerFactory.getLogger(RedisClient.class);
	
	/**
	 * 一次性加载配置信息到缓存中，并返回配置信息集合
	 * 
	 * @return List<CacheConfigRedisKey> {@see
	 *         com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	protected List<RedisConfigRedisKey> reloadRedisKey() throws RedisException {
		List<RedisConfigRedisKey> redisKeys = cacheconfigRediskeyDao.readAllConfigFromDB(redisConfigType);
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
		} catch (JedisException e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throws RedisException {
		RedisConfigRedisKey redisval = null;
		try {
			String appIdKeyIdentifier = redisConfigKeyName+"_"+appId + keyIdentifier;
			String redisValue = redisCluster.get(appIdKeyIdentifier);
			if (StringUtils.isNullOrEmpty(redisValue)) {
				redisval = cacheconfigRediskeyDao.readFromDB(appId, keyIdentifier, redisConfigType);
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
		} catch (CacheConfigNotExistException e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CACHE_CONFIG_NOTEXIST_ERROR_EVENTKEY);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
		} finally {
		}
	}

	/*
	设置没有过期时间的兼职对
	 */
	public String setNoTime(int appId, String key_identifier, String str,String str1, String value)
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str,str1);
		try {
			return redisCluster.set(cacheKey, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		} finally {
		}
	}
	/*
	设置没有过期时间的兼职对
	 */
	public String setNoTime(int appId, String key_identifier, String str, String value)
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			return redisCluster.set(cacheKey, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//			Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY, e.getMessage());
		} finally {
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
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		try {
			return redisCluster.get(cacheKey);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			RedisCallback callback) throws CacheConfigNotExistException,RedisException {
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
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			String str2, String value) throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		try {
			return redisCluster.setex(cacheKey, redisKey.getTtl(), value);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
					  String str2, String value, int ttl) throws CacheConfigNotExistException,RedisException {
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
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		try {
			logger.info("===========rediskey===============");
			logger.info(cacheKey);
			logger.info("====================================");
			return redisCluster.get(cacheKey);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
		} finally {
			//do nothing
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
			String str2, RedisCallback callback) throws CacheConfigNotExistException,RedisException {
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
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		try {
			if(cacheKey == null) {
				throw new CacheConfigNotExistException();
			}
			logger.info("redis lpush : key={},value={}",cacheKey,newvalue);
			return redisCluster.lpush(cacheKey, newvalue);
		} catch (CacheConfigNotExistException e) { 
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CACHE_CONFIG_NOTEXIST_ERROR_EVENTKEY);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
	public List<String> brpop(int appId, String key_identifier) throws RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = redisKey.getPattern();
		try {
			if(cacheKey == null) {
				throw new CacheConfigNotExistException();
			}
			return redisCluster.brpop(Constant.BRPOP_BLOCKING_TIMEOUT, cacheKey);
		} catch (CacheConfigNotExistException e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CACHE_CONFIG_NOTEXIST_ERROR_EVENTKEY);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			return redisCluster.brpoplpush(cacheKey_rpop, cacheKey_lpush, Constant.BRPOP_BLOCKING_TIMEOUT);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
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
	public Long incr(int appId, String key_identifier, String str1, String str2)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str1, str2);
		if(redisCluster.exists(cacheKey)) {
			return redisCluster.incr(cacheKey);
		} else {
			return null;
		}
	}

	/**
	 * 对存储在指定key的数值执行原子的加1操作。
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 替代第一个通配符的字符串
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public Long incr(int appId, String key_identifier, String str)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		if(redisCluster.exists(cacheKey)) {
			return redisCluster.incr(cacheKey);
		} else {
			return null;
		}
	}

	/**
	 * 对存储在指定key的数值执行原子的加1操作。只是不存在时会设置为1
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 替代第一个通配符的字符串
	 * @throws CacheConfigNotExistException 关键词未配置的提示异常
	 */
	public Long incrIfNotExist(int appId, String key_identifier, String str)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		return redisCluster.incr(cacheKey);
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
	
	/**
	 * 添加排序队列
	 * @param appId 调用方编号
	 * @param key_identifier 关键词
	 * @param time 排序字段
	 * @param str2 内容
	 * @throws CacheConfigNotExistException
	 */
	public void zadd(int appId, String key_identifier, long time, String str2)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		redisCluster.zadd(redisKey.getPattern(), time, str2);
	}
	
	/**
	 * 添加排序队列
	 * @param appId 调用方编号
	 * @param key_identifier 关键词
	 * @param time 排序字段
	 * @param str2 内容
	 * @param zaddParams 排序字段参数
	 * @throws CacheConfigNotExistException
	 */
	public void zaddWithZaddParams(int appId, String key_identifier, long time, String str2, ZAddParams zaddParams)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		redisCluster.zadd(redisKey.getPattern(), time, str2, zaddParams);
	}
	
	/**
	 * 查找排序队列的内容
	 * @param appId 调用方编号
	 * @param key_identifier 关键词
	 * @param min 排序字段起始位置
	 * @param max 排序字段结束位置
	 * @return 查找到的排序字段集合
	 * @throws CacheConfigNotExistException
	 */
	public Set<String> zrange(int appId, String key_identifier, Long min, Long max)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		long start = 0; 
		long end = -1;
		if(min != null && min > 0) {
			start = min;
		}
		if(max != null) {
			end = max;
		}
		return redisCluster.zrange(redisKey.getPattern(), start, end);
	}
	
	/**
	 * 查找排序队列的内容
	 * @param appId 调用方编号
	 * @param key_identifier 关键词
	 * @param min 排序字段起始位置
	 * @param max 排序字段结束位置
	 * @return
	 * @throws CacheConfigNotExistException
	 */
	public Set<String> rangeByScore(int appId, String key_identifier, Long min, Long max)
			throws CacheConfigNotExistException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		long start = 0; 
		long end = 0;
		if(min != null && min > 0) {
			start = min;
		}
		if(max != null) {
			end = max;
		}
		return redisCluster.zrangeByScore(redisKey.getPattern(), start, end);
	}
	
	/**
	 * 删除排序队列任务
	 * @param appId 调用方编号
	 * @param key_identifier 关键词
	 * @param min 排序字段起始位置
	 * @param max 排序字段结束位置
	 * @return
	 */
	public Long zRemRangeByScore(int appId, String key_identifier, Long min, Long max) {
		long start = 0; 
		long end = 0;
		if(min != null && min > 0) {
			start = min;
		}
		if(max != null) {
			end = max;
		}
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		return redisCluster.zremrangeByScore(redisKey.getPattern(), start, end);
	}

	/**
	 * 每日操作次数限制
	 * @param keyIdentifier 关键词
	 * @param pattern 匹配字段
	 * @param limit 上限
	 * @param defautValue 默认值
	 * @return 是否允许再次操作
	 * @throws CacheConfigNotExistException
	 */
	public boolean isAllowed(String keyIdentifier, String pattern, int limit, String defautValue)
			throws CacheConfigNotExistException {
		DateTime dt = new DateTime();
		int second = dt.getSecondOfDay();
		int dateTime = 60 * 60 * 24;
		if (second >= dateTime) {
			return false;
		}
		if (StringUtils.isNullOrEmpty(defautValue)) {
			defautValue = "0";
		}
		String getResult = get(0, keyIdentifier, pattern);
		if (getResult == null) {
			set(0, keyIdentifier, pattern, null, defautValue, dateTime-second);
			return true;
		} else if (Long.valueOf(getResult) < limit) {
			incr(0, keyIdentifier, pattern);
			return true;
		} else {
			logger.info("每日操作次数达到上线。关键词：{}, 上线是：{}", pattern, limit);
			return false;
		}
	}

	/**
	 *	验证redis中是否存在key
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词,可变参数
	 * @return true:存在，false：不存在
	 */
	public boolean exists(int appId, String key_identifier, String...str){
		String cacheKey = genCacheKey(appId,key_identifier,str);
		return exists(cacheKey);
	}

	/**
	 * 只验证key
	 * @param cacheKey 需要验证的key
	 * @return	true：存在，false：不存在
	 */
	public boolean exists(String cacheKey){
		try {
			return redisCluster.exists(cacheKey);
		} catch (Exception e) {
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
		}
	}

	/**
	 *	根据传入的参数生成redis的key
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词,可变参数
	 * @return
	 */
	public String genCacheKey(int appId, String key_identifier, String...str){
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		return String.format(redisKey.getPattern(), str);
	}


	/**
	 * 长时间验证redis是否存在key,超时时长默认4分钟，240000毫秒
	 * @param key	验证的key
	 * @return
	 * @throws ConnectException
	 */
	public boolean existWithTimeOutCheck(String key) throws ConnectException {
		return existWithTimeOutCheck(key,Constant.BIND_GET_REDIS_TIMEOUT);
	}

	/**
	 * 长时间验证redis是否存在key
	 * @param key	验证的key
	 * @param time	超时时长，毫秒单位
	 * @return
	 * @throws ConnectException
	 */
	public boolean existWithTimeOutCheck(String key,long time) throws ConnectException{
		logger.info("尝试循环验证redis中是否存在:"+key);
		int tick=300;
		int count=0;

		while (!exists(key)){
			try {
				Thread.sleep(tick);
			}catch (InterruptedException e2){

			}finally {
				count++;
			}
			if(count*tick>time){
				logger.info("Redis验证:"+key+"超时");
				throw new ConnectException();
			}
		}
		logger.info("Redis中存在:"+key);
		return true;
	}

	/**
	 * 设置超时时间
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词
	 * @param seconds
	 * @return 更新涉及的列数
	 */
	public long expire(int appId, String key_identifier, String str,int seconds){
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str);
		if(exists(cacheKey)) {
			long updateCount = redisCluster.expire(cacheKey, seconds);
			logger.info("set key:{} expire:{} update:{}",cacheKey,seconds,updateCount);
			return updateCount;
		}

		return 0;
	}

	/**
	 *
	 * redisClient.setnx 用来设置关键词对应的字符串，并设置在给定的时间之后超时
	 * SETNX mykey value
	 * EXPIRE mykey seconds
	 * @param appId 调用方项目编号
	 * @param key_identifier config_cacheconfig_rediskey.key_identifier 关键词标识符
	 * @param str 关键词
	 * @param str1 关键词
	 * @param value 关键词对应的字符串
	 * @return 原子性操作，set成功返回1，若key已存在即为失败返回0
	 * @throws CacheConfigNotExistException 不存在的前缀
	 * @throws RedisException redis异常
	 */
	public long setnx(int appId, String key_identifier, String str, String str1, String value)
			throws CacheConfigNotExistException,RedisException {
		RedisConfigRedisKey redisKey = readRedisKey(appId, key_identifier);
		String cacheKey = String.format(redisKey.getPattern(), str, str1);
		try {
			long result = redisCluster.setnx(cacheKey, value);
			if(exists(cacheKey)) {
				long updateCount = redisCluster.expire(cacheKey, redisKey.getTtl());
				logger.info("setnx key:{} expire:{} update:{}",cacheKey,redisKey.getTtl(),updateCount);
			}
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			del(appId, key_identifier, str);
			throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, className, Constant.REDIS_CONNECT_ERROR_EVENTKEY);
		} finally {
		}
	}

	/**
	 * 获取锁
	 * @param appId 项目编号
	 * @param keyIdentifier 关键词
	 * @param key 锁的名称
	 * @param value 锁的内容。用于排他解锁
	 * @return true 获得锁；false 被其他锁住，无法获得锁
	 */
	public boolean tryGetLock(int appId, String keyIdentifier, String key, String value) {

		RedisConfigRedisKey redisKey = readRedisKey(appId, keyIdentifier);
		String cacheKey = String.format(redisKey.getPattern(), key);
		long expireTime = redisKey.getTtl() > 0 ? redisKey.getTtl() * 1000 : EXPIRE_TIME;
		String result = redisCluster.set(cacheKey, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

		if (LOCK_SUCCESS.equals(result)) {
			return true;
		}
		return false;
	}

	/**
	 * 释放锁
	 * @param key 锁的名称
	 * @param value 锁的内容。用于排他解锁。如果锁的内容和当前的值不相等，无法解锁
	 * @return true 解锁成功；false 解锁失败
	 */
	public boolean releaseLock(int appId, String keyIdentifier, String key, String value) {
		RedisConfigRedisKey redisKey = readRedisKey(appId, keyIdentifier);
		String cacheKey = String.format(redisKey.getPattern(), key);
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = redisCluster.eval(script, Collections.singletonList(cacheKey), Collections.singletonList(value));

		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}

	/**
	 * 暴力解锁
	 * @param key 锁的名字
	 * @return 直接删除
	 */
	public boolean violentlyReleaseLock(int appId, String keyIdentifier, String key) {
		RedisConfigRedisKey redisKey = readRedisKey(appId, keyIdentifier);
		String cacheKey = String.format(redisKey.getPattern(), key);
		redisCluster.del(cacheKey);
		return true;

	}

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final int EXPIRE_TIME = 60*1000;
	private static final Long RELEASE_SUCCESS = 1L;
}
