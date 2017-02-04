package com.moseeker.common.constants;

/**
 * 是否启用的标志。由于历史遗留问题，老程序的启用都是0，禁用为1；以后的启用都是1，禁用是0
 *
 * @author jack
 * date : Jan 22, 2017
 * email: wengjianfei@moseeker.com
 */
public enum AbleFlag {

	ENABLE(1), DISABLE(0), OLDENABLE(0), OLDDISABLE(1);
	
	private int value;
	
	private AbleFlag(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getValueStr() {
		return String.valueOf(value);
	}
}
