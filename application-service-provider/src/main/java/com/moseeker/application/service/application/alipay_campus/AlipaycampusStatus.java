package com.moseeker.application.service.application.alipay_campus;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝招聘进度
 * @author wyf
 *
 */
public enum AlipaycampusStatus {

	CVChecked("简历被查看","4"),
	CVPassed("待处理", "5"),
	Offered("面试通过/待处理", "5"),
	Hired("已入职", "8"),
	Rejected("拒绝/不合适","7");



	private String name;
	private String value;

	private static Map<String, AlipaycampusStatus> codeToStatus = new HashMap<>();

	static {
		for(AlipaycampusStatus status : values()) {
			codeToStatus.put(status.getValue(), status);
		}
	}

	private AlipaycampusStatus(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public static AlipaycampusStatus instanceFromCode(String code) {
		return codeToStatus.get(code);
	}
}
