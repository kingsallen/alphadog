package com.moseeker.servicemanager.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.redis.RedisClientFactory;

public class ResponseLogNotifyalert {
	public String message = null;
	public String data = null;
	public int status=0;
	public int errcode;
	
	private final static int appid = 0;
	private final static String logkey = "LOG";	
	

	public static String success(HttpServletRequest request, String jsondata){
		
		ResponseLogNotifyalert response = new ResponseLogNotifyalert();
		response.setStatus(0);
		response.setData(jsondata);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);		
		return jsonresponse;
		
	}

	public static String fail(HttpServletRequest request, String message){
		ResponseLogNotifyalert response = new ResponseLogNotifyalert();
		response.setStatus(1);
		response.setErrcode(0);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);
		return jsonresponse;		
	}
	

	public static String fail(HttpServletRequest request, int errcode, String message){
		ResponseLogNotifyalert response = new ResponseLogNotifyalert();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);		
		return jsonresponse;
	}	
	
	public static String fail(HttpServletRequest request, int errcode, String message, String jsondata){
		ResponseLogNotifyalert response = new ResponseLogNotifyalert();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		response.setData(jsondata);
		String jsonresponse = JSON.toJSONString(response);
		logRequestResponse(request, jsonresponse);		
		return jsonresponse;
	}	
	
	private static void logRequestResponse(HttpServletRequest request, String response){
		Map reqResp = new HashMap();
		reqResp.put("request", request.getParameterMap());
		reqResp.put("response", response);
		reqResp.put("remote_ip", Spring.getRemoteIp(request));
		reqResp.put("web_server_ip", "192.22.22.22");

		try {
			RedisClientFactory.getInstance().getLog().lpush(appid, logkey, JSON.toJSONString(reqResp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
