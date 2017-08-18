package com.moseeker.position.service.position.alipaycampus;

public enum AlipaycampusWorkType {

	fullTime("全职", "3"), partTime("兼职", "2"), practice("实习", "1");
	
	private AlipaycampusWorkType(String name, String value) {
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
