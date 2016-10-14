package com.moseeker.servicemanager.web.controller.util;

import java.util.HashMap;

import com.moseeker.common.util.BeanUtils;

/**
 * 
 * 参数 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version v1.3.3
 * @param <K> 关键词
 * @param <V> 值
 */
public class Params<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 4134047428932833422L;

	public Integer getInt(String key) {
		return BeanUtils.converToInteger(get(key));
	}
	
	public Integer getInt(String key, Integer defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.converToInteger(get(key));
	}
	
	public Long getLong(String key) {
		return BeanUtils.converToLong(get(key));
	}
	
	public Long getLong(String key, Long defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.converToLong(get(key));
	}
	
	public String getString(String key) {
		return BeanUtils.converToString(get(key));
	}
	
	public String getString(String key, String defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.converToString(get(key));
	}
	
	public Double getDouble(String key) {
		return BeanUtils.converToDouble(get(key));
	}
	
	public Double getDouble(String key, Double defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.converToDouble(get(key));
	}
	
	public Float getFloat(String key) {
		return BeanUtils.converToFloat(get(key));
	}
	
	public Float getFloat(String key, Float defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.converToFloat(get(key));
	}
	
	public Byte getByte(String key) {
		return BeanUtils.converToByte(get(key));
	}
	
	public Byte getByte(String key, Byte defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.converToByte(get(key));
	}
	
	public Short getShort(String key) {
		return BeanUtils.convertToShort(get(key));
	}
	
	public Short getShort(String key, Short defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.convertToShort(get(key));
	}
	
	public Boolean getBoolean(String key) {
		return BeanUtils.convertToBoolean(get(key));
	}
	
	public Boolean getBoolean(String key, Boolean defaultValue) {
		if(get(key) == null) {
			return defaultValue;
		}
		return BeanUtils.convertToBoolean(get(key));
	}
}
