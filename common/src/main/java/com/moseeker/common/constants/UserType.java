package com.moseeker.common.constants;

public enum UserType {
	
	PC(0), HR(1);

	private UserType(int value) {
		this.value = value;
	}
	private int value;
	
	public int getValue() {
		return value;
	}
	
	public byte getValueToByte() {
		return (byte)value;
	}
}
