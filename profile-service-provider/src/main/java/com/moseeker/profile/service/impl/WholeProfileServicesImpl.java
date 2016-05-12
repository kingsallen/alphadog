package com.moseeker.profile.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.Constant;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileExtRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileInternshipRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileRewardRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSchooljobRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileTrainingRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.AttachmentDao;
import com.moseeker.profile.dao.EducationDao;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.profile.dao.InternshipDao;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.ProfileExtDao;
import com.moseeker.profile.dao.ProfileImportDao;
import com.moseeker.profile.dao.ProjectExpDao;
import com.moseeker.profile.dao.RewardDao;
import com.moseeker.profile.dao.SchoolJobDao;
import com.moseeker.profile.dao.SkillDao;
import com.moseeker.profile.dao.TrainingDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.profile.dao.WorksDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;

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
				List<ProfileAttachmentRecord> attachmentRecords = attachmentDao.getResources(query);
				profile.put("attachments", attachmentRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileEducationRecord> educationRecords = educationDao.getResources(query);
				profile.put("educations", educationRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileIntentionRecord> intentionRecords = intentionDao.getResources(query);
				profile.put("intentions", intentionRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			/*try {
				List<ProfileIntentionRecord> intentionRecords = intentionDao.getResources(query);
				profile.put("intentions", intentionRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}*/
			try {
				List<ProfileInternshipRecord> internshipRecords = internshipDao.getResources(query);
				profile.put("internships", internshipRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileLanguageRecord> languageRecords = languageDao.getResources(query);
				profile.put("languages", languageRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				ProfileBasicRecord profileBasic = profileBasicDao.getResource(query);
				profile.put("basic", profileBasic);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				ProfileProfileRecord profileProfile = profileDao.getResource(profileQuery);
				profile.put("profile", profileProfile);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				ProfileExtRecord profileExtRecord = profileExtDao.getResource(profileQuery);
				profile.put("profile_ext", profileExtRecord);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileRewardRecord> rewardRecord = rewardDao.getResources(query);
				profile.put("rewards", rewardRecord);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileSchooljobRecord> schooljobRecords = schoolJobDao.getResources(query);
				profile.put("schooljobs", schooljobRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileSkillRecord> skillRecords = skillDao.getResources(query);
				profile.put("skills", skillRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileTrainingRecord> trainingRecords = trainingDao.getResources(query);
				profile.put("trainings", trainingRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileWorkexpRecord> workexpRecords = workExpDao.getResources(query);
				profile.put("work_exps", workexpRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			try {
				List<ProfileWorksRecord> worksRecords = worksDao.getResources(query);
				profile.put("works", worksRecords);
			} catch (Exception e) {
				logger.error("//todo", e);
			}
			response.setStatus(0);
			response.setMessage(Constant.TIPS_SUCCESS);
			response.setData(JSON.toJSONString(profile));
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
	private InternshipDao internshipDao;
	
	@Autowired
	private LanguageDao languageDao;
	
	@Autowired
	private ProfileBasicDao profileBasicDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private ProfileExtDao profileExtDao;
	
	@Autowired
	private ProfileImportDao profileImportDao;
	
	@Autowired
	private ProjectExpDao projectExpDao;
	
	@Autowired
	private RewardDao rewardDao;
	
	@Autowired
	private SchoolJobDao schoolJobDao;
	
	@Autowired
	private SkillDao skillDao;
	
	@Autowired
	private TrainingDao trainingDao;
	
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

	public InternshipDao getInternshipDao() {
		return internshipDao;
	}

	public void setInternshipDao(InternshipDao internshipDao) {
		this.internshipDao = internshipDao;
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

	public ProfileExtDao getProfileExtDao() {
		return profileExtDao;
	}

	public void setProfileExtDao(ProfileExtDao profileExtDao) {
		this.profileExtDao = profileExtDao;
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

	public RewardDao getRewardDao() {
		return rewardDao;
	}

	public void setRewardDao(RewardDao rewardDao) {
		this.rewardDao = rewardDao;
	}

	public SchoolJobDao getSchoolJobDao() {
		return schoolJobDao;
	}

	public void setSchoolJobDao(SchoolJobDao schoolJobDao) {
		this.schoolJobDao = schoolJobDao;
	}

	public SkillDao getSkillDao() {
		return skillDao;
	}

	public void setSkillDao(SkillDao skillDao) {
		this.skillDao = skillDao;
	}

	public TrainingDao getTrainingDao() {
		return trainingDao;
	}

	public void setTrainingDao(TrainingDao trainingDao) {
		this.trainingDao = trainingDao;
	}

	public WorkExpDao getWorkExpDao() {
		return workExpDao;
	}

	public void setWorkExpDao(WorkExpDao workExpDao) {
		this.workExpDao = workExpDao;
	}
}
