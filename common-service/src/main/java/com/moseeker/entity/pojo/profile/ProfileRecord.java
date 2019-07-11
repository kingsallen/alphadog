package com.moseeker.entity.pojo.profile;

import com.moseeker.baseorm.dao.profiledb.IntentionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 简历数据
 */
public class ProfileRecord {
    
    private static final Logger logger = LoggerFactory.getLogger(ProfileRecord.class);
	private UserUserRecord userRecord;
	private ProfileProfileRecord profileRecord;
	private ProfileBasicRecord basicRecord;
	private List<ProfileAttachmentRecord> attachmentRecords;
	private List<ProfileAwardsRecord> awardsRecords;
	private List<ProfileCredentialsRecord> credentialsRecords;
	private List<ProfileEducationRecord> educationRecords;
	private ProfileImportRecord importRecords;
	private List<IntentionRecord> intentionRecords;
	private List<ProfileLanguageRecord> languageRecords;
	private ProfileOtherRecord otherRecord;
	private List<ProfileProjectexpRecord> projectExps;
	private List<ProfileSkillRecord> skillRecords;
	private List<ProfileWorkexpRecord> workexpRecords;
	private List<ProfileWorksRecord> worksRecords;

	public UserUserRecord getUserRecord() {
		return userRecord;
	}

	public void setUserRecord(UserUserRecord userRecord) {
		this.userRecord = userRecord;
	}

	public ProfileProfileRecord getProfileRecord() {
		return profileRecord;
	}

	public void setProfileRecord(ProfileProfileRecord profileRecord) {
		this.profileRecord = profileRecord;
	}

	public ProfileBasicRecord getBasicRecord() {
		return basicRecord;
	}

	public void setBasicRecord(ProfileBasicRecord basicRecord) {
		this.basicRecord = basicRecord;
	}

	public List<ProfileAttachmentRecord> getAttachmentRecords() {
		return attachmentRecords;
	}

	public void setAttachmentRecords(List<ProfileAttachmentRecord> attachmentRecords) {
		this.attachmentRecords = attachmentRecords;
	}

	public List<ProfileAwardsRecord> getAwardsRecords() {
		return awardsRecords;
	}

	public void setAwardsRecords(List<ProfileAwardsRecord> awardsRecords) {
		this.awardsRecords = awardsRecords;
	}

	public List<ProfileCredentialsRecord> getCredentialsRecords() {
		return credentialsRecords;
	}

	public void setCredentialsRecords(List<ProfileCredentialsRecord> credentialsRecords) {
		this.credentialsRecords = credentialsRecords;
	}

	public List<ProfileEducationRecord> getEducationRecords() {
		return educationRecords;
	}

	public void setEducationRecords(List<ProfileEducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	public ProfileImportRecord getImportRecords() {
		return importRecords;
	}

	public void setImportRecords(ProfileImportRecord importRecords) {
		this.importRecords = importRecords;
	}

	public List<IntentionRecord> getIntentionRecords() {
		return intentionRecords;
	}

	public void setIntentionRecords(List<IntentionRecord> intentionRecords) {
		this.intentionRecords = intentionRecords;
	}

	public List<ProfileLanguageRecord> getLanguageRecords() {
		return languageRecords;
	}

	public void setLanguageRecords(List<ProfileLanguageRecord> languageRecords) {
		this.languageRecords = languageRecords;
	}

	public ProfileOtherRecord getOtherRecord() {
		return otherRecord;
	}

	public void setOtherRecord(ProfileOtherRecord otherRecord) {
		this.otherRecord = otherRecord;
	}

	public List<ProfileProjectexpRecord> getProjectExps() {
		return projectExps;
	}

	public void setProjectExps(List<ProfileProjectexpRecord> projectExps) {
		this.projectExps = projectExps;
	}

	public List<ProfileSkillRecord> getSkillRecords() {
		return skillRecords;
	}

	public void setSkillRecords(List<ProfileSkillRecord> skillRecords) {
		this.skillRecords = skillRecords;
	}

	public List<ProfileWorkexpRecord> getWorkexpRecords() {
		return workexpRecords;
	}

	public void setWorkexpRecords(List<ProfileWorkexpRecord> workexpRecords) {
		this.workexpRecords = workexpRecords;
	}

	public List<ProfileWorksRecord> getWorksRecords() {
		return worksRecords;
	}

	public void setWorksRecords(List<ProfileWorksRecord> worksRecords) {
		this.worksRecords = worksRecords;
	}
}
