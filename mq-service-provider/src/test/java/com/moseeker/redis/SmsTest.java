//package com.moseeker.redis;
//
//import java.util.HashMap;
//
//import org.apache.thrift.TException;
//import org.junit.Test;
//
//import com.alibaba.fastjson.JSONObject;
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.dict.struct.City;
//import com.moseeker.thrift.gen.mq.service.MqService;
//import com.moseeker.thrift.gen.mq.struct.SmsType;
//
//public class SmsTest {
//	
//	MqService.Iface service = ServiceManager.SERVICE_MANAGER.getService(MqService.Iface.class);
//	
//	//@Test
//	public void sendSMSTest() {
//		try {
//			//'mobile': '18058808209', 'sys': 1, 'ip': '::1', 'code': '18YJ4c'
//			service.sendSMS(SmsType.EMPLOYEE_MERGE_ACCOUNT_SMS, "13020287221", new HashMap<String, String>(){{put("mobile", "18058808209");put("code", "18YJ4c");}}, "1", "::1");
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
