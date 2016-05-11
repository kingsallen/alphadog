package com.moseeker.common.providerutils;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.moseeker.thrift.gen.common.struct.Response;

public class ResponseUtils {
	public String message = null;
	public String data = null;
	public int status = 0;

	public static Response success(Map hashmap) {

		Response response = new Response();
		response.setStatus(0);
		response.setMessage("ok");
		response.setData(JSON.toJSONString(hashmap));
		return response;

	}

	public static Response fail(String message) {
		Response response = new Response();
		response.setStatus(1);
		response.setMessage(message);
		return response;
	}

	public static Response fail(int errcode, String message) {
		Response response = new Response();
		response.setStatus(errcode);
		response.setMessage(message);
		return response;
	}

	public static Response fail(int errcode, String message, Map hashmap) {
		Response response = new Response();
		response.setStatus(errcode);
		response.setMessage(message);
		response.setData(JSON.toJSONString(hashmap));
		return response;
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

}
