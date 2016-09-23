package com.moseeker.mq.service.email;

/**
 * 
 * 不间断邮件的邮件数据 
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 23, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ConstantlyMessage extends Message {
	
	private int bizType;			//业务类型。用于选定具体的模版

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
}
