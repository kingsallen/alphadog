package com.moseeker.common.constants;

public enum AppId {

	APPID_ALPHADOG(0), APPID_C(1), APPID_QX(2), APPID_PLATFORM(3), APPID_HR(4), APPID_ATS(5), APPID_SYSPLAT(
			6), APPID_CRONJOB(7), APPID_ANALYTICS(8);

	private int value;

	AppId(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
