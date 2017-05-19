package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.dao.dictdb.DictMajorDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
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
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    public List<ProfileEducationDO> getResources(Query query) throws TException {
        // 按照结束时间倒序
        query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
        query.getOrders().add(new OrderBy("start", Order.DESC));

        List<ProfileEducationDO> educations = dao.getDatas(query);
        if (educations != null && educations.size() > 0) {
            List<Integer> collegeCodes = new ArrayList<>();
            List<String> majorCodes = new ArrayList<>();
            educations.forEach(education -> {
                // cityCodes.add(basic.getCity());
                if (education.getCollegeCode() > 0 && StringUtils.isNullOrEmpty(education.getCollegeName())) {
                    collegeCodes.add((int) education.getCollegeCode());
                }
                if (!StringUtils.isNullOrEmpty(education.getMajorCode())
                        && StringUtils.isNullOrEmpty(education.getMajorName())) {
                    majorCodes.add(education.getMajorCode());
                }
            });
            // 学校
            List<DictCollegeDO> colleges = collegeDao.getCollegesByIDs(collegeCodes);
            if (colleges != null && colleges.size() > 0) {
                for (ProfileEducationDO education : educations) {
                    for (DictCollegeDO college : colleges) {
                        if (education.getCollegeCode() == college.getCode()) {
                            education.setCollegeName(college.getName());
                            education.setCollegeLogo(college.getLogo());
                            break;
                        }
                    }
                }
            }
            // 专业
            List<DictMajorDO> majors = majorDao.getMajorsByIDs(majorCodes);
            if (majors != null && majors.size() > 0) {
                for (ProfileEducationDO education : educations) {
                    for (DictMajorDO major : majors) {
                        if (education.getMajorCode().equals(major.getCode())) {
                            education.setMajorName(major.getName());
                            break;
                        }
                    }
                }
            }

        }
        return educations;
    }

    public ProfileEducationDO getResource(Query query) throws TException {
        ProfileEducationDO result = dao.getData(query);
        if (result != null) {
            Query dictQuery = new Query.QueryBuilder().where("code", result.getCollegeCode()).buildQuery();
            DictCollegeDO college = collegeDao.getData(dictQuery);
            if (college != null) {
                result.setCollegeName(college.getName());
                result.setCollegeLogo(college.getLogo());
            }
            Query majorQuery = new Query.QueryBuilder().where("code", result.getMajorCode()).buildQuery();
            DictMajorDO major = majorDao.getData(majorQuery);
            if (major != null) {
                result.setMajorName(major.getName());
            }
        }
        return result;
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
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
    public ProfileEducationDO postResource(ProfileEducationDO education) throws TException {
        ProfileEducationDO result = null;
        if (education != null) {
            //添加信息校验
            ValidationMessage<ProfileEducationDO> vm = ProfileValidation.verifyEducation(education);
            if (!vm.isPass()) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
            }
            if (education.getCollegeCode() > 0) {
                DictCollegeDO college = collegeDao.getCollegeByID(education.getCollegeCode());
                if (college != null) {
                    education.setCollegeName(college.getName());
                    education.setCollegeLogo(college.getLogo());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
                }
            }
            if (!StringUtils.isNullOrEmpty(education.getMajorCode())) {
                DictMajorDO major = majorDao.getMajorByID(education.getMajorCode());
                if (major != null) {
                    education.setMajorName(major.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
                }
            }
            result = dao.addData(education);
            if (result != null && result.getId() > 0) {
                Set<Integer> profileIds = new HashSet<>();
                profileIds.add(education.getProfileId());
                profileDao.updateUpdateTime(profileIds);
                
				/* 计算profile完整度 */
                completenessImpl.reCalculateProfileEducation(education.getProfileId(), 0);
            }
        }
        return result;

    }

    @Transactional
    public int putResource(ProfileEducationDO education) throws TException {
        int result = 0;

        if (education != null) {
            if (education.getCollegeCode() > 0) {
                DictCollegeDO college = collegeDao.getCollegeByID(education.getCollegeCode());
                if (college != null) {
                    education.setCollegeName(college.getName());
                    education.setCollegeLogo(college.getLogo());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_COLLEGE_NOTEXIST);
                }
            }
            if (!StringUtils.isNullOrEmpty(education.getMajorCode())) {
                DictMajorDO major = majorDao.getMajorByID(education.getMajorCode());
                if (major != null) {
                    education.setMajorName(major.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_MAJOR_NOTEXIST);
                }
            }

            result = dao.updateData(education);

            if (result > 0) {
                updateUpdateTime(education);
            /* 计算profile完整度 */
                completenessImpl.reCalculateProfileEducation(education.getProfileId(), education.getId());
            }
        }
        return result;
    }

    @Transactional
    public List<ProfileEducationDO> postResources(List<ProfileEducationDO> structs) throws TException {

        List<ProfileEducationDO> resultDatas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {

            //添加信息校验
            if (structs != null && structs.size() > 0) {
                Iterator<ProfileEducationDO> ie = structs.iterator();
                while (ie.hasNext()) {
                    ProfileEducationDO education = ie.next();
                    ValidationMessage<ProfileEducationDO> vm = ProfileValidation.verifyEducation(education);
                    if (!vm.isPass()) {
                        ie.remove();
                    }
                }
            }

            if (structs.size() > 0) {
                resultDatas = dao.addAllData(structs);
                Set<Integer> profileIds = new HashSet<>();
                resultDatas.forEach(struct -> {
                    if (struct.getProfileId() > 0) {
                        profileIds.add(struct.getProfileId());
                    }
                });

                profileDao.updateUpdateTime(profileIds);

                profileIds.forEach(profileId -> {
                    /* 计算profile完整度 */
                    completenessImpl.reCalculateProfileEducation(profileId, 0);
                });
            }
        }
        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<ProfileEducationDO> structs) throws TException {

        int[] result = new int[0];

        if (structs != null && structs.size() > 0) {

            result = dao.updateDatas(structs);

            List<ProfileEducationDO> updatedDatas = new ArrayList<>();

            for (int i : result) {
                if (i > 0) updatedDatas.add(structs.get(i));
            }

            updateUpdateTime(updatedDatas);
            updatedDatas.forEach(struct -> {
                /* 计算profile完整度 */
                completenessImpl.reCalculateProfileEducation(struct.getProfileId(), struct.getId());
            });
        }
        return result;
    }

    @Transactional
    public int[] delResources(List<ProfileEducationDO> structs) throws TException {
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
            int[] result = dao.deleteDatas(structs);

            if (deleteDatas != null && deleteDatas.size() > 0) {
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet()));
                for (ProfileEducationDO data : deleteDatas) {
                    completenessImpl.reCalculateProfileEducation(data.getProfileId(), 0);
                }
            }
            return result;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int delResource(ProfileEducationDO struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id", struct.getId())).buildQuery();
            //找到要删除的数据
            ProfileEducationDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(struct);
                if (result > 0) {
                    updateUpdateTime(struct);
                    /* 计算profile完整度 */
                    completenessImpl.reCalculateProfileEducation(deleteData.getProfileId(), 0);
                }
            }
        }

        return result;
    }

    public void updateUpdateTime(List<ProfileEducationDO> educations) {
        if(educations == null  || educations.size() == 0)return;

        HashSet<Integer> educationIds = new HashSet<>();

        educations.forEach(education -> {
            educationIds.add(education.getId());
        });
        dao.updateProfileUpdateTime(educationIds);
    }

    private void updateUpdateTime(ProfileEducationDO education) {

        if (education == null) return;

        List<ProfileEducationDO> educations = new ArrayList<>();
        educations.add(education);
        updateUpdateTime(educations);
    }

}
