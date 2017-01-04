package com.moseeker.profile.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.thrift.gen.profile.struct.Skill;

public class ProfileSkillServiceTest {
	
	ProfileSkillService skillService = new ProfileSkillService();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifySkill() {
		Skill skill = new Skill();
		skill.setName("test");
		ValidationMessage<Skill> vm = skillService.verifySkill(skill);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifySkill1() {
		Skill skill = new Skill();
		ValidationMessage<Skill> vm = skillService.verifySkill(skill);
		assertEquals(false, vm.isPass());
	}
}
