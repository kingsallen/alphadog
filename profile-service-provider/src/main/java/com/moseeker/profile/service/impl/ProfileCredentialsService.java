package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileCredentialsDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCredentials;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCredentialsDO;
import com.moseeker.thrift.gen.profile.struct.Credentials;
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
public class ProfileCredentialsService {

    Logger logger = LoggerFactory.getLogger(ProfileCredentialsService.class);

    @Autowired
    private ProfileCredentialsDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;


    public Credentials getResource(Query query) throws TException {
        return dao.getData(query, Credentials.class);
    }

    public List<Credentials> getResources(Query query) throws TException {
        return dao.getDatas(query, Credentials.class);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }

    @Transactional
    public List<Credentials> postResources(List<Credentials> structs) throws TException {

        List<Credentials> resultDatas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {
            Iterator<Credentials> ic = structs.iterator();
            while (ic.hasNext()) {
                Credentials credential = ic.next();
                ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(credential);
                if (!vm.isPass()) {
                    ic.remove();
                }
            }
            List<ProfileCredentialsRecord> records = BeanUtils.structToDB(structs, ProfileCredentialsRecord.class);
            records = dao.addAllRecord(records);
            resultDatas = BeanUtils.DBToStruct(Credentials.class, records);
        /* 重新计算profile完整度 */
            Set<Integer> profileIds = new HashSet<>();
            structs.forEach(struct -> {
                if (struct.getProfile_id() > 0)
                    profileIds.add(struct.getProfile_id());
            });
            profileDao.updateUpdateTime(profileIds);

            profileIds.forEach(profileId -> {
                profileEntity.recalculateProfileCredential(profileId, 0);
                profileCompanyTagService.handlerCompanyTag(profileId);
            });
        }
        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<Credentials> structs) throws TException {
        if (structs != null && structs.size() > 0) {

            List<ProfileCredentialsRecord> recordList = BeanUtils.structToDB(structs, ProfileCredentialsRecord.class);
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(
                    new Condition(ProfileCredentials.PROFILE_CREDENTIALS.ID.getName(),
                            recordList.stream().map(profileCredentialsRecord -> profileCredentialsRecord.getId())
                                    .collect(Collectors.toList())));

            List<ProfileCredentialsRecord> profileCredentialsRecordList = dao.getRecords(queryBuilder.buildQuery());
            if (profileCredentialsRecordList != null && profileCredentialsRecordList.size() > 0) {
                profileCredentialsRecordList = profileCredentialsRecordList.stream().filter(profileCredentialsRecord -> {
                    Optional<ProfileCredentialsRecord> profileCredentialsRecordOptional =
                            recordList.stream().filter(record ->
                                    profileCredentialsRecord.getId().intValue() == record.getId().intValue())
                                    .findAny();
                    if (profileCredentialsRecordOptional.isPresent()) {
                        RecordTool.recordToRecord(profileCredentialsRecord, profileCredentialsRecordOptional.get());
                        ValidationMessage<ProfileCredentialsRecord> validationMessage = ProfileValidation.verifyCredential(profileCredentialsRecord);
                        if (validationMessage.isPass()) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());

            }

            int[] updateResult = dao.updateRecords(profileCredentialsRecordList);
            /* 计算profile完整度 */
            List<Credentials> updatedDatas = new ArrayList<>();
            Set<Integer> profileIds = new HashSet<>();
            for (int i = 0; i < updateResult.length; i++) {
                if (updateResult[i] > 0) {
                    updatedDatas.add(structs.get(i));
                    profileIds.add(structs.get(i).getProfile_id());
                }
            }
            updateUpdateTime(updatedDatas);
            profileIds.forEach(profileId ->{
                profileCompanyTagService.handlerCompanyTag(profileId);
            });
            updatedDatas.forEach(data -> {
                profileEntity.recalculateProfileCredential(data.getProfile_id(), data.getId());
            });
            return updateResult;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int[] delResources(List<Credentials> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id",
                            structs.stream()
                                    .map(struct -> struct.getId())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileCredentialsDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileCredentialsRecord.class));

            if (deleteDatas != null && deleteDatas.size() > 0) {
                //更新对应的profile更新时间
                Set<Integer> profileIds = deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet());
                profileDao.updateUpdateTime(profileIds);
                profileIds.forEach(profileId ->{
                    profileEntity.recalculateProfileCredential(profileId, 0);
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });

            }
            return result;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public Credentials postResource(Credentials struct) throws CommonException {
        Credentials result = null;
        if (struct != null) {
            ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(struct);
            if (!vm.isPass()) {
                throw ProfileException.validateFailed(vm.getResult());
            }
            result = dao.addRecord(BeanUtils.structToDB(struct, ProfileCredentialsRecord.class)).into(Credentials.class);
        /* 计算profile完整度 */
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(result.getProfile_id());
            profileDao.updateUpdateTime(profileIds);
            profileCompanyTagService.handlerCompanyTag(result.getProfile_id());
            profileEntity.recalculateProfileCredential(result.getProfile_id(), result.getId());
        }
        return result;
    }

    @Transactional
    public int putResource(Credentials struct) throws CommonException {
        int result = 0;

        if (struct != null) {

            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(ProfileCredentials.PROFILE_CREDENTIALS.ID.getName(), struct.getId());
            ProfileCredentialsRecord record = dao.getRecord(queryBuilder.buildQuery());

            ProfileCredentialsRecord origin = BeanUtils.structToDB(struct, ProfileCredentialsRecord.class);

            if (record != null) {
                RecordTool.recordToRecord(record, origin);
                ValidationMessage<ProfileCredentialsRecord> validationMessage = ProfileValidation.verifyCredential(record);
                if (validationMessage.isPass()) {
                    result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileCredentialsRecord.class));
                } else {
                    throw ProfileException.validateFailed(validationMessage.getResult());
                }
            }
        /* 计算profile完整度 */
            if (result > 0) {
                updateUpdateTime(struct);
                profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
            }
        }
        return result;
    }

    @Transactional
    public int delResource(Credentials struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id", struct.getId())).buildQuery();
            //找到要删除的数据
            ProfileCredentialsDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(deleteData);
                if (result > 0) {
                    profileDao.updateUpdateTime(new HashSet<Integer>() {{
                        add(deleteData.getProfileId());
                    }});
                    profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
                    profileEntity.recalculateProfileCredential(deleteData.getProfileId(), 0);
                }
            }
        }

        return result;
    }

    private void updateUpdateTime(List<Credentials> credentials) {

        if (credentials == null || credentials.size() == 0) return;

        Set<Integer> credentialIds = new HashSet<>();

        credentials.forEach(Credential -> {
            credentialIds.add(Credential.getId());
        });
        dao.updateProfileUpdateTime(credentialIds);
    }

    private void updateUpdateTime(Credentials credential) {

        if (credential == null) return;

        List<Credentials> credentials = new ArrayList<>();
        credentials.add(credential);
        updateUpdateTime(credentials);
    }

}
