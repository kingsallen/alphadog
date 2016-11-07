package com.moseeker.warn.uitls;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.StringUtils;
import com.moseeker.warn.dto.Member;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;

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
		public String send(List<Member> recipients, String message) {
//			getLog().info("start send email to={0}, message={1}", Arrays.toString(recipients.toArray()), message);
			System.out.println(MessageFormat.format("start send email to={0}, message={1}", Arrays.toString(recipients.toArray()), message));
			EmailContent emailContent = new EmailContent();
			emailContent.setSubject("预警信息");
			emailContent.setContent(message);
			emailContent.setRecipients(new ArrayList<String>());
			recipients.forEach(member -> {
				if (StringUtils.isNotNullOrEmpty(member.getEmail())) {
					emailContent.getRecipients().add(member.getEmail());
				}
			});
			
			EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
			MailBuilder mailBuilder;
			Mail mail;
			try {
				mailBuilder = new MailBuilder();
				mail = mailBuilder.buildSessionConfig(sessionConfig).build(emailContent);
				mail.send();
			} catch (Exception e) {
				getLog().error("send email fail", e);
			}
			return null;
		}
	},
	
	/**
	 * 手机短信 
	 */
	SMS {
		@Override
		public String send(List<Member> recipients, String message) {
			getLog().info("start send sms to={0}, message={1}", Arrays.toString(recipients.toArray()), message);
			return null;
		}
	},
	
	/**
	 * 微信 
	 */
	WECHAT {
		@Override
		public String send(List<Member> recipients, String message) {
//			getLog().info("start send wechat to={0}, message={1}", Arrays.toString(recipients.toArray()), message);
			System.out.println(MessageFormat.format("start send wechat to={0}, message={1}", Arrays.toString(recipients.toArray()), message));
			return null;
		}
	};
	
	/**
	 * @param recipients 收件人信息{@link Member}
	 * @param message 消息
	 * @return 回执
	 */
	public abstract String send(List<Member> recipients, String message);
	
	public Logger getLog(){
		return LoggerFactory.getLogger(getClass());
	}
}
