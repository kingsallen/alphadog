package com.moseeker.common.constants;

import java.util.Date;

/**
 * 
 * 第三方帐号 bean 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ThirdPartyAccountPojo {

	private String username;
	private String password;
	private String memberName;
	private int remainNum;
	private Date syncTime;
	private ChannelType channel;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getRemainNum() {
		return remainNum;
	}
	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}
	public Date getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}
	public ChannelType getChannel() {
		return channel;
	}
	public void setChannel(ChannelType channel) {
		this.channel = channel;
	}
}
