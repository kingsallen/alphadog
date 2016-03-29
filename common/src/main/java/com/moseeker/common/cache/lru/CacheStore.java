package com.moseeker.common.cache.lru;

import java.util.List;

import com.moseeker.common.cache.DbManager;



/**
 * 
 * Redis关键词配置表 缓存帮助类 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class CacheStore {

	/**
	 * Redis关键词配置表缓存帮助类
	 */
	public static volatile CacheStore instance = null;
	
	private LRUCache lruCache = null;
	
	private CacheStore() {
		initCapacity(200);
	}
	/**
	 * 初始化类
	 * @return CacheStore
	 */
	public static CacheStore getInstance() {
		if(instance == null) {
			synchronized (CacheStore.class) {
				if(instance == null) {
					instance = new CacheStore();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 初始化缓存存储空间
	 * @param capacity
	 */
	private void initCapacity(int capacity) {
		if (lruCache == null) {
			lruCache = new LRUCache(capacity);
		} else {
			lruCache.setCapacity(capacity);
		}
	}
	
	/**
	 * 一次性加载配置信息到缓存中，并返回配置信息集合
	 * @return List<CacheConfigRedisKey> {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	public List<CacheConfigRedisKey> reloadRedisKey() {
		List<CacheConfigRedisKey> redisKeys = DbManager.readAllConfigFromDB();
		if(redisKeys != null && redisKeys.size() > 0) {
			for(CacheConfigRedisKey redisKey : redisKeys) {
				lruCache.set(redisKey.getAppId()+redisKey.getKeyIdentifier(), redisKey);
			}
		}
		return redisKeys;
	}
	
	/**
	 * 根据项目编号和标识符查找缓存配置。先查找缓存中是否存在，如果不存在则查找数据库，并更新缓存
	 * @param appId 项目编号
	 * @param keyIdentifier 标识符
	 * @return CacheConfigRedisKey {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	public CacheConfigRedisKey readRedisKey(int appId, String keyIdentifier) {
		String appIdKeyIdentifier = appId + keyIdentifier;
		if (lruCache.contains(appIdKeyIdentifier)) {
			System.out.println("# Cache Hit!");
			return lruCache.getRedisKey(appIdKeyIdentifier);
		}
		CacheConfigRedisKey redisKey = DbManager.readFromDB(appId, keyIdentifier);
		lruCache.set(appIdKeyIdentifier, redisKey);
		return redisKey;
	}
	
	public void clear() {
		lruCache.clear();
	}
}
