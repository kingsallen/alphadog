package com.moseeker.position.service.position;

/**
 * 
 * 用于同步队列
 * @author wjf
 *
 */
public class PositionForSynchronizationPojo {

	private String title;				//职位名称
	private String category_main_code;	//第二级职位职能
	private String category_sub_code;	//第三级职位职能
	private String quantity;			//招聘人数（hr自填）
	private String degree_code;			//学位对应的code
	private String degree;				//学位	
	private String experience_code;		//工作经验
	private String salary_low;			//最低薪资
	private String salary_high;			//最高薪资
	private String description;			//职位描述与要求
	private String pub_place_code;		//发布职位的城市
	private String position_id;			//职位编号
	private String work_place;			//工作地点（hr自填）
	private String email;				//邮箱
	private String stop_date;			//结束时间
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory_main_code() {
		return category_main_code;
	}
	public void setCategory_main_code(String category_main_code) {
		this.category_main_code = category_main_code;
	}
	public String getCategory_sub_code() {
		return category_sub_code;
	}
	public void setCategory_sub_code(String category_sub_code) {
		this.category_sub_code = category_sub_code;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDegree_code() {
		return degree_code;
	}
	public void setDegree_code(String degree_code) {
		this.degree_code = degree_code;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getExperience_code() {
		return experience_code;
	}
	public void setExperience_code(String experience_code) {
		this.experience_code = experience_code;
	}
	public String getSalary_low() {
		return salary_low;
	}
	public void setSalary_low(String salary_low) {
		this.salary_low = salary_low;
	}
	public String getSalary_high() {
		return salary_high;
	}
	public void setSalary_high(String salary_high) {
		this.salary_high = salary_high;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPub_place_code() {
		return pub_place_code;
	}
	public void setPub_place_code(String pub_place_code) {
		this.pub_place_code = pub_place_code;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStop_date() {
		return stop_date;
	}
	public void setStop_date(String stop_date) {
		this.stop_date = stop_date;
	}
}
