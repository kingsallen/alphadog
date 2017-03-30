package com.moseeker.common.exception;

import com.moseeker.common.util.StringUtils;

/**
 * 
 * 异常基类。继承RuntimeException{@see java.lang.RuntimeException} 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 30, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 1982007458282752099L;

	public CommonException() {}

	public CommonException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getMessage() {
		if(StringUtils.isNullOrEmpty(this.message)) {
			return super.getMessage();
		} else {
			return message;
		}

	}

	public int getCode() {
		return code;
	}

	protected void setCode(int code) {
		this.code = code;
	}

	protected void setMessage(String msg) {
		this.message = msg;
	}

	private int code;
	private String message;
}
