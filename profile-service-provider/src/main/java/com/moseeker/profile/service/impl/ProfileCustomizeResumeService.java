package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfileUtils;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
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
public class ProfileCustomizeResumeService {

    Logger logger = LoggerFactory.getLogger(ProfileCustomizeResumeService.class);

    @Autowired
    private ProfileOtherDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    ProfileParseUtil profileParseUtil;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;



    public CustomizeResume getResource(Query query) throws TException {
        CustomizeResume data= dao.getData(query, CustomizeResume.class);
        profileParseUtil.handerSortCustomizeResumeOther(data);
        return data;
    }

    public List<CustomizeResume> getResources(Query query) throws TException {
        List<CustomizeResume> list= dao.getDatas(query, CustomizeResume.class);
        profileParseUtil.handerSortCustomizeResumeOtherList(list);
        return list;
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }

    @Transactional
    public List<CustomizeResume> postResources(List<CustomizeResume> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Iterator<CustomizeResume> icr = structs.iterator();
            while (icr.hasNext()) {
                CustomizeResume cr = icr.next();
                ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(cr);
                if (!vm.isPass()) {
                    icr.remove();
                }
            }
        }
        if (structs != null && structs.size() > 0) {

            List<ProfileOtherRecord> records = BeanUtils.structToDB(structs, ProfileOtherRecord.class);

            records = dao.addAllRecord(records);
            Set<Integer> profileIds = records.stream().map(m -> m.getProfileId()).collect(Collectors.toSet());
            profileDao.updateUpdateTime(profileIds);
            profileIds.forEach(profileId -> {
                profileCompanyTagService.handlerCompanyTag(profileId);
            });
            return BeanUtils.DBToStruct(CustomizeResume.class, records);
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public int[] putResources(List<CustomizeResume> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            List<ProfileOtherRecord> origOtherRecordList = BeanUtils.structToDB(structs, ProfileOtherRecord.class);
            List<Integer> profileIdList = structs.stream().map(struct -> struct.getProfile_id()).collect(Collectors.toList());
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition(ProfileOther.PROFILE_OTHER.PROFILE_ID.getName(), profileIdList, ValueOp.IN));

            List<ProfileOtherRecord> descOtherRecordList = dao.getRecords(queryBuilder.buildQuery());
            if (descOtherRecordList != null) {
                Iterator<ProfileOtherRecord> iterator = descOtherRecordList.iterator();
                while (iterator.hasNext()) {
                    ProfileOtherRecord profileOtherRecord = iterator.next();
                    Optional<ProfileOtherRecord> optional = origOtherRecordList
                            .stream()
                            .filter(origOtherRecord -> origOtherRecord.getProfileId().intValue() == profileOtherRecord.getProfileId())
                            .findAny();
                    if (optional.isPresent()) {
                        RecordTool.recordToRecord(profileOtherRecord, optional.get());
                        ValidationMessage<ProfileOtherRecord> validationMessage = ProfileValidation.verifyCustomizeResume(profileOtherRecord);
                        if (!validationMessage.isPass()) {
                            iterator.remove();
                        }
                    }
                }
                if (descOtherRecordList.size() > 0) {
                    int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileOtherRecord.class));
                    updateProfileUpdateTime(descOtherRecordList);
                    return result;
                }
            }
        }
        return new int[0];
    }

    @Transactional
    public int[] delResources(List<CustomizeResume> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id",
                            structs.stream()
                                    .map(struct -> struct.getProfile_id())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileOtherDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileOtherRecord.class));

            if (deleteDatas != null && deleteDatas.size() > 0) {
                Set<Integer> profileIds = deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet());
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(profileIds);
                profileIds.forEach(profileId ->{
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
            }
            return result;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int delResource(CustomizeResume struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id", struct.getProfile_id())).buildQuery();
            //找到要删除的数据
            ProfileOtherDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(deleteData);
                if (result > 0) {
                    updateUpdateTime(struct);
                }
            }
        }

        return result;
    }

    @Transactional
    public CustomizeResume postResource(CustomizeResume struct) throws CommonException {
        CustomizeResume result = null;
        if (struct != null) {
            ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(struct);
            if (!vm.isPass()) {
                throw ProfileException.validateFailed(vm.getResult());
            }
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(ProfileOther.PROFILE_OTHER.PROFILE_ID.getName(), struct.getProfile_id());
            ProfileOtherRecord repeat = dao.getRecord(queryBuilder.buildQuery());
            if (repeat != null) {
                throw ProfileException.PROFILE_REPEAT_DATA;
            }
            result = dao.addRecord(BeanUtils.structToDB(struct, ProfileOtherRecord.class)).into(CustomizeResume.class);
            updateUpdateTime(result);
        }
        return result;
    }

    @Transactional
    public int putResource(CustomizeResume struct) throws CommonException {
        int result = 0;
        if (struct != null) {
            ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(struct);
            if (!vm.isPass()) {
                throw ProfileException.validateFailed(vm.getResult());
            }
            result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileOtherRecord.class));
            if (result > 0) {
                updateUpdateTime(struct);
            }
        }
        return result;
    }

    private void updateUpdateTime(CustomizeResume customizeResume) {
        if (customizeResume == null) return;

        List<CustomizeResume> customizeResumes = new ArrayList<>(1);

        customizeResumes.add(customizeResume);
        updateUpdateTime(customizeResumes);
        profileCompanyTagService.handlerCompanyTag(customizeResume.getProfile_id());
    }

    private void updateUpdateTime(List<CustomizeResume> customizeResumes) {
        if (customizeResumes == null || customizeResumes.size() == 0)
            return;

        HashSet<Integer> profileIds = new HashSet<>();

        customizeResumes.forEach(customizeResume -> {
            profileIds.add(customizeResume.getProfile_id());
        });
        profileDao.updateUpdateTime(profileIds);
    }

    private void updateProfileUpdateTime(List<ProfileOtherRecord> descOtherRecordList) {
        if (descOtherRecordList == null || descOtherRecordList.size() == 0) {
            return;
        }
        Set<Integer> profileIdSet = descOtherRecordList
                .stream()
                .map(profileOtherRecord -> profileOtherRecord.getProfileId())
                .collect(Collectors.toSet());
        profileIdSet.forEach(profileId ->{
            profileCompanyTagService.handlerCompanyTag(profileId);
        });
        profileDao.updateUpdateTime(profileIdSet);
    }
}
