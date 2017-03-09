package com.moseeker.mq.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.mq.dao.UserDao;
import com.moseeker.mq.service.impl.EmailProducer;
import com.moseeker.mq.service.impl.MandrillEmailProducer;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService.Iface;
import com.moseeker.thrift.gen.mq.struct.EmailStruct;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;

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

	public TemplateMsgProducer getMqService() {
		return mqService;
	}

	public void setMqService(TemplateMsgProducer mqService) {
		this.mqService = mqService;
	}

	public EmailProducer getEmailProvider() {
		return emailProvider;
	}

	public void setEmailProvider(EmailProducer emailProvider) {
		this.emailProvider = emailProvider;
	}

	@Override
	public Response sendMandrilEmail(MandrillEmailStruct mandrillEmailStruct) throws TException {
		// TODO Auto-generated method stub
		return mandrillEmailProducer.queueEmail(mandrillEmailStruct);
	}

	public MandrillEmailProducer getMandrillEmailProducer() {
		return mandrillEmailProducer;
	}

	public void setMandrillEmailProducer(MandrillEmailProducer mandrillEmailProducer) {
		this.mandrillEmailProducer = mandrillEmailProducer;
	}

	@Override
	public Response sendAuthEMail(Map<String, String> params, int eventType,
			String email, String subject) throws TException {
		return emailProvider.sendBizEmail(params, eventType, email, subject);
	}
}
