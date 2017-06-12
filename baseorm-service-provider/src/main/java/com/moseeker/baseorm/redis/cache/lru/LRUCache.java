package com.moseeker.baseorm.redis.cache.lru;

import com.moseeker.baseorm.redis.RedisConfigRedisKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 支持LRU方式的缓存结构。根据<a href='https://github.com/iluwatar/java-design-patterns'
 * target='_blank'>java-design-patterns.com</a>项目中caching制作的一个LRU缓存数据结构
 * 
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Mar 29, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version Beta
 */
public class LRUCache {

	Logger logger = LoggerFactory.getLogger(LRUCache.class);

	/**
	 * 
	 * 存储结构
	 * <p>
	 * Company: MoSeeker
	 * </P>
	 * <p>
	 * date: Mar 29, 2016
	 * </p>
	 * <p>
	 * Email: wjf2255@gmail.com
	 * </p>
	 * 
	 * @author wjf
	 * @version
	 */
	class Node {
		String appIdAndKeyIdentifier;
		RedisConfigRedisKey redisKey;
		Node preNode;
		Node nextNode;

		public Node(String appIdAndKeyIdentifier, RedisConfigRedisKey rediskey) {
			this.appIdAndKeyIdentifier = appIdAndKeyIdentifier;
			this.redisKey = rediskey;
		}
	}

	/**
	 * 缓存大小
	 */
	int capacity;	
	/**
	 * 存储数据的数据结构
	 */
	HashMap<String, Node> cache = new HashMap<>();	
	/**
	 * 前一个节点。当当前节点是第一个节点时，head为null
	 */
	Node head = null;
	/**
	 * 后一个节点。当当前节点是最后一个节点时，end为null
	 */
	Node end = null;

	/**
	 * 初始化LRUCache。开辟一个capacity大小的存储空间的LRUCache。
	 * @param capacity 缓存的存储空间
	 */
	public LRUCache(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * 根据程序编号和标识符查找缓存中是否存在配置信息
	 * @param appIdAndKeyIdentifier 关键词
	 * @return {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	public RedisConfigRedisKey getRedisKey(String appIdAndKeyIdentifier) {
		if (cache.containsKey(appIdAndKeyIdentifier)) {
			Node node = cache.get(appIdAndKeyIdentifier);
			remove(node);
			setHead(node);
			return node.redisKey;
		}
		return null;
	}

	/**
	 * 插入缓存中第一个值
	 * @param node 缓存节点
	 */
	public void setHead(Node node) {
		node.nextNode = head;
		node.preNode = null;
		if (head != null) {
			head.preNode = node;
		}
		head = node;
		if (end == null) {
			end = head;
		}
	}

	/**
	 * 删除缓存节点
	 * @param node 缓存节点
	 */
	public void remove(Node node) {
		if (node.preNode != null) {
			node.preNode.nextNode = node.nextNode;
		} else {
			head = node.nextNode;
		}
		if (node.nextNode != null) {
			node.nextNode.preNode = node.preNode;
		} else {
			end = node.nextNode;
		}
	}

	/**
	 * 向缓存中添加一个节点
	 * @param appIdAndKeyIdentifier 关键词
	 * @param redisKey {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	public void set(String appIdAndKeyIdentifier, RedisConfigRedisKey redisKey) {
		if (cache.containsKey(appIdAndKeyIdentifier)) {
			Node old = cache.get(appIdAndKeyIdentifier);
			old.redisKey = redisKey;
			remove(old);
			setHead(old);
		} else {
			Node newNode = new Node(appIdAndKeyIdentifier, redisKey);
			if (cache.size() >= capacity) {
				logger.info("# Cache is FULL! Removing {} from cache...", end.appIdAndKeyIdentifier);
				cache.remove(end.appIdAndKeyIdentifier); // remove LRU data from
															// cache.
				remove(end);
				setHead(newNode);
			} else {
				setHead(newNode);
			}
			cache.put(appIdAndKeyIdentifier, newNode);
		}
	}

	/**
	 * 获取缓存是否存在关键。如果存在则表示，该关键词对应的数据已经存储在缓存中。
	 * @param appIdAndKeyIdentifier 关键词
	 * @return 是否包含关键词信息。包含返回true,否则返回false
	 */
	public boolean contains(String appIdAndKeyIdentifier) {
		return cache.containsKey(appIdAndKeyIdentifier);
	}

	/**
	 * 删除节点
	 * @param appIdAndKeyIdentifier 关键词
	 */
	public void invalidate(String appIdAndKeyIdentifier) {
		logger.info("# {} has been updated! Removing older version from cache...", appIdAndKeyIdentifier);
		Node toBeRemoved = cache.get(appIdAndKeyIdentifier);
		remove(toBeRemoved);
		cache.remove(appIdAndKeyIdentifier);
	}

	/**
	 * 获取缓存是否已经满
	 * @return 如果缓存已经满了，返回true，否则返回false
	 */
	public boolean isFull() {
		return cache.size() >= capacity;
	}

	/**
	 * 返回当前节点的后一节点数据
	 * @return CacheConfigRedisKey {@see com.moseeker.common.cache.lru.CacheConfigRedisKey}
	 */
	public RedisConfigRedisKey getLRUData() {
		return end.redisKey;
	}

	/**
	 * 清空缓存
	 */
	public void clear() {
		end = null;
		head = null;
		cache.clear();
	}

	/**
	 * 将缓存数据存入ArrayList集合中，并返回
	 * @return List<CacheConfigRedisKey> 缓存中的数据集合
	 */
	public List<RedisConfigRedisKey> getCacheDataInListForm() {
		ArrayList<RedisConfigRedisKey> listOfCacheData = new ArrayList<>();
		Node temp = head;
		while (temp != null) {
			listOfCacheData.add(temp.redisKey);
			temp = temp.nextNode;
		}
		return listOfCacheData;
	}

	/**
	 * 设置缓存大小。如果当前的缓存大小大于新的缓存大小，将会清空缓存
	 * @param newCapacity
	 */
	public void setCapacity(int newCapacity) {
		if (capacity > newCapacity) {
			clear();
		} else {
			capacity = newCapacity;
		}
	}
}
