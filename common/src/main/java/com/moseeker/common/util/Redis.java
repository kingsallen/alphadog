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
    public static JedisCluster getRedisCluster() {  
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

    public static Jedis getRedis() {  
    if (redis == null) {  
        synchronized (Redis.class) {  
        if (redis == null) {  
        	redis = new Jedis("192.168.31.10", 6379);	
        }  
       }  
   }  
   return redis;  
   }  

    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();

		Jedis redis = Redis.getRedis();

		
//		Jedis redis = new Jedis("192.168.31.10", 6379);	
		
		long init = System.currentTimeMillis();
		
		for(int i=0; i<100; i++){
			
			redis.setex(String.valueOf(i), 1000, String.valueOf(i)+"value");
			redis.get(String.valueOf(i));
		}

		//redis.close();
		
		long setget = System.currentTimeMillis();
		
		System.out.println(start);
		System.out.print("初始化时间 s ： ");
		System.out.println((init-start)/1000.0);
		System.out.print("setget 时间 s ： ");
		System.out.println((setget-init)/1000.0);
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
}
