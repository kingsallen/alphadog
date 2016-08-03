package com.moseeker.servicemanager.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.profile.struct.Intention;

public class ParamUtils {
	
	public static String getRestfullApiName(HttpServletRequest request) {
		String path = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return path;
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

	/**
	 * 通用参数解析工具。将request parameter中的参数放入到对象中。
	 * 
	 * @param request
	 *            request请求
	 * @param t
	 *            参数对象
	 * @return t 参数对象
	 * @throws Exception
	 */
	public static <T> T initCommonQuery(HttpServletRequest request, T t)
			throws Exception {
		if (t != null) {
			try {
				Map<String, Object> data = new HashMap<String, Object>();
				data.putAll(initParamFromRequestBody(request));
				data.putAll(initParamFromRequestParameter(request));
				
				if (data.get("appid") == null) {
					throw new Exception("请设置 appid!");
				}

				Method method1 = t.getClass().getMethod("setAppid",
						int.class);
				method1.invoke(t, BeanUtils.converToInteger(data.get("appid")));
				if (data.get("page") != null) {
					Method method = t.getClass()
							.getMethod("setPage", int.class);
					method.invoke(t, BeanUtils.converToInteger(data.get("page")));
				}
				if (data.get("per_page") != null) {
					Method method = t.getClass().getMethod("setPer_page",
							int.class);
					method.invoke(t, BeanUtils.converToInteger(data.get("per_page")));
				}
				if (data.get("sortby") != null) {
					Method method = t.getClass().getMethod("setSortby",
							String.class);
					method.invoke(t, BeanUtils.converToString(data.get("sortby")));
				}
				if (data.get("order") != null) {
					Method method = t.getClass().getMethod("setOrder",
							String.class);
					method.invoke(t, BeanUtils.converToString(data.get("order")));
				}
				
				if (data.get("fields") != null) {
					Method method = t.getClass().getMethod("setFields",
							String.class);
					method.invoke(t, BeanUtils.converToString(data.get("fields")));
				}
				if (data.get("nocache") != null) {
					Method method = t.getClass().getMethod("setNocache",
							String.class);
					method.invoke(t, BeanUtils.convertToBoolean(data.get("nocache")));
				}
				Map<String, String> param = new HashMap<>();
				Map<String, String[]> reqParams = request.getParameterMap();
				if (reqParams != null) {
					for (Entry<String, String[]> entry : reqParams.entrySet()) {
						if (!entry.getKey().equals("appid")
								&& !entry.getKey().equals("page")
								&& !entry.getKey().equals("per_page")
								&& !entry.getKey().equals("sortby")
								&& !entry.getKey().equals("order")
								&& !entry.getKey().equals("fields")
								&& !entry.getKey().equals("nocache")) {
							param.put(entry.getKey(),
									entry.getValue()[0]);
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
	 * 将request请求中的参数，不管是request的body中的参数还是以getParameter方式获取的参数存入到HashMap并染回该HashMap
	 * @param request request请求
	 * @return 存储通过request请求传递过来的参数
	 * @throws Exception 
	 */
	public static Map<String, Object> mergeRequestParameters(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}		
		return data;
	}
	
	/**
	 * 将request请求中的参数，不管是request的body中的参数还是以getParameter方式获取的参数存入到HashMap并染回该HashMap
	 * @param request request请求
	 * @return 存储通过request请求传递过来的参数
	 * @throws Exception 
	 */
	public static Map<String, Object> mergeRequestParameterList(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameterList(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}		
		return data;
	}

	/**
	 * 通用参数解析工具。初始化参数数据结构，并将request parameter中的参数放入到对象中。
	 * 
	 * @param request
	 *            request request请求
	 * @param clazz
	 *            参数对象
	 * @return <T> T 参数对象
	 * @throws Exception
	 */
	public static <T> T initCommonQuery(HttpServletRequest request,
			Class<T> clazz) throws Exception {
		T t = clazz.newInstance();
		initCommonQuery(request, t);
		return t;
	}

	public static <T> T initModelForm(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		T t = clazz.newInstance();

		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}
		
		if (data != null && data.size() > 0) {
			Field[] fields = clazz.getDeclaredFields();
			for (Entry<String, Object> entry : data.entrySet()) {
				for (int i = 0; i < fields.length; i++) {
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

		return t;
	}
	
	public static <T> T initModelFormForList(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		T t = clazz.newInstance();
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameterList(request));
		
		if (data.get("appid") == null){
			throw new Exception("请设置 appid!");
		}
		
		if (data != null && data.size() > 0) {
			Field[] fields = clazz.getDeclaredFields();
			for (Entry<String, Object> entry : data.entrySet()) {
				for (int i = 0; i < fields.length; i++) {
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

		return t;
	}
	
	public static <T> T initModelForm(Map<String, Object> data, Class<T> clazz) throws Exception {
		T t = null;
		if(data != null && data.size() > 0) {
			if (data.get("appid") == null){
				throw new Exception("请设置 appid!");
			}
			t = clazz.newInstance();
			if (data != null && data.size() > 0) {
				Field[] fields = clazz.getDeclaredFields();
				for (Entry<String, Object> entry : data.entrySet()) {
					for (int i = 0; i < fields.length; i++) {
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

	@Test
	public void testArray() {
		int[] array = { 1, 2, 3 };
		Object a = array;
		if (a instanceof Object[] || a instanceof byte[] || a instanceof char[]
				|| a instanceof int[] || a instanceof long[]
				|| a instanceof float[] || a instanceof double[]) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}

	@Test
	public void testPrimary() {
		int b = 1;
		Object a = b;
		if (a instanceof Object) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
	
	private static Map<String, Object> initParamFromRequestParameter(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Map<String, String[]> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, String[]> entry : reqParams.entrySet()) {
				param.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return param;
	}
	
	private static Map<String, Object> initParamFromRequestParameterList(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Map<String, String[]> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, String[]> entry : reqParams.entrySet()) {
				param.put(entry.getKey(), entry.getValue());
			}
		}
		return param;
	}

	/**
	 * 获取post中的信息
	 * 
	 * @return map 参数键值对
	 */
	public static Map<String, Object> initParamFromRequestBody(
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
		Map<String, Object> map = parseJSON2Map(jb.toString());
		return map;
	}

	/**
	 * json 转 HashMap
	 * 
	 * @param jsonStr
	 *            json
	 * @return HashMap
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验jsonStr格式是否正确
		if (!jsonStr.startsWith("{") || !jsonStr.endsWith("}")) {
			return map;
		}
		Object object = JSON.parse(jsonStr);
		;
		if (object instanceof Map) {
			map = (Map<String, Object>) object;
			for(Entry<String, Object> entry : map.entrySet()) {
				if(entry.getValue() instanceof JSONArray) {
					entry.setValue(((JSONArray)entry.getValue()).toArray());
				}
			}
		} else if (object instanceof List) {
			map.put(object.toString(), object);
		} else {
			map.put(object.toString(), object);
		}
		return map;
	}
	
	public static void buildIntention(Map<String, Object> reqParams, Intention intention) {
		Map<Integer, Integer> industryCode = new HashMap<>();
		Map<String, Integer> industryName= new HashMap<>();
		
		Map<Integer, Integer> positionCode = new HashMap<>();
		Map<String, Integer> positionName= new HashMap<>();
		
		Map<Integer, Integer> cityCode = new HashMap<>();
		Map<String, Integer> cityName= new HashMap<>();
		if (reqParams != null) {
			for (Entry<String, Object> entry : reqParams.entrySet()) {
				if(entry.getKey().startsWith("industries[")) {
					if(entry.getKey().contains("industry_code")) {
						industryCode.put(Integer.valueOf(entry.getKey().charAt(11)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("industry_name")) {
						industryName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(11)));
					}
				}
				
				if(entry.getKey().startsWith("cities[")) {
					if(entry.getKey().contains("city_code")) {
						cityCode.put(Integer.valueOf(entry.getKey().charAt(7)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("city_name")) {
						cityName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(7)));
					}
				}
				
				if(entry.getKey().startsWith("positions[")) {
					if(entry.getKey().contains("position_code")) {
						positionCode.put(Integer.valueOf(entry.getKey().charAt(10)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("position_name")) {
						positionName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(10)));
					}
				}
			}
		}
		if(industryName.size() > 0) {
			for(Entry<String, Integer> entry : industryName.entrySet()) {
				if(intention.getIndustries() == null) {
					intention.setIndustries(new HashMap<String, Integer>());
				}
				int code = 0;
				if(industryCode.size() > 0) {
					if(industryCode.get(entry.getValue()) != null) {
						code = industryCode.get(entry.getValue());
					}
				}
				intention.getIndustries().put(entry.getKey(), code);
			}
		}
		if(positionName.size() > 0) {
			for(Entry<String, Integer> entry : positionName.entrySet()) {
				if(intention.positions == null) {
					intention.setPositions(new HashMap<String, Integer>());
				}
				int code = 0;
				if(positionCode.size() > 0) {
					if(positionCode.get(entry.getValue()) != null) {
						code = positionCode.get(entry.getValue());
					}
				}
				intention.getPositions().put(entry.getKey(), code);
			}
		}
		if(cityName.size() > 0) {
			for(Entry<String, Integer> entry : cityName.entrySet()) {
				if(intention.cities == null) {
					intention.setCities(new HashMap<String, Integer>());
				}
				int code = 0;
				if(cityCode.get(entry.getValue()) != null) {
					code = cityCode.get(entry.getValue());
				}
				intention.getCities().put(entry.getKey(), code);
			}
		}
	}

	public static void buildIntention(HttpServletRequest request, Intention intention) {
		Map<Integer, Integer> industryCode = new HashMap<>();
		Map<String, Integer> industryName= new HashMap<>();
		
		Map<Integer, Integer> positionCode = new HashMap<>();
		Map<String, Integer> positionName= new HashMap<>();
		
		Map<Integer, Integer> cityCode = new HashMap<>();
		Map<String, Integer> cityName= new HashMap<>();
		Map<String, String[]> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, String[]> entry : reqParams.entrySet()) {
				if(entry.getKey().startsWith("industries[")) {
					if(entry.getKey().contains("industry_code")) {
						industryCode.put(Integer.valueOf(entry.getKey().charAt(11)), Integer.valueOf(entry.getValue()[0]));
					}
					if(entry.getKey().contains("industry_name")) {
						industryName.put(entry.getValue()[0], Integer.valueOf(entry.getKey().charAt(11)));
					}
				}
				
				if(entry.getKey().startsWith("cities[")) {
					if(entry.getKey().contains("city_code")) {
						cityCode.put(Integer.valueOf(entry.getKey().charAt(7)), Integer.valueOf(entry.getValue()[0]));
					}
					if(entry.getKey().contains("city_name")) {
						cityName.put(entry.getValue()[0], Integer.valueOf(entry.getKey().charAt(7)));
					}
				}
				
				if(entry.getKey().startsWith("positions[")) {
					if(entry.getKey().contains("position_code")) {
						positionCode.put(Integer.valueOf(entry.getKey().charAt(10)), Integer.valueOf(entry.getValue()[0]));
					}
					if(entry.getKey().contains("position_name")) {
						positionName.put(entry.getValue()[0], Integer.valueOf(entry.getKey().charAt(10)));
					}
				}
			}
		}
		if(industryName.size() > 0) {
			for(Entry<String, Integer> entry : industryName.entrySet()) {
				if(intention.getIndustries() == null) {
					intention.setIndustries(new HashMap<String, Integer>());
				}
				int code = 0;
				if(industryCode.size() > 0) {
					if(industryCode.get(entry.getValue()) != null) {
						code = industryCode.get(entry.getValue());
					}
				}
				intention.getIndustries().put(entry.getKey(), code);
			}
		}
		if(positionName.size() > 0) {
			for(Entry<String, Integer> entry : positionName.entrySet()) {
				if(intention.positions == null) {
					intention.setPositions(new HashMap<String, Integer>());
				}
				int code = 0;
				if(positionCode.size() > 0) {
					if(positionCode.get(entry.getValue()) != null) {
						code = positionCode.get(entry.getValue());
					}
				}
				intention.getPositions().put(entry.getKey(), code);
			}
		}
		if(cityName.size() > 0) {
			for(Entry<String, Integer> entry : cityName.entrySet()) {
				if(intention.cities == null) {
					intention.setCities(new HashMap<String, Integer>());
				}
				int code = 0;
				if(cityCode.get(entry.getValue()) != null) {
					code = cityCode.get(entry.getValue());
				}
				intention.getCities().put(entry.getKey(), code);
			}
		}
	}
}
