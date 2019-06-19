package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.profile.struct.Basic;
import org.apache.thrift.TException;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CounterIface
public class ProfileBasicService {

    Logger logger = LoggerFactory.getLogger(ProfileBasicService.class);

    @Autowired
    ProfileBasicDao dao;

    @Autowired
    DictCityDao cityDao;

    @Autowired
    DictCountryDao countryDao;

    @Autowired
    ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    ScheduledThread scheduledThread = ScheduledThread.Instance;
    @Resource(name = "cacheClient")
    protected RedisClient client;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    public List<Basic> getResources(Query query) {
        List<Basic> basics = dao.getDatas(query, Basic.class);
        if (basics != null && basics.size() > 0) {
            List<Integer> profileIds = new ArrayList<>();
            List<Integer> cityCodes = new ArrayList<>();
            List<Integer> contryIds = new ArrayList<>();
            basics.forEach(basic -> {
                //cityCodes.add(basic.getCity());
                if (basic.getCity_code() > 0 && StringUtils.isNullOrEmpty(basic.getCity_name()))
                    cityCodes.add((int) basic.getCity_code());
                if (basic.getNationality_code() > 0 && StringUtils.isNullOrEmpty(basic.getNationality_name()))
                    contryIds.add((int) basic.getNationality_code());
                profileIds.add(basic.getProfile_id());
            });

            //城市
            List<DictCityRecord> cities = cityDao.getCitiesByCodes(cityCodes);
            if (cities != null && cities.size() > 0) {
                for (Basic basic : basics) {
                    for (DictCityRecord record : cities) {
                        if (basic.getCity_code() == record.getCode().intValue()) {
                            basic.setCity_name(record.getName());
                            break;
                        }
                    }
                }
            }
            //国旗
            List<DictCountryRecord> countries = countryDao.getCountresByIDs(cityCodes);
            if (countries != null && countries.size() > 0) {
                for (Basic basic : basics) {
                    for (DictCountryRecord record : countries) {
                        if (basic.getNationality_code() == record.getId().intValue()) {
                            basic.setNationality_name(record.getName());
                            break;
                        }
                    }
                }
            }
            Result<Record2<Integer, String>> result = profileDao.findRealName(profileIds);
            if (result != null && result.size() > 0) {
                for (Basic basic : basics) {
                    for (Record2<Integer, String> record2 : result) {
                        if (basic.getProfile_id() == (int) record2.get(0)) {
                            basic.setName((String) record2.get(1));
                            break;
                        }
                    }
                }
            }
        }
        return basics;
    }

    public Basic getResource(Query query) {
        Basic basic = dao.getData(query, Basic.class);
        if (basic != null) {
            if (basic.getCity_code() > 0 && StringUtils.isNullOrEmpty(basic.getCity_name())) {
                DictCityRecord city = cityDao.getCityByCode(basic.getCity_code());
                if (city != null) {
                    basic.setCity_name(city.getName());
                }
            }
            if (basic.getNationality_code() > 0 && StringUtils.isNullOrEmpty(basic.getNationality_name())) {
                DictCountryRecord country = countryDao.getCountryByID(basic.getNationality_code());
                if (country != null) {
                    basic.setNationality_name(country.getName());
                }
            }
            String realName = profileDao.findRealName(basic.getProfile_id());
            if (StringUtils.isNullOrEmpty(realName)) {
                basic.setName(realName);
            }
        }
        return basic;
    }

    @Transactional
    public Basic postResource(Basic struct) throws BIZException {
        Basic resultStruct = null;
        if (struct != null) {
            ValidationMessage<Basic> vm = ProfileValidation.verifyBasic(struct);
            if (!vm.isPass()) {
                throw ProfileException.validateFailed(vm.getResult());
            }
            if (struct.getCity_code() > 0) {
                DictCityRecord city = cityDao.getCityByCode(struct.getCity_code());
                if (city != null) {
                    struct.setCity_name(city.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
                }
            }else if(StringUtils.isNotNullOrEmpty(struct.getCity_name())){
                DictCityRecord city = cityDao.getCityByName(struct.getCity_name());
                if (city != null) {
                    struct.setCity_code(city.getCode());
                }else{
                    logger.warn("根据cityName：{}；没有查找到对应city数据", struct.getCity_name());
                }
            }
            if (struct.getNationality_code() > 0) {
                DictCountryRecord country = countryDao.getCountryByID(struct.getNationality_code());
                if (country != null) {
                    struct.setNationality_name(country.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_NATIONALITY_NOTEXIST);
                }
            }
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileBasicRecord repeat = dao.getRecord(qu);
            if (repeat != null) {
                throw ExceptionUtils.getCommonException(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
            }
            if (struct.getProfile_id() > 0) {
                resultStruct = dao.addRecord(BeanUtils.structToDB(struct, ProfileBasicRecord.class)).into(Basic.class);

                if (resultStruct.getProfile_id() > 0) {

                    updateUpdateTime(resultStruct);

                    if (!StringUtils.isNullOrEmpty(struct.getName())) {
                        profileDao.updateRealName(resultStruct.getProfile_id(), struct.getName());
                    }
                /* 计算用户基本信息的简历完整度 */
                    profileEntity.reCalculateUserUser(struct.getProfile_id());

                    this.handlerCompanyTag(struct.getProfile_id());
                    this.updateEsUsersAndProfile(struct.getProfile_id());
                    return resultStruct;
                }
            }
        }

        return resultStruct;
    }

    private void updateEsUsersAndProfile(int profileId){
        if(profileId>0){
            ProfileProfileRecord profileProfile=profileDao.getProfileByIdOrUserIdOrUUID(0,profileId,null);
            if(profileProfile!=null&&profileProfile.getUserId()>0){
                Map<String, Object> result = new HashMap<>();
                result.put("user_id", profileProfile.getUserId());
                result.put("tableName","user_meassage");
                scheduledThread.startTast(()->{
                    client.lpush(Constant.APPID_ALPHADOG, "ES_REALTIME_UPDATE_INDEX_USER_IDS", JSON.toJSONString(result));
                    client.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_PROFILE_COMPANY_USER_IDS",String.valueOf(profileProfile.getUserId()));
                },2000);
            }

        }

    }



    @Transactional
    public int putResource(Basic struct) throws TException {
        int i = 0;
        if (struct != null) {
            if (struct.getCity_code() > 0) {
                DictCityRecord city = cityDao.getCityByCode(struct.getCity_code());
                if (city != null) {
                    struct.setCity_name(city.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
                }
            }else if(StringUtils.isNotNullOrEmpty(struct.getCity_name())){
                DictCityRecord city = cityDao.getCityByName(struct.getCity_name());
                if (city != null) {
                    struct.setCity_code(city.getCode());
                }else{
                    logger.warn("根据cityName：{}；没有查找到对应city数据", struct.getCity_name());
                }
            }
            if (struct.getNationality_code() > 0) {
                DictCountryRecord country = countryDao.getCountryByID(struct.getNationality_code());
                if (country != null) {
                    struct.setNationality_name(country.getName());
                } else {
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DICT_NATIONALITY_NOTEXIST);
                }
            }
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileBasicRecord repeat = dao.getRecord(qu);
            if (repeat == null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_DATA_NULL);
            }

            ProfileBasicRecord param = BeanUtils.structToDB(struct, ProfileBasicRecord.class);
            RecordTool.recordToRecord(repeat, param);
            ValidationMessage<ProfileBasicRecord> validationMessage = ProfileValidation.verifyBasic(repeat);
            if (validationMessage.isPass()) {
                i = dao.updateRecord(BeanUtils.structToDB(struct, ProfileBasicRecord.class));
                if (i > 0) {

                    updateUpdateTime(struct);

                    if (!StringUtils.isNullOrEmpty(struct.getName())) {
                        profileDao.updateRealName(struct.getProfile_id(), struct.getName());
                    }

				/* 计算用户基本信息的简历完整度 */
                    profileEntity.reCalculateUserUser(struct.getProfile_id());
                    profileEntity.reCalculateProfileBasic(struct.getProfile_id());

                    this.updateEsUsersAndProfile(struct.getProfile_id());

                }
            } else {
                throw ProfileException.validateFailed(validationMessage.getResult());
            }

        }
        this.handlerCompanyTag(struct.getProfile_id());
        return i;
    }



    @Transactional
    public List<Basic> postResources(List<Basic> structs) throws TException {
        List<Basic> resultDatas = new ArrayList<>();

        //删除不符合数据要求的基本信息数据
        removeIllegalBasic(structs);

        if (structs != null && structs.size() > 0) {

            List<ProfileBasicRecord> records = BeanUtils.structToDB(structs, ProfileBasicRecord.class);

            records = dao.addAllRecord(records);

            resultDatas = BeanUtils.DBToStruct(Basic.class, records);

            HashSet<Integer> profileIds = new HashSet<>();
            for (Basic basic : structs) {
                if (basic.getProfile_id() > 0) {
                    profileIds.add(basic.getProfile_id());
                }
            }
            profileDao.updateUpdateTime(profileIds);
            profileIds.forEach(profileId -> {
                /* 计算用户基本信息的简历完整度 */
                profileEntity.reCalculateUserUser(profileId);
                profileEntity.reCalculateProfileBasic(profileId);
                this.handlerCompanyTag(profileId);
            });
        }

        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<Basic> structs) throws TException {

        if (structs != null && structs.size() > 0) {

            List<ProfileBasicRecord> paramList = BeanUtils.structToDB(structs, ProfileBasicRecord.class);

            /** 校验基本信息数据是否符合要求 */
            List<Integer> paramIdList = paramList.stream().map(basicRecord -> basicRecord.getProfileId()).collect(Collectors.toList());
            List<ProfileBasicRecord> basicRecordList = dao.fetchBasicByProfileIdList(paramIdList);
            if (basicRecordList != null && basicRecordList .size() > 0) {
                Iterator<ProfileBasicRecord> iterator = basicRecordList.iterator();
                while (iterator.hasNext()) {
                    ProfileBasicRecord basicRecord = iterator.next();
                    Optional<ProfileBasicRecord> optional = paramList
                            .stream()
                            .filter(basic -> basic.getProfileId().intValue() == basicRecord.getProfileId().intValue())
                            .findAny();
                    if (optional.isPresent()) {
                        RecordTool.recordToRecord(basicRecord, optional.get());
                        ValidationMessage<ProfileBasicRecord> validationMessage = ProfileValidation.verifyBasic(basicRecord);
                        if (!validationMessage.isPass()) {
                            iterator.remove();
                        }
                    } else {
                        iterator.remove();
                    }
                }
            }

            int[] putResult = dao.updateRecords(basicRecordList);

            HashSet<Integer> profileIds = new HashSet<>();

            for (int i = 0; i < putResult.length; i++) {
                if (putResult[i] > 0) {
                    profileIds.add(structs.get(i).getProfile_id());
                }
            }

            profileDao.updateUpdateTime(profileIds);
            profileIds.forEach(profileId -> {
                /* 计算用户基本信息的简历完整度 */
                profileEntity.reCalculateUserUser(profileId);
                profileEntity.reCalculateProfileBasic(profileId);
                this.handlerCompanyTag(profileId);
            });
            return putResult;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int delResource(Basic struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id", struct.getProfile_id())).buildQuery();
            //找到要删除的数据
            ProfileBasicDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteRecord(BeanUtils.structToDB(struct, ProfileBasicRecord.class));
                if (result > 0) {
                    updateUpdateTime(struct);

                /* 计算用户基本信息的简历完整度 */
                    profileEntity.reCalculateUserUser(struct.getProfile_id());
                    profileEntity.reCalculateProfileBasic(struct.getProfile_id());
                    this.handlerCompanyTag(struct.getProfile_id());
                }
            }
        }
        return result;
    }

    @Transactional
    public int[] delResources(List<Basic> structs) throws TException {

        if (structs != null && structs.size() > 0) {

            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id",
                            structs.stream()
                                    .map(struct -> struct.getProfile_id())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileBasicDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileBasicRecord.class));


            if (deleteDatas != null && deleteDatas.size() > 0) {
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet()));

                for (ProfileBasicDO data : deleteDatas) {
                    profileEntity.reCalculateUserUser(data.getProfileId());
                    profileEntity.reCalculateProfileBasic(data.getProfileId());
                    this.handlerCompanyTag(data.getProfileId());
                }
            }

            return result;
        } else {
            return new int[0];
        }
    }

    private void updateUpdateTime(Basic basic) {

        if (basic == null) return;

        HashSet<Integer> profileIds = new HashSet<>();

        profileIds.add(basic.getProfile_id());
        profileDao.updateUpdateTime(profileIds);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }

    /**
     * 过滤不合法的基本信息
     * @param structs
     */
    private void removeIllegalBasic(List<Basic> structs) {
        if (structs != null && structs.size() > 0) {
            Iterator<Basic> ie = structs.iterator();
            while (ie.hasNext()) {
                Basic basic = ie.next();
                ValidationMessage<Basic> vm = ProfileValidation.verifyBasic(basic);
                if (!vm.isPass()) {
                    ie.remove();
                }
            }
        }
    }
    private void  handlerCompanyTag(int profileId){
        profileCompanyTagService.handlerProfileCompanyTag(profileId,0);
    }
}
