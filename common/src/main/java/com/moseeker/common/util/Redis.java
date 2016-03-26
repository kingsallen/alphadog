package com.moseeker.common.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
        	
    		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    		//Jedis Cluster will attempt to discover cluster nodes automatically
    		// 192.168.31.10:7000~7005
    		jedisClusterNodes.add(new HostAndPort("192.168.31.10", 30001));
    		jedisClusterNodes.add(new HostAndPort("192.168.31.10", 30002));
    		jedisClusterNodes.add(new HostAndPort("192.168.31.10", 30003));

    		redisCluster = new JedisCluster(jedisClusterNodes);
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
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();

		//Jedis redis = Redis.getRedis();

		try {
		//	System.out.println(set(0, "DEFAULT", "123", "newjson"));
		//	System.out.println(get(0, "DEFAULT", "123"));
			System.out.println(lpush(2, "LOG","jsonapiresult2"));
			System.out.println(lpush(2, "LOG","jsonapiresult1"));
			System.out.println(rpoplpush(2, "LOG", "LOG_INPROGRESS"));
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		Jedis redis = new Jedis("192.168.31.10", 6379);	
		
//		long init = System.currentTimeMillis();
//		
//		for(int i=0; i<1; i++){
//			
//			redis.setex(String.valueOf(i), 1000, String.valueOf(i)+"value");
//			redis.get(String.valueOf(i));
//		}
//
//		//redis.close();
//		
//		long setget = System.currentTimeMillis();
//		
//		
//		
//		
//		
//		System.out.println(start);
//		System.out.print("初始化时间 s ： ");
//		System.out.println((init-start)/1000.0);
//		System.out.print("setget 时间 s ： ");
//		System.out.println((setget-init)/1000.0);
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
    
}
