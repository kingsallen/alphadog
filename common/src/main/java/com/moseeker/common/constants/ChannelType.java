package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.util.ConfigPropertiesUtil;

/**
 * 
 * 渠道 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum ChannelType {
	
	JOB51(1, "51job"), LIANPIAN(2, ""), ZHILIAN(3, "zhaopin"), LINKEDIN(4, "");
	
	private Logger logger = LoggerFactory.getLogger(ChannelType.class);
	
	private ChannelType(int value, String name) {
		try {
			ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
			configUtils.loadResource("chaos.properties");
			this.domain = configUtils.get("choas.domain", String.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		this.value = value;
		this.name = name;
	}
	
	private int value = 0;				//渠道值
	private String name = null;			//渠道名称
	private String domain = null;		//chaos域名
	
	private static final String BINDING = "position/binding"; //帐号绑定请求名称
	private static final String REMAIN_NUM = "position/remain"; //帐号绑定请求名称
	
	private static final Map<Integer, ChannelType> intToEnum
    	= new HashMap<Integer, ChannelType>();
	
	static { // Initialize map from constant name to enum constant
	    for (ChannelType op : values())
	    	intToEnum.put(op.getValue(), op);
	}
	
	public static ChannelType instaceFromInteger(int value) {
		return intToEnum.get(value);
	}
	
	public int getValue() {
		return this.value;
	}
	
	/**
	 * 返回该渠道的绑定请求地址
	 * @param domain chaos域名
	 * @return
	 */
	public String getBindURI() {
		return domain+"/"+name+"/"+BINDING;
	}
	
	/**
	 * 返回该渠道的绑定请求地址
	 * @param domain chaos域名
	 * @return
	 */
	public String getRemain() {
		return domain+"/"+name+"/"+REMAIN_NUM;
	}
	
	/**
	 * 返回该渠道的可发布职位数的请求地址
	 * @param domain chaos域名
	 * @return
	 */
	public String getRemainURI() {
		return domain+"/"+name+"/"+REMAIN_NUM;
	}
}
