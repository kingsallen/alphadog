package com.moseeker.servicemanager.common.exception;

import com.moseeker.common.util.ConstantErrorCodeMessage;

public class CrawlerNoPermittion extends ServiceManagerException {

	private static final long serialVersionUID = 5762513661152245350L;

	@Override
	public String getMessage() {
		return ConstantErrorCodeMessage.APPLICATION_VALIDATE_COUNT_CHECK;
	}

	
}
