package com.moseeker.common.util;

import org.junit.Test;

import java.time.LocalDateTime;

public class ConfigPropertiesUtilTest {

	//@Test
	public void testInit() {
		ConfigPropertiesUtil configPropertiesUtil = null;
		configPropertiesUtil = ConfigPropertiesUtil.getInstance();
		if(configPropertiesUtil != null) {
			configPropertiesUtil.get("service_name", String.class);
		} else {
			System.out.println("configPropertiesUtil is null");
		}
	}

//	@Test
	public void testDate(){
		LocalDateTime start = DateUtils.getCurrentTwoMonthStartTime();
		LocalDateTime end = DateUtils.getCurrentTwoMonthEndTime();

		System.out.println(start.toString()+"-"+end.toString());

		for(int i=1;i<=12;i++){
			int dayOfMonth = DateUtils.getLastDayOfMonth(2017,i);
			System.out.println(dayOfMonth);
		}
	}
}
