package com.moseeker.servicemanager.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.profile.struct.Intention;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	/**
	 * 将HttpServletRequest参数转化为struct对象
	 *
	 * @param request
	 * @param clazz
	 * @param <T> struct 对象
	 * @return
     * @throws Exception
     */
	public static <T> T initModelForm(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));

		return initModelForm(data, clazz);
	}

	/**
	 * 将HttpServletRequest参数(包含list参数类型的)转化为struct对象
	 *
	 * @param request
	 * @param clazz
	 * @param <T> struct 对象
	 * @return
     * @throws Exception
     */
	public static <T> T initModelFormForList(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameterList(request));

		return initModelForm(data, clazz);
	}

	/**
	 * 将参数map转换为thrift struct对象
	 *
	 * @param data
	 * @param clazz
	 * @param <T> struct 对象
	 * @return
     * @throws Exception
     */
	public static <T> T initModelForm(Map<String, Object> data, Class<T> clazz) throws Exception {
		T t = null;
		if(data != null && data.size() > 0) {
			if (data.get("appid") == null){
				throw new Exception("请传参数appid!");
			}
			t = clazz.newInstance();
			if (data != null && data.size() > 0) {
				// thrift 都是自动生成的public类型, 故使用getFields,如果不是public的时候, 请不要使用此方法
				Field[] fields = clazz.getFields();
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
	
}
