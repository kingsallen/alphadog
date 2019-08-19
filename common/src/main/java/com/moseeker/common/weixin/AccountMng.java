package com.moseeker.common.weixin;

import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.UrlUtil;

/**
 * 
 * 微信公众号帐号管理 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class AccountMng {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String qucreateUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create"; //微信二维码请求地址
	private static String qrPicUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	
	private static int expireSeconds = 7200;   								//该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	private static QrcodeType actionName = QrcodeType.QR_SCENE;			//二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
	private static int sceneId = 0;										//场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	
	/**
	 * 创建ticket
	 * @param expireSeconds 过期时间
	 * @param actionName 二维码类型
	 * @param sceneId	场景值
	 * @param sceneStr 场景值
	 * @param accessToken 公众号token
	 * @return
	 * @throws ConnectException 
	 */
	public static WeixinTicketBean createTicket(String accessToken, Integer expireSeconds, QrcodeType actionName, Long sceneId, String sceneStr) throws ConnectException {
		WeixinTicketBean bean = null;
		JSONObject params = initParams(expireSeconds, actionName, sceneId, sceneStr);
		LoggerFactory.getLogger(AccountMng.class).info("AccountMng createTicket params {}",params.toJSONString());
		String result = UrlUtil.sendPost(qucreateUrl+"?access_token="+accessToken, params.toJSONString());
		JSONObject jo = JSON.parseObject(result);
		if(!jo.containsKey("errcode")) {
			bean = jo.toJavaObject(WeixinTicketBean.class);
		} else {
			LoggerFactory.getLogger(AccountMng.class).error(result);
		}
		return bean;
	}
	
	/**
	 * 利用ticket获取二维码图片数据
	 * @param ticket 票据
	 * @return
	 */
	public static String getQrcode(String ticket) {
		return UrlUtil.sendGet(qrPicUrl, "ticket="+ticket);
	}

	/**
	 * 初始化参数
	 * @param expireSeconds2 过期时间
	 * @param actionName2 二维码类型
	 * @param sceneId2 场景值
	 * @param sceneStr2 场景值
	 * @return
	 */
	private static JSONObject initParams(Integer expireSeconds2, QrcodeType actionName2, Long sceneId2, String sceneStr2) {
		JSONObject paramJson = new JSONObject();
		if(expireSeconds2 != null && expireSeconds2 > 0) {
			paramJson.put("expire_seconds", expireSeconds2);
		} else {
			paramJson.put("expire_seconds", expireSeconds);
		}
		if(actionName2 != null) {
			paramJson.put("action_name", actionName2.toString());
		} else {
			paramJson.put("action_name", actionName.toString());
		}
		if(sceneId2 != null) {
			JSONObject senceId = new JSONObject();
			senceId.put("scene_id", sceneId2);
			
			JSONObject sence = new JSONObject();
			sence.put("scene", senceId);
			paramJson.put("action_info", sence);
		} else if(StringUtils.isNotNullOrEmpty(sceneStr2)) {
			JSONObject senceId = new JSONObject();
			senceId.put("scene_str", sceneStr2);
			
			JSONObject sence = new JSONObject();
			sence.put("scene", senceId);
			paramJson.put("action_info", sence);
		} else {
			JSONObject senceId = new JSONObject();
			senceId.put("scene_id", sceneId);
			
			JSONObject sence = new JSONObject();
			sence.put("scene", senceId);
			paramJson.put("action_info", sence);
		}
		return paramJson;
	}
}
