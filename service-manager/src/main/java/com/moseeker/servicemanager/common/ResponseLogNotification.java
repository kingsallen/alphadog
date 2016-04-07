package com.moseeker.servicemanager.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Notification;

public class ResponseLogNotification {
	public String message = null;
	public String data = null;
	public int status = 0;
	public int errcode;

	private final static int appid = 0;
	private final static String logkey = "LOG";
	private final static String eventkey = "RESTFUL_API_ERROR";

	public static String success(HttpServletRequest request, String jsondata) {

		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(0);
		response.setData(jsondata);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		return jsonresponse;

	}

	public static String fail(HttpServletRequest request, String message) {
		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(1);
		response.setErrcode(0);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		int appid = 0;
		if (request.getParameter("appid") != null){
			appid = Integer.parseInt(request.getParameter("appid"));
		}
		Notification.sendNotification(appid, eventkey, message);
		return jsonresponse;
	}

	public static String fail(HttpServletRequest request, int errcode, String message) {
		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		int appid = 0;
		if (request.getParameter("appid") != null){
			appid = Integer.parseInt(request.getParameter("appid"));
		}
		Notification.sendNotification(appid, eventkey, message);
		return jsonresponse;
	}

	public static String fail(HttpServletRequest request, int errcode, String message, String jsondata) {
		ResponseLogNotification response = new ResponseLogNotification();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		response.setData(jsondata);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		int appid = 0;
		if (request.getParameter("appid") != null){
			appid = Integer.parseInt(request.getParameter("appid"));
		}
		Notification.sendNotification(appid, eventkey, message + "," + jsondata);		
		return jsonresponse;
	}

	private static void logRequestResponse(HttpServletRequest request, String response) {
		Map reqResp = new HashMap();
		reqResp.put("appid", request.getParameter("appid"));
		reqResp.put("request", request.getParameterMap());
		reqResp.put("response", response);
		reqResp.put("remote_ip", Spring.getRemoteIp(request));
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

	private void setMessage(String message) {
		this.message = message;
	}

	private void setStatus(int status) {
		this.status = status;
	}

	private void setData(String jsondata) {
		this.data = jsondata;
	}

	private void setErrcode(int errcode) {
		this.errcode = errcode;
	}

}
