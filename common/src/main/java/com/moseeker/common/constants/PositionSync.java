package com.moseeker.common.constants;

public enum PositionSync {

	unbind(0), bound(1), binding(2),failed(3);
	
	private int value;
	
	private PositionSync(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
