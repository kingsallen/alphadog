package com.moseeker.position.service.position.qianxun;

public enum EmployeeType {

	socialRecruitment("社招"), campusRecruitment("校招");
	
	private EmployeeType(String name) {
		this.name = name;
	}
	private String name;
	
	public String getName() {
		return this.name();
	}
}
