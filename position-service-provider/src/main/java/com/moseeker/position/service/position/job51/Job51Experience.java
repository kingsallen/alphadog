package com.moseeker.position.service.position.job51;

public enum Job51Experience {

	NotRequired("无工作经验", "1"), One("1年", "3"), Two("2年", "4"), ThreeToFour("3-4年","5"), FiveToSeven("5-7年", "6"), EightToNine("8-9年","7"), MoreThanTen("10年以上", "8"), None("", "");
	
	private String value;
	private String name;
	private Job51Experience(String name, String value) {
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
