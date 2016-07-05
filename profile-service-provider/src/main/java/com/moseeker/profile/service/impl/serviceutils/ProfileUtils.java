package com.moseeker.profile.service.impl.serviceutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.dao.impl.IntentionRecord;
import com.mysql.jdbc.StringUtils;

public class ProfileUtils {

	Logger logger = LoggerFactory.getLogger(ProfileUtils.class);

	public List<ProfileWorksRecord> mapToWorksRecords(List<Map<String, Object>> works) {
		List<ProfileWorksRecord> worksRecords = new ArrayList<>();
		if (works != null && works.size() > 0) {
			works.forEach(work -> {
				ProfileWorksRecord record = BeanUtils.MapToRecord(work, ProfileWorksRecord.class);
				if (record != null) {
					worksRecords.add(record);
				}
			});
		}
		return worksRecords;
	}

	public List<ProfileWorkexpEntity> mapToWorkexpRecords(List<Map<String, Object>> workexps) {
		List<ProfileWorkexpEntity> workexpRecords = new ArrayList<>();
		if (workexps != null && workexps.size() > 0) {
			workexps.forEach(workexp -> {
				ProfileWorkexpEntity record = BeanUtils.MapToRecord(workexp, ProfileWorkexpEntity.class);
				if (record != null) {
					if (workexp.get("start_date") != null) {
						record.setStart(BeanUtils.convertToSQLDate(workexp.get("start_date")));
					}
					if (workexp.get("end_date") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(workexp.get("end_date")));
					}
					if (workexp.get("company") != null) {
						@SuppressWarnings("unchecked")
						Map<String, Object> company = (Map<String, Object>) workexp.get("company");
						if (company != null) {
							HrCompanyRecord hrCompany = new HrCompanyRecord();
							if(company.get("company_name") != null) {
								hrCompany.setName(BeanUtils.converToString(company.get("company_name")));
							}
							if(company.get("company_industry") != null) {
								hrCompany.setIndustry(BeanUtils.converToString(company.get("company_industry")));
							}
							if(company.get("company_introduction") != null) {
								hrCompany.setIntroduction(BeanUtils.converToString(company.get("company_introduction")));
							}
							if(company.get("company_scale") != null) {
								hrCompany.setScale(BeanUtils.converToString(company.get("company_scale")));
							}
							if(company.get("company_property") != null) {
								hrCompany.setProperty(BeanUtils.converToUByte(company.get("company_property")));
							}
							hrCompany.setType(UByte.valueOf(Constant.COMPANY_TYPE_FREE));
							hrCompany.setSource(UByte.valueOf(Constant.COMPANY_SOURCE_PROFILE));
							record.setCompany(hrCompany);
						}
					}
					workexpRecords.add(record);
				}
			});
		}
		return workexpRecords;
	}

	public List<ProfileSkillRecord> mapToSkillRecords(List<Map<String, Object>> skills) {
		List<ProfileSkillRecord> skillRecords = new ArrayList<>();
		if (skills != null && skills.size() > 0) {
			skills.forEach(skill -> {
				ProfileSkillRecord record = BeanUtils.MapToRecord(skill, ProfileSkillRecord.class);
				if (record != null) {
					skillRecords.add(record);
				}
			});
		}
		return skillRecords;
	}

	public List<ProfileProjectexpRecord> mapToProjectExpsRecords(List<Map<String, Object>> projectexps) {
		List<ProfileProjectexpRecord> projectExpRecords = new ArrayList<>();
		if (projectexps != null && projectexps.size() > 0) {
			projectexps.forEach(projectexp -> {
				ProfileProjectexpRecord record = BeanUtils.MapToRecord(projectexp, ProfileProjectexpRecord.class);
				if (record != null) {
					if (projectexp.get("start_date") != null) {
						record.setStart(BeanUtils.convertToSQLDate(projectexp.get("start_date")));
					}
					if (projectexp.get("end_date") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(projectexp.get("end_date")));
					}
					projectExpRecords.add(record);
				}
			});
		}
		return projectExpRecords;
	}

	public ProfileOtherRecord mapToOtherRecord(Map<String, Object> other) {
		ProfileOtherRecord otherRecord = null;
		if (other != null) {
			otherRecord = new ProfileOtherRecord();
			otherRecord.setOther(JSON.toJSONString(other));
		}
		return otherRecord;
	}

	public List<ProfileLanguageRecord> mapToLanguageRecord(List<Map<String, Object>> languages) {
		List<ProfileLanguageRecord> languageRecords = new ArrayList<>();
		if (languages != null && languages.size() > 0) {
			languages.forEach(language -> {
				ProfileLanguageRecord record = BeanUtils.MapToRecord(language, ProfileLanguageRecord.class);
				if (record != null) {
					languageRecords.add(record);
				}
			});
		}
		return languageRecords;
	}

	@SuppressWarnings("unchecked")
	public List<IntentionRecord> mapToIntentionRecord(List<Map<String, Object>> intentions) {
		List<IntentionRecord> intentionRecords = new ArrayList<>();
		if (intentions != null && intentions.size() > 0) {
			intentions.forEach(intention -> {
				IntentionRecord record = BeanUtils.MapToRecord(intention, IntentionRecord.class);
				if (record != null) {
					if (intention.get("cities") != null) {
						List<Map<String, Object>> cities = (List<Map<String, Object>>) intention.get("cities");
						if (cities != null && cities.size() > 0) {
							for (Map<String, Object> city : cities) {
								ProfileIntentionCityRecord cityRecord = BeanUtils.MapToRecord(city,
										ProfileIntentionCityRecord.class);
								if (cityRecord != null && ((cityRecord.getCityCode() != null
										&& cityRecord.getCityCode().intValue() != 0)
										|| !StringUtils.isNullOrEmpty(cityRecord.getCityName()))) {
									record.getCities().add(cityRecord);
								}
							}
						}
					}

					if (intention.get("positions") != null) {
						List<Map<String, Object>> positions = (List<Map<String, Object>>) intention.get("positions");
						if (positions != null && positions.size() > 0) {
							for (Map<String, Object> position : positions) {
								ProfileIntentionPositionRecord positionRecord = BeanUtils.MapToRecord(position,
										ProfileIntentionPositionRecord.class);
								if (positionRecord != null && ((positionRecord.getPositionCode() != null
										&& positionRecord.getPositionCode().intValue() != 0)
										|| !StringUtils.isNullOrEmpty(positionRecord.getPositionName()))) {
									record.getPositions().add(positionRecord);
								}
							}
						}
					}

					if (intention.get("industries") != null) {
						List<Map<String, Object>> industries = (List<Map<String, Object>>) intention.get("industries");
						if (industries != null && industries.size() > 0) {
							for (Map<String, Object> industry : industries) {
								ProfileIntentionIndustryRecord industryRecord = BeanUtils.MapToRecord(industry,
										ProfileIntentionIndustryRecord.class);
								if (industryRecord != null && ((industryRecord.getIndustryCode() != null
										&& industryRecord.getIndustryCode().intValue() != 0)
										|| !StringUtils.isNullOrEmpty(industryRecord.getIndustryName()))) {
									record.getIndustries().add(industryRecord);
								}
							}
						}
					}
					intentionRecords.add(record);
				}
			});
		}
		return intentionRecords;
	}

	public ProfileImportRecord mapToImportRecord(Map<String, Object> importMap) {
		ProfileImportRecord record = null;
		if (importMap != null) {
			record = BeanUtils.MapToRecord(importMap, ProfileImportRecord.class);
			if(importMap.get("data") != null) {
				record.setData(JSON.toJSONString(importMap.get("data")));
			}
			return record;
		}
		return record;
	}

	public List<ProfileEducationRecord> mapToEducationRecords(List<Map<String, Object>> educations) {
		List<ProfileEducationRecord> educationRecords = new ArrayList<>();
		if (educations != null && educations.size() > 0) {
			educations.forEach(education -> {
				ProfileEducationRecord record = BeanUtils.MapToRecord(education, ProfileEducationRecord.class);
				if (record != null) {
					if (education.get("start_date") != null) {
						record.setStart(BeanUtils.convertToSQLDate(education.get("start_date")));
					}
					if (education.get("end_date") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(education.get("end_date")));
					}
					educationRecords.add(record);
				}
			});
		}
		return educationRecords;
	}

	public List<ProfileCredentialsRecord> mapToCredentialsRecords(List<Map<String, Object>> credentials) {
		List<ProfileCredentialsRecord> credentialRecords = new ArrayList<>();
		if (credentials != null && credentials.size() > 0) {
			credentials.forEach(credential -> {
				ProfileCredentialsRecord record = BeanUtils.MapToRecord(credential, ProfileCredentialsRecord.class);
				if (record != null) {
					credentialRecords.add(record);
				}
			});
		}
		return credentialRecords;
	}

	public List<ProfileAwardsRecord> mapToAwardsRecords(List<Map<String, Object>> awards) {
		List<ProfileAwardsRecord> awardsRecords = new ArrayList<>();
		if (awards != null && awards.size() > 0) {
			awards.forEach(award -> {
				ProfileAwardsRecord record = BeanUtils.MapToRecord(award, ProfileAwardsRecord.class);
				if (record != null) {
					awardsRecords.add(record);
				}
			});
		}
		return awardsRecords;
	}

	public List<ProfileAttachmentRecord> mapToAttachmentRecords(List<Map<String, Object>> attachments) {
		List<ProfileAttachmentRecord> attchmentRecords = new ArrayList<>();
		if (attachments != null && attachments.size() > 0) {
			attachments.forEach(attachment -> {
				ProfileAttachmentRecord record = BeanUtils.MapToRecord(attachment, ProfileAttachmentRecord.class);
				if (record != null) {
					attchmentRecords.add(record);
				}
			});
		}
		return attchmentRecords;
	}

	public ProfileBasicRecord mapToBasicRecord(Map<String, Object> basic) {
		ProfileBasicRecord record = null;
		if (basic != null) {
			record = BeanUtils.MapToRecord(basic, ProfileBasicRecord.class);
			return record;
		}
		return record;
	}

	public ProfileProfileRecord mapToProfileRecord(Map<String, Object> profile) {
		ProfileProfileRecord record = null;
		if (profile != null && profile.get("user_id") != null) {
			record = new ProfileProfileRecord();
			record.setUuid((String) profile.get("uuid"));
			if (profile.get("lang") != null) {
				record.setLang(UByte.valueOf((Integer) profile.get("lang")));
			}
			if (profile.get("source") != null) {
				record.setSource(UInteger.valueOf((Integer) profile.get("source")));
			}
			if (profile.get("completeness") != null) {
				record.setCompleteness(UByte.valueOf((Integer) profile.get("completeness")));
			}
			record.setUserId(UInteger.valueOf((Integer) profile.get("user_id")));
			if (profile.get("disable") != null) {
				record.setDisable(UByte.valueOf((Integer) profile.get("disable")));
			} else {
				record.setDisable(UByte.valueOf(1));
			}
			return record;
		}
		return record;
	}

	public List<Map<String, Object>> buildOthers(ProfileProfileRecord profileRecord, List<ProfileOtherRecord> records) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (profileRecord != null && records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("profile_id", record.getProfileId().intValue());
					map.put("other", record.getOther());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	public List<Map<String, Object>> buildImports(ProfileProfileRecord profileRecord,
			List<ProfileImportRecord> records) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (profileRecord != null && records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("source", record.getSource().intValue());
					if (record.getLastUpdateTime() != null) {
						map.put("last_update_time", DateUtils.dateToShortTime(record.getLastUpdateTime()));
					}
					map.put("account_id", record.getAccountId());
					map.put("resume_id", record.getResumeId());
					map.put("user_name", record.getUserName());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	public List<Map<String, Object>> buildAttachments(ProfileProfileRecord profileRecord,
			List<ProfileAttachmentRecord> records) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					if (record.getProfileId() != null) {
						map.put("profile_id", record.getProfileId().intValue());
					}
					map.put("name", record.getName());
					map.put("path", record.getPath());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	public UserUserRecord mapToUserUserRecord(Map<String, Object> map) {
		UserUserRecord record = null;
		if (map != null) {
			record = BeanUtils.MapToRecord(map, UserUserRecord.class);
			return record;
		}
		return record;
	}
}
