package com.moseeker.function.thrift.service;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.function.service.wordpress.WordpressServiceImpl;
import com.moseeker.thrift.gen.foundation.wordpress.service.WordpressService.Iface;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterData;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;

/**
 * 代理wordpress功能
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 10, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class WordpressThriftService implements Iface {
	
	@Autowired
	private WordpressServiceImpl wordpressService;

	@Override
	public NewsletterData getNewsletter(NewsletterForm newsletter) throws TException {
		
		return wordpressService.getNewsletter(newsletter);
	}

	public WordpressServiceImpl getWordpressService() {
		return wordpressService;
	}

	public void setWordpressService(WordpressServiceImpl wordpressService) {
		this.wordpressService = wordpressService;
	}
}
