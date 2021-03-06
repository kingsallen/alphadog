package com.moseeker.common.redis.cache;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jack on 16/03/2017.
 */
public class CacheClientTest {

    protected JedisCluster redisCluster;

    public static void main(String[] args) {

        CacheClientTest cacheClientTest = new CacheClientTest();

        ThreadPool threadPool = ThreadPool.Instance;

        /*for(int i=0; i<10000; i++) {
            cacheClientTest.getRedisCluster().lpush("test1", "hello100"+i);
        }*/

        for(int i=0; i<10000; i++) {
            threadPool.startTast(() -> cacheClientTest.getList());
            threadPool.startTast(() -> cacheClientTest.getKey());
        }
        threadPool.close();
    }

    public List<String> getList() {
        List<String> name = redisCluster.brpop(5,"test1");
        System.out.println("test1:"+name);
        return name;
    }

    public String getKey() {
        String name = redisCluster.get("test2");
        System.out.println("test:"+name);
        return name;
    }

    public CacheClientTest() {
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
    }

    public JedisCluster getRedisCluster() {
        return redisCluster;
    }

    public void setRedisCluster(JedisCluster redisCluster) {
        this.redisCluster = redisCluster;
    }
}