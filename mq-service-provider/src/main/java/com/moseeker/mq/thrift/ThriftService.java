package com.moseeker.mq.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return emailProvider.sendBizEmail(emailStruct.getUser_id(), emailStruct.getEmail(), emailStruct.getUrl(),
				emailStruct.getSubject());
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
}
