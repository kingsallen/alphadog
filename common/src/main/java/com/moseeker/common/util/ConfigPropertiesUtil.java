package com.moseeker.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 配置文件读取帮助类。利用getResourceAsStream方法读取默认的配置文件，
 * 将读取的结果交给java.util.Properties解析并存储。
 *  
 * @date Mar 25, 2016
 * @company moseeker
 * @author wjf
 * @email wjf2255@gmail.com
 */
public class ConfigPropertiesUtil {

	private static Properties properties;			//储配置文件内容存
	private static ConfigPropertiesUtil self;	
	
	/**
	 * 读取配置信息帮助类 默认读取serviceprovider.properties配置文件
	 * @throws Exception
	 */
	private ConfigPropertiesUtil() throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("serviceprovider.properties");
			properties = new Properties();
			properties.load(inputStream);
		} catch (Exception e) {
			//todo 错误信息需要记录到日志中
			e.printStackTrace();
			throw new Exception("can not find default properties");
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 读取指定名字的配置文件。如果配置文件的key和已存在的key冲突，会覆盖已存在的key的内容。
	 * @param fileName 配置文件的名称
	 * @throws Exception 如果配置文件不存在，抛出异常
	 */
	public static void loadResource(String fileName) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream(fileName); 
			properties.load(inputStream);
		} catch (Exception e) {
			//todo 错误信息需要记录到日志中
			e.printStackTrace();
			throw new Exception("can not find default properties");
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static synchronized ConfigPropertiesUtil getInstance() {
		if(self == null) {
			try {
				self = new ConfigPropertiesUtil();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return self;
	}
	
	/**
	 * 将配置文件中的字符串类型转成其他类型指定的类型
	 * @param key 关键词
	 * @param clazzType 关键词对应内容的类型
	 * @return 转成指定类型之后的内容
	 */
	public <T> T get(String key, Class<T> clazzType) {
		Object object = properties.get(key);
		return convertTo(object, clazzType);
	}
	
	/**
	 * 类型转换。提供将对象转成指定的类型的功能
	 * @param value1 被转换的对象
	 * @param clazzType 指定一个转成的类型
	 * @return 返回转换的结果
	 */
	@SuppressWarnings("unchecked")
	private static <T> T convertTo(Object value1, Class<T> clazzType) {
		if (clazzType.isAssignableFrom(String.class)) {
			return (T) value1.toString();
		} else if (clazzType.isAssignableFrom(Long.class)) {
			return (T) new Long(value1.toString());
		} else if (clazzType.isAssignableFrom(Byte.class)) {
			return (T) new Byte(value1.toString());
		} else if (clazzType.isAssignableFrom(Integer.class)) {
			return (T) new Integer(value1.toString());
		} else if (clazzType.isAssignableFrom(Float.class)) {
			return (T) new Float(value1.toString());
		} else if (clazzType.isAssignableFrom(Float.class)) {
			return (T) new Double(value1.toString());
		} else if (clazzType.isAssignableFrom(Boolean.class)) {
			return (T) new Boolean(value1.toString());
		} else {
			return (T) value1.toString();
		}
	}
}
