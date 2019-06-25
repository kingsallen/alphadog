package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileAttachmentDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.commonservice.annotation.iface.CompanyTagUpate;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
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

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

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
                    boolean bool = profileIds.add(attachement.getProfile_id());
                    if(bool){
                        this.handlerCompanyTag(attachement.getProfile_id());
                    }
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
                    this.handlerCompanyTag(deleteData.getProfileId());
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
                Set<Integer> set = deleteDatas.stream().map(data -> data.getProfileId()).collect(Collectors.toSet());
                //更新对应的profile更新时间
                profileDao.updateUpdateTime(set);
                set.forEach(profile_id -> {
                    this.handlerCompanyTag(profile_id);
                });

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
            this.handlerCompanyTag(data.getProfileId());
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
                this.handlerCompanyTag(struct.getProfile_id());
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
        attachmentIds.forEach(profileId -> {
            this.handlerCompanyTag(profileId);
        });
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

        return ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas);
    }
    @CounterIface
    public Response delPcAttachment(int id){
        Query query1=new Query.QueryBuilder().where("id",id).buildQuery();
        ProfileAttachmentDO attachmentDO=dao.getData(query1);
        if(attachmentDO==null){
            return ResponseUtils.fail(1,"附件不存在");
        }
        int profileId=attachmentDO.getProfileId();
        Query query=new Query.QueryBuilder().where("id",profileId).and("disable",1).buildQuery();
        ProfileProfileDO profileDO=profileDao.getData(query);
        if(profileDO==null){
            return ResponseUtils.fail(1,"简历不存在");
        }
        //删除附件时，去除简历完整度小于70的校验
        /*int completeness=profileDO.getCompleteness();
        if(completeness<70){
            return ResponseUtils.fail(1,"简历完整度小于70");
        }*/
        int result=dao.delAttachmentsByProfileId(profileId);
        logger.info("del attachments  result=============="+result);
        if(result==0){
            return ResponseUtils.fail(1,"简历附件删除失败");
        }
        return ResponseUtils.success(result);
    }

    private void  handlerCompanyTag(int profileId){
        profileCompanyTagService.handlerProfileCompanyTag(profileId,0);
    }
}
