package com.moseeker.common.exception;


public class ValidateNotAppointValidateException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2028400976261013276L;

	@Override
	public String getMessage() {
		return "未指定任何校验规则!";
	}
}
