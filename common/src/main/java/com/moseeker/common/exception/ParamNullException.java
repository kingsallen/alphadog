package com.moseeker.common.exception;

public class ParamNullException extends CommonException {

	private static final long serialVersionUID = -3692990533499508228L;

	@Override
	public String getMessage() {
		return "参数不能为空！";
	}

	
}
