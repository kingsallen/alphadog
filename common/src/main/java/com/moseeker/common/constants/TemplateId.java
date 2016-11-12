package com.moseeker.common.constants;

public enum TemplateId {
	
	TEMPLATE_MESSAGE_FAV_HR(29);
	
	private TemplateId(int value) {
		this.value = value;
	}

	private int value;
	
	public int getValue() {
		return value;
	}
}
