package com.moseeker.profile.service.impl.serviceutils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;

public class CompletenessCalculator {
	
	Logger logger = LoggerFactory.getLogger(CompletenessCalculator.class);

	public int calculateUserUser(UserUserRecord userRecord, UserSettingsRecord settingsRecord, UserWxUserRecord wxuser)
			throws ParamIllegalException {
		int completeness = 0;
		if (userRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_USERRECORD_LOST);
		}
		if (!StringUtils.isNullOrEmpty(userRecord.getUsername())) {
			completeness += 5;
		}
		if (!StringUtils.isNullOrEmpty(userRecord.getEmail())) {
			completeness += 5;
		}
		if (!StringUtils.isNullOrEmpty(userRecord.getHeadimg())) {
			completeness += 3;
		} else {
			if (wxuser != null && !StringUtils.isNullOrEmpty(wxuser.getHeadimgurl())) {
				completeness += 3;
			}
		}
		if(settingsRecord != null && StringUtils.isNotNullOrEmpty(settingsRecord.getBannerUrl())) {
			completeness += 1;
		}
		if (!StringUtils.isNullOrEmpty(userRecord.getName())) {
			completeness += 5;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_USERUSER_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_USERUSER_MAXVALUE;
		}
		return completeness;
	}

	public int calculateProfileBasic(ProfileBasicRecord basicRecord) throws ParamIllegalException {
		int completeness = 0;

		if (basicRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEBASIC_LOST);
		}
		if (basicRecord.getGender() != null && basicRecord.getGender().intValue() > 0) {
			completeness += 3;
		}
		if ((basicRecord.getNationalityCode() != null && basicRecord.getNationalityCode() > 0)
				|| !StringUtils.isNullOrEmpty(basicRecord.getNationalityName())) {
			completeness += 2;
		}
		if (!StringUtils.isNullOrEmpty(basicRecord.getMotto())) {
			completeness += 1;
		}
		if ((basicRecord.getCityCode() != null && basicRecord.getCityCode() > 0)
				|| !StringUtils.isNullOrEmpty(basicRecord.getCityName())) {
			completeness += 2;
		}
		if (basicRecord.getBirth() != null) {
			completeness += 3;
		}
		if (StringUtils.isNotNullOrEmpty(basicRecord.getWeixin())) {
			completeness += 3;
		}
		if (StringUtils.isNotNullOrEmpty(basicRecord.getQq())) {
			completeness += 3;
		}
		if (!StringUtils.isNullOrEmpty(basicRecord.getSelfIntroduction())) {
			completeness += 2;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_BASIC_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_BASIC_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateProfileWorkexps(List<? extends ProfileWorkexpRecord> workexpRecords, List<HrCompanyRecord> companies) {
		int completeness = 0;
		if(workexpRecords != null && workexpRecords.size() > 0) {
			for(ProfileWorkexpRecord workexp : workexpRecords) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE;
					break;
				}
				HrCompanyRecord companyRecord = null;
				for(HrCompanyRecord company : companies) {
					if(workexp.getCompanyId() != null && workexp.getCompanyId().intValue() == company.getId().intValue()) {
						companyRecord = company;
						break;
					}
				}
				completeness = calculateProfileWorkexp(workexp, companyRecord, completeness);
			}
		}
		return completeness;
	}

	public int calculateProfileWorkexp(ProfileWorkexpRecord workexpRecord, HrCompanyRecord company, int completeness)
			throws ParamIllegalException {
		if (workexpRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEWORKEXP_LOST);
		}
		if (company != null) {
			if (!StringUtils.isNullOrEmpty(company.getName())) {
				completeness += 3;
			}
			if (!StringUtils.isNullOrEmpty(company.getLogo())) {
				completeness += 1;
			}
		}
		if (!StringUtils.isNullOrEmpty(workexpRecord.getDepartmentName())) {
			completeness += 1;
		}
		if (StringUtils.isNotNullOrEmpty(workexpRecord.getJob())) {
			completeness += 5;
		}
		if (workexpRecord.getStart() != null) {
			completeness += 2;
		}
		if (workexpRecord.getEnd() != null || (workexpRecord.getEndUntilNow() != null
				&& workexpRecord.getEndUntilNow().intValue() == 1)) {
			completeness += 2;
		}
		if (StringUtils.isNotNullOrEmpty(workexpRecord.getDescription())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateProfileEducations(List<ProfileEducationRecord> records) throws ParamIllegalException {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileEducationRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE;
					break;
				}
				
				completeness = calculateProfileEducation(record, completeness);
			}
		}
		return completeness;
	}

	public int calculateProfileEducation(ProfileEducationRecord record, int completeness) throws ParamIllegalException {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEEDUCATION_LOST);
		}
		if (record.getCollegeCode() != null && record.getCollegeCode() > 0) {
			completeness += 6;
		} else {
			if (StringUtils.isNotNullOrEmpty(record.getCollegeName())) {
				completeness += 5;
			}
			if (StringUtils.isNotNullOrEmpty(record.getCollegeLogo())) {
				completeness += 1;
			}
		}
		if ((StringUtils.isNotNullOrEmpty(record.getMajorCode()) && !record.getMajorCode().equals("0"))
				|| StringUtils.isNotNullOrEmpty(record.getMajorName())) {
			completeness += 3;
		}
		if (record.getDegree() != null && record.getDegree().intValue() > 0) {
			completeness += 3;
		}
		if (record.getStart() != null) {
			completeness += 2;
		}
		if (record.getEnd() != null || (record.getEndUntilNow() != null && record.getEndUntilNow().intValue() == 1)) {
			completeness += 2;
		}
		if (StringUtils.isNotNullOrEmpty(record.getDescription())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateProjectexps(List<ProfileProjectexpRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileProjectexpRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE;
					break;
				}
				try {
					completeness = calculateProjectexp(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateProjectexp(ProfileProjectexpRecord record, int completeness) throws ParamIllegalException {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEPROJECTEXP_LOST);
		}
		if (record.getStart() != null) {
			completeness += 1;
		}
		if (record.getEnd() != null || (record.getEndUntilNow() != null && record.getEndUntilNow().intValue() == 1)) {
			completeness += 1;
		}
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 3;
		}
		if (StringUtils.isNotNullOrEmpty(record.getCompanyName())) {
			completeness += 2;
		}
		if (StringUtils.isNotNullOrEmpty(record.getDescription())) {
			completeness += 1;
		}
		if (StringUtils.isNotNullOrEmpty(record.getRole())) {
			completeness += 3;
		}
		if (StringUtils.isNotNullOrEmpty(record.getMember())) {
			completeness += 2;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateLanguages(List<ProfileLanguageRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileLanguageRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE;
					break;
				}
				try {
					completeness = calculateLanguage(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateLanguage(ProfileLanguageRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILELANGUAGE_LOST);
		}
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 3;
		}
		if (record.getLevel() != null && record.getLevel().intValue() > 0) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateSkills(List<ProfileSkillRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileSkillRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE;
					break;
				}
				try {
					completeness = calculateSkill(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateSkill(ProfileSkillRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILESKILL_LOST);
		}
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateCredentials(List<ProfileCredentialsRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileCredentialsRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE;
					break;
				}
				try {
					completeness = calculateCredential(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateCredential(ProfileCredentialsRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILECREDENTIALS_LOST);
		}
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateAwards(List<ProfileAwardsRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileAwardsRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE;
					break;
				}
				try {
					completeness = calculateAward(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateAward(ProfileAwardsRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEAWARDS_LOST);
		}

		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 1;
		}
		if (record.getRewardDate() != null) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateWorks(List<ProfileWorksRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileWorksRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE;
					break;
				}
				try {
					completeness = calculateWork(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}
	
	public int calculateWork(ProfileWorksRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEWORKS_LOST);
		}

		if (StringUtils.isNotNullOrEmpty(record.getCover())) {
			completeness += 1;
		}
		if(StringUtils.isNotNullOrEmpty(record.getUrl())) {
			completeness += 1;
		}
		if(StringUtils.isNotNullOrEmpty(record.getDescription())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateIntentions(List<? extends ProfileIntentionRecord> records, List<ProfileIntentionCityRecord> cityRecords, List<ProfileIntentionPositionRecord> positionRecords) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileIntentionRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_INTENTION_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_INTENTION_MAXVALUE;
					break;
				}
				ProfileIntentionCityRecord cityRecord = null;
				ProfileIntentionPositionRecord positionRecord = null;
				if(cityRecords != null && cityRecords.size() > 0) {
					for(ProfileIntentionCityRecord city: cityRecords) {
						if(city.getProfileIntentionId().intValue() == record.getId().intValue()) {
							cityRecord = city;
							break;
						}
					}
				}
				if(positionRecords != null && positionRecords.size() > 0) {
					for(ProfileIntentionPositionRecord position : positionRecords) {
						if(position.getProfileIntentionId().intValue() == record.getId().intValue()) {
							positionRecord = position;
							break;
						}
					}
				}
				completeness = calculateIntention(record, cityRecord, positionRecord, completeness);
			}
		}
		return completeness;
	}

	public int calculateIntention(ProfileIntentionRecord record, ProfileIntentionCityRecord cityRecord,
			ProfileIntentionPositionRecord positionRecord, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILINTENTION_LOST);
		}
		if (record.getSalaryCode() != null && record.getSalaryCode().intValue() > 0) {
			completeness += 1;
		}
		if (record.getWorktype() != null && record.getWorktype().intValue() > 0) {
			completeness += 1;
		}
		if (cityRecord != null) {
			if ((cityRecord.getCityCode() != null && cityRecord.getCityCode().intValue() > 0)
					|| StringUtils.isNotNullOrEmpty(cityRecord.getCityName())) {
				completeness += 1;
			}
		}
		if (positionRecord != null) {
			if ((positionRecord.getPositionCode() != null && positionRecord.getPositionCode().intValue() > 0)
					|| StringUtils.isNotNullOrEmpty(positionRecord.getPositionName())) {
				completeness += 1;
			}
		}
		return completeness;
	}
}
