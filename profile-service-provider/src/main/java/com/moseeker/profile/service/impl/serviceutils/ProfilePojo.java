package com.moseeker.profile.service.impl.serviceutils;

import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.dao.impl.IntentionRecord;

public class ProfilePojo {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

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
	private List<ProfileWorkexpEntity> workexpRecords;
	private List<ProfileWorksRecord> worksRecords;
	
	/**
	 * 解析profile生成ProfilePojo类
	 * @param resume
	 * @param userRecord
	 * @param profileDB 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ProfilePojo parseProfile(Map<String, Object> resume, UserUserRecord userRecord) {
		LoggerFactory.getLogger(ProfilePojo.class).info("------parseProfile-------");
		ProfilePojo pojo = new ProfilePojo();
		ProfileUtils profileUtils = new ProfileUtils();
		
		//解析profile
		ProfileProfileRecord profileRecord = profileUtils
				.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
		if(profileRecord.getUserId().intValue() != userRecord.getId().intValue()) {
			profileRecord.setUserId(userRecord.getId());
		}
		profileRecord.setDisable((byte)(Constant.ENABLE));
		profileRecord.setUuid(UUID.randomUUID().toString());
		if(resume.get("channel") != null && (profileRecord.getSource() == null || profileRecord.getSource().intValue() == 0)) {
			int channel = (Integer)resume.get("channel");
			ChannelType channelType = ChannelType.instaceFromInteger(channel);
			profileRecord.setOrigin(channelType.getOrigin(profileRecord.getOrigin()));
		}
		pojo.setProfileRecord(profileRecord);
		
		//解析用户信息
		UserUserRecord crawlerUser = null;
		try {
			crawlerUser = profileUtils.mapToUserUserRecord((Map<String, Object>) resume.get("user"));
		} catch (Exception e1) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e1.getMessage(), e1);
		}
		//解析基本信息
		ProfileBasicRecord basicRecord = null;
		try {
			basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"));
			pojo.setBasicRecord(basicRecord);
		} catch (Exception e1) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e1.getMessage(), e1);
		}
		//更新用户信息
		profileUtils.updateUser(userRecord, basicRecord, crawlerUser);
		
		//解析附件信息
		List<ProfileAttachmentRecord> attachmentRecords = null;
		try {
			attachmentRecords = profileUtils
					.mapToAttachmentRecords((List<Map<String, Object>>) resume.get("attachments"));
			pojo.setAttachmentRecords(attachmentRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析奖励信息
		List<ProfileAwardsRecord> awardsRecords = null;
		try {
			awardsRecords = profileUtils
					.mapToAwardsRecords((List<Map<String, Object>>) resume.get("awards"));
			pojo.setAwardsRecords(awardsRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析证书信息
		List<ProfileCredentialsRecord> credentialsRecords = null;
		try {
			credentialsRecords = profileUtils
					.mapToCredentialsRecords((List<Map<String, Object>>) resume.get("credentials"));
			pojo.setCredentialsRecords(credentialsRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析教育经历
		List<ProfileEducationRecord> educationRecords = null;
		try {
			educationRecords = profileUtils
					.mapToEducationRecords((List<Map<String, Object>>) resume.get("educations"));
			pojo.setEducationRecords(educationRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析导入信息
		ProfileImportRecord importRecords = null;
		try {
			importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"));
			pojo.setImportRecords(importRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析期望
		List<IntentionRecord> intentionRecords = null;
		try {
			intentionRecords = profileUtils
					.mapToIntentionRecord((List<Map<String, Object>>) resume.get("intentions"));
			pojo.setIntentionRecords(intentionRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析语言
		List<ProfileLanguageRecord> languageRecords = null;
		try {
			languageRecords = profileUtils
					.mapToLanguageRecord((List<Map<String, Object>>) resume.get("languages"));
			pojo.setLanguageRecords(languageRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析其他
		ProfileOtherRecord otherRecord = null;
		try {
			otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"));
			pojo.setOtherRecord(otherRecord);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析项目经验
		List<ProfileProjectexpRecord> projectExps = null;
		try {
			projectExps = profileUtils
					.mapToProjectExpsRecords((List<Map<String, Object>>) resume.get("projectexps"));
			pojo.setProjectExps(projectExps);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析技能
		List<ProfileSkillRecord> skillRecords = null;
		try {
			skillRecords = profileUtils
					.mapToSkillRecords((List<Map<String, Object>>) resume.get("skills"));
			pojo.setSkillRecords(skillRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析工作经验
		List<ProfileWorkexpEntity> workexpRecords = null;
		try {
			int source = 0;
			if(profileRecord.getSource() != null) {
				source = profileRecord.getSource().intValue();
			}
			LoggerFactory.getLogger(ProfilePojo.class).info("workexp:{}", JSON.toJSONString(resume.get("workexps")));
			workexpRecords = profileUtils
					.mapToWorkexpRecords((List<Map<String, Object>>) resume.get("workexps"), source);
			if(workexpRecords != null && workexpRecords.size() > 0) {
				workexpRecords.forEach(workexp -> {
					if(workexp.getCompany() != null) {
						LoggerFactory.getLogger(ProfilePojo.class).info("company:{}, company.name:{}", workexp.getCompany(), workexp.getCompany().getName());
					}
				});
			}
			pojo.setWorkexpRecords(workexpRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		//解析作品
		List<ProfileWorksRecord> worksRecords = null;
		try {
			worksRecords = profileUtils
					.mapToWorksRecords((List<Map<String, Object>>) resume.get("works"));
			pojo.setWorksRecords(worksRecords);
		} catch (Exception e) {
			LoggerFactory.getLogger(ProfilePojo.class).error(e.getMessage(), e);
		}
		
		return pojo;
	}
	
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
	public List<ProfileWorkexpEntity> getWorkexpRecords() {
		return workexpRecords;
	}
	public void setWorkexpRecords(List<ProfileWorkexpEntity> workexpRecords) {
		this.workexpRecords = workexpRecords;
	}
	public List<ProfileWorksRecord> getWorksRecords() {
		return worksRecords;
	}
	public void setWorksRecords(List<ProfileWorksRecord> worksRecords) {
		this.worksRecords = worksRecords;
	}
	
	@Test
	public void test() {
		String stri = "THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLATED_QUEUE{SYNCHRONIZATION}";
		System.out.println(stri.length());
	}
}
