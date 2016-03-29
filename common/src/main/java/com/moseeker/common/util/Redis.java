package com.moseeker.common.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;



public class Redis {  
    private  static JedisCluster redisCluster;  
    private  static Jedis redis;  
    
    private Redis (){}   
    protected static JedisCluster getRedisCluster() {  
    if (redisCluster == null) {  
        synchronized (Redis.class) {  
        if (redisCluster == null) {  
        	GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        	config.setMaxIdle(20);
            config.setMaxTotal(60);
            config.setMaxWaitMillis(2000);
        	
    		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    		//Jedis Cluster will attempt to discover cluster nodes automatically
    		// 192.168.31.10:7000~7005
    		jedisClusterNodes.add(new HostAndPort("192.168.31.10", 30001));
    		jedisClusterNodes.add(new HostAndPort("192.168.31.10", 30002));
    		jedisClusterNodes.add(new HostAndPort("192.168.31.10", 30003));
    		//jedisClusterNodes.add(new HostAndPort("localhost", 7004));
    		//jedisClusterNodes.add(new HostAndPort("localhost", 7005));

    		redisCluster = new JedisCluster(jedisClusterNodes, config);
    		//redisCluster = new JedisCluster(jedisClusterNodes);
        }  
       }  
   }  
   return redisCluster;  
   }  

    protected static Jedis getRedis() {  
    if (redis == null) {  
        synchronized (Redis.class) {  
        if (redis == null) {  
        	redis = new Jedis("192.168.31.10", 6379);	
        }  
       }  
   }  
   return redis;  
   }  
    
    public static String set(int appid, String key_identifier, String str, String value) throws Exception{
    	CacheConfig cfg = new CacheConfig(appid, key_identifier);
    	String cacheKey = String.format(cfg.getPattern(), str);
    	JedisCluster redisc = Redis.getRedisCluster();
    	return redisc.setex(cacheKey, cfg.getTtl(), value);
    }

    public static String get(int appid, String key_identifier, String str) throws Exception{
    	CacheConfig cfg = new CacheConfig(appid, key_identifier);
    	String cacheKey = String.format(cfg.getPattern(), str);
    	JedisCluster redisc = Redis.getRedisCluster();
    	return redisc.get(cacheKey);
    }
    
 
    public static String set(int appid, String key_identifier, String str1, String str2, String value) throws Exception{
    	CacheConfig cfg = new CacheConfig(appid, key_identifier);
    	String cacheKey = String.format(cfg.getPattern(), str1, str2);
    	JedisCluster redisc = Redis.getRedisCluster();
    	return redisc.setex(cacheKey, cfg.getTtl(), value);
    }   
    
    public static String get(int appid, String key_identifier, String str1, String str2) throws Exception{
    	CacheConfig cfg = new CacheConfig(appid, key_identifier);
    	String cacheKey = String.format(cfg.getPattern(), str1, str2);
    	JedisCluster redisc = Redis.getRedisCluster();
    	return redisc.get(cacheKey);
    } 
    
    public static Long lpush(int appid, String key_identifier, String newvalue) throws Exception{
    	CacheConfig cfg = new CacheConfig(appid, key_identifier);
    	String cacheKey = cfg.getPattern();
    	JedisCluster redisc = Redis.getRedisCluster();
    	return redisc.lpush(cacheKey, newvalue);
    }

    public static String rpoplpush(int appid, String key_identifier_pop, String key_identifier_push) throws Exception{
    	CacheConfig cfg_rpop = new CacheConfig(appid, key_identifier_pop);
    	CacheConfig cfg_lpush = new CacheConfig(appid, key_identifier_push);
    	String cacheKey_rpop = cfg_rpop.getPattern();
    	String cacheKey_lpush = cfg_lpush.getPattern();
    	JedisCluster redisc = Redis.getRedisCluster();
    	return redisc.rpoplpush(cacheKey_rpop, cacheKey_lpush);
    }    
    
    
    public static String rpop(int appid, String key_identifier) throws Exception{
    	CacheConfig cfg = new CacheConfig(appid, key_identifier);
    	String cacheKey = cfg.getPattern();
    	JedisCluster redisc = Redis.getRedisCluster();   
    	return redisc.rpop(cacheKey);
    }
    
    public static void main(String[] args) {
    	for(int i=0; i< 20; i++) {
    		String key = "test"+i;
    		Runnable run = () -> {
    			long start = System.currentTimeMillis();
    	    	JedisCluster redisCluster = getRedisCluster();
    	    	long afterInit = System.currentTimeMillis();
    	    	System.out.println(key+":"+(afterInit-start)/1000.0 +" s");
    	    	redisCluster.set(key, "test_value");
    	    	redisCluster.get(key);
    	    	long afterGetAndSet = System.currentTimeMillis();
    	    	System.out.println(key+":"+(afterGetAndSet-afterInit)/1000.0 +" s");
    		};
    		run.run();
    		
    		if(i == 10) {
    			try {
					Thread.sleep(3000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
}
