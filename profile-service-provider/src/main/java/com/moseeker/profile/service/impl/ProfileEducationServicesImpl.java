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
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.EducationServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Education;

@Service
public class ProfileEducationServicesImpl extends JOOQBaseServiceImpl<Education, ProfileEducationRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileEducationServicesImpl.class);

	@Autowired
	private EducationDao dao;
	
	@Autowired
	private CollegeDao collegeDao;
	
	@Autowired
	private MajorDao majorDao;
	
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

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}
	
	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<ProfileEducationRecord> educationRecords = dao.getResources(query);
			List<Education> educations = DBsToStructs(educationRecords);
			if(educations != null && educations.size() > 0) {
				List<Integer> collegeCodes = new ArrayList<>();
				List<String> majorCodes = new ArrayList<>();
				educations.forEach(education -> {
					//cityCodes.add(basic.getCity());
					if(education.getCollege_code() > 0 && StringUtils.isNullOrEmpty(education.getCollege_name())) {
						collegeCodes.add((int)education.getCollege_code());
					}
					if(!StringUtils.isNullOrEmpty(education.getMajor_code()) && StringUtils.isNullOrEmpty(education.getMajor_name())) {
						majorCodes.add(education.getMajor_code());
					}
				});
				//学校
				List<DictCollegeRecord> colleges = collegeDao.getCollegesByIDs(collegeCodes);
				if(colleges != null && colleges.size() > 0) {
					for(Education education : educations) {
						for(DictCollegeRecord college : colleges) {
							if(education.getCollege_code() == college.getCode().intValue()) {
								education.setCollege_name(college.getName());
								education.setCollege_logo(college.getLogo());
								break;
							}
						}
					}
				}
				//专业
				List<DictMajorRecord> majors = majorDao.getMajorsByIDs(majorCodes);
				if(majors != null && majors.size() > 0) {
					for(Education education : educations) {
						for(DictMajorRecord major : majors) {
							if(education.getMajor_code().equals(major.getCode())) {
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
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		try {
			ProfileEducationRecord educationRecord = dao.getResource(query);
			if(educationRecord != null) {
				Education education = DBToStruct(educationRecord);
				DictCollegeRecord college = collegeDao.getCollegeByID(educationRecord.getCollegeCode().intValue());
				if(college != null) {
					education.setCollege_name(college.getName());
					education.setCollege_logo(college.getLogo());
				}
				DictMajorRecord major = majorDao.getMajorByID(educationRecord.getMajorCode());
				if(major != null) {
					education.setMajor_name(major.getName());
				}
				return ResponseUtils.success(educationRecord);
			}
		} catch (Exception e) {
			logger.error("getResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response postResource(Education education) throws TException {
		try {
			if(education.getCollege_code() > 0) {
				DictCollegeRecord college = collegeDao.getCollegeByID(education.getCollege_code());
				if(college != null) {
					education.setCollege_name(college.getName());
					education.setCollege_logo(college.getLogo());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
				}
			}
			if(!StringUtils.isNullOrEmpty(education.getMajor_code())) {
				DictMajorRecord major = majorDao.getMajorByID(education.getMajor_code());
				if(major != null) {
					education.setMajor_name(major.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
				}
			}
			ProfileEducationRecord record = structToDB(education);
			int id = dao.postResource(record);
			if(id > 0) {
				/* 计算profile完整度 */
				completenessImpl.reCalculateProfileEducationByEducationId(record.getId().intValue());
				return 	ResponseUtils.success(String.valueOf(id));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	@Override
	public Response putResource(Education education) throws TException {
		if(education.getCollege_code() > 0) {
			DictCollegeRecord college = collegeDao.getCollegeByID(education.getCollege_code());
			if(college != null) {
				education.setCollege_name(college.getName());
				education.setCollege_logo(college.getLogo());
			} else {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
			}
		}
		if(!StringUtils.isNullOrEmpty(education.getMajor_code())) {
			DictMajorRecord major = majorDao.getMajorByID(education.getMajor_code());
			if(major != null) {
				education.setMajor_name(major.getName());
			} else {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
			}
		}
		Response response = super.putResource(education);
		if(response.getStatus() == 0) {
			/* 计算profile完整度 */
			completenessImpl.reCalculateProfileEducationByEducationId(education.getId());
		}
		return response;
	}
	
	@Override
	public Response postResources(List<Education> structs) throws TException {
		Response response = super.postResources(structs);
		if(response.getStatus() == 0) {
			if(structs != null && structs.size() > 0) {
				Set<Integer> profileIds = new HashSet<>();
				structs.forEach(struct -> {
					if(struct.getProfile_id() > 0) {
						profileIds.add(struct.getProfile_id());
					}
				});
				profileIds.forEach(profileId -> {
					/* 计算profile完整度 */
					completenessImpl.reCalculateProfileEducationByProfileId(profileId);
				});
			}
		}
		return response;
	}

	@Override
	public Response putResources(List<Education> structs) throws TException {
		Response response = super.putResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				/* 计算profile完整度 */
				completenessImpl.reCalculateProfileEducationByEducationId(struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Education> structs) throws TException {
		Response response = super.delResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				/* 计算profile完整度 */
				completenessImpl.reCalculateProfileEducationByEducationId(struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResource(Education struct) throws TException {
		Response response = super.delResource(struct);
		if(response.getStatus() == 0)
			completenessImpl.reCalculateProfileEducationByEducationId(struct.getId());
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
}
