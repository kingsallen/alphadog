package com.moseeker.servicemanager.common;

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

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.BeanUtils;

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
				
				Integer appId = request.getParameter("appid") == null ? null
						: Integer.parseInt(request.getParameter("appid"));

				if (appId == null) {
					throw new Exception(" failed to get appid.");
				}

				if (appId != null) {
					Method method = t.getClass().getMethod("setAppid",
							int.class);
					method.invoke(t, appId);
				}
/*
				Integer limit = request.getParameter("limit") == null ? null
						: Integer.parseInt(request.getParameter("limit"));
				if (limit != null) {
					Method method = t.getClass().getMethod("setLimit",
							int.class);
					method.invoke(t, limit);
				}
				Integer offset = request.getParameter("offset") == null ? null
						: Integer.parseInt(request.getParameter("offset"));
				if (offset != null) {
					Method method = t.getClass().getMethod("setOffset",
							int.class);
					method.invoke(t, offset);
				}
*/				
				Integer page = request.getParameter("page") == null ? null
						: Integer.parseInt(request.getParameter("page"));
				if (page != null) {
					Method method = t.getClass()
							.getMethod("setPage", int.class);
					method.invoke(t, page);
				}
				Integer perPage = request.getParameter("per_page") == null ? null
						: Integer.parseInt(request.getParameter("per_page"));
				if (perPage != null) {
					Method method = t.getClass().getMethod("setPer_page",
							int.class);
					method.invoke(t, perPage);
				}
				String sortby = request.getParameter("sortby") == null ? null
						: request.getParameter("sortby");
				if (sortby != null) {
					Method method = t.getClass().getMethod("setSortby",
							String.class);
					method.invoke(t, sortby);
				}
				String order = request.getParameter("order") == null ? null
						: request.getParameter("order");
				if (order != null) {
					Method method = t.getClass().getMethod("setOrder",
							String.class);
					method.invoke(t, order);
				}
				String fields = request.getParameter("fields") == null ? null
						: request.getParameter("fields");
				if (fields != null) {
					Method method = t.getClass().getMethod("setFields",
							String.class);
					method.invoke(t, fields);
				}
				Boolean nocache = request.getParameter("nocache") == null ? null
						: Boolean.parseBoolean(request.getParameter("nocache"));
				if (nocache != null) {
					Method method = t.getClass().getMethod("setNocache",
							boolean.class);
					method.invoke(t, nocache);
				}
				Map<String, String> param = new HashMap<>();
				@SuppressWarnings("unchecked")
				Map<String, Object> reqParams = request.getParameterMap();
				if (reqParams != null) {
					for (Entry<String, Object> entry : reqParams.entrySet()) {
						if (!entry.getKey().equals("appid")
								&& !entry.getKey().equals("limit")
								&& !entry.getKey().equals("offset")
								&& !entry.getKey().equals("page")
								&& !entry.getKey().equals("per_page")
								&& !entry.getKey().equals("sortby")
								&& !entry.getKey().equals("order")
								&& !entry.getKey().equals("fields")
								&& !entry.getKey().equals("nocache")) {
							param.put(entry.getKey(),
									((String[]) entry.getValue())[0]);
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
	 */
	public static Map<String, Object> mergeRequestParameters(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));
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
		return t;
	}

	public static <T> T initModelForm(HttpServletRequest request, Class<T> clazz)
			throws Exception {
		T t = clazz.newInstance();

		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(initParamFromRequestBody(request));
		data.putAll(initParamFromRequestParameter(request));
		if (data != null && data.size() > 0) {
			Field[] fields = clazz.getFields();
			for (Entry<String, Object> entry : data.entrySet()) {
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getName().equals(entry.getKey())) {
						String methodName = "set"
								+ fields[i].getName().substring(0, 1)
										.toUpperCase()
								+ fields[i].getName().substring(1);
						Method method = clazz.getMethod(methodName,
								fields[i].getType());
						try {
							method.invoke(t, BeanUtils.convertTo(
									entry.getValue(), fields[i].getType()));
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
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> initParamFromRequestParameter(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Map<String, Object> reqParams = request.getParameterMap();
		if (reqParams != null) {
			for (Entry<String, Object> entry : reqParams.entrySet()) {
				param.put(entry.getKey(), ((String[]) entry.getValue())[0]);
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
		} else if (object instanceof List) {
			map.put(object.toString(), object);
		} else {
			map.put(object.toString(), object);
		}
		return map;
	}
}
