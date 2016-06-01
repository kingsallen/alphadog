package com.moseeker.common.exception;


public class ValidateIllegalParamException extends CommonException {

	private static final long serialVersionUID = -5446296401078891273L;

	@Override
	public String getMessage() {
		return "参数指定有误！";
	}

}
