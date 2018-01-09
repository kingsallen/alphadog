package com.moseeker.mq.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.mq.service.impl.EmailProducer;
import com.moseeker.mq.service.impl.MandrillEmailProducer;
import com.moseeker.mq.service.impl.ResumeDeliveryService;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.mq.service.sms.SmsService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService.Iface;
import com.moseeker.thrift.gen.mq.struct.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ResumeDeliveryService deliveryService;

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
                ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance();
				String subject = propertiesUtil.get("email.verify.subject", String.class);
				String senderName = propertiesUtil.get("email.verify.sendName", String.class);
				String senderDisplay = propertiesUtil.get("email.verify.sendDisplay", String.class);
				return emailProvider.sendBizEmail(params, emailStruct.getEventType(), emailStruct.getEmail(), subject, senderName, senderDisplay);
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
    public Response sendAuthEMail(Map<String, String> params, int eventType, String email, String subject, String senderName, String senderDisplay) throws TException {
        return emailProvider.sendBizEmail(params, eventType, email, subject, senderName, senderDisplay);
    }

	@Override
	public Response sendSMS(SmsType smsType, String mobile,
			Map<String, String> data, String sys, String ip) throws TException {
		return smsService.sendSMS(smsType, mobile, data, sys, ip);
	}

    @Override
    public Response sendMessageAndEmail(int application_id) throws TException{
        return null;
    }

    @Override
    public Response sendMessageAndEmailToDelivery(MessageEmailStruct messageEmailStruct) throws TException {
        return deliveryService.sendMessageAndEmail(messageEmailStruct);
    }
}
