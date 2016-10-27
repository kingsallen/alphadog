package com.moseeker.common.exception;

/**
 * 
 * 消息体初始化失败异常 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version v1.3.3
 */
public class InitConstantMessageException extends CommonException {

	private static final long serialVersionUID = 1982007458282752099L;

	@Override
	public String getMessage() {
		return "缺乏关键参数，错误消息初始化失败！";
	}

	
}
