package com.moseeker.position.service.position.zhilian;

public enum ZhilianWorkType {

	fullTime("全职", 0), partTime("兼职", 1), practice("实习", 2), none("", -1);
	
	private ZhilianWorkType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	private String name;
	private int value;
	
	public String getName() {
		return this.name;
	}
	
	public int getValue() {
		return this.value;
	}
}
