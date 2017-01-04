package com.moseeker.profile.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;

public class ProfileCustomizeResumeServiceTest {
	
	ProfileCustomizeResumeService customizeResumeService = new ProfileCustomizeResumeService();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifyCustomizeResume() {
		CustomizeResume customizeResume = new CustomizeResume();
		customizeResume.setOther("test");
		ValidationMessage<CustomizeResume> vm = customizeResumeService.verifyCustomizeResume(customizeResume); 
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyCustomizeResume1() {
		CustomizeResume customizeResume = new CustomizeResume();
		ValidationMessage<CustomizeResume> vm = customizeResumeService.verifyCustomizeResume(customizeResume); 
		assertEquals(false, vm.isPass());
	}
}
