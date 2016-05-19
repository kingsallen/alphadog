package com.moseeker.profile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.AttachmentDao;
import com.moseeker.profile.dao.EducationDao;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.ProfileImportDao;
import com.moseeker.profile.dao.ProjectExpDao;
import com.moseeker.profile.dao.SkillDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.profile.dao.WorksDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Attachment;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.Intention;
import com.moseeker.thrift.gen.profile.struct.Language;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.Skill;
import com.moseeker.thrift.gen.profile.struct.WorkExp;
import com.moseeker.thrift.gen.profile.struct.Works;

@Service
public class WholeProfileServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(WholeProfileServicesImpl.class);

	@Override
	public Response getResource(int id) throws TException {
		Response response = new Response();
		try {
			HashMap<String, Object> profile = new HashMap<String, Object>();
			CommonQuery query = new CommonQuery();
			HashMap<String, String> equalFilter = new HashMap<String, String>();
			equalFilter.put("profile_id", String.valueOf(id));
			query.setEqualFilter(equalFilter);
			
			CommonQuery profileQuery = new CommonQuery();
			HashMap<String, String> profileEqualFilter = new HashMap<String, String>();
			profileEqualFilter.put("id", String.valueOf(id));
			profileQuery.setEqualFilter(profileEqualFilter);
			try {
				List<Attachment> attachments = new ArrayList<>();
				List<ProfileAttachmentRecord> attachmentRecords = attachmentDao.getResources(query);
				if(attachmentRecords != null) {
					attachmentRecords.forEach(attachmentRecord->{
						attachments.add((Attachment)BeanUtils.DBToStruct(Attachment.class, attachmentRecord));
					});
				}
				profile.put("attachments", attachments);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<Education> educations = new ArrayList<>();
				List<ProfileEducationRecord> educationRecords = educationDao.getResources(query);
				if(educationRecords != null) {
					educationRecords.forEach(education -> {
						educations.add((Education)BeanUtils.DBToStruct(Education.class, education));
					});
				}
				profile.put("educations", educations);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<Intention> intentions = new ArrayList<>();
				List<ProfileIntentionRecord> intentionRecords = intentionDao.getResources(query);
				if(intentionRecords != null) {
					intentionRecords.forEach(intention -> {
						intentions.add((Intention)BeanUtils.DBToStruct(Intention.class, intention));
					});
				}
				profile.put("intentions", intentions);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<Language> languages = new ArrayList<>();
				List<ProfileLanguageRecord> languageRecords = languageDao.getResources(query);
				if(languageRecords != null) {
					languageRecords.forEach(language -> {
						languages.add((Language)BeanUtils.DBToStruct(Language.class, language));
					});
				}
				profile.put("languages", languages);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				Basic basic = null;
				ProfileBasicRecord profileBasic = profileBasicDao.getResource(query);
				if(profileBasic != null) {
					basic = (Basic)BeanUtils.DBToStruct(Basic.class, profileBasic);
				}
				profile.put("basic", basic);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				Profile profilestruct = null;
				ProfileProfileRecord profileProfile = profileDao.getResource(profileQuery);
				if(profileProfile != null) {
					profilestruct = (Profile)BeanUtils.DBToStruct(Profile.class, profileProfile);
				}
				profile.put("profile", profilestruct);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<Skill> skills = new ArrayList<>();
				List<ProfileSkillRecord> skillRecords = skillDao.getResources(query);
				if(skillRecords != null) {
					skillRecords.forEach(skill -> {
						skills.add((Skill)BeanUtils.DBToStruct(Skill.class, skill));
					});
				}
				profile.put("skills", skills);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<WorkExp> workExps = new ArrayList<>();
				List<ProfileWorkexpRecord> workexpRecords = workExpDao.getResources(query);
				if(workexpRecords != null) {
					workexpRecords.forEach(workExp -> {
						workExps.add((WorkExp)BeanUtils.DBToStruct(WorkExp.class, workExp));
					});
				}
				profile.put("work_exps", workExps);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<Works> works = new ArrayList<>();
				List<ProfileWorksRecord> worksRecords = worksDao.getResources(query);
				if(worksRecords != null) {
					worksRecords.forEach(work -> {
						works.add((Works)BeanUtils.DBToStruct(Works.class, work));
					});
				}
				profile.put("works", works);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			response.setStatus(0);
			response.setMessage(Constant.TIPS_SUCCESS);
			//Gson gson = new Gson();
			response.setData(JSON.toJSONString(profile));
			//response.setData(gson.toJson(profile));
		} catch (Exception e) {
			logger.error("//todo",e);
			response.setStatus(0);
			response.setMessage(Constant.TIPS_ERROR);
			response.setData(Constant.NONE_JSON);
		}
		return response;
	}
	
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Autowired
	private WorksDao worksDao;
	
	@Autowired
	private EducationDao educationDao;
	
	@Autowired
	private IntentionDao intentionDao;

	@Autowired
	private LanguageDao languageDao;
	
	@Autowired
	private ProfileBasicDao profileBasicDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private ProfileImportDao profileImportDao;
	
	@Autowired
	private ProjectExpDao projectExpDao;
	
	@Autowired
	private SkillDao skillDao;
	
	@Autowired
	private WorkExpDao workExpDao;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public WorksDao getWorksDao() {
		return worksDao;
	}

	public void setWorksDao(WorksDao worksDao) {
		this.worksDao = worksDao;
	}

	public EducationDao getEducationDao() {
		return educationDao;
	}

	public void setEducationDao(EducationDao educationDao) {
		this.educationDao = educationDao;
	}

	public IntentionDao getIntentionDao() {
		return intentionDao;
	}

	public void setIntentionDao(IntentionDao intentionDao) {
		this.intentionDao = intentionDao;
	}

	public LanguageDao getLanguageDao() {
		return languageDao;
	}

	public void setLanguageDao(LanguageDao languageDao) {
		this.languageDao = languageDao;
	}

	public ProfileBasicDao getProfileBasicDao() {
		return profileBasicDao;
	}

	public void setProfileBasicDao(ProfileBasicDao profileBasicDao) {
		this.profileBasicDao = profileBasicDao;
	}

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	public ProfileImportDao getProfileImportDao() {
		return profileImportDao;
	}

	public void setProfileImportDao(ProfileImportDao profileImportDao) {
		this.profileImportDao = profileImportDao;
	}

	public ProjectExpDao getProjectExpDao() {
		return projectExpDao;
	}

	public void setProjectExpDao(ProjectExpDao projectExpDao) {
		this.projectExpDao = projectExpDao;
	}

	public SkillDao getSkillDao() {
		return skillDao;
	}

	public void setSkillDao(SkillDao skillDao) {
		this.skillDao = skillDao;
	}

	public WorkExpDao getWorkExpDao() {
		return workExpDao;
	}

	public void setWorkExpDao(WorkExpDao workExpDao) {
		this.workExpDao = workExpDao;
	}
}
