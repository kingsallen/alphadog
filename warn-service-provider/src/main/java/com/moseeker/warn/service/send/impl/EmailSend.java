package com.moseeker.warn.service.send.impl;
import java.util.Map;

import com.moseeker.warn.dto.Event;
import com.moseeker.warn.service.email.EmailService;
import com.moseeker.warn.service.email.OperatorEmailMessage;
import com.moseeker.warn.service.send.SendParent;

public class EmailSend implements SendParent {

	@Override
	public void send(Map<String,Object> map) throws Exception {
		// TODO Auto-generated method stub
		String msg=new OperatorEmailMessage(map).Operator();
		new EmailService(msg).send();
	}
}
