package com.moseeker.servicemanager.web.controller.crawler.form;

import java.util.List;

public class Intention {

	private int id;
	private int profile_id;
	private int worktype;
	private String worktype_name;
	private int workstate;
	private String workstate_name;
	private int salary_type;
	private String salary_type_name;
	private int salary_code;
	private int salary_code_name;
	private String tag;
	private int consider_venture_company_opportunities;
	private String consider_venture_company_opportunities_name;
	private String create_time;
	private String update_time;
	private List<IntentionCity> cities;
	private List<IntentionPosition> positions;
	private List<IntentionIndustry> industries;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(int profile_id) {
		this.profile_id = profile_id;
	}
	public int getWorktype() {
		return worktype;
	}
	public void setWorktype(int worktype) {
		this.worktype = worktype;
	}
	public int getWorkstate() {
		return workstate;
	}
	public void setWorkstate(int workstate) {
		this.workstate = workstate;
	}
	public int getSalary_type() {
		return salary_type;
	}
	public void setSalary_type(int salary_type) {
		this.salary_type = salary_type;
	}
	public int getSalary_code() {
		return salary_code;
	}
	public void setSalary_code(int salary_code) {
		this.salary_code = salary_code;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getConsider_venture_company_opportunities() {
		return consider_venture_company_opportunities;
	}
	public void setConsider_venture_company_opportunities(int consider_venture_company_opportunities) {
		this.consider_venture_company_opportunities = consider_venture_company_opportunities;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getWorktype_name() {
		return worktype_name;
	}
	public void setWorktype_name(String worktype_name) {
		this.worktype_name = worktype_name;
	}
	public String getWorkstate_name() {
		return workstate_name;
	}
	public void setWorkstate_name(String workstate_name) {
		this.workstate_name = workstate_name;
	}
	public String getSalary_type_name() {
		return salary_type_name;
	}
	public void setSalary_type_name(String salary_type_name) {
		this.salary_type_name = salary_type_name;
	}
	public int getSalary_code_name() {
		return salary_code_name;
	}
	public void setSalary_code_name(int salary_code_name) {
		this.salary_code_name = salary_code_name;
	}
	public String getConsider_venture_company_opportunities_name() {
		return consider_venture_company_opportunities_name;
	}
	public void setConsider_venture_company_opportunities_name(String consider_venture_company_opportunities_name) {
		this.consider_venture_company_opportunities_name = consider_venture_company_opportunities_name;
	}
	public List<IntentionCity> getCities() {
		return cities;
	}
	public void setCities(List<IntentionCity> cities) {
		this.cities = cities;
	}
	public List<IntentionPosition> getPositions() {
		return positions;
	}
	public void setPositions(List<IntentionPosition> positions) {
		this.positions = positions;
	}
	public List<IntentionIndustry> getIndustries() {
		return industries;
	}
	public void setIndustries(List<IntentionIndustry> industries) {
		this.industries = industries;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intention other = (Intention) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
