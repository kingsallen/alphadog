package com.moseeker.position.service.position;

import com.moseeker.position.service.position.alipaycampus.AlipaycampusWorkType;
import com.moseeker.position.service.position.job51.Job51WorkType;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.position.service.position.zhilian.ZhilianWorkType;

/**
 * 工作性质转换
 * @author wjf
 *
 */
public class WorkTypeChangeUtil {

	public static Job51WorkType getJob51EmployeeType(WorkType workType) {
		Job51WorkType job51WorkType = Job51WorkType.none;
		switch(workType) {
		case fullTime: job51WorkType = Job51WorkType.fullTime; break;
		case partTime: job51WorkType = Job51WorkType.partTime; break;
		default: job51WorkType = Job51WorkType.fullTime;
		}
		return job51WorkType;
	}
	
	public static ZhilianWorkType getZhilianEmployeeType(WorkType workType) {
		
		ZhilianWorkType zhilianWorkType = ZhilianWorkType.none;
		switch(workType) {
		case fullTime: zhilianWorkType = ZhilianWorkType.fullTime; break;
		case partTime: zhilianWorkType = ZhilianWorkType.partTime; break;
		case practice: zhilianWorkType = ZhilianWorkType.practice; break;
		default: zhilianWorkType = ZhilianWorkType.fullTime;
		}
		return zhilianWorkType;
	}

	public static AlipaycampusWorkType getAlipaycampusWorkType(WorkType workType) {

		AlipaycampusWorkType alipaycampusWorkType = null;
		switch(workType) {
			case fullTime: alipaycampusWorkType = AlipaycampusWorkType.fullTime; break;
			case partTime: alipaycampusWorkType = AlipaycampusWorkType.partTime; break;
			case practice: alipaycampusWorkType = AlipaycampusWorkType.practice; break;
			default: alipaycampusWorkType = AlipaycampusWorkType.fullTime;
		}
		return alipaycampusWorkType;
	}


}
