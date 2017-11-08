package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 绑定状态
 * @author wjf
 *
 */
public enum BindingStatus {

	//'0：未绑定，1:绑定成功，2：绑定中，3：刷新中，4：用户名密码错误，5：绑定或刷新失败，
	// 6：绑定程序发生错误（前端和2状态一致），7：刷新程序发生错误（前端和3状态一致），8:绑定成功，正在获取信息，100:需要获取验证码'
	UNBIND(0), BOUND(1),BOUNDING(2),REFRESH(3),INFOWRONG(4),FAIL(5),ERROR(6),REFRESHWRONG(7),GETINGINFO(8),NEEDCODE(100);
	
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
