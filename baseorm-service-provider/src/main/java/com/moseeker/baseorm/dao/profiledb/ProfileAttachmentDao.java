package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileAttachment;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;

/**
* @author xxx
* ProfileAttachmentDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileAttachmentDao extends StructDaoImpl<ProfileAttachmentDO, ProfileAttachmentRecord, ProfileAttachment> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileAttachment.PROFILE_ATTACHMENT;
   }
}
