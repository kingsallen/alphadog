package com.moseeker.position.service.position.qianxun;

import java.util.HashMap;

public enum WorkType {

	fullTime(0,"全职"), partTime(1, "兼职"), contract(2, "合同工"), practice(3, "实习"), other(9,"其他");
	
	private WorkType(int value, String name) {
		this.name = name;
		this.value = value;
	}
	private String name;
	private int value;
	
	private static HashMap<Integer, WorkType> intToEnum = new HashMap<>();
	
	static {
		for(WorkType workType : values()) {
			intToEnum.put(workType.getValue(), workType);
		}
	}
	
	public static WorkType instanceFromInt(int value) {
		return intToEnum.get(value);
	}
	
	public String getName() {
		return this.name;
	}
	public int getValue() {
		return this.value;
	}
}
