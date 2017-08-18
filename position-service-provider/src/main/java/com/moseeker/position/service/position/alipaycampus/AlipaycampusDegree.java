package com.moseeker.position.service.position.alipaycampus;

/**
 * 支付宝学历要求
 * @author wyf
 *
 */
public enum AlipaycampusDegree {

	BelowJuniorCollege("大专以下","1"), JuniorCollege("大专", "2"), College("本科", "3"), Master("硕士", "4"), Doctor("博士", "5"), Other("其他","6"), NotRequired("不限","7");
	
	private String name;
	private String value;
	
	private AlipaycampusDegree(String name, String value) {
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
