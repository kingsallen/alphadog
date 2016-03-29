package com.moseeker.common.cache;

import org.junit.Test;

public class RedisClusterTest {

	@Test
	public void connPoolTest() {
		/*for(int i=0; i< 5; i++) {
    		String key = "test"+i;
    		Runnable run = () -> {
    			long start = System.currentTimeMillis();
    	    	String redisValue = "";
				try {
					String redisKey = Redis.set(1, key, "test", "test value");
					redisValue = Redis.get(1, "test", redisKey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	long afterGetAndSet = System.currentTimeMillis();
    	    	System.out.println("redisValue:"+redisValue);
    	    	System.out.println(key+":"+(start-afterGetAndSet)/1000.0 +" s");
    		};
    		run.run();
    		
    		if(i == 20) {
    			try {
					Thread.sleep(3000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}*/
		RedisClient redisClient = RedisClient.getInstance();
		String key = "DEFAULT";
		long start = System.currentTimeMillis();
    	String redisValue = "";
    	String redisKey = "";
		try {
			redisKey = redisClient.set(0, key, "test", "test value");
			redisValue = redisClient.get(0, key, "test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	long afterGetAndSet = System.currentTimeMillis();
    	System.out.println("redisKey:"+redisKey);
    	System.out.println("redisValue:"+redisValue);
    	System.out.println(key+":"+(afterGetAndSet-start)/1000.0 +" s");
	}
}
