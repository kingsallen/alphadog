package com.moseeker.servicemanager.common;

import com.alibaba.fastjson.JSON;

public class JsonResponse {
	public String message = null;
	public String data = null;
	public int status=0;
	public int errcode;

	public static String success(String jsondata){
		
		JsonResponse response = new JsonResponse();
		response.setStatus(0);
		response.setData(jsondata);
		
		return JSON.toJSONString(response);
		
	}

	public static String fail(String message){
		JsonResponse response = new JsonResponse();
		response.setStatus(1);
		response.setErrcode(0);
		response.setMessage(message);
		return JSON.toJSONString(response);
		
	}
	

	public static String fail(int errcode, String message){
		JsonResponse response = new JsonResponse();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		return JSON.toJSONString(response);	
	}	
	
	public static String fail(int errcode, String message, String jsondata){
		JsonResponse response = new JsonResponse();
		response.setStatus(1);
		response.setErrcode(errcode);
		response.setMessage(message);
		response.setData(jsondata);
		return JSON.toJSONString(response);	
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
