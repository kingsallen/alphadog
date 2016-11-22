package com.moseeker.position.service.position.zhilian;

/**
 * 智联学位
 * @author wjf
 *
 */
public enum ZhilianDegree {

	None("", "-1"), NotRequired("学历不限", "-1"), SpecicalSecondarySchool("中专", "4"), Polytechnic("中技", "3"), HighSchool("高中以下", "2"), JuniorCollege("大专", "5"), College("本科", "6"), Master("硕士", "7"), Doctor("博士", "8");
	
	private String name;
	private String value;
	
	private ZhilianDegree(String name, String value) {
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
