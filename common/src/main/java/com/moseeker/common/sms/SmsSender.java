package com.moseeker.common.sms;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsSender {

	private static TaobaoClient taobaoclient;
	private static Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);

	public static TaobaoClient initTaobaoClientInstance() {
		if (taobaoclient == null) {
			synchronized (SmsSender.class) {
				if (taobaoclient == null) {
					ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
							.getInstance();
					String smsAccesskeyId = propertiesUtils.get("sms.accesskeyid",
							String.class);
					String smsAccesskeySecret = propertiesUtils.get("sms.accesskeysecret",
							String.class);
					taobaoclient = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", smsAccesskeyId, smsAccesskeySecret);
				}
			}
		}
		return taobaoclient;
	}	

	public static boolean sendSMS(String mobile, String templateCode, HashMap params){
		initTaobaoClientInstance();

		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(mobile);
		req.setSmsType("normal");
		req.setSmsFreeSignName("仟寻");

		if (params != null){
			req.setSmsParamString(JSON.toJSONString(params));
		}

		req.setRecNum(mobile);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp;

		try {
			rsp = taobaoclient.execute(req);
			if (rsp.getBody().indexOf("success")>-1) 
				return true;
			else{
				LOGGER.warn("短信发送失败:" + rsp.getBody());
			}
		} catch (ApiException e) {
			LOGGER.warn("短信发送失败:" + e.getMessage());
		}
		return false;	
	}
	/**
	 *      SMS_5755096
	 *      您的验证码是：${code}。请不要把验证码泄露给其他人。	
	 * @param mobile
	 * @return
	 */
	public static boolean sendSMS_signup(String mobile){
		HashMap<String, String> params = new HashMap<String, String>();
		String signupcode = getRandomStr();
		params.put("code", signupcode);	
		RedisClientFactory.getCacheClient().set(0, "SMS_SIGNUP", mobile, signupcode);
		return sendSMS(mobile,"SMS_5755096",params);
	} 

	/**
	 *      SMS_5755096
	 *      您的验证码是：${code}。请不要把验证码泄露给其他人。	
	 * @param mobile
	 * @return
	 */
	public static boolean sendSMS_passwordforgot(String mobile){
		HashMap<String, String> params = new HashMap<String, String>();
		String passwordforgotcode = getRandomStr();
		params.put("code", passwordforgotcode);		
		RedisClientFactory.getCacheClient().set(0, "SMS_SIGNUP", mobile, passwordforgotcode);		
		return sendSMS(mobile,"SMS_5755096",params);
	} 
	public static void main(String[] args) {
		sendSMS_signup("13818252514");
	}
	
	private static String getRandomStr(){
		return String.valueOf((int) (Math.random()*9000+1000));
	}

}
