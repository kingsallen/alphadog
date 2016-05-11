package com.moseeker.common.exception;

import org.apache.thrift.TException;

public class TCodeMessageException extends TException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2811118985078134439L;
	private int code;

	public TCodeMessageException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
