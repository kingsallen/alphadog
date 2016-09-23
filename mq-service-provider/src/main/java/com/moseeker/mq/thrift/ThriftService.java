package com.moseeker.mq.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.mq.service.impl.MqServiceImpl;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService.Iface;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;

/**
 * 
 * thrift服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 23, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class ThriftService implements Iface {
	
	@Autowired
	private MqServiceImpl mqService;

	@Override
	public Response messageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException {
		return mqService.messageTemplateNotice(messageTemplateNoticeStruct);
	}

	public MqServiceImpl getMqService() {
		return mqService;
	}

	public void setMqService(MqServiceImpl mqService) {
		this.mqService = mqService;
	}
}
