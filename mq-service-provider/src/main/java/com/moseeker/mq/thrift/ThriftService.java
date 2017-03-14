package com.moseeker.mq.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.mq.service.impl.EmailProducer;
import com.moseeker.mq.service.impl.MandrillEmailProducer;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.mq.service.sms.SmsService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService.Iface;
import com.moseeker.thrift.gen.mq.struct.EmailStruct;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.SmsType;

/**
 * 
 * thrift服务
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Sep 23, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version
 */
@Service
public class ThriftService implements Iface {

	@Autowired
	private TemplateMsgProducer mqService;
	
	@Autowired
	private SmsService smsService;

	@Autowired
	private EmailProducer emailProvider;
	
	@Autowired
	private MandrillEmailProducer mandrillEmailProducer;
	
	@Override
	public Response messageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException {
		return mqService.messageTemplateNotice(messageTemplateNoticeStruct);
	}

	@Override
	public Response sendEMail(EmailStruct emailStruct) throws TException {
		//参数校验
		ValidateUtil vu = new ValidateUtil();
		vu.addRequiredStringValidate("邮箱", emailStruct.getEmail(), null, null);
		vu.addRequiredStringValidate("认证地址", emailStruct.getUrl(), null, null);
		vu.addIntTypeValidate("用户编号", emailStruct.getUser_id(), null, null, 1, null);
		String vuResult = vu.validate();
		
		if(StringUtils.isNullOrEmpty(vuResult)) {
			String username = emailProvider.genUsername(emailStruct.getUser_id());
			if (StringUtils.isNotNullOrEmpty(username)) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("# username #", username);
				params.put("# send_date #", new DateTime().toString("yyyy-MM-dd"));
				params.put("# verified_url #", emailStruct.getUrl());
				return emailProvider.sendBizEmail(params, emailStruct.getEventType(), emailStruct.getEmail(), emailStruct.getSubject());
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vuResult));
		}
		
	}

	@Override
	public Response sendMandrilEmail(MandrillEmailStruct mandrillEmailStruct) throws TException {
		// TODO Auto-generated method stub
		return mandrillEmailProducer.queueEmail(mandrillEmailStruct);
	}
	
	@Override
	public Response sendAuthEMail(Map<String, String> params, int eventType,
			String email, String subject) throws TException {
		return emailProvider.sendBizEmail(params, eventType, email, subject);
	}
	
	@Override
	public Response sendSMS(SmsType smsType, String mobile,
			Map<String, String> data) throws TException {
		return smsService.sendSMS(smsType, mobile, data);
	}
}
