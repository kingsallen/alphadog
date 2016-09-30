package com.moseeker.mq.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.mq.service.impl.EmailProvider;
import com.moseeker.mq.service.impl.MqServiceImpl;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService.Iface;
import com.moseeker.thrift.gen.mq.struct.EmailStruct;
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
	private MqServiceImpl mqService;

	@Autowired
	private EmailProvider emailProvider;

	@Override
	public Response messageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException {
		return mqService.messageTemplateNotice(messageTemplateNoticeStruct);
	}

	@Override
	public Response sendEMail(EmailStruct emailStruct) throws TException {
		return emailProvider.sendBizEmail(emailStruct.getUser_id(), emailStruct.getEmail(), emailStruct.getUrl(),
				emailStruct.getSubject());
	}

	public MqServiceImpl getMqService() {
		return mqService;
	}

	public void setMqService(MqServiceImpl mqService) {
		this.mqService = mqService;
	}

	public EmailProvider getEmailProvider() {
		return emailProvider;
	}

	public void setEmailProvider(EmailProvider emailProvider) {
		this.emailProvider = emailProvider;
	}
}
