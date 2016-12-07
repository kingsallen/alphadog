package com.moseeker.apps.constants;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.InitConstantMessageException;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 
 * 消息
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum ResultMessage {

	THIRD_PARTY_ACCOUNT_NOT_BIND(10003, "没有同步的权限!"),
	THIRD_PARTY_POSITION_NOT_SYNC(10004, "没有刷新的权限!"),
	POSITION_NOT_ALLOW_REFRESH(100005, "职位不允许刷新!"),
	
	PROGRAM_PARAM_NOTEXIST(90015, "参数不正确!"),
	SUCCESS(0, "success"),
	PROGRAM_EXCEPTION(99999, "程序异常，请稍后再试！"),
	PROGRAM_EXHAUSTED(-1, "系统繁忙，请稍候再试!"),
	POSITION_NOT_EXIST(100002, "职位数据不存在!");
	
	
	private static final HashMap<Integer, ResultMessage> intToCode = new HashMap<>();  //code池
	
	private int status;			//状态码
	private String message;		//状态吗对应的消息
	
	ResultMessage(Object...objects ) {
		if(objects == null || objects[0] == null) {
			throw new InitConstantMessageException();
		}
		this.status = (Integer)objects[0];
		this.message = (String)objects[1];
	}
	/**
	 * 转通用操作结果类
	 * @return Response 通用操作结果类 
	 */
	public Response toResponse() {
		Response response = new Response();
		response.setStatus(status);
		response.setMessage(message);
		return response;
	}
	
	/**
	 * 转通用操作结果类
	 * @return Response 通用操作结果类 
	 */
	public Response toResponse(Object data) {
		Response response = new Response();
		response.setStatus(status);
		response.setMessage(message);
		response.setData(JSON.toJSONString(data));
		return response;
	}
	
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("message", message);
		return json.toJSONString();
	}
	
	/**
	 * 利用状态码查找消息
	 * @param status 状态码
	 * @return 消息
	 */
	public ResultMessage fromStatus(int status) {
		return intToCode.get(status);
	}
	
	/**
	 * 设置消息提示
	 * @param message
	 * @return
	 */
	public ResultMessage setMessage(String message) {
		this.message = message;
		return this;
	}
	
	static {
		for(ResultMessage code : values()) {
			//校验是否存在错误的消息状态码
			if(intToCode.containsKey(code.getStatus())) {
				throw new AssertionError("重复的消息状态码！");
			}
			intToCode.put(code.getStatus(), code);
		}
	}
	
	/**
	 * 获取状态码
	 * @return 状态码
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * 获取消息
	 * @return 消息
	 */
	public String getMessage() {
		return message;
	}
}
