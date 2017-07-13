package com.moseeker.common.constants;

public enum AccountSync {
	
	unbind(0), bound(1), binding(2),refreshing(3),accountpasserror(4),error(5),bingdingerror(6),refresherror(7);

	private AccountSync(int value) {
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
