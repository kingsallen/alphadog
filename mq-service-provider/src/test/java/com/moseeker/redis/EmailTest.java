package com.moseeker.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Test;

import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.mq.service.email.ConstantlyMail;

public class EmailTest {

	@Test
	public void sendEmail() {
		ConstantlyMail mailUtil = new ConstantlyMail();
		try {
			mailUtil.start();
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void sendMail() {
		try {
			MailBuilder mailBuilder = new MailBuilder();
			EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
			EmailContent emailContent = new EmailContent();
			emailContent.setCharset("utf-8");
			emailContent.setContent("<html><head><title>hello</title></head><body>hello world</body></html>");
			List<String> recipients = new ArrayList<>();
			recipients.add("wengjianfei@moseeker.com");
			emailContent.setRecipients(recipients);
			emailContent.setSenderName("jzh@moseeker.com");
			emailContent.setSenderDisplay("仟寻");
			emailContent.setSubject("hello world!");
			emailContent.setSubType("html");
			Mail mail = mailBuilder.buildSessionConfig(sessionConfig).buildMailServer();
			mail.send();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
