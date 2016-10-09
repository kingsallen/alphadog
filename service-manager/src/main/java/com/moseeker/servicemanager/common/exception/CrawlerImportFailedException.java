package com.moseeker.servicemanager.common.exception;

import com.moseeker.common.constants.ConstantErrorCodeMessage;

public class CrawlerImportFailedException extends ServiceManagerException {

	private static final long serialVersionUID = -3131840730134240483L;

	@Override
	public String getMessage() {
		return ConstantErrorCodeMessage.CRAWLER_IMPORT_FAILED;
	}
}
