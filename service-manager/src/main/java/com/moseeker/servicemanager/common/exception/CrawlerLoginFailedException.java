package com.moseeker.servicemanager.common.exception;

import com.moseeker.common.constants.ConstantErrorCodeMessage;

public class CrawlerLoginFailedException extends ServiceManagerException {

	private static final long serialVersionUID = 2336388373153551366L;
	
	@Override
	public String getMessage() {
		return ConstantErrorCodeMessage.CRAWLER_LOGIN_FAILED;
	}
}
