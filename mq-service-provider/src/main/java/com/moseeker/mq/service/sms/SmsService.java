package com.moseeker.mq.service.sms;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.LogDBDao;
import com.moseeker.thrift.gen.dao.struct.LogSmsSendRecordDO;
import com.moseeker.thrift.gen.mq.struct.SmsType;

@Service
public class SmsService {
	
	private final Logger log = LoggerFactory.getLogger(SmsService.class);
	
	private LogDBDao.Iface logDBDao = ServiceManager.SERVICEMANAGER.getService(LogDBDao.Iface.class);
	
	public Response sendSMS(SmsType type, String mobile, Map<String, String> data, String sys, String ip) {
		Response response;
		try {
			SmsTemplate smsTemplate = SmsTemplate.valueOf(type.name());
			if (SmsSender.sendSMS(mobile, smsTemplate.getSmsCode(), data)) {
				response = ResponseUtils.success("{}");
				// 记录发送的短信信息
				LogSmsSendRecordDO smsDo = new LogSmsSendRecordDO();
				smsDo.setMobile(mobile);
				smsDo.setSys(Byte.valueOf(sys));
				smsDo.setIp(ip);
				smsDo.setMsg(smsTemplate.getSmsCode().concat(": ").concat(String.format(smsTemplate.getMsg(), data.values().toArray())));
				logDBDao.saveSmsSenderRecord(smsDo);
			} else {
				response = ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			response = ResponseUtils.fail("not found sms_code by ".concat(type.name()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = ResponseUtils.fail(ConstantErrorCodeMessage.USER_SMS_LIMITED);
		}
		return response;
	}
}
