package com.moseeker.redis;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;

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
}
