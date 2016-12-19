package com.moseeker.position.service.position;

import com.moseeker.position.service.position.job51.Job51WorkType;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.position.service.position.zhilian.ZhilianWorkType;

/**
 * 工作性质转换
 * @author wjf
 *
 */
public class WordTypeChangeUtil {

	public static Job51WorkType getJob51EmployeeType(WorkType wordType) {
		Job51WorkType job51WorkType = Job51WorkType.none;
		switch(wordType) {
		case fullTime: job51WorkType = Job51WorkType.fullTime; break;
		case partTime: job51WorkType = Job51WorkType.partTime; break;
		default: job51WorkType = Job51WorkType.fullTime;
		}
		return job51WorkType;
	}
	
	public static ZhilianWorkType getZhilianEmployeeType(WorkType wordType) {
		
		ZhilianWorkType zhilianWorkType = ZhilianWorkType.none;
		switch(wordType) {
		case fullTime: zhilianWorkType = ZhilianWorkType.fullTime; break;
		case partTime: zhilianWorkType = ZhilianWorkType.partTime; break;
		case practice: zhilianWorkType = ZhilianWorkType.practice; break;
		default: zhilianWorkType = ZhilianWorkType.fullTime;
		}
		return zhilianWorkType;
	}
}
