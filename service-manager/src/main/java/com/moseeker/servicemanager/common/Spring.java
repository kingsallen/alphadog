package com.moseeker.servicemanager.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

public class Spring {
	public static String getRestfullApiName(HttpServletRequest request){
		  String path = (String) request.getAttribute(
		            HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		  return path;
	}
	
	public static CommonQuery initCommonQuery(CommonQuery query, HttpServletRequest request) throws Exception{
		if (request.getParameter("appid") != null) {
			int appid = Integer.parseInt(request.getParameter("appid"));
			if (appid > 0) {
				query.setAppid(appid);
			}
		}else{
			throw new Exception("appid is empty!") ;
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
			boolean nocache = Boolean.valueOf(request.getParameter("nocache"))
					.booleanValue();
			query.setNocache(nocache);
		}
		
		return query;

		
	}

}
