package com.moseeker.common.constants;

public enum PositionRefreshType {

	notRefresh(0), refreshed(1), refreshing(2),failed(3);
	
	private int value;
	
	private PositionRefreshType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
