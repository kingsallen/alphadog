package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
					if(education.getSchool_code() > 0 && StringUtils.isNullOrEmpty(education.getSchool_name())) {
						collegeCodes.add((int)education.getSchool_code());
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
							if(education.getSchool_code() == college.getCode().intValue()) {
								education.setSchool_name(college.getName());
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
				//return ResponseUtils.success(records);
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
		
		return super.getResource(query);
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
