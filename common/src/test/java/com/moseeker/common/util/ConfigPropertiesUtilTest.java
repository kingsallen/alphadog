package com.moseeker.common.util;

import org.junit.Test;

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
}
