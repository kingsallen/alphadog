package com.moseeker.profile.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;

public class ProfileProjectExpServiceTest extends ProfileProjectExpService {

	ProfileProjectExpService projectExpService = new ProfileProjectExpService();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifyProjectExp() {
		ProjectExp projectExp = new ProjectExp();
		projectExp.setName("项目名称");
		projectExp.setStart_date("2016-12-14");
		ValidationMessage<ProjectExp> vm = projectExpService.verifyProjectExp(projectExp);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyProjectExp1() {
		ProjectExp projectExp = new ProjectExp();
		ValidationMessage<ProjectExp> vm = projectExpService.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExp2() {
		ProjectExp projectExp = new ProjectExp();
		projectExp.setName("项目名称");
		ValidationMessage<ProjectExp> vm = projectExpService.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExp3() {
		ProjectExp projectExp = new ProjectExp();
		projectExp.setStart_date("2016-12-14");
		ValidationMessage<ProjectExp> vm = projectExpService.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
}
