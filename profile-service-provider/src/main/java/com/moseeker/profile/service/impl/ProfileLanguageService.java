package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileLanguageDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Query;
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

        profileIds.forEach(profileId -> {
            //计算profile完整度
            profileEntity.recalculateprofileLanguage(profileId, 0);
        });

        profileDao.updateUpdateTime(profileIds);
        return ResponseUtils.success("1");
    }

    @Transactional
    public Response putResources(List<Language> structs) throws TException {
        int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileLanguageRecord.class));
        if (ArrayUtils.contains(result, 1)) {
            updateUpdateTime(structs);
            structs.forEach(struct -> {
                //计算profile完整度
                profileEntity.recalculateprofileLanguage(0, struct.getId());
            });
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
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
    public Response postResource(Language struct) throws TException {
        ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        ProfileLanguageRecord record = dao.addRecord(BeanUtils.structToDB(struct, ProfileLanguageRecord.class));

        Set<Integer> profileIds = new HashSet<>();
        profileIds.add(struct.getProfile_id());
        profileDao.updateUpdateTime(profileIds);

        profileEntity.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
        return ResponseUtils.success(String.valueOf(record.getId()));
    }

    @Transactional
    public Response putResource(Language struct) throws TException {
        int result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileLanguageRecord.class));
        if (result > 0) {
            updateUpdateTime(struct);
            profileEntity.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
            return ResponseUtils.success("1");
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
