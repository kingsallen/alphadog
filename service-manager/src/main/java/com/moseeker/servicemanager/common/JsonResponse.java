package com.moseeker.servicemanager.common;


public class JsonResponse {
	public String message = null;
	public String data = null;
	public int status=0;

	/*public static String success(String data){
		
		JsonResponse response = new JsonResponse();
		response.setStatus(0);
		response.setMessage(data);
		return JSON.toJSONString(response);
		
	}

	public static String fail(String message){
		JsonResponse response = new JsonResponse();
		response.setStatus(1);
		response.setMessage(message);
		return JSON.toJSONString(response);
		
	}
	
	public static String fail(int status, String message){
		JsonResponse response = new JsonResponse();
		response.setStatus(status);
		response.setMessage(message);
		return JSON.toJSONString(response);
		
	}	
	

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(int status) {
		this.status = status;
	}*/

}
