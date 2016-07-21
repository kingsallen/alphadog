package com.moseeker.common.exception;

public class ParamIllegalException extends CommonException {

	private static final long serialVersionUID = -5323723624613983749L;
	
	private String message;
	
	public ParamIllegalException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	
}
