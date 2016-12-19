package com.moseeker.position.service.position.job51;

public enum Job51EmployeeType {

	none("");
	
	private Job51EmployeeType(String name) {
		this.name = name;
	}
	private String name;
	
	public String getName() {
		return this.name();
	}
}
