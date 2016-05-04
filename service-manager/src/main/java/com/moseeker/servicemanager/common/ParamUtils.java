package com.moseeker.servicemanager.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.common.util.BeanUtils;

public class ParamUtils {
	public static String getRestfullApiName(HttpServletRequest request) {
		String path = (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return path;
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
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// warning 报警 / logging 日志
				e.printStackTrace();
			}
		}
		return t;
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
		T t = null;
		try {
			t = clazz.newInstance();
			initCommonQuery(request, t);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}

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
				for(int i=0; i<fields.length-1; i++) {
					if(fields[i].getName().equals(entry.getKey())) {
						String methodName = "set"
								+ fields[i].getName().substring(0, 1).toUpperCase()
								+ fields[i].getName().substring(1);
						Method method = clazz.getMethod(methodName, fields[i].getType());
						try {
							method.invoke(t, BeanUtils.convertTo(entry.getValue(), fields[i].getType()));
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

	@SuppressWarnings("unchecked")
	private static Map<String, Object> initParamFromRequestParameter(
			HttpServletRequest request) {
		System.out.println(request.getParameter("uuid"));
		return request.getParameterMap();
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
		//校验jsonStr格式是否正确
		if(!jsonStr.startsWith("{") || !jsonStr.endsWith("}")) {
			return map;
		}
		Object object = JSON.parse(jsonStr);;
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
