package com.moseeker.common.redis;

/**
 * 
 * 用于支持在调用缓存时，如果未能查询到缓存中的数据，直接使用RedisCallback中的call方法返回的数据
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 30, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@FunctionalInterface
public interface RedisCallback {

	public String call();
}
