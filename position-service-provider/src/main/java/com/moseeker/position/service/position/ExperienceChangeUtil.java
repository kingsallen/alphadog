package com.moseeker.position.service.position;

import com.moseeker.position.service.position.job51.Job51Experience;
import com.moseeker.position.service.position.zhilian.ZhilianExperience;

/**
 * 工作经验转换
 * @author wjf
 *
 */
public class ExperienceChangeUtil {

	public static Job51Experience getJob51Experience(Integer experience) {
		Job51Experience job51Experience = Job51Experience.None;
		if(experience == null || experience < 0) {
			return job51Experience;
		}
		switch(experience) {
		case 0: job51Experience = Job51Experience.NotRequired; break;
		case 1: job51Experience = Job51Experience.One; break;
		case 2: job51Experience = Job51Experience.Two; break;
		case 3:
		case 4: job51Experience = Job51Experience.ThreeToFour;break;
		case 5:
		case 6:
		case 7: job51Experience = Job51Experience.FiveToSeven; break;
		case 8:
		case 9: job51Experience = Job51Experience.EightToNine; break;
		default: job51Experience = Job51Experience.MoreThanTen;
		}
		return job51Experience;
	}
	
	public static ZhilianExperience getZhilianExperience(Integer experience) {
		
		ZhilianExperience zhilianExperience = ZhilianExperience.None;
		if(experience == null || experience < 0) {
			return zhilianExperience;
		}
		switch(experience) {
		case 0: zhilianExperience = ZhilianExperience.LessThanOneYear; break;
		case 1: 
		case 2: 
		case 3:zhilianExperience = ZhilianExperience.OneToThree; break;
		case 4: 
		case 5:zhilianExperience = ZhilianExperience.ThreeToFive;break;
		case 6:
		case 7: 
		case 8:
		case 9: 
		case 10: zhilianExperience = ZhilianExperience.FiveToTen; break;
		default: zhilianExperience = ZhilianExperience.MoreThanTen;
		}
		return zhilianExperience;
	}
}
