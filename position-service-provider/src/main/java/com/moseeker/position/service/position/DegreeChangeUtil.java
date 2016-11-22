package com.moseeker.position.service.position;

import com.moseeker.position.service.position.job51.Job51Degree;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.zhilian.ZhilianDegree;

/**
 * 学位转换
 * @author wjf
 *
 */
public class DegreeChangeUtil {

	public Job51Degree getJob51EmployeeType(Degree degree) {
		Job51Degree job51Degree = Job51Degree.None;
		switch(degree) {
		case None: break;
		case SpecicalSecondarySchool : job51Degree = Job51Degree.SpecicalSecondarySchool; break;
		case HighSchool: job51Degree = Job51Degree.HighSchool; break;
		case JuniorCollege: job51Degree = Job51Degree.JuniorCollege; break;
		case College: job51Degree = Job51Degree.College; break;
		case MBA:
		case Master: job51Degree = Job51Degree.Master; break;
		case Doctor: job51Degree = Job51Degree.Doctor; break;
		default: 
		}
		return job51Degree;
	}
	
	public ZhilianDegree getZhilianEmployeeType(Degree degree) {
		
		ZhilianDegree zhilianDegree = ZhilianDegree.NotRequired;
		switch(degree) {
		case None: zhilianDegree = ZhilianDegree.None; break;
		case SpecicalSecondarySchool : zhilianDegree = ZhilianDegree.SpecicalSecondarySchool; break;
		case HighSchool: zhilianDegree = ZhilianDegree.HighSchool; break;
		case JuniorCollege: zhilianDegree = ZhilianDegree.JuniorCollege; break;
		case College: zhilianDegree = ZhilianDegree.College; break;
		case MBA:
		case Master: zhilianDegree = ZhilianDegree.Master; break;
		case Doctor: zhilianDegree = ZhilianDegree.Doctor; break;
		default: zhilianDegree = ZhilianDegree.None;break;
		}
		return zhilianDegree;
	}
}
