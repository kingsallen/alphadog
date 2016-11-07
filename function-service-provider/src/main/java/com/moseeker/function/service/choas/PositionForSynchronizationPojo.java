package com.moseeker.function.service.choas;

/**
 * 
 * 职位同步表单数据 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class PositionForSynchronizationPojo {

	private int id;
	private String address;
	private String occupationLevel1;
	private String occupationLevel2;
	private ChannelType channel;
	private int salaryTop;
	private int salaryBottom;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOccupationLevel1() {
		return occupationLevel1;
	}
	public void setOccupationLevel1(String occupationLevel1) {
		this.occupationLevel1 = occupationLevel1;
	}
	public String getOccupationLevel2() {
		return occupationLevel2;
	}
	public void setOccupationLevel2(String occupationLevel2) {
		this.occupationLevel2 = occupationLevel2;
	}
	public ChannelType getChannel() {
		return channel;
	}
	public void setChannel(ChannelType channel) {
		this.channel = channel;
	}
	public int getSalaryTop() {
		return salaryTop;
	}
	public void setSalaryTop(int salaryTop) {
		this.salaryTop = salaryTop;
	}
	public int getSalaryBottom() {
		return salaryBottom;
	}
	public void setSalaryBottom(int salaryBottom) {
		this.salaryBottom = salaryBottom;
	}
}
