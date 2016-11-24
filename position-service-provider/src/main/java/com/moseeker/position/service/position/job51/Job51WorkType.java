package com.moseeker.position.service.position.job51;

public enum Job51WorkType {

	fullTime("全职", 0), partTime("兼职", 1), none("", -1);
	
	private Job51WorkType(String name, int value) {
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
