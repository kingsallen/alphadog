package com.moseeker.common.email.config;

/**
 * 
 * 邮件协议配置
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 21, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class EmailSessionConfig {

	private boolean auth;		//是否验证
	private String protocol;	//邮件协议
	
	public EmailSessionConfig(boolean auth, String protocol) {
		super();
		this.auth = auth;
		this.protocol = protocol;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
