package com.moseeker.profile.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

public class ProfileWorkExpServiceTest {
	
	ProfileWorkExpService workExpService = new ProfileWorkExpService();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifyWorkExp() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setCompany_name("test");
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyWorkExp1() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_name("test");
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp2() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp3() {
		WorkExp workExp = new WorkExp();
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp4() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp5() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setDescription("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp6() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setDescription("test");
		workExp.setJob("test");
		ValidationMessage<WorkExp> vm = workExpService.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
}
