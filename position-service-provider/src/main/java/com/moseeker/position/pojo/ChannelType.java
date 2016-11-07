package com.moseeker.position.pojo;

/**
 * 
 * 渠道 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum ChannelType {

	JOB51(1), LIANPIAN(2), ZHILIAN(3), LINKEDIN(4);
	
	private int value;
	
	private ChannelType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
