package com.moseeker.position.service.position.zhilian;

public enum ZhilianExperience {

	NotRequired("经验不限", "-1"), NotWork("无经验", "0"), LessThanOneYear("1年以下", "1"), OneToThree("1-3年", "103"), ThreeToFive("3-5年","305"), FiveToTen("5-10年", "510"),  MoreThanTen("10年以上", "1099"), None("","-1");
	
	private String value;
	private String name;
	private ZhilianExperience(String name, String value) {
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
