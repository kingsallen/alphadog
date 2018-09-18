package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileAwardsDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.baseorm.tool.RecordTool;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.commonservice.annotation.iface.CompanyTagUpate;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAwardsDO;
import com.moseeker.thrift.gen.profile.struct.Awards;
import com.moseeker.thrift.gen.profile.struct.Profile;
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
public class ProfileAwardsService {

    Logger logger = LoggerFactory.getLogger(ProfileAwardsService.class);

    @Autowired
    private ProfileAwardsDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;


    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

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

            /** 对参数做校验，如果校验不通过，不予添加 */
            Iterator<Awards> iterator = structs.iterator();
            while (iterator.hasNext()) {
                Awards awards = iterator.next();
                ValidationMessage<Awards> validationMessage = ProfileValidation.verifyAward(awards);
                if (!validationMessage.isPass()) {
                    iterator.remove();
                }
            }
            if (structs.size() > 0) {
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
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
            }
        }
        return datas;
    }

    @Transactional
    public int[] putResources(List<Awards> structs) throws TException {
        if (structs != null && structs.size() > 0) {

            List<Integer> idList = structs.stream().map(awards -> awards.getId()).collect(Collectors.toList());

            /** 参数和数据中的数据合并，并对合并后结果做数据校验。如果检验不通过，不予修改 */
            List<ProfileAwardsRecord> awardsRecordList = dao.fetchByIdList(idList);
            if (awardsRecordList.size() > 0) {
                List<ProfileAwardsRecord> params = BeanUtils.structToDB(structs, ProfileAwardsRecord.class);
                Iterator<ProfileAwardsRecord> iterator = awardsRecordList.iterator();
                while (iterator.hasNext()) {
                    ProfileAwardsRecord awardsRecord = iterator.next();
                    Optional<ProfileAwardsRecord> awardsRecordOptional = params.
                            stream()
                            .filter(param -> param.getId().intValue() == awardsRecord.getId().intValue())
                            .findAny();
                    if (awardsRecordOptional.isPresent()) {
                        RecordTool.recordToRecord(awardsRecord, awardsRecordOptional.get());
                        ValidationMessage<ProfileAwardsRecord> validationMessage = ProfileValidation.verifyAward(awardsRecord);
                        if (!validationMessage.isPass()) {
                            iterator.remove();
                        }
                    } else {
                        iterator.remove();
                    }
                }

                int[] updateResult = dao.updateRecords(awardsRecordList);

                /* 计算profile完成度 */
                List<Awards> updatedList = new ArrayList<>();

                for (int i = 0; i < updateResult.length; i++) {
                    if (updateResult[i] > 0) updatedList.add(structs.get(i));
                }

                updateUpdateTime(updatedList);

                updatedList.forEach(struct -> {
                    profileEntity.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
                    profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
                });

                return updateResult;
            }
        }
        return new int[0];
    }

    @Transactional
    public Awards postResource(Awards struct) throws CommonException {

        Awards data = null;

        ValidationMessage<Awards> validationMessage = ProfileValidation.verifyAward(struct);
        if (!validationMessage.isPass()) {
            throw ProfileException.validateFailed(validationMessage.getResult());
        }
        if (struct != null) {

            ProfileAwardsRecord record = BeanUtils.structToDB(struct, ProfileAwardsRecord.class);

            data = dao.addRecord(record).into(Awards.class);
        /* 计算profile完成度 */

            Set<Integer> profileIds = new HashSet<>();

            profileIds.add(struct.getProfile_id());

            profileDao.updateUpdateTime(profileIds);

            profileEntity.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
            profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
        }
        return data;
    }

    @Transactional
    public int putResource(Awards struct) throws TException {

        int updateResult = 0;

        if (struct != null) {

            /** 将参数和数据库中的数据合并之后，对数据做校验，如果数据校验不通过，不让修改 */
            ProfileAwardsRecord profileAwardsRecord = dao.fetchById(struct.getId());
            if (profileAwardsRecord != null) {
                ProfileAwardsRecord param = BeanUtils.structToDB(struct, ProfileAwardsRecord.class);
                RecordTool.recordToRecord(profileAwardsRecord, param);
                ValidationMessage<ProfileAwardsRecord> validationMessage = ProfileValidation.verifyAward(profileAwardsRecord);
                if (validationMessage.isPass()) {
                    updateResult = dao.updateRecord(BeanUtils.structToDB(struct, ProfileAwardsRecord.class));

                    /* 计算profile完成度 */
                    if (updateResult > 0) {
                        updateUpdateTime(struct);
                        profileEntity.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
                        profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
                    }
                }
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
                    profileCompanyTagService.handlerCompanyTag(deleteData.getProfileId());
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
                Set<Integer> set = deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet());
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(set);

                set.forEach(profile_id -> {
                    profileEntity.reCalculateProfileAward(profile_id, 0);
                    profileCompanyTagService.handlerCompanyTag(profile_id);
                });

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
            logger.info("--------");
            logger.info("-----award.getId():" + award.getId() + "-------");
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
