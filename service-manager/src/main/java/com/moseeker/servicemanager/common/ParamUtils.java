package com.moseeker.servicemanager.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.servicemanager.web.controller.util.Params;

/**
 * 
 * 主要用于生成form表单 
 * <p>Company: MoSeeker</P>  
 * <p>date: Aug 22, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ParamUtils {
	
	/**
	 * 通用的参数解析方法。将request参数信息解析出来，如果属性名字和参数名称一致，则设法将参数的值赋值给类对象的值。
	 * @param request HttpServletRequest
	 * @param clazz 转换类类型
	 * @return 转换类
	 * @throws Exception 异常信息
	 */
	public static <T> T initModelForm(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		Map<String, Object> data = parseRequestParam(request);
		T t = initModelForm(data, clazz);
		return t;
	}
	
	/**
	 * 用于解析常用的查询类。和initModelForm不同的是，会将form表单不存在的属性存入到EqualFilter中
	 * @param request HttpServletRequest 
	 * @param clazz 转换类的类型属性
	 * @return 转换后的类
	 * @throws Exception 异常
	 */
	public static <T> T initCommonQuery(HttpServletRequest request,
			Class<T> clazz) throws Exception {
		Map<String, Object> data = parseRequestParam(request);
		T t = initModelForm(data, clazz);
		buildEqualParam(data, t);
		return t;
	}
	
	/**
	 * 将request请求中的参数，不管是request的body中的参数还是以getParameter方式获取的参数存入到HashMap并染回该HashMap
	 * @param request request请求
	 * @return 存储通过request请求传递过来的参数
	 * @throws Exception 
	 */
	public static Params<String, Object> parseRequestParam(HttpServletRequest request) throws Exception {
		Params<String, Object> data = new Params<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}		
		return data;
	}
	
	/**
	 * 解析参数中的不在通用查询约定的属性，将其放入通用参数等比较集合中
	 * 
	 * @param data 参数集合
	 * @param t 通用查询类
	 * @return 通用查询类
	 * @throws Exception 异常
	 */
	private static <T> T buildEqualParam(Map<String, Object> data, T t)
			throws Exception {
		if (t != null) {
			try {
				Map<String, Object> param = new HashMap<>();
				
				if (data != null) {
					for (Entry<String, Object> entry : data.entrySet()) {
						if (!entry.getKey().equals("appid")
								&& !entry.getKey().equals("page")
								&& !entry.getKey().equals("per_page")
								&& !entry.getKey().equals("sortby")
								&& !entry.getKey().equals("order")
								&& !entry.getKey().equals("fields")
								&& !entry.getKey().equals("nocache")) {
							param.put(entry.getKey(), entry.getValue());
						}
					}
				}
				
				Method method = t.getClass().getMethod("setEqualFilter",
						Map.class);
				method.invoke(t, param);
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// warning 报警 / logging 日志
				e.printStackTrace();
			} finally {
				//do nothing
			}
		}
		return t;
	}
	
	/**
	 * 利用放射的方式将类类型初始化，并根据类属性和参数名相同将参数值赋值给属性
	 * 
	 * @param data 参数
	 * @param clazz 类类型
	 * @return 赋值后的类对象
	 * @throws Exception 异常
	 */
	public static <T> T initModelForm(Map<String, Object> data, Class<T> clazz) throws Exception {
		T t = null;
		if(data != null && data.size() > 0) {
			t = clazz.newInstance();
			if (data != null && data.size() > 0) {
				// thrift 都是自动生成的public类型, 故使用getFields,如果不是public的时候, 请不要使用此方法
				Field[] fields = clazz.getDeclaredFields();
				Map<String, Integer> fieldMap = new HashMap<String, Integer>();
				for (int f = 0; f < fields.length; f++) {
					// 过滤掉转换不需要的字段 metaDataMap
					if(!"metaDataMap".equals(fields[f].getName())) {
						fieldMap.put(fields[f].getName(), f);
					}
				}
				Integer i = null;
				for (Entry<String, Object> entry : data.entrySet()) {
					if(fieldMap.containsKey(entry.getKey())) {
						i = fieldMap.get(entry.getKey());
						if (i == null) {
							continue;
						}
						if (fields[i].getName().equals(entry.getKey())) {
							String methodName = "set"
									+ fields[i].getName().substring(0, 1)
									.toUpperCase()
									+ fields[i].getName().substring(1);
							Method method = clazz.getMethod(methodName,
									fields[i].getType());
							Object cval = BeanUtils.convertTo(
									entry.getValue(), fields[i].getType());
							try {
								method.invoke(t, cval);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return t;
	}

	/**
	 * 解析x-www-form-urlencoded参数
	 * @param request 请求
	 * @return 参数
	 */
	private static Map<String, Object> initParamFromRequestParameter(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Map<String, String[]> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, String[]> entry : reqParams.entrySet()) {
				if(entry.getValue() != null && entry.getValue().length > 1) {
					List<String> values = new ArrayList<>();
					for(String value : entry.getValue()) {
						if(value != null) {
							values.add(value);
						}
					}
					param.put(entry.getKey(), values);
				} else {
					param.put(entry.getKey(), entry.getValue()[0]);
				}
			}
		}
		return param;
	}
	
	/**
	 * 获取request.reader都到的参数信息。主要用于解析application/json数据
	 * @param request 请求
	 * @return 参数
	 */
	private static Map<String, Object> initParamFromRequestBody(
			HttpServletRequest request) {

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
		} catch (IOException | IllegalStateException e) {
		}
		Map<String, Object> map = JsonToMap.parseJSON2Map(jb.toString());
		return map;
	}
	
	public static String getLocalHostIp(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		}
		return "unknow";
	}
	
	public static String getRemoteIp(HttpServletRequest request) {
		String remoteIpForwardedbyLbs = request.getHeader("REMOTE_ADDR");// php
																			// 和
																			// python
																			// tornado不一致，需要实际测试。
		return remoteIpForwardedbyLbs == null ? request.getRemoteAddr()
				: remoteIpForwardedbyLbs;
	}
}
