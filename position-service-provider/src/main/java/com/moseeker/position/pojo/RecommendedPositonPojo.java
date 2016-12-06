package com.moseeker.position.pojo;

/**
 * Created by chendi on 5/28/16.
 */
public class RecommendedPositonPojo {

    public int pid;
    public String job_title;
    public int company_id;
    public String company_name;
    public String company_logo;
    private int salary_top;
    private int salary_bottom;
    private String job_city;
    private int publisher;
    
	public int getPublisher() {
		return publisher;
	}
	public void setPublisher(int publisher) {
		this.publisher = publisher;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_logo() {
		return company_logo;
	}
	public void setCompany_logo(String company_logo) {
		this.company_logo = company_logo;
	}
	public int getSalary_top() {
		return salary_top;
	}
	public void setSalary_top(int salary_top) {
		this.salary_top = salary_top;
	}
	public int getSalary_bottom() {
		return salary_bottom;
	}
	public void setSalary_bottom(int salary_bottom) {
		this.salary_bottom = salary_bottom;
	}
	public String getJob_city() {
		return job_city;
	}
	public void setJob_city(String job_city) {
		this.job_city = job_city;
	}
}
