package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import com.moseeker.thrift.gen.profile.struct.Attachment;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;

/**
* @author xxx
* ProfileAttachmentDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileAttachmentDao extends JooqCrudImpl<ProfileAttachmentDO, ProfileAttachmentRecord> {

    public ProfileAttachmentDao() {
        super(ProfileAttachment.PROFILE_ATTACHMENT, ProfileAttachmentDO.class);
    }



    public ProfileAttachmentDao(TableImpl<ProfileAttachmentRecord> table, Class<ProfileAttachmentDO> profileAttachmentDOClass) {
        super(table, profileAttachmentDOClass);
    }

    protected Attachment DBToStruct(ProfileAttachmentRecord r) {
        Attachment attachment = null;
        if (r != null) {
            attachment = new Attachment();
            if (r.getCreateTime() != null) {
                attachment.setCreate_time(DateUtils.dateToNormalDate(new Date(r.getCreateTime().getTime())));
            }
            if (r.getUpdateTime() != null) {
                attachment.setUpdate_time(DateUtils.dateToNormalDate(new Date(r.getUpdateTime().getTime())));
            }
            attachment.setDescription(r.getDescription());
            attachment.setId(r.getId().intValue());
            attachment.setName(r.getName());
            attachment.setPath(r.getPath());
            attachment.setProfile_id(r.getProfileId().intValue());
        }
        return attachment;
    }

    protected ProfileAttachmentRecord structToDB(Attachment attachment) throws ParseException {
        ProfileAttachmentRecord record = new ProfileAttachmentRecord();
        if (attachment.isSetId()) {
            record.setId((int)(attachment.getId()));
        }
        if (attachment.isSetName()) {
            record.setName(attachment.getName());
        }
        if (attachment.isSetPath()) {
            record.setPath(attachment.getPath());
        }
        if (attachment.isSetProfile_id()) {
            record.setProfileId((int)(attachment.getProfile_id()));
        }
        if (attachment.isSetDescription()) {
            record.setDescription(attachment.getDescription());
        }
        return record;
    }

    public int updateProfileUpdateTime(HashSet<Integer> attachmentIds) {
        int status = 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(create.select(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID)
                                    .from(ProfileAttachment.PROFILE_ATTACHMENT)
                                    .where(ProfileAttachment.PROFILE_ATTACHMENT.ID.in(attachmentIds))))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return status;
    }

    public int delAttachmentsByProfileId(int profileId) {
        int count= 0;
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
            count = create.delete(ProfileAttachment.PROFILE_ATTACHMENT)
                    .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.equal((int)(profileId)))
                    .execute();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return count;
    }
}
