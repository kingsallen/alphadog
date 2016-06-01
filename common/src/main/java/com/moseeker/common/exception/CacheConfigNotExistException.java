package com.moseeker.common.exception;

public class CacheConfigNotExistException extends CommonException {

	private static final long serialVersionUID = -6574626288815577326L;

	@Override
	public String getMessage() {
		return "配置信息不存在";
	}

}
