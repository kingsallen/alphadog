package com.moseeker.position.service.position.qianxun;

import java.util.HashMap;
import java.util.Map;

/**
 * 仟寻学位
 * @author wjf
 *
 */
public enum Degree {

	None("", "0"), SpecicalSecondarySchool("中专", "6"), HighSchool("高中", "7"), JuniorCollege("大专", "1"), College("本科", "2"), Master("硕士", "3"), Doctor("博士", "5"), MBA("MBA","4");
	
	private String name;
	private String value;
	
	private static Map<String, Degree> codeToDegree = new HashMap<>();
	
	static {
		for(Degree degree : values()) {
			codeToDegree.put(degree.getValue(), degree);
		}
	}
	
	private Degree(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Degree instanceFromCode(String code) {
		return codeToDegree.get(code);
	}
}
