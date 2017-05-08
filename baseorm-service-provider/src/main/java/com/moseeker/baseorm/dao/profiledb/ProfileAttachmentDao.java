package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
