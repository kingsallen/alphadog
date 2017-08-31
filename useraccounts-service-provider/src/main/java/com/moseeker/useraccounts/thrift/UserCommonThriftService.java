package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;
import com.moseeker.thrift.gen.useraccounts.service.UserCommonService.Iface;
import com.moseeker.useraccounts.service.impl.UserCommonService;

@Service
public class UserCommonThriftService implements Iface {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserCommonService userCommonService;

	@Override
	public Response newsletter(NewsletterForm form) throws TException {
		try {
			return userCommonService.newsletter(form);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}
}
