package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCompletenessDO;

/**
* @author xxx
* ProfileCompletenessDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileCompletenessDao extends StructDaoImpl<ProfileCompletenessDO, ProfileCompletenessRecord, ProfileCompleteness> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileCompleteness.PROFILE_COMPLETENESS;
   }
}
