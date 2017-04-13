package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;

/**
* @author xxx
* ProfileWorkexpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileWorkexpDao extends StructDaoImpl<ProfileWorkexpDO, ProfileWorkexpRecord, ProfileWorkexp> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileWorkexp.PROFILE_WORKEXP;
   }
}
