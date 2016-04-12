package com.moseeker.common.proxy;

/**
 * 
 * 定义代理工具的执行方法 
 * <p>Company: MoSeeker</P>  
 * <p>date: Apr 12, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public interface Execution {

	public void beforeMethod();
	
	public void afterMethod();
}
