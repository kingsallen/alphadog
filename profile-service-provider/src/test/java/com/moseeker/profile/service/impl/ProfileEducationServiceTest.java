package com.moseeker.profile.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.thrift.gen.profile.struct.Education;

public class ProfileEducationServiceTest extends ProfileEducationService {
	
	ProfileEducationService educationService = new ProfileEducationService();

	@Test
	public void testVerifyEducation() {
		Education education = new Education();
		education.setCollege_code(1);
		education.setCollege_name("");
		education.setDegree(1);
		education.setStart_date("2016-12-17");
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyEducation1() {
		Education education = new Education();
		education.setCollege_name("");
		education.setDegree(1);
		education.setStart_date("2016-12-17");
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducation2() {
		Education education = new Education();
		education.setCollege_code(0);
		education.setCollege_name("");
		education.setDegree(1);
		education.setStart_date("2016-12-17");
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducation3() {
		Education education = new Education();
		education.setCollege_code(0);
		education.setCollege_name("test");
		education.setDegree(1);
		education.setStart_date("2016-12-17");
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyEducatione4() {
		Education education = new Education();
		education.setCollege_code(0);
		education.setCollege_name("test");
		education.setStart_date("2016-12-17");
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducatione5() {
		Education education = new Education();
		education.setCollege_code(0);
		education.setCollege_name("test");
		education.setDegree(1);
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducatione6() {
		Education education = new Education();
		education.setCollege_code(0);
		education.setCollege_name("test");
		education.setDegree(1);
		education.setStart_date("2016-12-17");
		ValidationMessage<Education> vm = educationService.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
}
