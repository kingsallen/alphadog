package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionIndustry;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionIndustryDO;

/**
* @author xxx
* ProfileIntentionIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionIndustryDao extends StructDaoImpl<ProfileIntentionIndustryDO, ProfileIntentionIndustryRecord, ProfileIntentionIndustry> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY;
   }
}
