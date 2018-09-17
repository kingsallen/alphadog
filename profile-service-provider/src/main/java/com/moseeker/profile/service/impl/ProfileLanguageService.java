package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileLanguageDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.ProfileLanguage;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Awards;
import com.moseeker.thrift.gen.profile.struct.Language;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CounterIface
public class ProfileLanguageService {

    Logger logger = LoggerFactory.getLogger(ProfileLanguageService.class);

    @Autowired
    private ProfileLanguageDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;
    @Transactional
    public Response postResources(List<Language> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Iterator<Language> ic = structs.iterator();
            while (ic.hasNext()) {
                Language language = ic.next();
                ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(language);
                if (!vm.isPass()) {
                    ic.remove();
                }
            }
        }

        List<ProfileLanguageRecord> records = BeanUtils.structToDB(structs, ProfileLanguageRecord.class);
        records = dao.addAllRecord(records);
        Set<Integer> profileIds = new HashSet<>();
        structs.forEach(struct -> {
            profileIds.add(struct.getProfile_id());
        });



        profileDao.updateUpdateTime(profileIds);
        profileIds.forEach(profileId -> {
            //计算profile完整度
            profileEntity.recalculateprofileLanguage(profileId, 0);
            profileCompanyTagService.handlerCompanyTag(profileId);
        });
        return ResponseUtils.success("1");
    }

    @Transactional
    public Response putResources(List<Language> structs) throws CommonException {

        if (structs != null) {
            List<ProfileLanguageRecord> originLanguageRecordList = BeanUtils.structToDB(structs, ProfileLanguageRecord.class);

            List<Integer> languageIdList = originLanguageRecordList.stream()
                    .map(profileLanguageRecord -> profileLanguageRecord.getId())
                    .collect(Collectors.toList());

            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition(ProfileLanguage.PROFILE_LANGUAGE.ID.getName(), languageIdList, ValueOp.IN));

            List<ProfileLanguageRecord> descLanguageRecordList = dao.getRecords(queryBuilder.buildQuery());
            if (descLanguageRecordList != null) {
                Iterator<ProfileLanguageRecord> iterator = descLanguageRecordList.iterator();
                while (iterator.hasNext()) {
                    ProfileLanguageRecord profileLanguageRecord = iterator.next();
                    Optional<ProfileLanguageRecord> optional = originLanguageRecordList.stream()
                            .filter(languageRecord -> languageRecord.getId().intValue() == profileLanguageRecord.getId())
                            .findAny();
                    if (optional.isPresent()) {
                        RecordTool.recordToRecord(profileLanguageRecord, optional.get());

                        ValidationMessage<ProfileLanguageRecord> validationMessage = ProfileValidation.verifyLanguage(profileLanguageRecord);
                        if (!validationMessage.isPass()) {
                            iterator.remove();
                        }
                    }
                }
                dao.updateRecords(descLanguageRecordList);
                updateProfileUpdateTime(descLanguageRecordList);
                descLanguageRecordList.forEach(profileLanguageRecord -> {
                    profileEntity.recalculateprofileLanguage(profileLanguageRecord.getProfileId(), profileLanguageRecord.getId());
                });
            }
        }
        return ResponseUtils.success("1");
    }

    @Transactional
    public Response delResources(List<Language> structs) throws TException {
        //dao.fetchProfileIds(structs);
        if (structs != null && structs.size() > 0) {
            QueryUtil qu = new QueryUtil();
            StringBuffer sb = new StringBuffer("[");
            structs.forEach(struct -> {
                sb.append(struct.getId());
                sb.append(",");
            });
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            qu.addEqualFilter("id", sb.toString());

            List<ProfileLanguageRecord> languageRecords = dao.getRecords(qu);
            Set<Integer> profileIds = new HashSet<>();
            if (languageRecords != null && languageRecords.size() > 0) {
                languageRecords.forEach(language -> {
                    profileIds.add(language.getProfileId().intValue());
                });
            }
            if (languageRecords != null && languageRecords.size() > 0) {
                int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileLanguageRecord.class));
                if (ArrayUtils.contains(result, 1)) {
                    updateUpdateTime(structs);
                    profileIds.forEach(profileId -> {
                        //计算profile完整度
                        profileEntity.recalculateprofileLanguage(profileId, 0);
                    });

                    return ResponseUtils.success("1");
                }
            }
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
        }

        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    @Transactional
    public Response postResource(Language struct) throws CommonException {
        ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        ProfileLanguageRecord record = dao.addRecord(BeanUtils.structToDB(struct, ProfileLanguageRecord.class));

        Set<Integer> profileIds = new HashSet<>();
        profileIds.add(struct.getProfile_id());
        profileDao.updateUpdateTime(profileIds);

        profileEntity.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
        profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
        return ResponseUtils.success(String.valueOf(record.getId()));
    }

    @Transactional
    public Response putResource(Language struct) throws TException {

        ProfileLanguageRecord originLanguageRecord = BeanUtils.structToDB(struct, ProfileLanguageRecord.class);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(ProfileLanguage.PROFILE_LANGUAGE.ID.getName(), originLanguageRecord.getId());

        ProfileLanguageRecord descLanguageRecord = dao.getRecord(queryBuilder.buildQuery());
        if (descLanguageRecord != null) {
            RecordTool.recordToRecord(descLanguageRecord, originLanguageRecord);
            ValidationMessage<ProfileLanguageRecord> validationMessage = ProfileValidation.verifyLanguage(descLanguageRecord);
            if (validationMessage.isPass()) {
                int result = dao.updateRecord(descLanguageRecord);
                if (result > 0) {
                    updateUpdateTime(struct);
                    profileEntity.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
                    return ResponseUtils.success("1");
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", validationMessage.getResult()));
            }

        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResource(Language struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileLanguageRecord language = null;
        language = dao.getRecord(qu);
        if (language != null) {
            int result = dao.deleteRecord(language);

            if (result > 0) {
                updateUpdateTime(struct);
                profileEntity.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }


    protected Language DBToStruct(ProfileLanguageRecord r) {
        return BeanUtils.DBToStruct(Language.class, r);
    }


    protected ProfileLanguageRecord structToDB(Language language) throws ParseException {
        return BeanUtils.structToDB(language, ProfileLanguageRecord.class);
    }

    private void updateUpdateTime(List<Language> languages) {
        Set<Integer> languageIds = new HashSet<>();
        languages.forEach(language -> {
            languageIds.add(language.getId());
        });
        dao.updateProfileUpdateTime(languageIds);
    }

    private void updateUpdateTime(Language language) {
        List<Language> languages = new ArrayList<>();
        languages.add(language);
        updateUpdateTime(languages);
    }

    private void updateProfileUpdateTime(List<ProfileLanguageRecord> descLanguageRecordList) {
        Set<Integer> languageIds = descLanguageRecordList.stream()
                .map(profileLanguageRecord -> profileLanguageRecord.getId())
                .collect(Collectors.toSet());
        dao.updateProfileUpdateTime(languageIds);
        languageIds.forEach(profileId ->{
            profileCompanyTagService.handlerCompanyTag(profileId);
        });
    }

    public Response getResource(Query query) throws TException {
        Language language = dao.getData(query, Language.class);
        if (language != null) {
            return ResponseUtils.success(language);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getResources(Query query) throws TException {
        List<Awards> datas = dao.getDatas(query, Awards.class);

        if (datas != null && datas.size() > 0) {
            return ResponseUtils.success(datas);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ResponseUtils.success(ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
    }
}
