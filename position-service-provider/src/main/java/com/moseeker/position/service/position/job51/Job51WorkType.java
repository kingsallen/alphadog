package com.moseeker.position.service.position.job51;

public enum Job51WorkType {

	fullTime("全职", "0"), partTime("兼职", "1"), none("", "");
	
	private Job51WorkType(String name, String value) {
		this.name = name;
		this.value = value;
	}
	private String name;
	private String value;
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}
}
