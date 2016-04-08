package com.moseeker.servicemanager.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerMapping;

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
																			// tornado
																			// 不一致，
																			// 需要实际测试。
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
	 */
	public static <T> T initCommonQuery(HttpServletRequest request, T t) {
		if (t != null) {
			try {
				Integer appId = request.getParameter("appid") == null ? null
						: Integer.parseInt(request.getParameter("appid"));
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
	 */
	public static <T> T initCommonQuery(HttpServletRequest request,
			Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
			initCommonQuery(request, t);
		} catch (InstantiationException | IllegalAccessException e) {
			// warning 报警/logging 日志
		}

		return t;
	}
}
