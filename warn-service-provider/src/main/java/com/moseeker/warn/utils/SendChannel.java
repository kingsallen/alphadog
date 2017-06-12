package com.moseeker.warn.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.moseeker.thrift.gen.dao.struct.configdb.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.common.util.StringUtils;

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
		public void send(List<Member> recipients, String message) {
			threadPool.execute(() -> {
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
					getLog().info("start send email to={}, message={}", Arrays.toString(recipients.toArray()), message);
				} catch (Exception e) {
					getLog().error("send email fail", e);
				}
			});
		}
	},
	
	/**
	 * 手机短信 
	 */
	SMS {
		@Override
		public void send(List<Member> recipients, String message) {
			getLog().info("start send sms to={}, message={}", Arrays.toString(recipients.toArray()), message);
		}
	},
	
	/**
	 * 微信 
	 */
	WECHAT {
		@Override
		public void send(List<Member> recipients, String message) {
			getLog().info("start send wechat to={}, message={}", Arrays.toString(recipients.toArray()), message);
		}
	};
	
	/**
	 * @param recipients 收件人信息{@link Member}
	 * @param message 消息
	 * @return 回执
	 */
	public abstract void send(List<Member> recipients, String message);
	
	/**
	 * 线程池 [用于邮件、短信、微信的发送]
	 */
	public static final ExecutorService threadPool = new ThreadPoolExecutor(5, 15, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	
	public Logger getLog(){
		return LoggerFactory.getLogger(getClass());
	}
}
