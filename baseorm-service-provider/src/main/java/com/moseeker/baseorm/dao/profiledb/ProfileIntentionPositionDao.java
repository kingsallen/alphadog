package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionPosition;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionPositionDO;

/**
* @author xxx
* ProfileIntentionPositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionPositionDao extends StructDaoImpl<ProfileIntentionPositionDO, ProfileIntentionPositionRecord, ProfileIntentionPosition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileIntentionPosition.PROFILE_INTENTION_POSITION;
   }
}
