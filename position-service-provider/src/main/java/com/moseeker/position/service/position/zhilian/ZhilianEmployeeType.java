package com.moseeker.position.service.position.zhilian;

public enum ZhilianEmployeeType {

	campusRecruitment("校园"), none("");
	
	private ZhilianEmployeeType(String name) {
		this.name = name;
	}
	private String name;
	
	public String getName() {
		return this.name();
	}
}
