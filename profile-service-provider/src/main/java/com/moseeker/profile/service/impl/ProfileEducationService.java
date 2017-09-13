package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.dao.dictdb.DictMajorDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.OrderBy;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import com.moseeker.thrift.gen.profile.struct.Education;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

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
            if (colleges != null && colleges.size() > 0) {
                for (Education education : educations) {
                    for (DictCollegeDO college : colleges) {
                        if (education.getCollege_code() == college.getCode()) {
                            education.setCollege_name(college.getName());
                            education.setCollege_logo(college.getLogo());
                            break;
                        }
                    }
                }
            }
            // 专业
            List<DictMajorDO> majors = majorDao.getMajorsByIDs(Major_codes);
            if (majors != null && majors.size() > 0) {
                for (Education education : educations) {
                    for (DictMajorDO major : majors) {
                        if (education.getMajor_code().equals(major.getCode())) {
                            education.setMajor_name(major.getName());
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
    public Education postResource(Education education) throws TException {
        Education result = null;
        if (education != null) {
            //添加信息校验
            ValidationMessage<Education> vm = ProfileValidation.verifyEducation(education);
            if (!vm.isPass()) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
            }
            if (education.getCollege_code() > 0) {
                DictCollegeDO college = collegeDao.getCollegeByID(education.getCollege_code());
                if (college != null) {
                    education.setCollege_name(college.getName());
                    education.setCollege_logo(college.getLogo());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
                }
            }
            if (!StringUtils.isNullOrEmpty(education.getMajor_code())) {
                DictMajorDO major = majorDao.getMajorByID(education.getMajor_code());
                if (major != null) {
                    education.setMajor_name(major.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
                }
            }

            result = DBToStruct(dao.addRecord(structToDB(education)));
            if (result != null && result.getId() > 0) {
                Set<Integer> profileIds = new HashSet<>();
                profileIds.add(education.getProfile_id());
                profileDao.updateUpdateTime(profileIds);
                
				/* 计算profile完整度 */
                profileEntity.reCalculateProfileEducation(education.getProfile_id(), 0);
            }
        }
        return result;

    }

    @Transactional
    public int putResource(Education education) throws TException {
        int result = 0;

        if (education != null) {
            if (education.getCollege_code() > 0) {
                DictCollegeDO college = collegeDao.getCollegeByID(education.getCollege_code());
                if (college != null) {
                    education.setCollege_name(college.getName());
                    education.setCollege_logo(college.getLogo());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
                }
            }
            if (!StringUtils.isNullOrEmpty(education.getMajor_code())) {
                DictMajorDO major = majorDao.getMajorByID(education.getMajor_code());
                if (major != null) {
                    education.setMajor_name(major.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
                }
            }

            result = dao.updateRecord(structToDB(education));

            if (result > 0) {
                updateUpdateTime(education);
            /* 计算profile完整度 */
                profileEntity.reCalculateProfileEducation(education.getProfile_id(), education.getId());
            }
        }
        return result;
    }

    @Transactional
    public List<Education> postResources(List<Education> structs) throws TException {

        List<Education> resultDatas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {

            //添加信息校验
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
                });
            }
        }
        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<Education> structs) throws TException {

        int[] result = new int[0];

        if (structs != null && structs.size() > 0) {

            result = dao.updateRecords(structsToDBs(structs));

            List<Education> updatedDatas = new ArrayList<>();

            for (int i = 0; i < result.length; i++) {
                if (result[i] > 0) updatedDatas.add(structs.get(i));
            }

            updateUpdateTime(updatedDatas);
            updatedDatas.forEach(struct -> {
                /* 计算profile完整度 */
                profileEntity.reCalculateProfileEducation(struct.getProfile_id(), struct.getId());
            });
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
                }
            }
        }

        return result;
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

}
