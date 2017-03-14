package com.moseeker.redis;

import java.util.HashMap;

import org.apache.thrift.TException;
import org.junit.Test;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.SmsType;

public class SmsTest {
	
	MqService.Iface service = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
	
	@Test
	public void sendSMSTest() {
		try {
			service.sendSMS(SmsType.RANDOM_SMS, "13020287221", new HashMap<String, String>(){{put("code", "123456");}});
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
