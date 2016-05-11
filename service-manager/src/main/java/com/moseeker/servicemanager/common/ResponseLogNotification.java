package com.moseeker.servicemanager.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Notification;
import com.moseeker.thrift.gen.common.struct.Response;

public class ResponseLogNotification {

	private final static int appid = 0;
	private final static String logkey = "LOG";
	private final static String eventkey = "RESTFUL_API_ERROR";

	public static String success(HttpServletRequest request, Response response) {
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		return jsonresponse;

	}

	public static String fail(HttpServletRequest request, Response response) {
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		int appid = 0;
		if (request.getParameter("appid") != null){
			appid = Integer.parseInt(request.getParameter("appid"));
		}
		Notification.sendNotification(appid, eventkey, response.getMessage());
		return jsonresponse;
	}

	public static String fail(HttpServletRequest request, String message) {
		Response response = new Response();
		response.setStatus(1);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		int appid = 0;
		if (request.getParameter("appid") != null){
			appid = Integer.parseInt(request.getParameter("appid"));
		}
		Notification.sendNotification(appid, eventkey, response.getMessage());
		return jsonresponse;
	}
	
	private static void logRequestResponse(HttpServletRequest request, String response) {
		Map reqResp = new HashMap();
		reqResp.put("appid", request.getParameter("appid"));
		reqResp.put("request", request.getParameterMap());
		reqResp.put("response", response);
		reqResp.put("remote_ip", ParamUtils.getRemoteIp(request));
		reqResp.put("web_server_ip", "192.22.22.22");

		try {
			RedisClientFactory.getLogClient().lpush(appid, logkey, JSON.toJSONString(reqResp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Notification.sendNotification(0, "MYSQL_CONNECT_ERROR", "mysql ip : 123.44.44.44");
	}

}
