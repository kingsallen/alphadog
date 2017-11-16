package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.profile.struct.*;
import org.joda.time.DateTime;

public class ProfileValidation {

	public static ValidationMessage<Credentials> verifyCredential(Credentials credentials) {
		ValidationMessage<Credentials> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(credentials.getName())) {
			vm.addFailedElement("证书名称", "未填写证书名称");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileCredentialsRecord> verifyCredential(ProfileCredentialsRecord credentials) {
		ValidationMessage<ProfileCredentialsRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(credentials.getName())) {
			vm.addFailedElement("证书名称", "未填写证书名称");
		}
		return vm;
	}

	public static ValidationMessage<CustomizeResume> verifyCustomizeResume(CustomizeResume customizeResume) {
		ValidationMessage<CustomizeResume> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(customizeResume.getOther())) {
			vm.addFailedElement("其他字段", "未填写其他字段的内容");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileOtherRecord> verifyCustomizeResume(ProfileOtherRecord customizeResume) {
		ValidationMessage<ProfileOtherRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(customizeResume.getOther())) {
			vm.addFailedElement("其他字段", "未填写其他字段的内容");
		}
		return vm;
	}
	
	public static ValidationMessage<Education> verifyEducation(Education education) {
		ValidationMessage<Education> vm = new ValidationMessage<>();
		if(education.getCollege_code() == 0 && StringUtils.isNullOrEmpty(education.getCollege_name())) {
			vm.addFailedElement("院校", "未选择院校");
		}
		if(education.getDegree() == 0) {
			vm.addFailedElement("学历", "未选择学历");
		}
		if(StringUtils.isNullOrEmpty(education.getStart_date())) {
			vm.addFailedElement("开始时间", "未选择开始时间");
		}
		if (education.getStart_date() != null && education.getEnd_date() != null
				&& DateTime.parse(education.getStart_date()).getMillis()
				>= DateTime.parse(education.getEnd_date()).getMillis()) {
			vm.addFailedElement("项目时间", "开始时间大于或者等于结束时间");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileEducationRecord> verifyEducation(ProfileEducationRecord education) {
		ValidationMessage<ProfileEducationRecord> vm = new ValidationMessage<>();
		if((education.getCollegeCode() == null ||  education.getCollegeCode()== 0) && StringUtils.isNullOrEmpty(education.getCollegeName())) {
			vm.addFailedElement("院校", "未选择院校");
		}
		if(education.getDegree() == null || education.getDegree().intValue() == 0) {
			vm.addFailedElement("学历", "未选择学历");
		}
		if(education.getStart() == null) {
			vm.addFailedElement("开始时间", "未选择开始时间");
		}
		if (education.getStart() != null && education.getEnd() != null && education.getStart().getTime() >= education.getEnd().getTime()) {
			vm.addFailedElement("项目时间", "开始时间大于或者等于结束时间");
		}
		return vm;
	}
	
	public static ValidationMessage<Language> verifyLanguage(Language language) {
		ValidationMessage<Language> vm = new ValidationMessage<Language>();
		if(StringUtils.isNullOrEmpty(language.getName())) {
			vm.addFailedElement("语言名称", "未填写语言名称");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileLanguageRecord> verifyLanguage(ProfileLanguageRecord language) {
		ValidationMessage<ProfileLanguageRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(language.getName())) {
			vm.addFailedElement("语言名称", "未填写语言名称");
		}
		return vm;
	}
	
	public static ValidationMessage<ProjectExp> verifyProjectExp(ProjectExp projectExp) {
		ValidationMessage<ProjectExp> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(projectExp.getName())) {
			vm.addFailedElement("项目名称", "未填写项目名称!");
		}
		if(StringUtils.isNullOrEmpty(projectExp.getStart_date())) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (projectExp.getStart_date() != null && projectExp.getEnd_date() != null
				&& DateTime.parse(projectExp.getStart_date()).getMillis()
				>= DateTime.parse(projectExp.getEnd_date()).getMillis()) {
			vm.addFailedElement("项目时间", "开始时间大于或者等于结束时间");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileProjectexpRecord> verifyProjectExp(ProfileProjectexpRecord projectExp) {
		ValidationMessage<ProfileProjectexpRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(projectExp.getName())) {
			vm.addFailedElement("项目名称", "未填写项目名称!");
		}
		if(projectExp.getStart() == null) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (projectExp.getStart() != null && projectExp.getEnd() != null && projectExp.getStart().getTime() >= projectExp.getEnd().getTime()) {
			vm.addFailedElement("项目时间", "开始时间大于或者等于结束时间");
		}
		return vm;
	}
	
	public static ValidationMessage<Skill> verifySkill(Skill skill) {
		ValidationMessage<Skill> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(skill.getName())) {
			vm.addFailedElement("语言名称", "未填写语言名称");
		} else if (skill.getName().length() > 100){
			vm.addFailedElement("语言名称", "超过最长字数限制");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileSkillRecord> verifySkill(ProfileSkillRecord skill) {
		ValidationMessage<ProfileSkillRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(skill.getName())) {
			vm.addFailedElement("技能名称", "未填写语言名称");
		} else if (skill.getName().length() > 128){
			vm.addFailedElement("技能名称", "超过最长字数限制");
		}
		return vm;
	}
	
	public static ValidationMessage<WorkExp> verifyWorkExp(WorkExp workExp) {
		ValidationMessage<WorkExp> vm = new ValidationMessage<>();
		if(workExp.getCompany_id() == 0 && StringUtils.isNullOrEmpty(workExp.getCompany_name())) {
			vm.addFailedElement("就职公司", "未填写就职公司");
		}
		if(StringUtils.isNullOrEmpty(workExp.getJob())) {
			vm.addFailedElement("职位名称", "未填写职位名称");
		}
		if(StringUtils.isNullOrEmpty(workExp.getStart_date())) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (workExp.getStart_date() != null && workExp.getEnd_date() != null
				&& DateTime.parse(workExp.getStart_date()).getMillis()
				>= DateTime.parse(workExp.getEnd_date()).getMillis()) {
			vm.addFailedElement("项目时间", "开始时间大于或者等于结束时间");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileWorkexpEntity> verifyWorkExp(ProfileWorkexpEntity workExp) {
		ValidationMessage<ProfileWorkexpEntity> vm = new ValidationMessage<>();
		if(workExp.getCompany() == null || StringUtils.isNullOrEmpty(workExp.getCompany().getName())) {
			vm.addFailedElement("就职公司", "未填写就职公司");
		}
		if(StringUtils.isNullOrEmpty(workExp.getJob())) {
			vm.addFailedElement("职位名称", "未填写职位名称");
		}
		if(workExp.getStart() == null) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if(StringUtils.isNullOrEmpty(workExp.getDescription())) {
			vm.addFailedElement("职位描述", "未对该职位做详细描述");
		}
		if (workExp.getStart() != null && workExp.getEnd() != null && workExp.getStart().getTime() >= workExp.getEnd().getTime()) {
			vm.addFailedElement("项目时间", "开始时间大于或者等于结束时间");
		}
		return vm;
	}
}