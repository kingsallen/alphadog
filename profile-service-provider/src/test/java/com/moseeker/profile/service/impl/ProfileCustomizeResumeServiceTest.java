package com.moseeker.profile.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;

public class ProfileCustomizeResumeServiceTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifyCustomizeResume() {
		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setOther("test");
		ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(customizeResume); 
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyCustomizeResume1() {
		CustomizeResume customizeResume = new CustomizeResume();
		ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(customizeResume); 
		assertEquals(false, vm.isPass());
	}
}
