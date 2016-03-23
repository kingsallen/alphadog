package com.moseeker.common.util;

public class ExceptionResponse {
	public String message = "server error!";
	public int code = 500;
	public int status = 500;

	public ExceptionResponse(String message) {
		this.message = message;
	}
}
