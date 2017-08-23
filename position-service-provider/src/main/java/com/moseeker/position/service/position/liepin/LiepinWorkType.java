package com.moseeker.position.service.position.liepin;

public enum LiepinWorkType {

	practice("实习", 1), other("其它", 0);

	private LiepinWorkType(String name, int value) {
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
	