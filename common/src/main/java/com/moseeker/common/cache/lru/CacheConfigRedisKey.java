package com.moseeker.common.cache.lru;

/**
 * 
 * 缓存关键词配置表 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class CacheConfigRedisKey {

	private String appIdKeyIdentifier;					//数据库编号
	private int appId;				//项目编号
	private String keyIdentifier;	//标识符
	private String pattern;			//匹配模式
	private int ttl;				//生存时间
	private String desc;			//描述
	
	
	public String getAppIdKeyIdentifier() {
		return appIdKeyIdentifier;
	}
	public void setAppIdKeyIdentifier(String appIdKeyIdentifier) {
		this.appIdKeyIdentifier = appIdKeyIdentifier;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getKeyIdentifier() {
		return keyIdentifier;
	}
	public void setKeyIdentifier(String keyIdentifier) {
		this.keyIdentifier = keyIdentifier;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
