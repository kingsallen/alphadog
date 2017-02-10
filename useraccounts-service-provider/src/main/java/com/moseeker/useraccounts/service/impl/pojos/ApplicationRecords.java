package com.moseeker.useraccounts.service.impl.pojos;

/**
 * 个人中心 求职记录信息
 * @author jack
 *
 */
public class ApplicationRecords {

	private int id;				//申请记录编号
	private String title;		//职位编号
	private String department;	//职位部门
	private String status;		//招聘进度状态
	private String time;		//申请时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
