package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryDao;
import com.moseeker.baseorm.dao.dictdb.DictPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorkexpDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@CounterIface
public class ProfileWorkExpService {

    Logger logger = LoggerFactory.getLogger(ProfileWorkExpService.class);

    @Autowired
    private ProfileWorkexpDao dao;

    @Autowired
    private DictIndustryDao industryDao;

    @Autowired
    private DictPositionDao positionDao;

    @Autowired
    private DictCityDao cityDao;

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;


    public Response getResources(Query query) throws TException {
        // 按照结束时间倒序
        query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
        query.getOrders().add(new OrderBy("start", Order.DESC));

        List<ProfileWorkexpRecord> records = dao.getRecords(query);
        if (records != null && records.size() > 0) {
            List<WorkExp> workExps = DBsToStructs(records);
            List<Integer> industryCodes = new ArrayList<>();
            List<Integer> cityCodes = new ArrayList<>();
            List<Integer> companyIds = new ArrayList<>();
            List<Integer> positionCodes = new ArrayList<>();
            records.forEach(record -> {
                if (record.getIndustryCode() != null && record.getIndustryCode().intValue() > 0
                        && StringUtils.isNotNullOrEmpty(record.getIndustryName())) {
                    industryCodes.add(record.getIndustryCode().intValue());
                }
                if (record.getCityCode() != null && record.getCityCode().intValue() > 0
                        && StringUtils.isNullOrEmpty(record.getCityName())) {
                    cityCodes.add(record.getCityCode().intValue());
                }
                if (record.getCompanyId() != null && record.getCompanyId().intValue() > 0) {
                    companyIds.add(record.getCompanyId().intValue());
                }
                if (record.getPositionCode() != null && record.getPositionCode().intValue() > 0) {
                    positionCodes.add(record.getPositionCode().intValue());
                }
            });
            if (workExps.size() > 0) {
                List<DictIndustryRecord> industryRecords = industryDao.getIndustriesByCodes(industryCodes);
                List<DictCityRecord> cityRecords = cityDao.getCitiesByCodes(cityCodes);
                List<HrCompanyRecord> companies = companyDao.getCompaniesByIds(companyIds);
                List<DictPositionRecord> positionRecords = positionDao.getPositionsByCodes(positionCodes);
                for (WorkExp workExp : workExps) {
                    if (industryRecords != null && industryRecords.size() > 0) {
                        for (DictIndustryRecord industry : industryRecords) {
                            if (workExp.getIndustry_code() == industry.getCode().intValue()) {
                                workExp.setIndustry_name(industry.getName());
                                break;
                            }
                        }
                    }
                    if (cityRecords != null && cityRecords.size() > 0) {
                        for (DictCityRecord city : cityRecords) {
                            if (workExp.getCity_code() == city.getCode().intValue()) {
                                workExp.setCity_name(city.getName());
                                break;
                            }
                        }
                    }
                    if (companies != null && companies.size() > 0) {
                        for (HrCompanyRecord company : companies) {
                            if (workExp.getCompany_id() == company.getId().intValue()) {
                                workExp.setCompany_name(company.getName());
                                workExp.setCompany_logo(company.getLogo());
                                break;
                            }
                        }
                    }
                    if (positionRecords != null && positionRecords.size() > 0) {
                        for (DictPositionRecord position : positionRecords) {
                            if (workExp.getPosition_code() == position.getCode().intValue()) {
                                workExp.setPosition_name(position.getName());
                                break;
                            }
                        }
                    }
                }
                return ResponseUtils.success(workExps);
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }


    public Response getResource(Query query) throws TException {
        ProfileWorkexpRecord record = dao.getRecord(query);
        if (record != null) {
            DictIndustryRecord industryRecord = industryDao.getIndustryByCode(record.getIndustryCode().intValue());
            DictCityRecord cityRecord = cityDao.getCityByCode(record.getCityCode().intValue());
            HrCompanyRecord company = companyDao.getCompanyRecordById(record.getCompanyId().intValue());
            DictPositionRecord positionRecord = positionDao.getPositionByCode(record.getPositionCode().intValue());
            WorkExp workExp = DBToStruct(record);
            if (industryRecord != null) {
                workExp.setIndustry_name(industryRecord.getName());
            }
            if (cityRecord != null) {
                workExp.setCity_name(cityRecord.getName());
            }
            if (company != null) {
                workExp.setCompany_name(company.getName());
                workExp.setCompany_logo(company.getLogo());
            }
            if (positionRecord != null) {
                workExp.setPosition_name(positionRecord.getName());
            }
            return ResponseUtils.success(workExp);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    @Transactional
    public Response postResource(WorkExp struct) throws TException {
        ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        if (struct.getCity_code() > 0) {
            DictCityRecord cityRecord = cityDao.getCityByCode(struct.getCity_code());
            if (cityRecord != null) {
                struct.setCity_name(cityRecord.getName());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
            }
        }
        if (struct.getPosition_code() > 0) {
            DictPositionRecord positionRecord = positionDao.getPositionByCode(struct.getPosition_code());
            if (positionRecord != null) {
                struct.setPosition_name(positionRecord.getName());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_POSITION_NOTEXIST);
            }
        }
        if (struct.getIndustry_code() > 0) {
            DictIndustryRecord industryRecord = industryDao.getIndustryByCode(struct.getIndustry_code());
            if (industryRecord != null) {
                struct.setIndustry_name(industryRecord.getName());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_INDUSTRY_NOTEXIST);
            }
        }
        if (struct.getCompany_id() > 0) {
            HrCompanyRecord company = companyDao.getCompanyRecordById(struct.getCompany_id());
            if (company == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
            }
        } else {
            if (!StringUtils.isNullOrEmpty(struct.getCompany_name())) {
                QueryUtil qu = new QueryUtil();
                qu.addEqualFilter("name", struct.getCompany_name());
                HrCompanyRecord company = companyDao.getRecord(qu);
                if (company != null) {
                    struct.setCompany_id(company.getId().intValue());
                } else {
                    HrCompanyRecord newCompany = new HrCompanyRecord();
                    newCompany.setName(struct.getCompany_name());
                    if (!StringUtils.isNullOrEmpty(struct.getCompany_logo())) {
                        newCompany.setLogo(struct.getCompany_logo());
                    }
                    newCompany.setType((byte) (Constant.COMPANY_TYPE_FREE));
                    newCompany.setSource((byte) (struct.getSource()));
                    newCompany = companyDao.addRecord(newCompany);
                    struct.setCompany_id(newCompany.getId());
                }
            }
        }

        ProfileWorkexpRecord record = structToDB(struct);
        record = dao.addRecord(record);

        if (record.getId() > 0) {
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(struct.getProfile_id());
            profileDao.updateUpdateTime(profileIds);
                /* 计算用户基本信息的简历完整度 */
            profileEntity.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
            profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());

            return ResponseUtils.success(String.valueOf(record.getId()));
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    @Transactional
    public Response putResource(WorkExp struct) throws TException {
        if (struct.getCity_code() > 0) {
            DictCityRecord cityRecord = cityDao.getCityByCode(struct.getCity_code());
            if (cityRecord != null) {
                struct.setCity_name(cityRecord.getName());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
            }
        }
        if (struct.getPosition_code() > 0) {
            DictPositionRecord positionRecord = positionDao.getPositionByCode(struct.getPosition_code());
            if (positionRecord != null) {
                struct.setPosition_name(positionRecord.getName());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_POSITION_NOTEXIST);
            }
        }
        if (struct.getIndustry_code() > 0) {
            DictIndustryRecord industryRecord = industryDao.getIndustryByCode(struct.getIndustry_code());
            if (industryRecord != null) {
                struct.setIndustry_name(industryRecord.getName());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_INDUSTRY_NOTEXIST);
            }
        }
        if (struct.getCompany_id() > 0) {
            HrCompanyRecord company = companyDao.getCompanyRecordById(struct.getCompany_id());
            if (company == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
            }
        } else {
            if (!StringUtils.isNullOrEmpty(struct.getCompany_name())) {
                QueryUtil qu = new QueryUtil();
                qu.addEqualFilter("name", struct.getCompany_name());
                HrCompanyRecord company = companyDao.getRecord(qu);
                if (company != null) {
                    struct.setCompany_id(company.getId().intValue());
                } else {
                    HrCompanyRecord newCompany = new HrCompanyRecord();
                    newCompany.setName(struct.getCompany_name());
                    if (!StringUtils.isNullOrEmpty(struct.getCompany_logo())) {
                        newCompany.setLogo(struct.getCompany_logo());
                    }
                    newCompany.setType((byte) (Constant.COMPANY_TYPE_FREE));
                    newCompany.setSource((byte) (struct.getSource()));
                    newCompany = companyDao.addRecord(newCompany);
                    struct.setCompany_id(newCompany.getId());
                }
            }
        }

        ProfileWorkexpRecord originProfileWorkexpRecord = structToDB(struct);

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(ProfileWorkexp.PROFILE_WORKEXP.ID.getName(), originProfileWorkexpRecord.getId());
        ProfileWorkexpRecord descProfileWorkexpRecord = dao.getRecord(queryBuilder.buildQuery());
        if (descProfileWorkexpRecord != null) {
            RecordTool.recordToRecord(descProfileWorkexpRecord, originProfileWorkexpRecord);
            ValidationMessage<ProfileWorkexpRecord> validationMessage = ProfileValidation.verifyWorkExp(descProfileWorkexpRecord);
            if (!validationMessage.isPass()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", validationMessage.getResult()));
            }
            int result = dao.updateRecord(descProfileWorkexpRecord);

            if (result > 0) {
                updateUpdateTime(descProfileWorkexpRecord);
                /* 计算用户基本信息的简历完整度 */
                profileEntity.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());

                profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());

                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response postResources(List<WorkExp> structs) throws TException {

        if (structs != null && structs.size() > 0) {
            Iterator<WorkExp> wei = structs.iterator();
            while (wei.hasNext()) {
                WorkExp workExp = wei.next();
                ValidationMessage<WorkExp> vm = ProfileValidation.verifyWorkExp(workExp);
                if (!vm.isPass()) {
                    wei.remove();
                }
            }
            if (structs.size() > 0) {
                List<ProfileWorkexpRecord> records = dao.addAllRecord(structsToDBs(structs));
                Set<Integer> profileIds = new HashSet<>();
                for (WorkExp struct : structs) {
                    profileIds.add(struct.getProfile_id());
                /* 计算用户基本信息的简历完整度 */
                    profileEntity.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
                    profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
                }
                profileDao.updateUpdateTime(profileIds);
                return ResponseUtils.success("1");
            }
        }

        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    @Transactional
    public Response putResources(List<WorkExp> structs) throws TException {

        List<ProfileWorkexpRecord> originProfileWorkexpRecordList = BeanUtils.structToDB(structs, ProfileWorkexpRecord.class);
        List<Integer> workexpIdList = originProfileWorkexpRecordList.stream()
                .map(profileWorkexpRecord -> profileWorkexpRecord.getId())
                .collect(Collectors.toList());
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition(ProfileWorkexp.PROFILE_WORKEXP.ID.getName(), workexpIdList, ValueOp.IN));
        List<ProfileWorkexpRecord> descProfileWorkExpRecordList = dao.getRecords(queryBuilder.buildQuery());
        if (descProfileWorkExpRecordList != null) {
            Iterator<ProfileWorkexpRecord> iterator = descProfileWorkExpRecordList.iterator();
            while (iterator.hasNext()) {
                ProfileWorkexpRecord profileWorkexpRecord = iterator.next();
                Optional<ProfileWorkexpRecord> optional = originProfileWorkexpRecordList.stream()
                        .filter(profileWorkexpRecord1 -> profileWorkexpRecord1.getId().intValue() == profileWorkexpRecord.getId())
                        .findAny();
                if (optional.isPresent()) {
                    RecordTool.recordToRecord(profileWorkexpRecord, optional.get());
                    ValidationMessage<ProfileWorkexpRecord> vm = ProfileValidation.verifyWorkExp(profileWorkexpRecord);
                    if (!vm.isPass()) {
                        iterator.remove();
                    }
                }
            }
            if(!StringUtils.isEmptyList(descProfileWorkExpRecordList)){
                dao.updateRecords(descProfileWorkExpRecordList);
                for(ProfileWorkexpRecord profileWorkexpRecord: descProfileWorkExpRecordList){
                    profileEntity.reCalculateProfileWorkExpUseWorkExpId(profileWorkexpRecord.getId());
                    profileCompanyTagService.handlerCompanyTag(profileWorkexpRecord.getProfileId());
                }
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResources(List<WorkExp> structs) throws TException {
        QueryUtil qu = new QueryUtil();
        StringBuffer sb = new StringBuffer("[");
        structs.forEach(struct -> {
            sb.append(struct.getId());
            sb.append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        qu.addEqualFilter("id", sb.toString());

        List<ProfileWorkexpRecord> workExpRecords = null;
        workExpRecords = dao.getRecords(qu);
        Set<Integer> profileIds = new HashSet<>();
        if (workExpRecords != null && workExpRecords.size() > 0) {
            workExpRecords.forEach(workExp -> {
                profileIds.add(workExp.getProfileId().intValue());
            });
        }

        if (workExpRecords != null && workExpRecords.size() > 0) {
            int[] result = dao.deleteRecords(structsToDBs(structs));
            if (ArrayUtils.contains(result, 1)) {
                profileIds.forEach(profileId -> {
                    updateUpdateTime(structs);
                /* 计算用户基本信息的简历完整度 */
                    profileEntity.reCalculateProfileWorkExp(profileId, 0);
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    @Transactional
    public Response delResource(WorkExp struct) throws TException {
        int result = dao.deleteRecord(structToDB(struct));
        if (result > 0) {
            updateUpdateTime(struct);
            /* 计算用户基本信息的简历完整度 */
            profileEntity.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
            profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    protected WorkExp DBToStruct(ProfileWorkexpRecord r) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return BeanUtils.DBToStruct(WorkExp.class, r, equalRules);
    }


    protected ProfileWorkexpRecord structToDB(WorkExp workExp) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return BeanUtils.structToDB(workExp, ProfileWorkexpRecord.class, equalRules);
    }

    protected List<WorkExp> DBsToStructs(List<ProfileWorkexpRecord> records) {
        List<WorkExp> structs = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (ProfileWorkexpRecord r : records) {
                structs.add(DBToStruct(r));
            }
        }
        return structs;
    }

    protected List<ProfileWorkexpRecord> structsToDBs(List<WorkExp> records) {
        List<ProfileWorkexpRecord> structs = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (WorkExp r : records) {
                structs.add(structToDB(r));
            }
        }
        return structs;
    }

    private void updateUpdateTime(List<WorkExp> workExps) {
        Set<Integer> workExpIds = new HashSet<>();
        workExps.forEach(workExp -> {
            workExpIds.add(workExp.getId());
        });
        dao.updateProfileUpdateTime(workExpIds);
    }

    private void updateUpdateTime(WorkExp workExp) {
        List<WorkExp> workExps = new ArrayList<>();
        workExps.add(workExp);
        updateUpdateTime(workExps);
    }

    private void updateUpdateTime(ProfileWorkexpRecord descProfileWorkexpRecord) {
        if (descProfileWorkexpRecord != null) {
            dao.updateProfileUpdateTime(new HashSet<Integer>(){{add(descProfileWorkexpRecord.getId());}});
        }
    }

    public Response getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ResponseUtils.success(ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
    }
}
