package com.moseeker.common.constants;

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
public enum RespnoseUtil {

	USERACCOUNT_WECHAT_NOTEXISTS(10023, "新密码和旧密码不能一致！"),
	USERACCOUNT_WECHAT_ACCESSTOKEN_NOTEXISTS(10024, "公共号缺乏access_token！"),
	USERACCOUNT_WECHAT_GETQRCODE_FAILED(10025, "获取微信二维码失败!"),
	USERACCOUNT_WECHAT_TICKET_NOTEXISTS(10026, "票据信息不存在!"),
	USERACCOUNT_WECHAT_NOTSCAN(10027, "没能找到扫码状态，用户未扫码!"),
	USERACCOUNT_WECHAT_SCAN_ERROR(10028, "扫码失败!"),
	
	SUCCESS(0, "success"),
	PROGRAM_EXCEPTION(99999, "程序异常，请稍后再试！");
	;
	
	private static final HashMap<Integer, RespnoseUtil> intToCode = new HashMap<>();  //code池
	
	private int status;			//状态码
	private String message;		//状态吗对应的消息
	private Object data;		//返回内容
	
	RespnoseUtil(Object...objects ) {
		if(objects == null || objects[0] == null) {
			throw new InitConstantMessageException();
		}
		this.status = (Integer)objects[0];
		this.message = (String)objects[1];
		this.data = objects[2];
	}
	/**
	 * 转通用操作结果类
	 * @return Response 通用操作结果类 
	 */
	public Response toResponse() {
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
		json.put("data", data);
		return json.toJSONString();
	}
	
	/**
	 * 利用状态码查找消息
	 * @param status 状态码
	 * @return 消息
	 */
	public RespnoseUtil fromStatus(int status) {
		return intToCode.get(status);
	}
	
	/**
	 * 设置结果
	 * @param data
	 */
	public RespnoseUtil setData(Object data) {
		this.data = data;
		return this;
	}
	
	/**
	 * 设置消息提示
	 * @param message
	 * @return
	 */
	public RespnoseUtil setMessage(String message) {
		this.message = message;
		return this;
	}
	
	static {
		for(RespnoseUtil code : values()) {
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
