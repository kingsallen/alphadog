package com.moseeker.warn.service.send.impl;
import java.util.Map;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.warn.service.send.SendParent;
public class SmsSend implements SendParent {

	@Override
	public void send(Map<String,Object> map)throws Exception  {
		// TODO Auto-generated method stub
		new SmsSender().sendSMS("", "", null);
	}

}
