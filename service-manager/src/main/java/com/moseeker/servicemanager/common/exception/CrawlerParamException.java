package com.moseeker.servicemanager.common.exception;

import com.moseeker.common.util.ConstantErrorCodeMessage;

public class CrawlerParamException extends ServiceManagerException {
	
	private static final long serialVersionUID = -3279673691741694121L;

	@Override
	public String getMessage() {
		return ConstantErrorCodeMessage.CRAWLER_PARAM_ILLEGAL;
	}

}
