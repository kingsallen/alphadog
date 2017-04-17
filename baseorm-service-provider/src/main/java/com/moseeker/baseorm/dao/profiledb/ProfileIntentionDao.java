package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileIntention;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionDO;

/**
* @author xxx
* ProfileIntentionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionDao extends StructDaoImpl<ProfileIntentionDO, ProfileIntentionRecord, ProfileIntention> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileIntention.PROFILE_INTENTION;
   }
}
