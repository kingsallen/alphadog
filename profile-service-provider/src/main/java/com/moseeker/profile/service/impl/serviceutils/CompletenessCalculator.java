package com.moseeker.profile.service.impl.serviceutils;

import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;

public class CompletenessCalculator {

	public int calculatorUserUser(UserUserRecord userRecord, UserSettingsRecord settingsRecord, UserWxUserRecord wxuser)
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
			completeness += 1;
		} else {
			if (wxuser != null && !StringUtils.isNullOrEmpty(wxuser.getHeadimgurl())) {
				completeness += 1;
			}
		}
		if (!StringUtils.isNullOrEmpty(userRecord.getName())) {
			completeness += 5;
		}
		return completeness;
	}

	public int calculatorProfileBasic(ProfileBasicRecord basicRecord) throws ParamIllegalException {
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
		if (basicRecord.getWeixin() != null) {
			completeness += 3;
		}
		if (basicRecord.getQq() != null) {
			completeness += 3;
		}
		if (!StringUtils.isNullOrEmpty(basicRecord.getSelfIntroduction())) {
			completeness += 2;
		}
		return completeness;
	}

	public int calculatorProfileWorkexp(ProfileWorkexpRecord workexpRecord, HrCompanyRecord company) throws ParamIllegalException {
		if (workexpRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEWORKEXP_LOST);
		}
		int completeness = 0;
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
		if (workexpRecord.getStart() != null && workexpRecord.getEndUntilNow() != null
				&& workexpRecord.getEndUntilNow().intValue() == 1) {
			completeness += 2;
		}
		if (StringUtils.isNotNullOrEmpty(workexpRecord.getDescription())) {
			completeness += 1;
		}
		return completeness;
	}

	public int calculatorProfileEducation(ProfileEducationRecord record) throws ParamIllegalException {
		int completeness = 0;
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
		if (!StringUtils.isNotNullOrEmpty(record.getMajorCode())
				|| !StringUtils.isNotNullOrEmpty(record.getMajorName())) {
			completeness += 3;
		}
		if(record.getDegree() != null && record.getDegree().intValue() > 0) {
			completeness += 3;
		}
		if(record.getStart() != null) {
			completeness += 2;
		}
		if(record.getEnd() != null || (record.getEndUntilNow() != null && record.getEndUntilNow().intValue() == 1)) {
			completeness += 2;
		}
		if(StringUtils.isNotNullOrEmpty(record.getDescription())) {
			completeness += 1;
		}
		return completeness;
	}
	
	public int calculatorProjectexp(ProfileProjectexpRecord record) throws ParamIllegalException {
		if(record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEPROJECTEXP_LOST);
		}
		int completeness = 0;
		
		if(record.getStart() != null) {
			completeness += 2;
		}
		if(record.getEnd() != null || (record.getEndUntilNow() != null && record.getEndUntilNow().intValue() == 1)) {
			completeness += 2;
		}
		if(StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 3;
		}
		if(StringUtils.isNotNullOrEmpty(record.getCompanyName())) {
			completeness += 2;
		}
		return completeness;
	}
}
