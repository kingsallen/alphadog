package com.moseeker.position.service.position;

import com.moseeker.position.service.position.job51.Job51EmployeeType;
import com.moseeker.position.service.position.qianxun.EmployeeType;
import com.moseeker.position.service.position.zhilian.ZhilianEmployeeType;

/**
 * 招聘类型转换
 * @author wjf
 *
 */
public class EmployeeTypeChangeUtil {

	public Job51EmployeeType getJob51EmployeeType(EmployeeType employeeType) {
		return Job51EmployeeType.none;
	}
	
	public ZhilianEmployeeType getZhilianEmployeeType(EmployeeType employeeType) {
		
		ZhilianEmployeeType zhilianEmployeeType = ZhilianEmployeeType.none;
		switch(employeeType) {
			case campusRecruitment:zhilianEmployeeType = ZhilianEmployeeType.campusRecruitment;break;
			default: zhilianEmployeeType = ZhilianEmployeeType.none;
		}
		return zhilianEmployeeType;
	}
}
