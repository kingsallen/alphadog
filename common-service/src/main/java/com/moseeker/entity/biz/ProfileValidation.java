package com.moseeker.entity.biz;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.Constant.UntitlNow;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.struct.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Timestamp;

public class ProfileValidation {

	private static Logger logger = LoggerFactory.getLogger(ProfileValidation.class);

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
		try {
			JSONObject jsonObject = JSONObject.parseObject(customizeResume.getOther());
			if(jsonObject == null) {
				vm.addFailedElement("其他字段", "未填写其他字段的内容");
			}
		} catch (Exception e) {
			vm.addFailedElement("其他字段", "不是正确的json数据");
			logger.error(e.getMessage(), e);
		}

		return vm;
	}

	public static ValidationMessage<ProfileOtherDO> verifyOther(ProfileOtherDO profileOtherDO) {
		ValidationMessage<ProfileOtherDO> vm = new ValidationMessage<>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(profileOtherDO.getOther());
			if(jsonObject == null) {
				vm.addFailedElement("其他字段", "未填写其他字段的内容");
			}
		} catch (Exception e) {
			vm.addFailedElement("其他字段", "不是正确的json数据");
			logger.error(e.getMessage(), e);
		}

		return vm;
	}
	
	public static ValidationMessage<ProfileOtherRecord> verifyCustomizeResume(ProfileOtherRecord customizeResume) {
		ValidationMessage<ProfileOtherRecord> vm = new ValidationMessage<>();
		try {
			JSONObject jsonObject = JSONObject.parseObject(customizeResume.getOther());
			if(jsonObject == null) {
				vm.addFailedElement("其他字段", "未填写其他字段的内容");
			}
		} catch (Exception e) {
			vm.addFailedElement("其他字段", "不是正确的json数据");
			logger.error(e.getMessage(), e);
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
		/**
		 * 教育经历去掉时间相关的校验
		 */
		/*if(StringUtils.isNullOrEmpty(education.getStart_date())) {
			vm.addFailedElement("开始时间", "未选择开始时间");
		}
		if (org.apache.commons.lang.StringUtils.isBlank(education.getEnd_date())
				&& education.getEnd_until_now() == UntitlNow.NotUntilNow.getStatus()) {
			vm.addFailedElement("结束时间", "未选择结束时间");
		}*/
		if (org.apache.commons.lang.StringUtils.isNotBlank(education.getStart_date())
				&& org.apache.commons.lang.StringUtils.isNotBlank(education.getEnd_date())
				&& education.getEnd_until_now() != UntitlNow.UntilNow.getStatus()
				&& DateTime.parse(education.getStart_date()).getMillis()
				> DateTime.parse(education.getEnd_date()).getMillis()) {
			vm.addFailedElement("时间", "开始时间大于结束时间");
		}
		logger.info("education经历开始时间：{}，islowerNow：{}", education.getStart_date(),lowerNow(education.getStart_date()));
		if (!lowerNow(education.getStart_date())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
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
		/**
		 * 教育经历去掉时间相关的校验
		 */
		/*if(education.getStart() == null) {
			vm.addFailedElement("开始时间", "未选择开始时间");
		}
		if (education.getEnd() == null && education.getEndUntilNow() == UntitlNow.NotUntilNow.getStatus()) {
			vm.addFailedElement("结束时间", "未选择结束时间");
		}*/
		if (education.getStart() != null && education.getEnd() != null
				&& education.getStart().getTime() > education.getEnd().getTime()
				&& (education.getEndUntilNow() == null
				|| education.getEndUntilNow() != UntitlNow.UntilNow.getStatus())) {
			vm.addFailedElement("时间", "开始时间大于结束时间");
		}
        logger.info("profile resume education ：{}，islowerNow：{}", education.getStart(),lowerNow(education.getStart()));

		if (education.getStart()!= null && !lowerNow(education.getStart().getTime())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
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
		/**
		 * 项目经历取消起始时间校验
		 */
		/*if(StringUtils.isNullOrEmpty(projectExp.getStart_date())&& projectExp.getEnd_until_now()  != UntitlNow.UntilNow.getStatus()) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (StringUtils.isNullOrEmpty(projectExp.getEnd_date())
				&& projectExp.getEnd_until_now()  != UntitlNow.UntilNow.getStatus()) {
			vm.addFailedElement("结束时间", "未填写结束时间");
		}*/
		if (org.apache.commons.lang.StringUtils.isNotBlank(projectExp.getStart_date())
				&& org.apache.commons.lang.StringUtils.isNotBlank(projectExp.getEnd_date())
				&& DateTime.parse(projectExp.getStart_date()).getMillis()
				> DateTime.parse(projectExp.getEnd_date()).getMillis()
				&& projectExp.getEnd_until_now() != UntitlNow.UntilNow.getStatus()) {
			vm.addFailedElement("项目时间", "开始时间大于结束时间");
		}

		if ( !lowerNow(projectExp.getStart_date())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(projectExp.getEnd_date())
				&& !lowerNow(projectExp.getEnd_date())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~至今之间");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileProjectexpRecord> verifyProjectExp(ProfileProjectexpRecord projectExp) {
        logger.info("profile resume project:{}",projectExp.getStart()+":"+projectExp.getEnd());
	    ValidationMessage<ProfileProjectexpRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(projectExp.getName())) {
			vm.addFailedElement("项目名称", "未填写项目名称!");
		}
		/**
		 * 项目经历取消起始时间校验
		 */
		/*if(projectExp.getStart() == null) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (projectExp.getEnd() == null && (projectExp.getEndUntilNow() == null || projectExp.getEndUntilNow()  != UntitlNow.UntilNow.getStatus())) {
			vm.addFailedElement("结束时间", "未填写结束时间");
		}*/
		if (projectExp.getStart() != null && projectExp.getEnd() != null
				&& projectExp.getStart().getTime() > projectExp.getEnd().getTime()
				&& (projectExp.getEndUntilNow() == null
				|| projectExp.getEndUntilNow()  != UntitlNow.UntilNow.getStatus())) {
			vm.addFailedElement("项目时间", "开始时间大于结束时间");
		}
		if (projectExp.getStart()!=null && !lowerNow(projectExp.getStart().getTime())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
		}
		if (projectExp.getEnd() != null && !lowerNow(projectExp.getEnd())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~至今之间");
		}
        logger.info("profile resume project vm:{}",vm.isPass()+":"+vm.getResult());
		return vm;
	}
	
	public static ValidationMessage<Skill> verifySkill(Skill skill) {
		ValidationMessage<Skill> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(skill.getName())) {
			vm.addFailedElement("技能名称", "未填写技能名称");
		} else if (skill.getName().length() > 100){
			vm.addFailedElement("技能名称", "超过最长字数限制");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileSkillRecord> verifySkill(ProfileSkillRecord skill) {
		ValidationMessage<ProfileSkillRecord> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(skill.getName())) {
			vm.addFailedElement("技能名称", "未填写技能名称");
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
		//取消职位校验
		/*if(StringUtils.isNullOrEmpty(workExp.getJob())) {
			vm.addFailedElement("职位名称", "未填写职位名称");
		}*/
        if(workExp.getJob()!=null && workExp.getJob().length()>100) {
            vm.addFailedElement("职位名称", "超过最长字数限制");
        }
        if(workExp.getDescription()!=null && workExp.getDescription().length()>5000) {
            vm.addFailedElement("工作描述", "超过最长字数限制");
        }
        //取消起始时间校验
		/*if(StringUtils.isNullOrEmpty(workExp.getStart_date())) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (StringUtils.isNullOrEmpty(workExp.getEnd_date())
				&& workExp.getEnd_until_now()  != UntitlNow.UntilNow.getStatus()) {
			vm.addFailedElement("结束时间", "未填写结束时间");
		}*/
		if (org.apache.commons.lang.StringUtils.isNotBlank(workExp.getStart_date())
				&& org.apache.commons.lang.StringUtils.isNotBlank(workExp.getEnd_date())
				&& DateTime.parse(workExp.getStart_date()).getMillis()
				> DateTime.parse(workExp.getEnd_date()).getMillis()
				&& workExp.getEnd_until_now()  != UntitlNow.UntilNow.getStatus()) {
			vm.addFailedElement("工作时间", "开始时间大于结束时间");
		}
		if (!lowerNow(workExp.getStart_date())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(workExp.getEnd_date())
				&& !lowerNow(workExp.getEnd_date())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~至今之间");
		}
		return vm;
	}
	
	public static ValidationMessage<ProfileWorkexpEntity> verifyWorkExp(ProfileWorkexpEntity workExp) {
        logger.info("profile resume works:{}",workExp.getStart()+":"+workExp.getEnd());
		ValidationMessage<ProfileWorkexpEntity> vm = new ValidationMessage<>();
		if(workExp.getCompany() == null || StringUtils.isNullOrEmpty(workExp.getCompany().getName())) {
			vm.addFailedElement("就职公司", "未填写就职公司");
		}
		/*if(StringUtils.isNullOrEmpty(workExp.getJob())) {
			vm.addFailedElement("职位名称", "未填写职位名称");
		}*/
		if(workExp.getJob()!=null && workExp.getJob().length()>100) {
			vm.addFailedElement("职位名称", "超过最长字数限制");
		}
        if(workExp.getDescription()!=null && workExp.getDescription().length()>5000) {
            vm.addFailedElement("工作描述", "超过最长字数限制");
        }
		/**
		 * 取消起始时间校验
		 */
		/*if(workExp.getStart() == null) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if (workExp.getEnd() == null && (workExp.getEndUntilNow() == null || workExp.getEndUntilNow() != UntitlNow.UntilNow.getStatus())) {
			vm.addFailedElement("结束时间", "未填写结束时间");
		}*/
		if (workExp.getStart() != null && workExp.getEnd() != null
				&& workExp.getStart().getTime() > workExp.getEnd().getTime()
				&& (workExp.getEndUntilNow() == null
				|| workExp.getEndUntilNow()  != UntitlNow.UntilNow.getStatus())) {
			vm.addFailedElement("工作时间", "开始时间大于结束时间");
		}

		if (workExp.getStart() != null && !lowerNow(workExp.getStart().getTime())) {

			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
		}
		if (workExp.getEnd() != null && !lowerNow(workExp.getEnd())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~至今之间");
		}
        logger.info("profile resume works vm:{}",vm.isPass()+":"+vm.getResult());
		return vm;
	}

	public static ValidationMessage<ProfileWorkexpRecord> verifyWorkExp(ProfileWorkexpRecord workExp) {
		ValidationMessage<ProfileWorkexpRecord> vm = new ValidationMessage<>();
		if(workExp.getCompanyId() == null ) {
			vm.addFailedElement("就职公司", "未填写就职公司");
		}
		/*if(StringUtils.isNullOrEmpty(workExp.getJob())) {
			vm.addFailedElement("职位名称", "未填写职位名称");
		}*/
		if(workExp.getJob()!=null && workExp.getJob().length()>100) {
			vm.addFailedElement("职位名称", "超过最长字数限制");
		}
        if(workExp.getDescription()!=null && workExp.getDescription().length()>5000) {
            vm.addFailedElement("工作描述", "超过最长字数限制");
        }
		/**
		 * 取消起始时间校验
		 */
		/*if(workExp.getStart() == null) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if(workExp.getEnd() == null && workExp.getEndUntilNow() != null
				&& workExp.getEndUntilNow() == UntitlNow.NotUntilNow.getStatus() ) {
			vm.addFailedElement("结束时间", "未填写结束时间");
		}*/
		if (workExp.getStart() != null && workExp.getEnd() != null
				&& workExp.getStart().getTime() > workExp.getEnd().getTime()
				&& (workExp.getEndUntilNow() == null
				|| workExp.getEndUntilNow()  != UntitlNow.UntilNow.getStatus())) {
			vm.addFailedElement("工作时间", "开始时间大于结束时间");
		}

		if (workExp.getStart() != null && !lowerNow(workExp.getStart().getTime())) {
			vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
		}
		if (workExp.getEnd() != null && !lowerNow(workExp.getEnd())) {
			vm.addFailedElement("结束时间", "时间限制在1900-01-01~至今之间");
		}
		return vm;
	}

	public static ValidationMessage<Basic> verifyBasic(Basic basicRecord) {
		ValidationMessage<Basic> vm = new ValidationMessage<>();
		if (basicRecord != null && basicRecord.getBirth() != null) {
			DateTime birth = DateTime.parse(basicRecord.getBirth());
            if (birth != null && !lowerNow(birth.getMillis())) {
                vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
            }
		}
		return vm;
	}

	public static ValidationMessage<ProfileBasicRecord> verifyBasic(ProfileBasicRecord basicRecord) {
        logger.info("prfile resume birth:{}",basicRecord.getBirth());
		ValidationMessage<ProfileBasicRecord> vm = new ValidationMessage<>();
		if (basicRecord != null && basicRecord.getBirth() != null) {
            if (!lowerNow(basicRecord.getBirth())) {
                vm.addFailedElement("开始时间", "时间限制在1900-01-01~至今之间");
            }
		}
        logger.info("prfile resume birth vm:{}",vm.isPass()+":"+vm.getResult());
		return vm;
	}

	public static ValidationMessage<ProfileAwardsRecord> verifyAward(ProfileAwardsRecord awardsRecord) {
		ValidationMessage<ProfileAwardsRecord> vm = new ValidationMessage<>();
		if (awardsRecord != null && awardsRecord.getRewardDate() != null) {
			if (!lowerNow(awardsRecord.getRewardDate().getTime())) {
				vm.addFailedElement("获奖日期", "时间限制在1900-01-01~至今之间");
			}
		}
		return vm;
	}

	public static ValidationMessage<Awards> verifyAward(Awards awards) {
		ValidationMessage<Awards> vm = new ValidationMessage<>();
		if (awards != null && awards.getReward_date() != null) {
			Timestamp timestamp = BeanUtils.convertToSQLTimestamp(awards.getReward_date());
			if (timestamp != null) {
				if (!lowerNow(timestamp.getTime())) {
					vm.addFailedElement("获奖日期", "时间限制在1900-01-01~至今之间");
				}
			}

		}
		return vm;
	}

	/**
	 * 判断字符串是否是合法的日期，并且日期是否在规定的区间。
	 * @param date 日期
	 * @return true 是在合适的区间；false 参数为空/不是正确的日期/不是在合适的区间
	 */
	public static boolean legalDate(String date) {
		if (date == null) {
			return false;
		}
		Timestamp timestamp = BeanUtils.convertToSQLTimestamp(date);
		if (timestamp != null) {
			return legalDate(timestamp.getTime());
		}
		return false;
	}

	/**
	 * 判断日期是否是在规定的区间。
	 * @param timestamp 日期
	 * @return true 是在合适的区间；false 参数为空/不是在合适的区间
	 */
	public static boolean legalDate(java.sql.Date timestamp) {
		if (timestamp != null) {
			return legalDate(timestamp.getTime());
		}
		return false;
	}

	/**
	 * 判断日期是否是在规定的区间。
	 * @param timestamp 日期
	 * @return true 是在合适的区间；false 参数为空/不是在合适的区间
	 */
	public static boolean legalDate(Timestamp timestamp) {
		if (timestamp != null) {
			return legalDate(timestamp.getTime());
		}
		return false;
	}

	private static boolean legalDate(long time) {
		if (time >= minTime && time <= maxTime) {
			return true;
		}
		return false;
	}

	private static boolean lowerNow(Date start) {
		if (start != null) {
			return legalDate(start);
		}
		return false;
	}


	public static boolean lowerNow(String date) {
		if (date == null) {
			return false;
		}
		Timestamp timestamp = BeanUtils.convertToSQLTimestamp(date);
		return lowerNow(timestamp.getTime());
	}

	private static boolean lowerNow(long time) {
        if (time < System.currentTimeMillis() && time >= minTime) {
            return true;
        }
		return false;
	}
}