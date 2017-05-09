package com.moseeker.useraccounts.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 绑定状态
 * @author wjf
 *
 */
public enum BindingStatus {

	UNBIND(0), BOUND(1);
	
	private BindingStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	private int value;
	
	private static final Map<Integer, BindingStatus> intToEnum = new HashMap<>();

	// Initialize map from constant name to enum constant
	static { 
		for (BindingStatus op : values())
			intToEnum.put(op.getValue(), op);
	}
}
