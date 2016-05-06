package com.moseeker.profile.dao.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.profiledb.tables.ProfileEducation;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.struct.Education;

@Repository
public class EducationDaoImpl extends
		BaseDaoImpl<ProfileEducationRecord, ProfileEducation, Education> implements
		ProfileDao<Education> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileEducation.PROFILE_EDUCATION;
	}

	@Override
	protected Education DBToStruct(ProfileEducationRecord r) {
		Education education = null;
		if (r != null) {
			education = new Education();
			if (r.getCreateTime() != null) {
				education.setCreate_time(DateUtils.dateToNormalDate(new Date(r
						.getCreateTime().getTime())));
			}
			if (r.getUpdateTime() != null) {
				education.setUpdate_time(DateUtils.dateToNormalDate(new Date(r
						.getUpdateTime().getTime())));
			}
			if(r.getStart() != null) {
				education.setStarDate(DateUtils.dateToNormalDate(new Date(r.getStart().getTime())));
			}
			if(r.getEnd() != null) {
				education.setEndDate(DateUtils.dateToNormalDate(new Date(r.getEnd().getTime())));
			}
			education.setDegree(r.getDegree());
			education.setDescription(r.getDescription());
			education.setEnd_until_now(r.getEndUntilNow());
			education.setId(r.getId().intValue());
			education.setMajor_code(r.getMajorCode());
			education.setMajor_name(r.getMajorName());
			education.setProfile_id(r.getProfileId().intValue());
			education.setSchool_code(r.getSchoolCode());
		}
		return education;
	}

	@Override
	protected ProfileEducationRecord structToDB(Education education)
			throws ParseException {
		ProfileEducationRecord record = new ProfileEducationRecord();
		education.isSetCreate_time();
		/*if(attachment.isSetId()) {
			record.setId(UInteger.valueOf(attachment.getId()));
		}
		if(attachment.isSetName()) {
			record.setName(attachment.getName());
		}
		if(attachment.isSetPath()) {
			record.setPath(attachment.getPath());
		}
		if(attachment.isSetProfile_id()) {
			record.setProfileId(UInteger.valueOf(attachment.getProfile_id()));
		}
		if(attachment.isSetDescription()) {
			record.setDescription(attachment.getDescription());
		}*/
		return record;
	}
}
