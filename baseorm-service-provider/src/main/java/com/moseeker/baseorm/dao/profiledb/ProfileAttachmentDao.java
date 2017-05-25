package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashSet;

/**
 * @author xxx
 *         ProfileAttachmentDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileAttachmentDao extends JooqCrudImpl<ProfileAttachmentDO, ProfileAttachmentRecord> {

    public ProfileAttachmentDao() {
        super(ProfileAttachment.PROFILE_ATTACHMENT, ProfileAttachmentDO.class);
    }


    public ProfileAttachmentDao(TableImpl<ProfileAttachmentRecord> table, Class<ProfileAttachmentDO> profileAttachmentDOClass) {
        super(table, profileAttachmentDOClass);
    }

    public int updateProfileUpdateTime(HashSet<Integer> attachmentIds) {
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        int status = create.update(ProfileProfile.PROFILE_PROFILE)
                .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                .where(ProfileProfile.PROFILE_PROFILE.ID
                        .in(create.select(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID)
                                .from(ProfileAttachment.PROFILE_ATTACHMENT)
                                .where(ProfileAttachment.PROFILE_ATTACHMENT.ID.in(attachmentIds))))
                .execute();

        return status;
    }

    public int delAttachmentsByProfileId(int profileId) {

        int count = create.delete(ProfileAttachment.PROFILE_ATTACHMENT)
                .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.equal(profileId))
                .execute();

        return count;
    }
}
