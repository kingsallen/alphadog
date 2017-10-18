package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileAwardsDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAwardsDO;
import com.moseeker.thrift.gen.profile.struct.Awards;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CounterIface
public class ProfileAwardsService {

    Logger logger = LoggerFactory.getLogger(ProfileAwardsService.class);

    @Autowired
    private ProfileAwardsDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    public Awards getResource(Query query) throws TException {
        return dao.getData(query, Awards.class);
    }

    public List<Awards> getResources(Query query) throws TException {
        return dao.getDatas(query, Awards.class);
    }

    @Transactional
    public List<Awards> postResources(List<Awards> structs) throws TException {

        List<Awards> datas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {

            List<ProfileAwardsRecord> records = BeanUtils.structToDB(structs, ProfileAwardsRecord.class);

            records = dao.addAllRecord(records);

            datas = BeanUtils.DBToStruct(Awards.class, records);

        /* 计算profile完成度 */
            Set<Integer> profileIds = new HashSet<>();

            structs.forEach(struct -> {
                profileIds.add(struct.getProfile_id());
            });

            profileDao.updateUpdateTime(profileIds);

            profileIds.forEach(profileId -> {
                profileEntity.reCalculateProfileAward(profileId, 0);
            });
        }
        return datas;
    }

    @Transactional
    public int[] putResources(List<Awards> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            int[] updateResult = dao.updateRecords(BeanUtils.structToDB(structs, ProfileAwardsRecord.class));
        /* 计算profile完成度 */

            List<Awards> updatedList = new ArrayList<>();

            for (int i = 0; i < updateResult.length; i++) {
                if (updateResult[i] > 0) updatedList.add(structs.get(i));
            }

            updateUpdateTime(updatedList);

            updatedList.forEach(struct -> {
                profileEntity.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
            });

            return updateResult;
        } else {
            return new int[0];
        }
    }

    @Transactional
    public Awards postResource(Awards struct) throws TException {

        Awards data = null;

        if (struct != null) {

            ProfileAwardsRecord record = BeanUtils.structToDB(struct, ProfileAwardsRecord.class);

            data = dao.addRecord(record).into(Awards.class);
        /* 计算profile完成度 */

            Set<Integer> profileIds = new HashSet<>();

            profileIds.add(struct.getProfile_id());

            profileDao.updateUpdateTime(profileIds);

            profileEntity.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
        }
        return data;
    }

    @Transactional
    public int putResource(Awards struct) throws TException {

        int updateResult = 0;

        if (struct != null) {

            updateResult = dao.updateRecord(BeanUtils.structToDB(struct, ProfileAwardsRecord.class));


        /* 计算profile完成度 */
            if (updateResult > 0) {
                updateUpdateTime(struct);
                profileEntity.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
            }
        }
        return updateResult;
    }

    @Transactional
    public int delResource(Awards struct) throws TException {
        int result = 0;
        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id", struct.getId())).buildQuery();
            //找到要删除的数据
            ProfileAwardsDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(deleteData);
                if (result > 0) {
                    //更新对应的profile更新时间
                    profileDao.updateUpdateTime(new HashSet<Integer>() {{
                        add(deleteData.getProfileId());
                    }});
            /* 计算profile完成度 */
                    profileEntity.reCalculateProfileAward(deleteData.getProfileId(), struct.getId());
                }
            }
        }

        return result;
    }

    @Transactional
    public int[] delResources(List<Awards> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id",
                            structs.stream()
                                    .map(struct -> struct.getId())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileAwardsDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileAwardsRecord.class));


            if (deleteDatas != null && deleteDatas.size() > 0) {
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet()));

                for (ProfileAwardsDO data : deleteDatas) {
                    profileEntity.reCalculateProfileAward(data.getProfileId(), 0);
                }

            }

            return result;
        } else {
            return new int[0];
        }
    }

    private void updateUpdateTime(List<Awards> awards) {

        if (awards == null || awards.size() == 0) return;

        HashSet<Integer> awardIds = new HashSet<>();

        awards.forEach(award -> {
            awardIds.add(award.getId());
            logger.error("--------");
            logger.error("-----award.getId():" + award.getId() + "-------");
        });
        dao.updateProfileUpdateTime(awardIds);
    }

    private void updateUpdateTime(Awards award) {

        if (award == null) return;

        List<Awards> awards = new ArrayList<>();
        awards.add(award);
        updateUpdateTime(awards);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }
}
