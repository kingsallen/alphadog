package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.Constant.UnitlNow;
import com.moseeker.thrift.gen.profile.struct.*;
import org.joda.time.DateTime;

import java.sql.Timestamp;

public class ProfileValidation {

	private static long minTime = new DateTime("1900-01-01").getMillis();
	private static long maxTime = new DateTime("2099-12-31").getMillis();

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
		if (org.apache.commons.lang.StringUtils.isNotBlank(education.getStart_date())
				&& org.apache.commons.lang.StringUtils.isNotBlank(education.getEnd_date())
				&& education.getEnd_until_now() != UnitlNow.NotUntilNow.getStatus()
				&& DateTime.parse(education.getStart_date()).getMillis()
				> DateTime.parse(education.getEnd_date()).getMillis()) {
			vm.addFailedElement("时间", "开始时间大于结束时间");
		}
		if (!legalDate(education.getStart_date())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(education.getEnd_date())
				&& !legalDate(education.getEnd_date())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~2099-12-31之间");
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
		if (education.getStart() != null && education.getEnd() != null
				&& education.getStart().getTime() > education.getEnd().getTime()
				&& (education.getEndUntilNow() == null
				|| education.getEndUntilNow() != UnitlNow.NotUntilNow.getStatus())) {
			vm.addFailedElement("时间", "开始时间大于结束时间");
		}
		if (!legalDate(education.getStart())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		if (education.getEnd() != null && !legalDate(education.getEnd())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~2099-12-31之间");
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
		if (org.apache.commons.lang.StringUtils.isNotBlank(projectExp.getStart_date())
				&& org.apache.commons.lang.StringUtils.isNotBlank(projectExp.getEnd_date())
				&& DateTime.parse(projectExp.getStart_date()).getMillis()
				> DateTime.parse(projectExp.getEnd_date()).getMillis()
				&& projectExp.getEnd_until_now() != UnitlNow.NotUntilNow.getStatus()) {
			vm.addFailedElement("项目时间", "开始时间大于结束时间");
		}
		if (!lowerNow(projectExp.getStart_date())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(projectExp.getEnd_date())
				&& !lowerNow(projectExp.getEnd_date())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~2099-12-31之间");
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
		if (projectExp.getStart() != null && projectExp.getEnd() != null
				&& projectExp.getStart().getTime() > projectExp.getEnd().getTime()
				&& (projectExp.getEndUntilNow() == null
				|| projectExp.getEndUntilNow()  != UnitlNow.NotUntilNow.getStatus())) {
			vm.addFailedElement("项目时间", "开始时间大于结束时间");
		}
		if (!lowerNow(projectExp.getStart())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		if (projectExp.getEnd() != null && !lowerNow(projectExp.getEnd())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~2099-12-31之间");
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
		if (org.apache.commons.lang.StringUtils.isNotBlank(workExp.getStart_date())
				&& org.apache.commons.lang.StringUtils.isNotBlank(workExp.getEnd_date())
				&& DateTime.parse(workExp.getStart_date()).getMillis()
				> DateTime.parse(workExp.getEnd_date()).getMillis()
				&& workExp.getEnd_until_now()  != UnitlNow.NotUntilNow.getStatus()) {
			vm.addFailedElement("工作时间", "开始时间大于结束时间");
		}
		if (!legalDate(workExp.getStart_date())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(workExp.getEnd_date())
				&& !legalDate(workExp.getEnd_date())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~2099-12-31之间");
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
		if (workExp.getStart() != null && workExp.getEnd() != null
				&& workExp.getStart().getTime() > workExp.getEnd().getTime()
				&& (workExp.getEndUntilNow() == null
				|| workExp.getEndUntilNow()  != UnitlNow.NotUntilNow.getStatus())) {
			vm.addFailedElement("工作时间", "开始时间大于结束时间");
		}
		if (!lowerNow(workExp.getStart())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		if (workExp.getEnd() != null && !lowerNow(workExp.getEnd())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~2099-12-31之间");
		}
		return vm;
	}

	public static boolean legalDate(String date) {
		Timestamp timestamp = BeanUtils.convertToSQLTimestamp(date);
		return legalDate(timestamp);
	}

	public static boolean legalDate(java.sql.Date timestamp) {
		if (timestamp != null) {
			if (timestamp.getTime() >= minTime && timestamp.getTime() <= maxTime) {
				return true;
			}
		}
		return false;
	}

	public static boolean legalDate(Timestamp timestamp) {
		if (timestamp != null) {
			if (timestamp.getTime() >= minTime && timestamp.getTime() <= maxTime) {
				return true;
			}
		}
		return false;
	}

	public static boolean lowerNow(String date) {
		Timestamp timestamp = BeanUtils.convertToSQLTimestamp(date);
		return lowerNow(timestamp);
	}

	public static boolean lowerNow(java.sql.Date timestamp) {
		if (timestamp != null) {
			if (timestamp.getTime() <= System.currentTimeMillis() && timestamp.getTime() >= minTime) {
				return true;
			}
		}
		return false;
	}

	public static boolean lowerNow(Timestamp timestamp) {
		if (timestamp != null) {
			if (timestamp.getTime() <= System.currentTimeMillis() && timestamp.getTime() >= minTime) {
				return true;
			}
		}
		return false;
	}
}