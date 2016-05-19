package com.moseeker.common.providerutils;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.ParamNullException;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.Response;

public class ResponseUtils {
	public String message = null;
	public String data = null;
	public int status = 0;
	
	public static Response buildFromConstant(String constantMessage) throws ParamNullException {
		Response response = new Response();
		if(StringUtils.isNullOrEmpty(constantMessage)) {
			throw new ParamNullException();
		}
		JSONObject jsonObject = JSONObject.parseObject(constantMessage);
		response.setData(Constant.NONE_JSON);
		response.setStatus(jsonObject.getIntValue("status"));
		response.setMessage(jsonObject.getString("message"));
		return response;
	}

	public static Response success(Object hashmap) {

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

	public static Response fail(int errcode, String message, Map<String, Object> hashmap) {
		Response response = new Response();
		response.setStatus(errcode);
		response.setMessage(message);
		response.setData(JSON.toJSONString(hashmap));
		return response;
	}
}
