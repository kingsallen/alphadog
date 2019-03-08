package com.moseeker.mq.service.sms;

import com.moseeker.baseorm.dao.logdb.SmsSendrecordDao;
import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.logdb.LogSmsSendrecordDO;
import com.moseeker.thrift.gen.mq.struct.SmsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsService {
	
	private final Logger log = LoggerFactory.getLogger(SmsService.class);

	@Autowired
    private SmsSendrecordDao smsDao;

	@Autowired
	SmsSender smsSender;

	@CounterIface
	public Response sendSMS(SmsType type, String mobile, Map<String, String> data, String sys, String ip) {
		Response response;
		try {
			SmsTemplate smsTemplate = SmsTemplate.valueOf(type.name());
			if (smsSender.sendSMS(mobile, smsTemplate.getSmsCode(), data)) {
				response = ResponseUtils.success("{}");
				// 记录发送的短信信息
                LogSmsSendrecordDO smsDo = new LogSmsSendrecordDO();
				smsDo.setMobile(Double.valueOf(mobile));
				smsDo.setSys(Byte.valueOf(sys));
				smsDo.setIp(ip);
				smsDo.setMsg(smsTemplate.getSmsCode().concat(": ").concat(String.format(smsTemplate.getMsg(), data.values().toArray())));
				smsDao.addData(smsDo);
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
