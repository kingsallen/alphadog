package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.baseorm.dao.dictdb.DictMajorDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import com.moseeker.thrift.gen.profile.struct.Education;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@CounterIface
public class ProfileEducationService {

    Logger logger = LoggerFactory.getLogger(ProfileEducationService.class);

    @Autowired
    private ProfileEducationDao dao;

    @Autowired
    private DictCollegeDao collegeDao;

    @Autowired
    private DictMajorDao majorDao;

    @Autowired
    private DictCountryDao countryDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    public List<Education> getResources(Query query) throws TException {
        // 按照结束时间倒序
        query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
        query.getOrders().add(new OrderBy("start", Order.DESC));

        List<Education> educations = DBsToStructs(dao.getRecords(query));
        if (educations != null && educations.size() > 0) {
            List<Integer> College_codes = new ArrayList<>();
            List<String> Major_codes = new ArrayList<>();
            educations.forEach(education -> {
                // cityCodes.add(basic.getCity());
                if (education.getCollege_code() > 0 && StringUtils.isNullOrEmpty(education.getCollege_name())) {
                    College_codes.add((int) education.getCollege_code());
                }
                if (!StringUtils.isNullOrEmpty(education.getMajor_code())
                        && StringUtils.isNullOrEmpty(education.getMajor_name())) {
                    Major_codes.add(education.getMajor_code());
                }
            });
            // 学校
            List<DictCollegeDO> colleges = collegeDao.getCollegesByIDs(College_codes);
            // 专业
            List<DictMajorDO> majors = majorDao.getMajorsByIDs(Major_codes);
            List<DictCountryDO> countryDOList = countryDao.getAll();
            for (Education education : educations) {
                if (colleges != null && colleges.size() > 0) {
                    for (DictCollegeDO college : colleges) {
                        if (education.getCollege_code() == college.getCode()) {
                            education.setCollege_name(college.getName());
                            education.setCollege_logo(college.getLogo());
                            break;
                        }
                    }
                }
                if (majors != null && majors.size() > 0) {
                    for (DictMajorDO major : majors) {
                        if (education.getMajor_code().equals(major.getCode())) {
                            education.setMajor_name(major.getName());
                            break;
                        }
                    }
                }
                if(!StringUtils.isEmptyList(countryDOList)){
                    for(DictCountryDO country : countryDOList){
                        if(education.getCountry_id() == country.getId()){
                            education.setCountry_name(country.getName());
                            break;
                        }else if(education.getCountry_id() == Constant.HKAMTW){
                            education.setCountry_name("港澳台地区");
                            break;
                        }
                    }
                }
            }
        }
        return educations;
    }

    public Education getResource(Query query) throws TException {
        Education result = DBToStruct(dao.getRecord(query));
        if (result != null) {
            Query dictQuery = new Query.QueryBuilder().where("code", result.getCollege_code()).buildQuery();
            DictCollegeDO college = collegeDao.getData(dictQuery);
            if (college != null) {
                result.setCollege_name(college.getName());
                result.setCollege_logo(college.getLogo());
            }
            Query majorQuery = new Query.QueryBuilder().where("code", result.getMajor_code()).buildQuery();
            DictMajorDO major = majorDao.getData(majorQuery);
            if (major != null) {
                result.setMajor_name(major.getName());
            }
        }
        return result;
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }

    /**
     * start-start_date
     * end-end_data
     *
     * @param education
     * @return
     * @throws TException
     */
    @Transactional
    public Education postResource(Education education) throws CommonException {
        Education result = null;
        if (education != null) {
            logger.info("education start：{}", education.getStart_date());
            //添加信息校验
            ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
            logger.info("education vm:{}",vm);
            if (!vm.isPass()) {
                throw ProfileException.validateFailed(vm.getResult());
            }
            if (education.getCollege_code() > 0) {
                DictCollegeDO college = collegeDao.getCollegeByID(education.getCollege_code());
                if (college != null) {
                    education.setCollege_name(college.getName());
                    education.setCollege_logo(college.getLogo());
                    education.setCountry_id(college.getCountry_code());
                } else {
                    throw ProfileException.PROFILE_DICT_COLLEGE_NOTEXIST;
                }
            }
            if (!StringUtils.isNullOrEmpty(education.getMajor_code())) {
                DictMajorDO major = majorDao.getMajorByID(education.getMajor_code());
                if (major != null) {
                    education.setMajor_name(major.getName());
                } else {
                    throw ProfileException.PROFILE_DICT_MAJOR_NOTEXIST;
                }
            }

            result = DBToStruct(dao.addRecord(structToDB(education)));
            if (result != null && result.getId() > 0) {
                Set<Integer> profileIds = new HashSet<>();
                profileIds.add(education.getProfile_id());
                profileDao.updateUpdateTime(profileIds);
                profileCompanyTagService.handlerCompanyTag(education.getProfile_id());
				/* 计算profile完整度 */
                profileEntity.reCalculateProfileEducation(education.getProfile_id(), 0);
            }
        }
        return result;

    }

    @Transactional
    public int putResource(Education education) throws CommonException {
        int result = 0;

        if (education != null) {

            if (education.getCollege_code() > 0) {
                DictCollegeDO college = collegeDao.getCollegeByID(education.getCollege_code());
                if (college != null) {
                    education.setCollege_name(college.getName());
                    education.setCollege_logo(college.getLogo());
                    education.setCountry_id(college.getCountry_code());
                } else {
                    throw ProfileException.PROFILE_DICT_COLLEGE_NOTEXIST;
                }
            }
            if (!StringUtils.isNullOrEmpty(education.getMajor_code())) {
                DictMajorDO major = majorDao.getMajorByID(education.getMajor_code());
                if (major != null) {
                    education.setMajor_name(major.getName());
                } else {
                    throw ProfileException.PROFILE_DICT_MAJOR_NOTEXIST;
                }
            }

            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(ProfileEducation.PROFILE_EDUCATION.ID.getName(), education.getId());
            ProfileEducationRecord educationRecord = dao.getRecord(queryBuilder.buildQuery());
            if (educationRecord != null) {
                ProfileEducationRecord originEducationRecord = structToDB(education);

                RecordTool.recordToRecord(educationRecord, originEducationRecord);

                ValidationMessage<ProfileEducationRecord> validationMessage = ProfileValidation.verifyEducation(educationRecord);
                if (validationMessage.isPass()) {
                    result = dao.updateRecord(structToDB(education));
                    if (result > 0) {
                        updateUpdateTime(education);
                        /* 计算profile完整度 */
                        profileEntity.reCalculateProfileEducation(education.getProfile_id(), education.getId());
                        profileCompanyTagService.handlerCompanyTag(education.getProfile_id());
                    }
                } else {
                    throw ProfileException.validateFailed(validationMessage.getResult());
                }
            }

        }
        return result;
    }

    @Transactional
    public List<Education> postResources(List<Education> structs) throws TException {

        List<Education> resultDatas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {

            //添加信息校验
            removeIllegalEducation(structs);

            if (structs.size() > 0) {

                List<ProfileEducationRecord> records = structsToDBs(structs);

                records = dao.addAllRecord(records);

                resultDatas = DBsToStructs(records);
                Set<Integer> profileIds = new HashSet<>();
                resultDatas.forEach(struct -> {
                    if (struct.getProfile_id() > 0) {
                        profileIds.add(struct.getProfile_id());
                    }
                });

                profileDao.updateUpdateTime(profileIds);

                profileIds.forEach(profileId -> {
                    /* 计算profile完整度 */
                    profileEntity.reCalculateProfileEducation(profileId, 0);
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
            }
        }
        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<Education> structs) throws TException {

        int[] result = new int[0];

        if (structs != null && structs.size() > 0) {

            List<ProfileEducationRecord> originEducationRecordList = structsToDBs(structs);

            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition(ProfileEducation.PROFILE_EDUCATION.ID.getName(),
                    structs.stream()
                            .map(education -> education.getId())
                            .collect(Collectors.toList())));
            List<ProfileEducationRecord> descEducationRecordList = dao.getRecords(queryBuilder.buildQuery());
            if (descEducationRecordList != null) {
                Iterator<ProfileEducationRecord> iterator = descEducationRecordList.iterator();
                while (iterator.hasNext()) {
                    ProfileEducationRecord profileEducationRecord = iterator.next();
                    Optional<ProfileEducationRecord> profileEducationRecordOptional = originEducationRecordList.stream()
                            .filter(profileEducationRecord1 -> profileEducationRecord1.getId().intValue() == profileEducationRecord.getId())
                            .findAny();
                    if (profileEducationRecordOptional.isPresent()) {
                        RecordTool.recordToRecord(profileEducationRecord, profileEducationRecordOptional.get());
                    }
                    ValidationMessage<ProfileEducationRecord> validationMessage = ProfileValidation.verifyEducation(profileEducationRecord);
                    if (!validationMessage.isPass()) {
                        iterator.remove();
                    }
                }
                result = dao.updateRecords(descEducationRecordList);
                updateProfileUpdateTime(descEducationRecordList);
                descEducationRecordList.forEach(profileEducationRecord -> {
                    profileEntity.reCalculateProfileEducation(profileEducationRecord.getProfileId(), profileEducationRecord.getId());
                    profileCompanyTagService.handlerCompanyTag(profileEducationRecord.getProfileId());
                });
            }
        }
        return result;
    }

    @Transactional
    public int[] delResources(List<Education> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id",
                            structs.stream()
                                    .map(struct -> struct.getId())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileEducationDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(structsToDBs(structs));

            if (deleteDatas != null && deleteDatas.size() > 0) {
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet()));
                for (ProfileEducationDO data : deleteDatas) {
                    profileEntity.reCalculateProfileEducation(data.getProfileId(), 0);
                    profileCompanyTagService.handlerCompanyTag(data.getProfileId());
                }
            }
            return result;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int delResource(Education struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id", struct.getId())).buildQuery();
            //找到要删除的数据
            ProfileEducationDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(deleteData);
                if (result > 0) {
                    updateUpdateTime(struct);
                    /* 计算profile完整度 */
                    profileEntity.reCalculateProfileEducation(deleteData.getProfileId(), 0);
                    profileCompanyTagService.handlerCompanyTag(deleteData.getProfileId());
                }
            }
        }

        return result;
    }

    /**
     * 过滤不合法的教育经历
     * @param structs
     */
    private void removeIllegalEducation(List<Education> structs) {
        if (structs != null && structs.size() > 0) {
            Iterator<Education> ie = structs.iterator();
            while (ie.hasNext()) {
                Education education = ie.next();
                ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
                if (!vm.isPass()) {
                    ie.remove();
                }
            }
        }
    }

    protected Education DBToStruct(ProfileEducationRecord r) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return BeanUtils.DBToStruct(Education.class, r, equalRules);
    }

    protected ProfileEducationRecord structToDB(Education attachment) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return BeanUtils.structToDB(attachment, ProfileEducationRecord.class, equalRules);
    }

    protected List<Education> DBsToStructs(List<ProfileEducationRecord> records) {
        List<Education> structs = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (ProfileEducationRecord r : records) {
                structs.add(DBToStruct(r));
            }
        }
        return structs;
    }

    protected List<ProfileEducationRecord> structsToDBs(List<Education> records) {
        List<ProfileEducationRecord> structs = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (Education r : records) {
                structs.add(structToDB(r));
            }
        }
        return structs;
    }

    public void updateUpdateTime(List<Education> educations) {
        if (educations == null || educations.size() == 0) return;

        HashSet<Integer> educationIds = new HashSet<>();

        educations.forEach(education -> {
            educationIds.add(education.getId());
        });
        dao.updateProfileUpdateTime(educationIds);
    }

    private void updateUpdateTime(Education education) {

        if (education == null) return;

        List<Education> educations = new ArrayList<>();
        educations.add(education);
        updateUpdateTime(educations);
    }

    private void updateProfileUpdateTime(List<ProfileEducationRecord> descEducationRecordList) {
        if (descEducationRecordList != null) {
            return;
        }
        Set<Integer> educationIdSet = descEducationRecordList.stream()
                .map(profileEducationRecord -> profileEducationRecord.getId())
                .collect(Collectors.toSet());
        dao.updateProfileUpdateTime(educationIdSet);
    }
}
