package com.moseeker.mq.service.email;

import java.util.ArrayList;
import java.util.List;

import com.moseeker.common.email.mail.Message;

public class WarningMail {
	
	private int interval; //发送相同邮件的时间间隔

	private List<Message> fetchWarningMessage() {
		List<Message> messages = new ArrayList<>();
		
		return messages;
	}
}
