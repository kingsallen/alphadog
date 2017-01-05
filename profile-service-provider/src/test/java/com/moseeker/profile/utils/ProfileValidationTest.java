package com.moseeker.profile.utils;

import static org.junit.Assert.assertEquals;

import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.junit.Before;
import org.junit.Test;

import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.thrift.gen.profile.struct.Credentials;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.Language;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
import com.moseeker.thrift.gen.profile.struct.Skill;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

public class ProfileValidationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerifyCredential() {
		Credentials credentials = new Credentials();
		credentials.setName("");
		ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(credentials);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyCredential1() {
		Credentials credentials = new Credentials();
		credentials.setName("test");
		ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(credentials);
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyCredentialsRecord() {
		ProfileCredentialsRecord credentials = new ProfileCredentialsRecord();
		credentials.setName("");
		ValidationMessage<ProfileCredentialsRecord> vm = ProfileValidation.verifyCredential(credentials);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyCredentialsRecord2() {
		ProfileCredentialsRecord credentials = new ProfileCredentialsRecord();
		credentials.setName("test");
		ValidationMessage<ProfileCredentialsRecord> vm = ProfileValidation.verifyCredential(credentials);
		assertEquals(true, vm.isPass());
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
	
	@Test
	public void testVerifyOtherRecord() {
		ProfileOtherRecord customizeResume = new ProfileOtherRecord();
		customizeResume.setOther("test");
		ValidationMessage<ProfileOtherRecord> vm = ProfileValidation.verifyCustomizeResume(customizeResume); 
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyOtherRecord1() {
		ProfileOtherRecord customizeResume = new ProfileOtherRecord();
		ValidationMessage<ProfileOtherRecord> vm = ProfileValidation.verifyCustomizeResume(customizeResume); 
		assertEquals(false, vm.isPass());
	}

	@Test
	public void testVerifyEducation() {
		Education education = new Education();
		education.setCollege_code(1);
		education.setCollege_name("");
		education.setDegree(1);
		education.setStart_date("2016-12-17");
		education.setDescription("详细描述");
		ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
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
		ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
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
		ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
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
		ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
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
		ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
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
		ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducationRecord() {
		ProfileEducationRecord education = new ProfileEducationRecord();
		education.setCollegeCode(Integer.valueOf(1));
		education.setCollegeName("");
		education.setDegree(UByte.valueOf(1));
		education.setStart(new java.sql.Date(System.currentTimeMillis()));
		education.setDescription("详细描述");
		ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyEducationRecord1() {
		ProfileEducationRecord education = new ProfileEducationRecord();
		education.setCollegeName("");
		education.setDegree(UByte.valueOf(1));
		education.setStart(new java.sql.Date(System.currentTimeMillis()));
		education.setDescription("详细描述");
		ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducationRecord2() {
		ProfileEducationRecord education = new ProfileEducationRecord();
		education.setCollegeCode(0);
		education.setCollegeName("");
		education.setDegree(UByte.valueOf(1));
		education.setStart(new java.sql.Date(System.currentTimeMillis()));
		education.setDescription("详细描述");
		ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducationRecord3() {
		ProfileEducationRecord education = new ProfileEducationRecord();
		education.setCollegeCode(0);
		education.setCollegeName("test");
		education.setDegree(UByte.valueOf(1));
		education.setStart(new java.sql.Date(System.currentTimeMillis()));
		education.setDescription("详细描述");
		ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyEducationRecord4() {
		ProfileEducationRecord education = new ProfileEducationRecord();
		education.setCollegeCode(0);
		education.setCollegeName("test");
		education.setStart(new java.sql.Date(System.currentTimeMillis()));
		education.setDescription("详细描述");
		ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyEducationRecord5() {
		ProfileEducationRecord education = new ProfileEducationRecord();
		education.setCollegeCode(0);
		education.setCollegeName("test");
		education.setDegree(UByte.valueOf(1));
		education.setDescription("详细描述");
		ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(education);
		System.out.println(vm.getResult());
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyLanguage() {
		Language language = new Language();
		language.setName("test");
		ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(language);
		assertEquals(true,  vm.isPass());
	}
	
	@Test
	public void testVerifyLanguage1() {
		Language language = new Language();
		ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(language);
		assertEquals(false,  vm.isPass());
	}
	
	@Test
	public void testVerifyLanguageRecord() {
		ProfileLanguageRecord language = new ProfileLanguageRecord();
		language.setName("test");
		ValidationMessage<ProfileLanguageRecord> vm = ProfileValidation.verifyLanguage(language);
		assertEquals(true,  vm.isPass());
	}
	
	@Test
	public void testVerifyLanguageRecord1() {
		ProfileLanguageRecord language = new ProfileLanguageRecord();
		ValidationMessage<ProfileLanguageRecord> vm = ProfileValidation.verifyLanguage(language);
		assertEquals(false,  vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExp() {
		ProjectExp projectExp = new ProjectExp();
		projectExp.setName("项目名称");
		projectExp.setStart_date("2016-12-14");
		ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyProjectExp1() {
		ProjectExp projectExp = new ProjectExp();
		ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExp2() {
		ProjectExp projectExp = new ProjectExp();
		projectExp.setName("项目名称");
		ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExp3() {
		ProjectExp projectExp = new ProjectExp();
		projectExp.setStart_date("2016-12-14");
		ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExpRecord() {
		ProfileProjectexpRecord projectExp = new ProfileProjectexpRecord();
		projectExp.setName("项目名称");
		projectExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileProjectexpRecord> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyProjectExpRecord1() {
		ProfileProjectexpRecord projectExp = new ProfileProjectexpRecord();
		ValidationMessage<ProfileProjectexpRecord> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExpRecord2() {
		ProfileProjectexpRecord projectExp = new ProfileProjectexpRecord();
		projectExp.setName("项目名称");
		ValidationMessage<ProfileProjectexpRecord> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyProjectExpRecord3() {
		ProfileProjectexpRecord projectExp = new ProfileProjectexpRecord();
		projectExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileProjectexpRecord> vm = ProfileValidation.verifyProjectExp(projectExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifySkill() {
		Skill skill = new Skill();
		skill.setName("test");
		ValidationMessage<Skill> vm = ProfileValidation.verifySkill(skill);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifySkill1() {
		Skill skill = new Skill();
		ValidationMessage<Skill> vm = ProfileValidation.verifySkill(skill);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifySkillRecord() {
		ProfileSkillRecord skill = new ProfileSkillRecord();
		skill.setName("test");
		ValidationMessage<ProfileSkillRecord> vm = ProfileValidation.verifySkill(skill);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifySkillRecord1() {
		ProfileSkillRecord skill = new ProfileSkillRecord();
		ValidationMessage<ProfileSkillRecord> vm = ProfileValidation.verifySkill(skill);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setCompany_name("test");
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyWorkExp1() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_name("test");
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp2() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp3() {
		WorkExp workExp = new WorkExp();
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp5() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setDescription("test");
		workExp.setStart_date("2016-07-01");
		ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExp6() {
		WorkExp workExp = new WorkExp();
		workExp.setCompany_id(1);
		workExp.setDescription("test");
		workExp.setJob("test");
		ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpRecord() {
		ProfileWorkexpRecord workExp = new ProfileWorkexpRecord();
		workExp.setCompanyId(UInteger.valueOf(1));
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpRecord> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}

	@Test
	public void testVerifyWorkExpRecord2() {
		ProfileWorkexpRecord workExp = new ProfileWorkexpRecord();
		workExp.setCompanyId(UInteger.valueOf(1));
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpRecord> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpRecord3() {
		ProfileWorkexpRecord workExp = new ProfileWorkexpRecord();
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpRecord> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpRecord5() {
		ProfileWorkexpRecord workExp = new ProfileWorkexpRecord();
		workExp.setCompanyId(UInteger.valueOf(1));
		workExp.setDescription("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpRecord> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpRecord6() {
		ProfileWorkexpRecord workExp = new ProfileWorkexpRecord();
		workExp.setCompanyId(UInteger.valueOf(1));
		workExp.setDescription("test");
		workExp.setJob("test");
		ValidationMessage<ProfileWorkexpRecord> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpEntity() {
		ProfileWorkexpEntity workExp = new ProfileWorkexpEntity();
		HrCompanyRecord company = new HrCompanyRecord();
		company.setName("test");
		workExp.setCompany(company);
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(true, vm.isPass());
	}

	
	@Test
	public void testVerifyWorkExpEntity3() {
		ProfileWorkexpEntity workExp = new ProfileWorkexpEntity();
		workExp.setDescription("test");
		workExp.setJob("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpEntity4() {
		ProfileWorkexpEntity workExp = new ProfileWorkexpEntity();
		HrCompanyRecord company = new HrCompanyRecord();
		company.setName("test");
		workExp.setCompany(company);
		workExp.setJob("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpEntity5() {
		ProfileWorkexpEntity workExp = new ProfileWorkexpEntity();
		HrCompanyRecord company = new HrCompanyRecord();
		company.setName("test");
		workExp.setCompany(company);
		workExp.setDescription("test");
		workExp.setStart(new java.sql.Date(System.currentTimeMillis()));
		ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
	
	@Test
	public void testVerifyWorkExpEntity6() {
		ProfileWorkexpEntity workExp = new ProfileWorkexpEntity();
		HrCompanyRecord company = new HrCompanyRecord();
		company.setName("test");
		workExp.setCompany(company);
		workExp.setDescription("test");
		workExp.setJob("test");
		ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(workExp);
		assertEquals(false, vm.isPass());
	}
}
