package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

        int status = 0;

        List<Integer> profileIdList = create.select(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID)
                .from(ProfileAttachment.PROFILE_ATTACHMENT)
                .where(ProfileAttachment.PROFILE_ATTACHMENT.ID.in(attachmentIds))
                .stream()
                .map(integerRecord1 -> integerRecord1.value1())
                .collect(Collectors.toList());

        if (profileIdList != null && profileIdList.size() > 0) {
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(profileIdList))
                    .execute();
        }

        return status;
    }

    public int delAttachmentsByProfileId(int profileId) {

        int count = create.delete(ProfileAttachment.PROFILE_ATTACHMENT)
                .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.equal(profileId))
                .execute();

        return count;
    }

    public List<ProfileAttachmentDO> fetchAttachmentByIds(List<Integer> ids) {

        List<ProfileAttachmentDO> result = create.selectFrom(ProfileAttachment.PROFILE_ATTACHMENT)
                .where(ProfileAttachment.PROFILE_ATTACHMENT.ID.in(ids))
                .fetchInto(ProfileAttachmentDO.class);

        return result;
    }


    public ProfileAttachmentRecord fetchAttachmentById(Integer id) {

        ProfileAttachmentRecord result = create.selectFrom(ProfileAttachment.PROFILE_ATTACHMENT)
                .where(ProfileAttachment.PROFILE_ATTACHMENT.ID.eq(id))
                .fetchOne();

        return result;
    }

    public int updateAttachmentPathByName(String name, String path, String oldName){
        return create.update(ProfileAttachment.PROFILE_ATTACHMENT)
                .set(ProfileAttachment.PROFILE_ATTACHMENT.NAME,ProfileAttachment.PROFILE_ATTACHMENT.PATH)
                .where(ProfileAttachment.PROFILE_ATTACHMENT.NAME.eq(oldName))
                .execute();
    }

    public List<ProfileAttachmentRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileAttachment.PROFILE_ATTACHMENT)
                    .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.eq(profileId))
                    .fetch();
        } else {
            return new ArrayList<>(0);
        }
    }
}
