package com.moseeker.warn.service;

import java.util.List;

/**
 * @author ltf
 * 发送渠道
 */
public enum SendChannel {
	
	/**
	 * email邮件
	 */
	EMAIL {
		@Override
		public String send(List<String> recipients, String message) {
			// TODO email的发送过程
			return null;
		}
	},
	
	/**
	 * 手机短信 
	 */
	SMS {
		@Override
		public String send(List<String> recipients, String message) {
			// TODO sms的发送过程
			return null;
		}
	},
	
	/**
	 * 微信 
	 */
	WECHAT {
		@Override
		public String send(List<String> recipients, String message) {
			// TODO wechat的发送过程
			return null;
		}
	};
	
	/**
	 * @param recipients 收件人列表
	 * @param message 消息
	 * @return 回执
	 */
	public abstract String send(List<String> recipients, String message);
}
