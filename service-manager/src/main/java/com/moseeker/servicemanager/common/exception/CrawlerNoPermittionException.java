package com.moseeker.servicemanager.common.exception;

import com.moseeker.common.constants.ConstantErrorCodeMessage;

public class CrawlerNoPermittionException extends ServiceManagerException {

	private static final long serialVersionUID = 5762513661152245350L;

	@Override
	public String getMessage() {
		return ConstantErrorCodeMessage.CRAWLER_USER_NOPERMITION;
	}
}
