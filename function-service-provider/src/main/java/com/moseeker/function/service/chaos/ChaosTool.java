package com.moseeker.function.service.chaos;

import java.util.HashMap;

import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 *
 * chaos帮助类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 8, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 * @version
 */
public class ChaosTool {

	/**
	 * 生成参数信息
	 * @param username 帐号名称
	 * @param password 密码
	 * @param memberName 会员名
	 * @param channel 渠道类型
	 * @return
	 */
	public static String getParams(String username, String password, String memberName, ChannelType channel) {
		HashMap<String, Object> pramas = new HashMap<String, Object>();
		switch (channel) {
		case JOB51:
			pramas.put("user_name", username);
			pramas.put("password", password);
			pramas.put("member_name", memberName);
			break;
		case LIANPIAN:
			break;
		case ZHILIAN:
			pramas.put("user_name", username);
			pramas.put("password", password);
			break;
		case LINKEDIN:
			break;
		default:break;
		}
		return JSON.toJSONString(pramas);
	}

	public static Response createResponse(String data) {
		Response response = new Response();
		try {
			JSONObject json = JSONObject.parseObject(data);
//			0 运行完成，返回结果
//			1 账号密码错误
//			2 网络超时
//			3 验证码错误，处理方式同异常
//			4 捕获异常，操作中断
			int status = json.getIntValue("status");
			response.setStatus(1);
			if(status == 0) {
				response.setData(json.getString("data"));
				response.setStatus(status);
				response.setMessage(json.getString("message"));
			}else if (status == 1){
				response.setMessage("账号或者密码错误！");
			}else if(status == 2){
				response.setMessage("绑定超时了，请重试！");
			}else if(status == 3){
				response.setMessage("绑定失败了，请重试！");
			}else if(status== 4){
				response.setMessage("绑定失败了，请稍后重试！");
			}else{
				response.setMessage("发生异常，请稍后重试！");
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(ChaosTool.class).error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
		return response;
	}
}
