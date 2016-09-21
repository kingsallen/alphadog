package com.moseeker.mq.email.config;

/**
 * 
 * 邮件服务器配置 
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 21, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class EmailServerConfig {

	private String host;			//域名
	private int port;				//端口
	private EmailAccountConifg account;	//账号
	private String protocol;		//协议
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public EmailAccountConifg getAccount() {
		return account;
	}
	public void setAccount(EmailAccountConifg account) {
		this.account = account;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
