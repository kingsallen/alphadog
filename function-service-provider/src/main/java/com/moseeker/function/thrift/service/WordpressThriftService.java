package com.moseeker.function.thrift.service;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.foundation.wordpress.service.WordpressService.Iface;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterData;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;

@Service
public class WordpressThriftService implements Iface {

	@Override
	public NewsletterData getNewsletter(NewsletterForm newsletter) throws TException {
		
		return null;
	}

}
