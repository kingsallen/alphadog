package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileImportDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileImportDO;
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

    public ProfileImportDO getResource(Query query) throws TException {
        return dao.getData(query);
    }

    public List<ProfileImportDO> getResources(Query query) throws TException {
        return dao.getDatas(query);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }

    @Transactional
    public List<ProfileImportDO> postResources(List<ProfileImportDO> structs) throws TException {
        List<ProfileImportDO> resultDatas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {
            resultDatas = dao.addAllData(structs);

            updateUpdateTime(resultDatas);
        }
        return resultDatas;
    }

    @Transactional
    public int[] putResources(List<ProfileImportDO> structs) throws TException {
        int[] result = new int[0];
        if (structs != null && structs.size() > 0) {
            result = dao.updateDatas(structs);

            List<ProfileImportDO> updatedDatas = new ArrayList<>();

            for (int i : result) {
                if (i > 0) updatedDatas.add(structs.get(i));
            }

            updateUpdateTime(updatedDatas);
        }

        return result;
    }

    @Transactional
    public int[] delResources(List<ProfileImportDO> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id",
                            structs.stream()
                                    .map(struct -> struct.getProfileId())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileImportDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteDatas(structs);

            if (deleteDatas != null && deleteDatas.size() > 0) {
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet()));
            }
            return result;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public int delResource(ProfileImportDO struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("profile_id", struct.getProfileId())).buildQuery();
            //找到要删除的数据
            ProfileImportDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(struct);
                if (result > 0) {
                    updateUpdateTime(deleteData);
                }
            }
        }

        return result;
    }

    @Transactional
    public ProfileImportDO postResource(ProfileImportDO struct) throws TException {
        ProfileImportDO result = null;
        if(struct != null) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfileId()));
            ProfileImportDO repeat = dao.getData(qu);
            if (repeat != null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
            }
            result = dao.addData(struct);
            updateUpdateTime(result);
        }
        return result;
    }

    @Transactional
    public int putResource(ProfileImportDO struct) throws TException {
        int result = 0;
        if(struct != null){
            result = dao.updateData(struct);
            if(result > 0){
                updateUpdateTime(struct);
            }
        }
        return result;
    }


    private void updateUpdateTime(ProfileImportDO profileImport) {

        if (profileImport == null) return;

        List<ProfileImportDO> profileImports = new ArrayList<>();

        profileImports.add(profileImport);
        updateUpdateTime(profileImports);
    }

    private void updateUpdateTime(List<ProfileImportDO> profileImports) {

        if (profileImports == null || profileImports.size() == 0) return;

        HashSet<Integer> profileIds = new HashSet<>();

        profileImports.forEach(profileImport -> {
            profileIds.add(profileImport.getProfileId());
        });

        profileDao.updateUpdateTime(profileIds);
    }
}
