package com.moseeker.servicemanager.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerMapping;

public class ParamUtils {
	public static String getRestfullApiName(HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return path;
	}

	public static String getRemoteIp(HttpServletRequest request) {
		String remoteIpForwardedbyLbs = request.getHeader("REMOTE_ADDR");// php 和 python tornado不一致，需要实际测试。
		return remoteIpForwardedbyLbs == null ? request.getRemoteAddr() : remoteIpForwardedbyLbs;
	}

	public static CommonQuery initCommonQuery(CommonQuery query, HttpServletRequest request) throws Exception {

		if (request.getParameter("appid") != null) {
			int appid = Integer.parseInt(request.getParameter("appid"));
			if (appid > 0) {
				query.setAppid(appid);
			}
		} else {
			throw new Exception("appid is empty!");
		}

		if (request.getParameter("limit") != null) {
			int limit = Integer.parseInt(request.getParameter("limit"));
			if (limit > 0) {
				query.setLimit(limit);
			}
		}

		if (request.getParameter("offset") != null) {
			int offset = Integer.parseInt(request.getParameter("offset"));
			if (offset > 0) {
				query.setOffset(offset);
			}
		}

		if (request.getParameter("page") != null) {
			int page = Integer.parseInt(request.getParameter("page"));
			if (page > 0) {
				query.setPage(page);
			}
		}

		if (request.getParameter("per_page") != null) {
			int per_page = Integer.parseInt(request.getParameter("per_page"));
			if (per_page > 0) {
				query.setPer_page(per_page);
			}
		}

		if (request.getParameter("sortby") != null) {
			String sortby = request.getParameter("sortby");
			query.setSortby(sortby);
		}
		if (request.getParameter("order") != null) {
			String order = request.getParameter("order");
			query.setOrder(order);

		}
		if (request.getParameter("fields") != null) {
			String fields = request.getParameter("fields");
			query.setFields(fields);

		}

		if (request.getParameter("nocache") != null) {
			boolean nocache = Boolean.valueOf(request.getParameter("nocache")).booleanValue();
			query.setNocache(nocache);
		}

		return query;

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
	public static <T> T initCommonQuery(HttpServletRequest request, T t) throws Exception {
		if (t != null) {
			try {
				Integer appId = request.getParameter("appid") == null ? null
						: Integer.parseInt(request.getParameter("appid"));

				if (appId == null) {
					throw new Exception(" failed to get appid.");
				}

				if (appId != null) {
					Method method = t.getClass().getMethod("setAppid", int.class);
					method.invoke(t, appId);
				}
				Integer limit = request.getParameter("limit") == null ? null
						: Integer.parseInt(request.getParameter("limit"));
				if (limit != null) {
					Method method = t.getClass().getMethod("setLimit", int.class);
					method.invoke(t, limit);
				}
				Integer offset = request.getParameter("offset") == null ? null
						: Integer.parseInt(request.getParameter("offset"));
				if (offset != null) {
					Method method = t.getClass().getMethod("setOffset", int.class);
					method.invoke(t, offset);
				}
				Integer page = request.getParameter("page") == null ? null
						: Integer.parseInt(request.getParameter("page"));
				if (page != null) {
					Method method = t.getClass().getMethod("setPage", int.class);
					method.invoke(t, page);
				}
				Integer perPage = request.getParameter("per_page") == null ? null
						: Integer.parseInt(request.getParameter("per_page"));
				if (perPage != null) {
					Method method = t.getClass().getMethod("setPer_page", int.class);
					method.invoke(t, perPage);
				}
				String sortby = request.getParameter("sortby") == null ? null : request.getParameter("sortby");
				if (sortby != null) {
					Method method = t.getClass().getMethod("setSortby", String.class);
					method.invoke(t, sortby);
				}
				String order = request.getParameter("order") == null ? null : request.getParameter("order");
				if (order != null) {
					Method method = t.getClass().getMethod("setOrder", String.class);
					method.invoke(t, order);
				}
				String fields = request.getParameter("fields") == null ? null : request.getParameter("fields");
				if (fields != null) {
					Method method = t.getClass().getMethod("setFields", String.class);
					method.invoke(t, fields);
				}
				Boolean nocache = request.getParameter("nocache") == null ? null
						: Boolean.parseBoolean(request.getParameter("nocache"));
				if (nocache != null) {
					Method method = t.getClass().getMethod("setNocache", boolean.class);
					method.invoke(t, nocache);
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
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
	public static <T> T initCommonQuery(HttpServletRequest request, Class<T> clazz) throws Exception {
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

}
