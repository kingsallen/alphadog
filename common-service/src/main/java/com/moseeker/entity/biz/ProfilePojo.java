package com.moseeker.entity.biz;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.profiledb.IntentionRecord;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 简历工具类
 */
public class ProfilePojo {
    
    private static final Logger logger = LoggerFactory.getLogger(ProfilePojo.class);
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
	 * @param extParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ProfilePojo parseProfile(Map<String, Object> resume, ProfileExtParam extParam) {
		logger.info("------parseProfile-------");

		ProfilePojo pojo = new ProfilePojo();
		ProfileUtils profileUtils = new ProfileUtils();

		//解析profile
		ProfileProfileRecord profileRecord = profileUtils
				.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
		if (profileRecord == null) {
			profileRecord = new ProfileProfileRecord();
		}
		profileRecord.setDisable((byte)(Constant.ENABLE));
		profileRecord.setUuid(UUID.randomUUID().toString());
		pojo.setProfileRecord(profileRecord);

        //解析用户信息
        UserUserRecord crawlerUser = null;
        try {
            Map<String, Object> userMap = (Map<String, Object>) resume.get("user");
            if (userMap != null) {
                String mobile = String.valueOf(userMap.get("mobile"));
                if (!org.apache.commons.lang.StringUtils.isNumeric(mobile)) {
                    userMap.remove("mobile");
                }
				if (userMap.get("email") != null) {
					String email = String.valueOf(userMap.get("email"));
					if(!email.trim().equals("")){
						if (!FormCheck.isEmail(email)) {
							userMap.remove("email");
						}
					}
				}
                crawlerUser = profileUtils.mapToUserUserRecord(userMap);
                pojo.setUserRecord(crawlerUser);
            }
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }

		//解析基本信息
		ProfileBasicRecord basicRecord = null;
		try {
			basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"), extParam);
			pojo.setBasicRecord(basicRecord);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}

		//解析附件信息
		List<ProfileAttachmentRecord> attachmentRecords = null;
		try {
			logger.info("ProfilePojo attachments:{}", resume.get("attachments"));
			attachmentRecords = profileUtils
					.mapToAttachmentRecords((List<Map<String, Object>>) resume.get("attachments"));
			pojo.setAttachmentRecords(attachmentRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析奖励信息
		List<ProfileAwardsRecord> awardsRecords = null;
		try {
			awardsRecords = profileUtils
					.mapToAwardsRecords((List<Map<String, Object>>) resume.get("awards"));
			pojo.setAwardsRecords(awardsRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析证书信息
		List<ProfileCredentialsRecord> credentialsRecords = null;
		try {
			credentialsRecords = profileUtils
					.mapToCredentialsRecords((List<Map<String, Object>>) resume.get("credentials"));
			pojo.setCredentialsRecords(credentialsRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析教育经历
		List<ProfileEducationRecord> educationRecords = null;
		try {
			educationRecords = profileUtils
					.mapToEducationRecords((List<Map<String, Object>>) resume.get("educations"), extParam);
			pojo.setEducationRecords(educationRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析导入信息
		ProfileImportRecord importRecords = null;
		try {
			importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"));
			pojo.setImportRecords(importRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析期望
		List<IntentionRecord> intentionRecords = null;
		try {
			intentionRecords = profileUtils
					.mapToIntentionRecord((List<Map<String, Object>>) resume.get("intentions"));
			pojo.setIntentionRecords(intentionRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析语言
		List<ProfileLanguageRecord> languageRecords = null;
		try {
			languageRecords = profileUtils
					.mapToLanguageRecord((List<Map<String, Object>>) resume.get("languages"));
			pojo.setLanguageRecords(languageRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析其他
		ProfileOtherRecord otherRecord = null;
		try {
			otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"), extParam);
			pojo.setOtherRecord(otherRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析项目经验
		List<ProfileProjectexpRecord> projectExps = null;
		try {
			projectExps = profileUtils
					.mapToProjectExpsRecords((List<Map<String, Object>>) resume.get("projectexps"));
			pojo.setProjectExps(projectExps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析技能
		List<ProfileSkillRecord> skillRecords = null;
		try {
			skillRecords = profileUtils
					.mapToSkillRecords((List<Map<String, Object>>) resume.get("skills"));
			pojo.setSkillRecords(skillRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析工作经验
		List<ProfileWorkexpEntity> workexpRecords = null;
		try {
			int source = 0;
			if(profileRecord.getSource() != null) {
				source = profileRecord.getSource().intValue();
			}
			logger.info("workexp:{}", JSON.toJSONString(resume.get("workexps")));
			workexpRecords = profileUtils
					.mapToWorkexpRecords((List<Map<String, Object>>) resume.get("workexps"), source);
			if(workexpRecords != null && workexpRecords.size() > 0) {
				workexpRecords.forEach(workexp -> {
					if(workexp.getCompany() != null) {
						logger.info("company:{}, company.name:{}", workexp.getCompany(), workexp.getCompany().getName());
					}
				});
			}
			pojo.setWorkexpRecords(workexpRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析作品
		List<ProfileWorksRecord> worksRecords = null;
		try {
			worksRecords = profileUtils
					.mapToWorksRecords((List<Map<String, Object>>) resume.get("works"));
			pojo.setWorksRecords(worksRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return pojo;
	}

	/**
	 * 解析profile生成ProfilePojo类
	 * @param resume
	 * @param userRecord
	 * @param extParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ProfilePojo parseProfile(Map<String, Object> resume, UserUserRecord userRecord, ProfileExtParam extParam) {
		logger.info("------parseProfile-------");
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
			Map<String, Object> userMap = (Map<String, Object>) resume.get("user");
			if (userMap != null) {
				if (userMap.get("mobile") != null) {
					String mobile = String.valueOf(userMap.get("mobile"));
					if (!org.apache.commons.lang.StringUtils.isNumeric(mobile)) {
						userMap.remove("mobile");
					}
				}
				if (userMap.get("email") != null&& StringUtils.isNotNullOrEmpty(String.valueOf(userMap.get("email")))) {
					String email = String.valueOf(userMap.get("email"));
					if (!FormCheck.isEmail(email)) {
						userMap.remove("email");
					}
				}

				crawlerUser = profileUtils.mapToUserUserRecord(userMap);
				pojo.setUserRecord(crawlerUser);
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}

		//解析基本信息
		ProfileBasicRecord basicRecord = null;
		try {
			basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"), extParam);
			pojo.setBasicRecord(basicRecord);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
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
			logger.error(e.getMessage(), e);
		}
		//解析奖励信息
		List<ProfileAwardsRecord> awardsRecords = null;
		try {
			awardsRecords = profileUtils
					.mapToAwardsRecords((List<Map<String, Object>>) resume.get("awards"));
			pojo.setAwardsRecords(awardsRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析证书信息
		List<ProfileCredentialsRecord> credentialsRecords = null;
		try {
			credentialsRecords = profileUtils
					.mapToCredentialsRecords((List<Map<String, Object>>) resume.get("credentials"));
			pojo.setCredentialsRecords(credentialsRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析教育经历
		List<ProfileEducationRecord> educationRecords = null;
		try {
			educationRecords = profileUtils
					.mapToEducationRecords((List<Map<String, Object>>) resume.get("educations"), extParam);
			pojo.setEducationRecords(educationRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析导入信息
		ProfileImportRecord importRecords = null;
		try {
			importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"));
			pojo.setImportRecords(importRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析期望
		List<IntentionRecord> intentionRecords = null;
		try {
			intentionRecords = profileUtils
					.mapToIntentionRecord((List<Map<String, Object>>) resume.get("intentions"));
			pojo.setIntentionRecords(intentionRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析语言
		List<ProfileLanguageRecord> languageRecords = null;
		try {
			languageRecords = profileUtils
					.mapToLanguageRecord((List<Map<String, Object>>) resume.get("languages"));
			pojo.setLanguageRecords(languageRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析其他
		ProfileOtherRecord otherRecord = null;
		try {
			otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"), extParam);
			pojo.setOtherRecord(otherRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析项目经验
		List<ProfileProjectexpRecord> projectExps = null;
		try {
			projectExps = profileUtils
					.mapToProjectExpsRecords((List<Map<String, Object>>) resume.get("projectexps"));
			pojo.setProjectExps(projectExps);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析技能
		List<ProfileSkillRecord> skillRecords = null;
		try {
			skillRecords = profileUtils
					.mapToSkillRecords((List<Map<String, Object>>) resume.get("skills"));
			pojo.setSkillRecords(skillRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析工作经验
		List<ProfileWorkexpEntity> workexpRecords = null;
		try {
			int source = 0;
			if(profileRecord.getSource() != null) {
				source = profileRecord.getSource().intValue();
			}
			logger.info("workexp:{}", JSON.toJSONString(resume.get("workexps")));
			workexpRecords = profileUtils
					.mapToWorkexpRecords((List<Map<String, Object>>) resume.get("workexps"), source);
			if(workexpRecords != null && workexpRecords.size() > 0) {
				workexpRecords.forEach(workexp -> {
					if(workexp.getCompany() != null) {
						logger.info("company:{}, company.name:{}", workexp.getCompany(), workexp.getCompany().getName());
					}
				});
			}
			pojo.setWorkexpRecords(workexpRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//解析作品
		List<ProfileWorksRecord> worksRecords = null;
		try {
			worksRecords = profileUtils
					.mapToWorksRecords((List<Map<String, Object>>) resume.get("works"));
			pojo.setWorksRecords(worksRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return pojo;
	}

	public String toJson() {
		Map<String, Object> map = new HashMap<>();

		if (this.userRecord != null)
			map.put("user", this.userRecord.intoMap());
		if (this.profileRecord != null) {
			map.put("profile", profileRecord.intoMap());
		}
		if (this.basicRecord != null) {
			map.put("basic", this.basicRecord.intoMap());
		}
		if (this.attachmentRecords != null && this.attachmentRecords.size() > 0) {
			List<Map<String, Object>> attachments = new ArrayList<>();
			for (ProfileAttachmentRecord attachment : this.attachmentRecords) {
				Map<String, Object> attachmentMap = attachment.intoMap();
				attachments.add(attachmentMap);
			}
			map.put("attachments", attachments);
		}
		if (this.awardsRecords != null && this.awardsRecords.size() > 0) {
			List<Map<String, Object>> awardsRecordList = new ArrayList<>();
			for (ProfileAwardsRecord awardsRecord : this.awardsRecords) {
				Map<String, Object> awardsRecordMap = awardsRecord.intoMap();
				awardsRecordList.add(awardsRecordMap);
			}
			map.put("awards", awardsRecordList);
		}
		if (this.credentialsRecords != null && this.credentialsRecords.size() > 0) {
			List<Map<String, Object>> credentialRecordList = new ArrayList<>(credentialsRecords.size());
			for (ProfileCredentialsRecord profileCredentialsRecord : this.credentialsRecords) {
				Map<String, Object> map1 = profileCredentialsRecord.intoMap();
				credentialRecordList.add(map1);
			}
			map.put("credentials", credentialRecordList);
		}
		if (this.educationRecords != null && this.educationRecords.size() > 0) {

			List<Map<String, Object>> educationList = new ArrayList<>(this.educationRecords.size());
			for (ProfileEducationRecord profileEducationRecord: this.educationRecords) {
				Map<String, Object> map1 = profileEducationRecord.intoMap();
				educationList.add(map1);
			}
			map.put("educations", educationList);
		}
		if (this.importRecords != null) {
			map.put("import", this.importRecords.intoMap());
		}
		if (this.intentionRecords != null && this.intentionRecords.size() > 0) {
			List<Map<String, Object>> intentionList = new ArrayList<>(this.intentionRecords.size());
			for (IntentionRecord intentionRecord : this.intentionRecords) {
				intentionList.add(intentionRecord.intoMapNew());
			}
			map.put("intentions", intentionList);
		}
		if (this.languageRecords != null && this.languageRecords.size() > 0) {
			List<Map<String, Object>> languageList = new ArrayList<>(this.languageRecords.size());
			map.put("languages", languageList);
		}
		if (this.otherRecord != null) {
			map.put("other", this.otherRecord.intoMap());
		}
		if (this.projectExps != null && this.projectExps.size() > 0) {
			List<Map<String, Object>> projectExpList = new ArrayList<>(this.projectExps.size());
			for (ProfileProjectexpRecord profileProjectexpRecord : this.projectExps) {
				projectExpList.add(profileProjectexpRecord.intoMap());
			}
			map.put("projectexps", projectExpList);
		}
		if (this.skillRecords != null && this.skillRecords.size() > 0) {
			List<Map<String, Object>> skillList = new ArrayList<>(this.skillRecords.size());
			for (ProfileSkillRecord profileSkillRecord : this.skillRecords) {
				skillList.add(profileSkillRecord.intoMap());
			}
			map.put("skills", skillList);
		}
		if (this.workexpRecords != null && this.workexpRecords.size() > 0) {
			List<Map<String, Object>> workexpList = new ArrayList<>(this.workexpRecords.size());
			for (ProfileWorkexpEntity profileWorkexpEntity : this.workexpRecords) {
				workexpList.add(profileWorkexpEntity.intoMapNew());
			}
			map.put("workexps", workexpList);
		}
		if (this.worksRecords != null && this.worksRecords.size() > 0) {
			List<Map<String, Object>> worksList = new ArrayList<>(this.worksRecords.size());
			for (ProfileWorksRecord profileWorksRecord : this.worksRecords) {
				worksList.add(profileWorksRecord.intoMap());
			}
			map.put("works", worksList);
		}

		return JSON.toJSONString(map);

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

	//@Test
	public void test() {
		String stri = "THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLATED_QUEUE{SYNCHRONIZATION}";
		System.out.println(stri.length());
	}
}
