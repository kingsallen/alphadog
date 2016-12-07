package com.moseeker.position.service.position.zhilian;

/**
 * 智联学位
 * @author wjf
 *
 */
public enum ZhilianDegree {

	None("", "-1"), NotRequired("学历不限", "-1"), SpecicalSecondarySchool("中专", "12"), Polytechnic("中技", "13"), HighSchool("高中以下", "7"), JuniorCollege("大专", "5"), College("本科", "4"), Master("硕士", "3"), Doctor("博士", "1");
	
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
