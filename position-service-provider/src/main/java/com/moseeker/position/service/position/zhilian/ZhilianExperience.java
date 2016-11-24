package com.moseeker.position.service.position.zhilian;

public enum ZhilianExperience {

	NotRequired("经验不限", "0"), NotWork("无经验", "1"), LessThanOneYear("1年以下", "2"), OneToThree("1-3年", "4"), ThreeToFive("3-5年","5"), FiveToTen("5-10年", "6"),  MoreThanTen("10年以上", "8"), None("","-1");
	
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
