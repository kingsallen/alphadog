package com.moseeker.application.service.application.qianxun;

import java.util.HashMap;
import java.util.Map;

/**
 * 仟寻招聘进度
 * @author wyf
 *
 */
public enum Status {

	// job_application.app_tpl_id 可以完全判断状态了。 is_viewed("未查阅", "1"),NotSuitable 1：不合适
	NewlyApplied("新申请","1"),
	CVChecked("简历被查看","6"),
	CVPassed("评审通过", "10"),
	Offered("面试通过", "12"),
	Hired("已入职", "3"),
	Rejected("拒绝/不合适","4");


	private String name;
	private String value;

	private static Map<String, Status> codeToStatus = new HashMap<>();

	static {
		for(Status status : values()) {
			codeToStatus.put(status.getValue(), status);
		}
	}

	private Status(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Status instanceFromCode(String code) {
		return codeToStatus.get(code);
	}
}
