package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.db.dictdb.tables.records.DictMajorRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.profile.dao.CollegeDao;
import com.moseeker.profile.dao.EducationDao;
import com.moseeker.profile.dao.MajorDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Education;

@Service
@CounterIface
public class ProfileEducationService extends JOOQBaseServiceImpl<Education, ProfileEducationRecord> {

	Logger logger = LoggerFactory.getLogger(ProfileEducationService.class);

	@Autowired
	private EducationDao dao;

	@Autowired
	private CollegeDao collegeDao;

	@Autowired
	private MajorDao majorDao;
	
	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private ProfileCompletenessImpl completenessImpl;

	public EducationDao getDao() {
		return dao;
	}

	public void setDao(EducationDao dao) {
		this.dao = dao;
	}

	public CollegeDao getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(CollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}

	public MajorDao getMajorDao() {
		return majorDao;
	}

	public void setMajorDao(MajorDao majorDao) {
		this.majorDao = majorDao;
	}

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<ProfileEducationRecord> educationRecords = dao.getResources(query);
			List<Education> educations = DBsToStructs(educationRecords);
			if (educations != null && educations.size() > 0) {
				List<Integer> collegeCodes = new ArrayList<>();
				List<String> majorCodes = new ArrayList<>();
				educations.forEach(education -> {
					// cityCodes.add(basic.getCity());
					if (education.getCollege_code() > 0 && StringUtils.isNullOrEmpty(education.getCollege_name())) {
						collegeCodes.add((int) education.getCollege_code());
					}
					if (!StringUtils.isNullOrEmpty(education.getMajor_code())
							&& StringUtils.isNullOrEmpty(education.getMajor_name())) {
						majorCodes.add(education.getMajor_code());
					}
				});
				// 学校
				List<DictCollegeRecord> colleges = collegeDao.getCollegesByIDs(collegeCodes);
				if (colleges != null && colleges.size() > 0) {
					for (Education education : educations) {
						for (DictCollegeRecord college : colleges) {
							if (education.getCollege_code() == college.getCode().intValue()) {
								education.setCollege_name(college.getName());
								education.setCollege_logo(college.getLogo());
								break;
							}
						}
					}
				}
				// 专业
				List<DictMajorRecord> majors = majorDao.getMajorsByIDs(majorCodes);
				if (majors != null && majors.size() > 0) {
					for (Education education : educations) {
						for (DictMajorRecord major : majors) {
							if (education.getMajor_code().equals(major.getCode())) {
								education.setMajor_name(major.getName());
								break;
							}
						}
					}
				}
				return ResponseUtils.success(educations);
			}
		} catch (Exception e) {
			logger.error("getResources error", e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		try {
			ProfileEducationRecord educationRecord = dao.getResource(query);
			if (educationRecord != null) {
				Education education = DBToStruct(educationRecord);
				DictCollegeRecord college = collegeDao.getCollegeByID(educationRecord.getCollegeCode().intValue());
				if (college != null) {
					education.setCollege_name(college.getName());
					education.setCollege_logo(college.getLogo());
				}
				DictMajorRecord major = majorDao.getMajorByID(educationRecord.getMajorCode());
				if (major != null) {
					education.setMajor_name(major.getName());
				}
				return ResponseUtils.success(educationRecord);
			}
		} catch (Exception e) {
			logger.error("getResources error", e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response postResource(Education education) throws TException {
		try {
			if (education.getCollege_code() > 0) {
				DictCollegeRecord college = collegeDao.getCollegeByID(education.getCollege_code());
				if (college != null) {
					education.setCollege_name(college.getName());
					education.setCollege_logo(college.getLogo());
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
				}
			}
			if (!StringUtils.isNullOrEmpty(education.getMajor_code())) {
				DictMajorRecord major = majorDao.getMajorByID(education.getMajor_code());
				if (major != null) {
					education.setMajor_name(major.getName());
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
				}
			}
			ProfileEducationRecord record = structToDB(education);
			int id = dao.postResource(record);
			if (id > 0) {
				
				Set<Integer> profileIds = new HashSet<>();
				profileIds.add(education.getProfile_id());
				profileDao.updateUpdateTime(profileIds);
				
				/* 计算profile完整度 */
				completenessImpl.reCalculateProfileEducation(education.getProfile_id(), 0);
				return ResponseUtils.success(String.valueOf(id));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	@Override
	public Response putResource(Education education) throws TException {
		if (education.getCollege_code() > 0) {
			DictCollegeRecord college = collegeDao.getCollegeByID(education.getCollege_code());
			if (college != null) {
				education.setCollege_name(college.getName());
				education.setCollege_logo(college.getLogo());
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
			}
		}
		if (!StringUtils.isNullOrEmpty(education.getMajor_code())) {
			DictMajorRecord major = majorDao.getMajorByID(education.getMajor_code());
			if (major != null) {
				education.setMajor_name(major.getName());
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
			}
		}
		Response response = super.putResource(education);
		if (response.getStatus() == 0) {
			updateUpdateTime(education);
			/* 计算profile完整度 */
			completenessImpl.reCalculateProfileEducation(education.getProfile_id(), education.getId());
		}
		return response;
	}

	@Override
	public Response postResources(List<Education> structs) throws TException {
		Response response = super.postResources(structs);
		if (response.getStatus() == 0) {
			if (structs != null && structs.size() > 0) {
				Set<Integer> profileIds = new HashSet<>();
				structs.forEach(struct -> {
					if (struct.getProfile_id() > 0) {
						profileIds.add(struct.getProfile_id());
					}
				});
				
				profileDao.updateUpdateTime(profileIds);
				
				profileIds.forEach(profileId -> {
					/* 计算profile完整度 */
					completenessImpl.reCalculateProfileEducation(profileId, 0);
				});
			}
		}
		return response;
	}

	@Override
	public Response putResources(List<Education> structs) throws TException {
		Response response = super.putResources(structs);
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
			updateUpdateTime(structs);
			structs.forEach(struct -> {
				/* 计算profile完整度 */
				completenessImpl.reCalculateProfileEducation(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Education> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());

		List<ProfileEducationRecord> educationRecords = null;
		try {
			educationRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if (educationRecords != null && educationRecords.size() > 0) {
			educationRecords.forEach(education -> {
				profileIds.add(education.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
			updateUpdateTime(structs);
			profileIds.forEach(profileId -> {
				/* 计算profile完整度 */
				completenessImpl.reCalculateProfileEducation(profileId, profileId);
			});
		}
		return response;
	}

	@Override
	public Response delResource(Education struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileEducationRecord education = null;
		try {
			education = dao.getResource(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(struct);
		if (response.getStatus() == 0 && education != null) {
			
			updateUpdateTime(struct);
			
			/* 计算profile完整度 */
			completenessImpl.reCalculateProfileEducation(education.getProfileId().intValue(),
					education.getId().intValue());
		}
		return response;
	}

	@Override
	protected Education DBToStruct(ProfileEducationRecord r) {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (Education) BeanUtils.DBToStruct(Education.class, r, equalRules);
	}

	@Override
	protected ProfileEducationRecord structToDB(Education attachment) throws ParseException {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (ProfileEducationRecord) BeanUtils.structToDB(attachment, ProfileEducationRecord.class, equalRules);
	}
	
	public void updateUpdateTime(List<Education> educations) {
		HashSet<Integer> educationIds = new HashSet<>();
		educations.forEach(education -> {
			educationIds.add(education.getId());
		});
		dao.updateProfileUpdateTime(educationIds);
	}
	
	private void updateUpdateTime(Education education) {
		List<Education> educations = new ArrayList<>();
		educations.add(education);
		updateUpdateTime(educations);
	}
}
