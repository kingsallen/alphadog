package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来来源
 * @author wjf
 *
 */
public enum UserSource {

	MOBILE_REGISTER(0), JUHE_QUICK_LOGIN(1), COMPANY_QUICK_LOGIN(2), PC_ADD(7), PC_POST_APPLICATION(8), PC_INTERESTED(9), PC_WX_SCAN(10), RETRIEVE_PROFILE(100), RETRIEVE_PROFILE_ZHIFUBAO(101)
	,TALENT_UPLOAD(102), EMPLOYEE_REFERRAL_CHATBOT(104), EMPLOYEE_REFERRAL(105), MV_HOUSE(106);
	
	private UserSource(int value) {
		this.value = value;
	}
	private int value;
	
	public static UserSource instaceFromInteger(int value) {
		return intToEnum.get(value);
	}
	
	public int getValue() {
		return this.value;
	}
	
	private static final Map<Integer, UserSource> intToEnum = new HashMap<>();
	
	static { // Initialize map from constant name to enum constant
	    for (UserSource op : values())
	    	intToEnum.put(op.getValue(), op);
	}
}
