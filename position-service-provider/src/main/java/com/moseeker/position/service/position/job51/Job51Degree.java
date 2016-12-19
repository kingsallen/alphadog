package com.moseeker.position.service.position.job51;

/**
 * 51JOB学位
 * @author wjf
 *
 */
public enum Job51Degree {

	None("", ""), MiddleSchool("初中及以下", "1"), SpecicalSecondarySchool("中专", "4"), Polytechnic("中技", "3"), HighSchool("高中", "2"), JuniorCollege("大专", "5"), College("本科", "6"), Master("硕士", "7"), Doctor("博士", "8");
	
	private String name;
	private String value;
	
	private Job51Degree(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
