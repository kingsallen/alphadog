package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileImportDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileImportDO;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import java.util.Set;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@CounterIface
public class ProfileImportService {

    Logger logger = LoggerFactory.getLogger(ProfileImportService.class);

    @Autowired
    private ProfileImportDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    public ProfileImport getResource(Query query) throws TException {
        return dao.getData(query, ProfileImport.class);
    }

    public List<ProfileImport> getResources(Query query) throws TException {
        return dao.getDatas(query, ProfileImport.class);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }

    @Transactional
    public List<ProfileImport> postResources(List<ProfileImport> structs) throws TException {
        List<ProfileImport> resultDatas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {

            List<ProfileImportRecord> records = BeanUtils.structToDB(structs, ProfileImportRecord.class);

            records = dao.addAllRecord(records);

            resultDatas = BeanUtils.DBToStruct(ProfileImport.class, records);

            updateUpdateTime(resultDatas);
        }
        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<ProfileImport> structs) throws TException {
        int[] result = new int[0];
        if (structs != null && structs.size() > 0) {
            result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileImportRecord.class));

            List<ProfileImport> updatedDatas = new ArrayList<>();

            for (int i = 0; i < result.length; i++) {
                if (result[i] > 0) updatedDatas.add(structs.get(i));
            }

            updateUpdateTime(updatedDatas);
        }

        return result;
    }

    @Transactional
    public int[] delResources(List<ProfileImport> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id",
                            structs.stream()
                                    .map(struct -> struct.getProfile_id())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileImportDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileImportRecord.class));

            if (deleteDatas != null && deleteDatas.size() > 0) {
                Set<Integer> ProfileIds = deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet());
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(ProfileIds);
                ProfileIds.forEach(profileId -> {
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
            }
            return result;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int delResource(ProfileImport struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id", struct.getProfile_id())).buildQuery();
            //找到要删除的数据
            ProfileImportRecord deleteData = dao.getRecord(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteRecord(deleteData);
                if (result > 0) {
                    updateUpdateTime(struct);
                }
            }
        }

        return result;
    }

    @Transactional
    public ProfileImport postResource(ProfileImport struct) throws TException {
        ProfileImport result = null;
        if (struct != null) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileImport repeat = dao.getData(qu, ProfileImport.class);
            if (repeat != null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
            }
            result = dao.addRecord(BeanUtils.structToDB(struct, ProfileImportRecord.class)).into(ProfileImport.class);
            updateUpdateTime(result);
        }
        return result;
    }

    @Transactional
    public int putResource(ProfileImport struct) throws TException {
        int result = 0;
        if (struct != null) {
            result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileImportRecord.class));
            if (result > 0) {
                updateUpdateTime(struct);
            }
        }
        return result;
    }


    private void updateUpdateTime(ProfileImport profileImport) {

        if (profileImport == null) return;

        List<ProfileImport> profileImports = new ArrayList<>();

        profileImports.add(profileImport);
        updateUpdateTime(profileImports);
    }

    private void updateUpdateTime(List<ProfileImport> profileImports) {

        if (profileImports == null || profileImports.size() == 0) return;

        HashSet<Integer> profileIds = new HashSet<>();

        profileImports.forEach(profileImport -> {
            profileIds.add(profileImport.getProfile_id());
        });

        profileDao.updateUpdateTime(profileIds);
        profileIds.forEach(profileId ->{
            profileCompanyTagService.handlerCompanyTag(profileId);
        });
    }
}
