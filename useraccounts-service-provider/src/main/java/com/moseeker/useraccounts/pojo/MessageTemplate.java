package com.moseeker.useraccounts.pojo;

/**
 * 
 * 发给HR的用户感兴趣的消息体 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 3, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class MessageTemplate {

	private String positionTitle; //刚兴趣职位
	private String name;		  //用户名称
	private String contact;		  //联系方式
	private int hrAccountId;	  //HR编号
	
	public String getPositionTitle() {
		return positionTitle;
	}
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getHrAccountId() {
		return hrAccountId;
	}
	public void setHrAccountId(int hrAccountId) {
		this.hrAccountId = hrAccountId;
	}
}
