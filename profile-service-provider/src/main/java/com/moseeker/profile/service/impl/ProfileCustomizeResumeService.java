package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
import com.moseeker.thrift.gen.profile.struct.Profile;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CounterIface
public class ProfileCustomizeResumeService {

    Logger logger = LoggerFactory.getLogger(ProfileCustomizeResumeService.class);

    @Autowired
    private ProfileOtherDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    public CustomizeResume getResource(Query query) throws TException {
        return dao.getData(query, CustomizeResume.class);
    }

    public List<CustomizeResume> getResources(Query query) throws TException {
        return dao.getDatas(query, CustomizeResume.class);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
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

            return BeanUtils.DBToStruct(CustomizeResume.class, records);
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public int[] putResources(List<CustomizeResume> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileOtherRecord.class));

            List<CustomizeResume> updatedDatas = new ArrayList<>();

            for (int i : result) {
                if (i > 0) updatedDatas.add(structs.get(i));
            }

            updateUpdateTime(updatedDatas);
            return result;
        } else {
            return new int[0];
        }
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
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet()));
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
    public CustomizeResume postResource(CustomizeResume struct) throws TException {
        CustomizeResume result = null;
        if (struct != null) {
            ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(struct);
            if (!vm.isPass()) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
            }

            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileOtherRecord repeat = dao.getRecord(qu);
            if (repeat != null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
            }
            result = dao.addRecord(BeanUtils.structToDB(struct, ProfileOtherRecord.class)).into(CustomizeResume.class);
            updateUpdateTime(result);
        }
        return result;
    }

    @Transactional
    public int putResource(CustomizeResume struct) throws TException {
        int result = 0;
        if (struct != null) {
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
    }

    private void updateUpdateTime(List<CustomizeResume> customizeResumes) {
        if (customizeResumes == null || customizeResumes.size() == 0) return;

        HashSet<Integer> profileIds = new HashSet<>();

        customizeResumes.forEach(customizeResume -> {
            profileIds.add(customizeResume.getProfile_id());
        });
        profileDao.updateUpdateTime(profileIds);
    }
}
