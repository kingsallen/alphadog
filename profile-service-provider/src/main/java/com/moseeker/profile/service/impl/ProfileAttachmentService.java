package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileAttachmentDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import com.moseeker.thrift.gen.profile.struct.Attachment;
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
public class ProfileAttachmentService {

    Logger logger = LoggerFactory.getLogger(ProfileAttachmentService.class);

    @Autowired
    ProfileAttachmentDao dao;

    @Autowired
    ProfileProfileDao profileDao;

    public Attachment getResource(Query query) throws TException {
        return dao.getData(query, Attachment.class);
    }

    public List<Attachment> getResources(Query query) throws TException {
        return dao.getDatas(query, Attachment.class);
    }

    @Transactional
    public List<Attachment> postResources(List<Attachment> structs) throws TException {
        List<Attachment> datas = new ArrayList<>();

        if (structs != null && structs.size() > 0) {

            List<ProfileAttachmentRecord> records = BeanUtils.structToDB(structs, ProfileAttachmentRecord.class);

            records = dao.addAllRecord(records);

            datas = BeanUtils.DBToStruct(Attachment.class, records);

            Set<Integer> profileIds = new HashSet<>();
            for (Attachment attachement : structs) {
                if (attachement.getProfile_id() > 0) {
                    profileIds.add(attachement.getProfile_id());
                }
            }
            profileDao.updateUpdateTime(profileIds);
        }
        return datas;
    }

    @Transactional
    public int[] putResources(List<Attachment> structs) throws TException {

        if (structs != null && structs.size() > 0) {
            int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileAttachmentRecord.class));

            List<Attachment> updatedDatas = new ArrayList<>();

            for (int i = 0; i < result.length; i++) {
                if (result[i] > 0) updatedDatas.add(structs.get(i));
            }

            updateUpdateTime(updatedDatas);
            return result;
        } else {
            return new int[0];
        }

    }

    @Transactional
    public int delResource(Attachment struct) throws TException {

        int result = 0;

        if (struct != null) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id", struct.getId())).buildQuery();
            //找到要删除的数据
            ProfileAttachmentDO deleteData = dao.getData(query);
            if (deleteData != null) {
                //正式删除数据
                result = dao.deleteData(deleteData);
                if (result > 0) {
                    //更新对应的profile更新时间
                    profileDao.updateUpdateTime(new HashSet<Integer>() {{
                        add(deleteData.getProfileId());
                    }});
                }
            }
        }
        return result;
    }

    @Transactional
    public int[] delResources(List<Attachment> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Query query = new Query
                    .QueryBuilder()
                    .where(Condition.buildCommonCondition("id",
                            structs.stream()
                                    .map(struct -> struct.getId())
                                    .collect(Collectors.toList()),
                            ValueOp.IN)).buildQuery();
            //找到要删除的数据
            List<ProfileAttachmentDO> deleteDatas = dao.getDatas(query);

            //正式删除数据
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileAttachmentRecord.class));

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
    public Attachment postResource(Attachment struct) throws TException {
        if (struct != null) {
            ProfileAttachmentRecord data = dao.addRecord(BeanUtils.structToDB(struct, ProfileAttachmentRecord.class));
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(data.getProfileId());
            profileDao.updateUpdateTime(profileIds);
            return data.into(Attachment.class);
        } else {
            return null;
        }
    }

    @Transactional
    public int putResource(Attachment struct) throws TException {

        int i = 0;
        if (struct != null) {
            i = dao.updateRecord(BeanUtils.structToDB(struct, ProfileAttachmentRecord.class));
            if (i > 0) {
                updateUpdateTime(struct);
            }
        }
        return i;
    }

    private void updateUpdateTime(List<Attachment> attachments) {

        if (attachments == null || attachments.size() == 0) return;

        HashSet<Integer> attachmentIds = new HashSet<>();

        attachments.forEach(attachment -> {
            attachmentIds.add(attachment.getId());
        });
        dao.updateProfileUpdateTime(attachmentIds);
    }

    private void updateUpdateTime(Attachment attachment) {

        if (attachment == null) return;

        List<Attachment> attachments = new ArrayList<>();

        attachments.add(attachment);
        updateUpdateTime(attachments);
    }

    public Pagination getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ProfileUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }
}
