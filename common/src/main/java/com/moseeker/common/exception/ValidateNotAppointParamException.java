package com.moseeker.common.exception;


public class ValidateNotAppointParamException extends CommonException {

	private static final long serialVersionUID = -7168842782452730796L;

	@Override
	public String getMessage() {
		return "缺少必要地校验参数！";
	}

	
}
