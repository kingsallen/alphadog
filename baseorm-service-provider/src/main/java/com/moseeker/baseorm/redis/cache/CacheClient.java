package com.moseeker.baseorm.redis.cache;

import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

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
@Component("cacheClient")
@Lazy
public class CacheClient extends RedisClient {

    @PostConstruct
	public void init() {
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        redisConfigKeyName = propertiesUtils.get("redis.cache.config_key_name", String.class);
        redisConfigTimeOut = propertiesUtils.get("redis.cache.config_timeout", Integer.class);
        redisConfigType = Constant.cacheConfigType;
        redisCluster = initRedisCluster();
        //reloadRedisKey();
    }

	protected JedisCluster initRedisCluster() throws RedisException {
		logger.info("CacheClient initRedisCluster");
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
		if (redisCluster == null) {
			try {
				Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
				// Jedis Cluster will attempt to discover cluster nodes
				String host = propertiesUtils.get("redis.cache.host", String.class);
				String port = propertiesUtils.get("redis.cache.port", String.class);
				if (!StringUtils.isNullOrEmpty(host) && !StringUtils.isNullOrEmpty(port)) {
					String[] hostArray = host.split(",");
					String[] portArray = port.split(",");
					if (hostArray.length == portArray.length) {
						for (int i = 0; i < hostArray.length; i++) {
							logger.info("host:{}, port:{}", hostArray[i], portArray[i]);
							jedisClusterNodes.add(new HostAndPort(hostArray[i], Integer.parseInt(portArray[i])));
						}
					}
				} else {
					throw new RedisException("Redis集群redis.cache.host,redis.cache.port尚未配置", Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//					Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID,
//							Constant.REDIS_CONNECT_ERROR_EVENTKEY, "Redis集群redis.cache.host,redis.cache.port尚未配置");

				}
				redisCluster = new JedisCluster(jedisClusterNodes);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RedisException(e, Constant.REDIS_CONNECT_ERROR_APPID, this.getClass().getName(), Constant.REDIS_CONNECT_ERROR_EVENTKEY);
//				Notification.sendNotification(Constant.REDIS_CONNECT_ERROR_APPID, Constant.REDIS_CONNECT_ERROR_EVENTKEY,
//						e.getMessage());

			}
		}
		return redisCluster;
	}
}
